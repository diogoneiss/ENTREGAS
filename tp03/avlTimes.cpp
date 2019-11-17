/**
 * @author Diogo Neiss, baseada na criada pelo professor Max.
 * arvore AVL de times
 */

#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <math.h>

/**
 * Structs do arquivo
 */
typedef struct No
{
	int elemento;   // Conteudo do no->
	struct No *esq; // No da esquerda.
	struct No *dir; // No da direita.
	int nivel;		//Numero de niveis abaixo do no
} No;

typedef struct AVL
{
	No *raiz; // Raiz da arvore.
} AVL;

No *construtorNo(int elemento);
No *setNivel(No *esse);
int getNivel(No *no);
AVL *construtorAVL();
int getAltura(AVL *arvore);
bool pesquisar(AVL *arvore, int elemento);
bool pesquisarPrivado(No *no, int x);
void mostrarCentral(AVL *arvore);
void mostrarCentral(No *no);
void mostrarPre(AVL *arvore);
void mostrarPre(No *no);
void inserir(AVL *arvore, int x);
No *inserir(No *no, int x);
No *balancear(No *no);
No *rotacionarDir(No *no);
No *rotacionarEsq(No *no);
void remover(AVL *arvore, int x);
No *remover(No *no, int x);
No *antecessor(No *n1, No *n2);
int maxDoisNumeros(int x, int y);

int main()
{
	AVL *arvoreAvl = construtorAVL();
	inserir(arvoreAvl, 17);
	inserir(arvoreAvl, 16);
	inserir(arvoreAvl, 15);
	inserir(arvoreAvl, 14);
	inserir(arvoreAvl, 13);
	inserir(arvoreAvl, 12);
	inserir(arvoreAvl, 11);

	mostrarPre(arvoreAvl);
}

/**
	 * Construtor da classe.
	 * @param elemento Conteudo do no->
	 */
No *construtorNo(int elemento)
{
	No *novoNo = (No *)malloc(sizeof(No));
	novoNo->elemento = elemento;
	novoNo->esq = NULL;
	novoNo->dir = NULL;
	novoNo->nivel = 1;

	return novoNo;
}

// Cálculo do número de níveis a partir de um vértice
No *setNivel(No *esse)
{
	esse->nivel = 1 + maxDoisNumeros(getNivel(esse->esq), getNivel(esse->dir));
	return esse;
}

// Retorna o número de níveis a partir de um vértice
int getNivel(No *no)
{
	return (no == NULL) ? 0 : no->nivel;
}

/**
	 * Construtor da classe.
	 */
AVL *construtorAVL()
{
	AVL *novaArvore = (AVL *)malloc(sizeof(AVL));
	novaArvore->raiz = NULL;
	return novaArvore;
}

int getAltura(AVL *arvore)
{
	return getNivel(arvore->raiz) - 1;
}

/**
	 * Metodo  iterativo para pesquisar elemento.
	 * @param elemento Elemento que sera procurado.
	 * @return <code>true</code> se o elemento existir,
	 * <code>false</code> em caso contrario.
	 */
bool pesquisar(AVL *arvore, int elemento)
{
	return pesquisarPrivado(arvore->raiz, elemento);
}

/**
	 * Metodo privado recursivo para pesquisar elemento.
	 * @param no *No em analise.
	 * @param x Elemento que sera procurado.
	 * @return <code>true</code> se o elemento existir,
	 * <code>false</code> em caso contrario.
	 */
bool pesquisarPrivado(No *no, int x)
{
	bool resp;
	if (no == NULL)
	{
		resp = false;
	}
	else if (x == no->elemento)
	{
		resp = true;
	}
	else if (x < no->elemento)
	{
		resp = pesquisarPrivado(no->esq, x);
	}
	else
	{
		resp = pesquisarPrivado(no->dir, x);
	}
	return resp;
}

/**
	 * Metodo  iterativo para exibir elementos.
	 */
void mostrarCentral(AVL *arvore)
{
	printf("[");
	mostrarCentral(arvore->raiz);
	printf(" ]\n");
}

/**
	 * Metodo privado recursivo para exibir elementos.
	 * @param no *No em analise.
	 */
void mostrarCentral(No *no)
{
	if (no != NULL)
	{
		mostrarCentral(no->esq);	 // Elementos da esquerda.
		printf("%d ", no->elemento); // Conteudo do no->
		mostrarCentral(no->dir);	 // Elementos da direita.
	}
}

/**
	 * Metodo  iterativo para exibir elementos.
	 */
void mostrarPre(AVL *arvore)
{
	printf("[ ");
	mostrarPre(arvore->raiz);
	printf("]\n");
}

/**
	 * Metodo privado recursivo para exibir elementos.
	 * @param no *No em analise.
	 */
void mostrarPre(No *no)
{
	if (no != NULL)
	{
		printf("%d (f %d) ", no->elemento, (getNivel(no->dir) - getNivel(no->esq))); // Conteudo do no->
		mostrarPre(no->esq);															  // Elementos da esquerda.
		mostrarPre(no->dir);															  // Elementos da direita.
	}
}

