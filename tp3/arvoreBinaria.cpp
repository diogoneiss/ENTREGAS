#include <iostream>


/*
	Desabilita todas os printfs, que só servem pra debug no código. O define substitui todos antes da compilacao e substitui pela versao comentada.
	Para mostrar as flags de debug, basta retirar o // antes de printf
*/
#define printf printf

class No {

	public:
     int elemento; // Conteudo do no.
	 No *esq, *dir;  // Filhos da esq e dir.

	/**
	 * Construtor da classe.
	 * @param elemento Conteudo do no.
	 */
	 No(int elemento) {
		No(elemento, NULL, NULL);
	}

	/**
	 * Construtor da classe.
	 * @param elemento Conteudo do no.
	 * @param esq No da esquerda.
	 * @param dir No da direita.
	 */
	No(int elemento, No *esq, No *dir) {
		this->elemento = elemento;
		printf("Elemento no nó, dentro do construtor: %d\n", this->elemento);
		this->esq = esq;
		this->dir = dir;
	}
    No(){
		printf("No vazio criado! Cuidado.\n");
    }
};

/**
 * Arvore binaria de pesquisa
 * @author Max do Val Machado
 */
class ArvoreBinaria {
	private:
     No *raiz; // Raiz da arvore.

	/**
	 * Construtor da classe.
	 */
	public:
    ArvoreBinaria() {
		raiz = NULL;
    }

	/**
	 * Metodo publico iterativo para pesquisar elemento.
	 * @param x Elemento que sera procurado.
	 * @return <code>true</code> se o elemento existir,
	 * <code>false</code> em caso contrario.
	 */
	public: bool pesquisar(int x) {
		return pesquisar(x, raiz);
	}

	/**
	 * Metodo privado recursivo para pesquisar elemento.
	 * @param x Elemento que sera procurado.
	 * @param i No em analise.
	 * @return <code>true</code> se o elemento existir,
	 * <code>false</code> em caso contrario.
	 */
	private: bool pesquisar(int x, No *i) {
      bool resp;
		if (i == NULL) {
         resp = false;

      } else if (x == i->elemento) {
         resp = true;

      } else if (x < i->elemento) {
         resp = pesquisar(x, i->esq);

      } else {
         resp = pesquisar(x, i->dir);
      }
      return resp;
	}

	/**
	 * Metodo publico iterativo para exibir elementos.
	 */
	 public: void mostrarCentral() {
		printf("[ ");
		mostrarCentral(raiz);
		printf("]\n");
	}

	/**
	 * Metodo privado recursivo para exibir elementos.
	 * @param i No em analise.
	 */
	 private: void mostrarCentral(No* i) {
		if (i != NULL) {
			mostrarCentral(i->esq); // Elementos da esquerda.
			printf(" %d", i->elemento); // Conteudo do no.
			mostrarCentral(i->dir); // Elementos da direita.
		}
	}

	/**
	 * Metodo publico iterativo para exibir elementos.
	 */
	public: void mostrarPre() {
		std::cout << "[ ";
		mostrarPre(raiz);
		std::cout << "]" << std::endl;
	}

	/**
	 * Metodo privado recursivo para exibir elementos.
	 * @param i No em analise.
	 */
	private:
	 void mostrarPre(No *i) {
		if (i != NULL) {
			std::cout << " " << i->elemento; // Conteudo do no.
			mostrarPre(i->esq); // Elementos da esquerda.
			mostrarPre(i->dir); // Elementos da direita.
		}
	}

	/**
	 * Metodo publico iterativo para exibir elementos.
	 */
	public:
	 void mostrarPos() {
		std::cout << "[ ";
		mostrarPos(raiz);
		std::cout << "]" << std::endl;
	}