/**
	 * Metodo  iterativo para inserir elemento.
	 * @param x Elemento a ser inserido.
	 * @ Se o elemento existir.
	 */
void inserir(AVL *arvore, int x)
{
	arvore->raiz = inserir(arvore->raiz, x);
}

/**
	 * Metodo privado recursivo para inserir elemento.
	 * @param no *No em analise.
	 * @param x Elemento a ser inserido.
	 * @return No em analise, alterado ou nao.
	 * @ Se o elemento existir.
	 */
No *inserir(No *no, int x)
{
	if (no == NULL)
	{
		printf("Inserindo o %d\n", x);
		no = construtorNo(x);
	}
	else if (x < no->elemento)
	{
		printf("->esq ");
		no->esq = inserir(no->esq, x);
	}
	else if (x > no->elemento)
	{
		printf("->dir ");
		no->dir = inserir(no->dir, x);
	}
	else
	{
		printf("Erro ao inserir elemento (%d)!", x);
	}

	no = balancear(no);
	return no;
}

No *balancear(No *no)
{
	if (no != NULL)
	{
		int fator = getNivel(no->dir) - getNivel(no->esq);

		//Se balanceada
		if (abs(fator) <= 1)
		{
			no = setNivel(no);

			//Se desbalanceada para a direita
		}
		else if (fator == 2)
		{

			int fatorFilhoDir = getNivel(no->dir->dir) - getNivel(no->dir->esq);

			//Se o filho a direita tambem estiver desbalanceado
			if (fatorFilhoDir == -1)
			{
				no->dir = rotacionarDir(no->dir);
			}
			no = rotacionarEsq(no);

			//Se desbalanceada para a esquerda
		}
		else if (fator == -2)
		{

			int fatorFilhoEsq = getNivel(no->esq->dir) - getNivel(no->esq->esq);

			//Se o filho a esquerda tambem estiver desbalanceado
			if (fatorFilhoEsq == 1)
			{
				no->esq = rotacionarEsq(no->esq);
			}
			no = rotacionarDir(no);
		}
		else
		{
			printf("Erro fator de balanceamento (%d) invalido!", fator);
		}
	}

	return no;
}

No *rotacionarDir(No *no)
{
	printf("Rotacionar DIR(%d)\n", no->elemento);
	No *noEsq = no->esq;
	No *noEsqDir = noEsq->dir;

	noEsq->dir = no;
	no->esq = noEsqDir;

	no = setNivel(no);
	noEsq = setNivel(noEsq);

	return noEsq;
}

No *rotacionarEsq(No *no)
{
	printf("Rotacionar ESQ(%d)", no->elemento);
	No *noDir = no->dir;
	No *noDirEsq = noDir->esq;

	noDir->esq = no;
	no->dir = noDirEsq;

	no = setNivel(no);
	noDir = setNivel(noDir);
	return noDir;
}

/**
	 * Metodo  iterativo para remover elemento.
	 * @param elemento Elemento a ser removido.
	 * @ Se nao encontrar elemento.
	 */
void remover(AVL *arvore, int x)
{
	arvore->raiz = remover(arvore->raiz, x);
}

/**
	 * Metodo privado recursivo para remover elemento.
	 * @param no *No em analise.
	 * @param x Elemento a ser removido.
	 * @return No em analise, alterado ou nao.
	 * @ Se nao encontrar elemento.
	 */
No *remover(No *no, int x)
{

	if (no == NULL)
	{
		printf("Erro ao remover!");
	}
	else if (x < no->elemento)
	{
		no->esq = remover(no->esq, x);
	}
	else if (x > no->elemento)
	{
		no->dir = remover(no->dir, x);

		// Sem no a direita.
	}
	else if (no->dir == NULL)
	{
		no = no->esq;

		// Sem no a esquerda.
	}
	else if (no->esq == NULL)
	{
		no = no->dir;

		// No a esquerda e no a direita.
	}
	else
	{
		no->esq = antecessor(no, no->esq);
	}

	no = balancear(no);
	return no;
}

/**
	 * Metodo para trocar no removido pelo antecessor.
	 * @param n1 No que teve o elemento removido.
	 * @param n2 No da subarvore esquerda.
	 * @return No em analise, alterado ou nao.
	 */
No *antecessor(No *n1, No *n2)
{

	// Existe no a direita.
	if (n2->dir != NULL)
	{
		// Caminha para direita.
		n2->dir = antecessor(n1, n2->dir);

		// Encontrou o maximo da subarvore esquerda.
	}
	else
	{
		n1->elemento = n2->elemento; // Substitui N1 por N2.
		n2 = n2->esq;				 // Substitui N2 por N2->ESQ.
	}
	return n2;
}

int maxDoisNumeros(int x, int y)
{
	return x > y ? x : y;
}