	/**
	 * Metodo privado recursivo para exibir elementos.
	 * @param i No em analise.
	 */
	private:
	 void mostrarPos(No *i) {
		if (i != NULL) {
			mostrarPos(i->esq); // Elementos da esquerda.
			mostrarPos(i->dir); // Elementos da direita.
			std::cout << " " << i->elemento; // Conteudo do no.
		}
	}


	/**
	 * Metodo publico iterativo para inserir elemento.
	 * @param x Elemento a ser inserido.
	 * @throws Exception Se o elemento já existir.
	 */
	public:
	 void inserir(int x){
		raiz = inserir(x, raiz);
	}
	
	/**
	 * Metodo privado recursivo para inserir elemento.
	 * @param x Elemento a ser inserido.
	 * @param i No em analise.
	 * @return No em analise, alterado ou nao.
	 * @throws Exception Se o elemento existir.
	 */
	private:
	 No* inserir(int x, No *i){

		if (i == NULL) {
			//printf("Elemento recebido: %d.\n", x);
         	i = new No(x);
			 // PROBLEMA NA LINHA ACIMA
		 	printf("Elemento alocado dentro da função inserir no Nó: %d\n", i->elemento);
		}
		 else if (x < i->elemento) {
         	i->esq = inserir(x, i->esq);
		} 
	     else if (x > i->elemento) {
         	i->dir = inserir(x, i->dir);
	    }
		else {
        	printf("Erro ao inserir!");
		exit(1);
	  	}

		return i;
	}

	/**
	 * Metodo publico iterativo para remover elemento.
	 * @param x Elemento a ser removido.
	 * @throws Exception Se nao encontrar elemento.
	 */
	public:
	void remover(int x){
		raiz = remover(x, raiz);
	}

	
	/**
	 * Metodo privado recursivo para remover elemento.
	 * @param x Elemento a ser removido.
	 * @param i No em analise.
	 * @return No em analise, alterado ou nao.
	 * @throws Exception Se nao encontrar elemento.
	 */

	private:
	No* remover(int x, No *i)  {

		if (i == NULL) {
         std::cout << "Erro ao remover!" << std::endl;

      } else if (x < i->elemento) {
         i->esq = remover(x, i->esq);

      } else if (x > i->elemento) {
         i->dir = remover(x, i->dir);

      // Sem no a direita.
      } else if (i->dir == NULL) {
         i = i->esq;

      // Sem no a esquerda.
      } else if (i->esq == NULL) {
         i = i->dir;

      // No a esquerda e no a direita.
      } else {
         i->esq = antecessor(i, i->esq);
		}

		return i;
	}

	/**
	 * Metodo para trocar no removido pelo antecessor.
	 * @param i No que teve o elemento removido.
	 * @param j No da subarvore esquerda.
	 * @return No em analise, alterado ou nao.
	 */
	No* antecessor(No *i, No *j) {

      // Existe no a direita.
		if (j->dir != NULL) {
         // Caminha para direita.
			j->dir = antecessor(i, j->dir);

      // Encontrou o maximo da subarvore esquerda.
		} else {
			i->elemento = j->elemento; // Substitui i por j.
			j = j->esq; // Substitui j por j.ESQ.
		}
		return j;
	}
};
    int main() {
      ArvoreBinaria *arvoreBinaria = new ArvoreBinaria();

      arvoreBinaria->inserir(3);
      arvoreBinaria->inserir(5);
      arvoreBinaria->inserir(1);
      arvoreBinaria->inserir(8);
      arvoreBinaria->inserir(2);
      arvoreBinaria->inserir(4);
      arvoreBinaria->inserir(7);
      arvoreBinaria->inserir(6);

      arvoreBinaria->mostrarCentral();
      arvoreBinaria->mostrarPre();
      arvoreBinaria->mostrarPos();

      arvoreBinaria->remover(6);
      arvoreBinaria->remover(2);
      arvoreBinaria->remover(4);

      arvoreBinaria->mostrarCentral();
      arvoreBinaria->mostrarPre();
      arvoreBinaria->mostrarPos();
   }
