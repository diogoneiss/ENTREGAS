/**
 * @author Diogo Neiss, baseada na criada pelo professor Max.
 * arvore AVL de times
 * 
 * A ordenação da árvore é baseada no atributo nome do time.
 */
#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>

#define TAM_LINHA 8000
#define TAM_ATRIBUTO 500
#define TAM_DATA 150

/**
 * Structs do programa
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
//tamanho em chars das linhas


typedef struct ClasseTime{
    char nome[TAM_ATRIBUTO];
    char apelido[TAM_ATRIBUTO];
    char estadio[TAM_ATRIBUTO];
    char tecnico[TAM_ATRIBUTO];
    char liga[TAM_ATRIBUTO];
    char nomeArquivo[TAM_ATRIBUTO];

    char datas[TAM_ATRIBUTO];

    int capacidade;
    int fundacaoDia;
    int fundacaoMes;
    int fundacaoAno;
    long paginaTam;

    void imprimir(){

        printf("%s", nome);
        printf(" ## %s", apelido);
        printf(" ## %02d/%02d/%04d",fundacaoDia, fundacaoMes, fundacaoAno);
        //printf(" ## %s", datas);
        printf(" ## %s", estadio);
        printf(" ## %d", capacidade);
        printf(" ## %s", tecnico);
        printf(" ## %s", liga);
        printf(" ## %s", nomeArquivo);
        printf(" ## %ld ## \n", paginaTam);

    }

}Time;


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
char* lerEntreAspas(char linhaCompleta[], int inicio);
char* lerEntreAspasAteTD(char linhaCompleta[], int inicio);
char* lerAPartirDaClasse(char linhaCompleta[]);
char* pegarNome(char linhaCompleta[]);
char* pegarApelido(char linhaCompleta[]);
char* pegarEstadio(char linhaCompleta[]);
char* pegarLiga(char linhaCompleta[]);
char* pegarTecnico(char linhaCompleta[]);
char* pegarManager(char linhaCompleta[]);
char* pegarData(char linhaCompleta[]);
int* filtrarData(char linhaOriginal[]);
int* splitarData(char linha[]);
int pegarCapacidade(char linhaCompleta[]);
long pegarTamanhoPag(char arquivo[]);
char* lerArquivo(char endereco[]);
char *replaceWord(const char *s, const char *oldW, const char *newW);
char* replace( char retirar[],  char linha[]);
char* substring(char frase[], int pos, int fim);
char* substring(char frase[], int pos);
char* substring (char padrao[], char entrada[]);
char* filtrarString(char arquivo[]);
Time* construtor(char arquivo[]);
bool ehFim(char entrada[]);


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

	//arranjo de nomes de arquivos.
    char entradaTimes[100][100];
    int quantidadeEntradas = -1;

    do{
        quantidadeEntradas++;
        //ler até encontrar o \n
        scanf("%s", entradaTimes[quantidadeEntradas]);

        //TIRAR O \n
        entradaTimes[quantidadeEntradas][strlen(entradaTimes[quantidadeEntradas])] = '\0';

    }while(!ehFim(entradaTimes[quantidadeEntradas]));
    // arranjo de referencias
    Time* conjuntoTimes[quantidadeEntradas];


  

    //criar os objetos para cada entrada e inserir na fila
    for (int i = 0; i < quantidadeEntradas; i++){
        // alocar memoria, atribuir atributos com a funcao construtor e imprimir
        conjuntoTimes[i] = construtor(entradaTimes[i]);
        //ListaDuplaDinamicaComOsTimes->inserirFim(conjuntoTimes[i]);
    }

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
/**
 INICIO DOS MÉTODOS MANIPULADORES DE "TIME"
 */

//a funcao vai pegar o que tiver dentro da primeira aspas, ler e retornar, desconsidersando tags;
char* lerEntreAspas(char linhaCompleta[], int inicio){
    bool tagAchada = false;
    bool leituraParenteses = false;

    int contadorPosicao = 0;
    char fraseFiltrada[TAM_LINHA];

    for (int i = inicio; i < strlen(linhaCompleta); i++)
    {
        //significa que achou uma tag e está no meio da leitura dela
        if(!leituraParenteses && !tagAchada && linhaCompleta[i] == '<')
            tagAchada = true;

            //siginifica que acabou a tag
        else if(!leituraParenteses && tagAchada && linhaCompleta[i] == '>')
            tagAchada = false;

            //leitura apos
        else if(!tagAchada){
            //sair se achar o <
            if(linhaCompleta[i] == '<'){
                //printf("Fim da string aqui! %c", linhaCompleta[i]);
                fraseFiltrada[contadorPosicao] = '\0';
                i = strlen(linhaCompleta);
            }

            else if (linhaCompleta[i+1] != '<'){
                fraseFiltrada[contadorPosicao] = linhaCompleta[i];
                contadorPosicao++;
                leituraParenteses = true;
            }
        }
    }
    return strdup(fraseFiltrada);

}

char* lerEntreAspasAteTD(char linhaCompleta[], int inicio){
    bool tagAchada = false;
    bool leituraParenteses = false;

    int contadorPosicao = 0;
    char *fraseFiltrada = (char*) malloc(sizeof(char)*TAM_LINHA);

    for (int i = inicio; i < strlen(linhaCompleta)-3; i++)
    {
        if(linhaCompleta[i] == '<' && linhaCompleta[i+1] == '/' && linhaCompleta[i+2] == 't' && linhaCompleta[i+3] == 'd'){
            //printf("Fim da string aqui! %c", linhaCompleta[i]);
            fraseFiltrada[contadorPosicao] = '\0';
            i = strlen(linhaCompleta);
        }
            //significa que achou uma tag e está no meio da leitura dela
        else if(!tagAchada && linhaCompleta[i] == '<')
            tagAchada = true;
            //siginifica que acabou a tag
        else if(tagAchada && linhaCompleta[i] == '>')
            tagAchada = false;
            //leitura apos
        else if(!tagAchada){
            //sair se achar o <
            fraseFiltrada[contadorPosicao] = linhaCompleta[i];
            contadorPosicao++;
        }
    }
    return fraseFiltrada;

}

char* lerAPartirDaClasse(char linhaCompleta[]){
    //vai receber tipo class="nickname" e deve seguir em frente com isso.
    // ela vai estar começando dentro de uma tag e precisa sair dela primeiro.
    int fimTag = 0;
    bool achouTag = false;

    // printf("\nTag recebida: %s \n", linhaCompleta);

    //buscar o fim da tag
    for(int i = 0; i < strlen(linhaCompleta); i++){
        //ignorar se achar um <i> no meio
        if(linhaCompleta[i] == '<')
            achouTag = true;
        else if(achouTag && linhaCompleta[i] == '>')
            achouTag = false;

        else if(!achouTag && linhaCompleta[i] == '>'){
            fimTag = i+1;
            i = strlen(linhaCompleta);
        }
    }
    //arranjo com a resposta com memset
    char *resposta = (char*)malloc(TAM_LINHA);
    memset(resposta, '\0', TAM_LINHA);

    int contadorPos = 0;

    // ler entre o > </td, ignorando tags entre
    for (int i = fimTag; i < strlen(linhaCompleta)-3; i++)
    {
        if(linhaCompleta[i] == '<' && linhaCompleta[i+1] == '/' && linhaCompleta[i+2] == 't' && linhaCompleta[i+3] == 'd')

            i = strlen(linhaCompleta);

        else if(linhaCompleta[i] == '<')
            achouTag = true;

        else if(achouTag && linhaCompleta[i] == '>')
            achouTag = false;

        else if(!achouTag){
            //adicionar o char dentro do outro arranjo
            resposta[contadorPos] = linhaCompleta[i];
            contadorPos++;
        }

    }

    return (resposta);
}

char* pegarNome(char linhaCompleta[]){

    char busca[] = "Full name";
    char* resposta  = (char*) malloc(TAM_ATRIBUTO);
    memset(resposta, '\0', TAM_ATRIBUTO);
    char* tmp = NULL;
    tmp = lerEntreAspasAteTD(substring(busca, linhaCompleta), strlen(busca));

    strcpy(resposta, tmp);
    delete(tmp);
    //printf("\n Resposta eh %s\n", resposta);
    //printf("\nCheguei e sai da funcao PegarNome com sucesso.\n");

    return (resposta);
}

char* pegarApelido(char linhaCompleta[]){
    char busca[] = "nickname";
    char* resposta  = (char*) malloc(TAM_ATRIBUTO);
    memset(resposta, '\0', TAM_ATRIBUTO);
    char* tmp = NULL;
    tmp = lerAPartirDaClasse(substring(busca, linhaCompleta));

    strcpy(resposta, tmp);
    delete(tmp);
    //printf("\n Resposta eh %s\n", resposta);
    //printf("\nCheguei e sai da funcao pegarApelido com sucesso.\n");
    return (resposta);
}

char* pegarEstadio(char linhaCompleta[]){
    char busca[] = "Ground";
    char* resposta  = (char*) malloc(TAM_ATRIBUTO);
    memset(resposta, '\0', TAM_ATRIBUTO);
    char* tmp = NULL;
    tmp = lerEntreAspasAteTD(substring(busca, linhaCompleta), strlen(busca));

    strcpy(resposta, tmp);
    delete(tmp);

    //printf("\n Resposta eh %s\n", resposta);
    //printf("\nCheguei e sai da funcao pegarEstadio com sucesso.\n");
    return (resposta);

}

char* pegarLiga(char linhaCompleta[]){
    char busca[] = "League";
    char* resposta = (char*)malloc(TAM_ATRIBUTO);
    memset(resposta, '\0', TAM_ATRIBUTO);

    char* tmp = NULL;
    tmp = lerEntreAspasAteTD(substring(busca, linhaCompleta), strlen(busca));
    strcpy(resposta, tmp );
    delete(tmp);
    //printf("\nCheguei e sai da funcao pegarLiga com sucesso.\n");
    return (resposta);
}

char* pegarTecnico(char linhaCompleta[]){
    //printf("\nCheguei na funcao de pegar tecnico. \n");
    char* resposta = (char*)malloc(TAM_ATRIBUTO);
    char busca[] = "Coach";
    char busca2[]= "Head coach";
    char* tmp = NULL;

    // procura o head coach
    if(strstr(linhaCompleta, (char*) busca2) != 0){
        tmp = lerEntreAspas(substring(busca2, linhaCompleta), strlen(busca2));
        strcpy(resposta , tmp);
        delete(tmp);
    }

        //verificar se nao tem tecnico mesmo
    else if(strstr(linhaCompleta, (char*) busca) == 0){
        // se nao tiver Coach procurar por manager
        tmp = pegarManager(linhaCompleta);
        strcpy(resposta, tmp);
        delete(tmp);
    }

    else{
        tmp = lerEntreAspas(substring(busca, linhaCompleta), strlen(busca));
        strcpy(resposta , tmp);
        delete(tmp);
    }

    //printf("\nCheguei e sai da funcao PegarCoach com sucesso. saida: %s\n", resposta);
    return (resposta);
}

char* pegarManager(char linhaCompleta[]){
    char busca[] = "Manager";
    char* resp = (char*) malloc(TAM_ATRIBUTO);
    char* tmp = NULL;
    memset(resp, '\0', TAM_ATRIBUTO);
    tmp = lerEntreAspas(substring(busca, linhaCompleta), strlen(busca));
    //printf("Manager: %s\n", tmp);
    strcpy(resp, tmp );
    delete(tmp);

    return (resp);
}

char* pegarData(char linhaCompleta[]){
    char busca[] = "bday";
    char busca2[] = "Founded";
    char* tmp = NULL;
    char* resposta = (char*)malloc(TAM_DATA);
    memset(resposta, '\0', TAM_DATA);

    //ver se tem bday. Se nao tiver procura Founded
    if(strstr(linhaCompleta, (char*)busca) != 0){
        tmp =  lerAPartirDaClasse(substring(busca, linhaCompleta));
        strcpy(resposta ,tmp);
        delete(tmp);
    }
    else{
        tmp =  lerEntreAspasAteTD(substring(busca2, linhaCompleta), strlen(busca2));
        strcpy(resposta ,tmp);
        delete(tmp);
    }

    //printf("\nCheguei e sai da funcao pegarData com sucesso.\n");
    int tam = strlen(resposta);
    // agora que tenho a resposta, preciso filtrá-la. O ideal é parar depois que achar algum char que não seja letra, dia ou traço
    char *respostaFiltrada = (char*) malloc(sizeof(char)*tam+1);
    //printf("\nResposta nao filtrada: %s\n", resposta);

    for(int i = 0; i < strlen(resposta); i++){
        bool ehLetra = (resposta[i] >= 'a' && resposta[i] <= 'z') || (resposta[i] >= 'A' && resposta[i] <= 'Z');
        bool ehNum = resposta[i] >= '0' && resposta[i] <= '9';

        bool continuar = ehLetra || ehNum || resposta[i] == '-' || resposta[i] == ' ' || resposta[i] == ',';

        if(!continuar){
            //printf("\nChar de parada: %c\n", resposta[i]);
            respostaFiltrada[i] = '\0';
            i = strlen(resposta);
        }else
            respostaFiltrada[i] = resposta[i];
    }
    //printf("\nResposta filtrada bugadona: %s\n", respostaFiltrada);
    delete(resposta);
    //printf("Resposta filtrada: %s \n", respostaFiltrada);
    return (respostaFiltrada);
}

int* filtrarData(char linhaOriginal[]){
    char* stringSemiLimpa = (char*) malloc(strlen(linhaOriginal));
    memset(stringSemiLimpa, '\0', strlen(linhaOriginal));
    strcpy(stringSemiLimpa, linhaOriginal);

    int* datas = (int*)malloc(sizeof(int)*3);

    if(strstr(linhaOriginal, (char*) "January") != 0){
        stringSemiLimpa =  replaceWord(stringSemiLimpa, (char*) "January", (char*) "-01-");

    }
    else if(strstr(linhaOriginal, (char*) "February") != 0){
        stringSemiLimpa =  replaceWord(stringSemiLimpa, (char*) "February", (char*) "-02-");

    }
    else if(strstr(linhaOriginal, (char*) "March") != 0){
        stringSemiLimpa =  replaceWord(stringSemiLimpa, (char*) "March", (char*) "-03-");

    }
    else if(strstr(linhaOriginal, (char*) "April") != 0){
        stringSemiLimpa =  replaceWord(stringSemiLimpa, (char*) "April", (char*) "-04-");

    }
    else if(strstr(linhaOriginal, (char*) "May") != 0){
        stringSemiLimpa =  replaceWord(stringSemiLimpa, (char*) "May", (char*) "-05-");

    }
    else if(strstr(linhaOriginal, (char*) "June") != 0){
        stringSemiLimpa =  replaceWord(stringSemiLimpa, (char*) "June", (char*) "-06-");

    }
    else if(strstr(linhaOriginal, (char*) "July") != 0){
        stringSemiLimpa =  replaceWord(stringSemiLimpa, (char*) "July", (char*) "-07-");

    }
    else if(strstr(linhaOriginal, (char*) "August") != 0){
        stringSemiLimpa =  replaceWord(stringSemiLimpa, (char*) "August", (char*) "-08-");

    }
    else if(strstr(linhaOriginal, (char*) "September") != 0){
        stringSemiLimpa =  replaceWord(stringSemiLimpa, (char*) "September", (char*) "09");

    }
    else if(strstr(linhaOriginal, (char*) "October") != 0){
        stringSemiLimpa =  replaceWord(stringSemiLimpa, (char*) "September", (char*) "-10-");

    }
    else if(strstr(linhaOriginal, (char*) "November") != 0){
        stringSemiLimpa =  replaceWord(stringSemiLimpa, (char*) "November", (char*) "-11-");

    }
    else if(strstr(linhaOriginal, (char*) "December") != 0){
        stringSemiLimpa =  replaceWord(stringSemiLimpa, (char*) "December", (char*) "-12-");

    }
    //printf("String semi limpa com substituicoes: %s \n", stringSemiLimpa);
    datas = splitarData(stringSemiLimpa);
    delete(stringSemiLimpa);
    return datas;
}

int* splitarData(char linha[]){
    int* datas = (int*)malloc(sizeof(int)*3);

    if(strlen(linha)> 4){
        //printf("\nString antes do split: %s\n", linha);
        char* frase = strdup(linha);


        char* p = strtok(frase, "- ");
        int i = 0;
        while (p != NULL && i < 4){
            //printf("\nDatas %d = %s\n", i , p);
            datas[i] = atoi(p);
            i++;
            p = strtok (NULL, "- ");
        }
        delete(frase);
    }
        //se for apenas ano
    else{
        datas[2] = atoi(linha);
        datas[1] = 0;
        datas[0] = 0;
    }
    //verificar inversoes
    if(datas[0] > 31){
        int aux = datas[0];
        datas[0] = datas[2];
        datas[2] = aux;
    }

    return datas;
}

int pegarCapacidade(char linhaCompleta[]){
    //printf("\nCheguei em capacidade.\n");
    char busca[] = "Capacity";
    char* resposta = (char*) malloc(TAM_ATRIBUTO);
    memset(resposta, '\0', sizeof(resposta));

    strcpy(resposta, lerEntreAspas(substring(busca, linhaCompleta), strlen(busca) ));

    //preciso dar replace em virgulas e etcs antes de fazer o atoi()

    char* aux = (char*) malloc(50);

    if(strstr(resposta, ",") != 0)
        strcpy(aux, replace((char*) ",", resposta));

    else if(strstr(resposta, ".") != 0)
        strcpy(aux, replace((char*)".", resposta));

    //printf("\nCapacidade em string: %s\n", aux);

    //converte string pra int
    //printf("\nCheguei e sai da funcao PegarCapacidade com sucesso.\n");
    delete(resposta);
    int capacidade = atoi(aux);
    delete(aux);
    return capacidade;
}

long pegarTamanhoPag(char arquivo[]){
    //printf("Arquivo atual: %s\n", arquivo);
    FILE *f = fopen(arquivo, "rb");

    fseek(f, 0, SEEK_END); // seek to end of file
    long size = ftell(f); // get current file pointer
    //printf("Li o tamanho. Tamanho: %ld\n", size);


    fseek(f, 0, SEEK_SET); // seek back to beginning of file
    fclose(f);
    //delete(f);
    //printf("\nCheguei e sai da funcao pegarTamanhoPag com sucesso.\n");
    return size;
}

char* lerArquivo(char endereco[]){
    char linha[10000];
    FILE *arquivo = fopen(endereco, "rb");
    size_t len = 0;
    char* aux = (char*)malloc(10000);
    memset(aux, '\0', sizeof(aux));

    if(arquivo == NULL){
        printf("Error! File is empty");
        exit(1);
    }
    else{
        fgets(linha, 10000, arquivo);
        linha[strlen(linha)-1] = ' ';
        //ler enquanto nao for eof e nao tiver "vcard"
        while(!feof(arquivo) && strstr(linha, "vcard") == 0){
            fgets(linha, 10000, arquivo);
            linha[strlen(linha)-1] = ' ';
        }
    }
    //jogar linha em aux
    strcpy(aux, linha);
    //se estiver com quebra de linha

    while(!feof(arquivo) && strstr(aux, "<table style") == 0){
        fgets(linha, 10000, arquivo);
        linha[strlen(linha)-1] = ' ';
        //linha[strlen(linha)] = ' ';
        strcat(aux, linha);
    }

    // delete(linha);
    fclose(arquivo);
    return (aux);

}
// Function to replace a string with another
// string
char *replaceWord(const char *s, const char *oldW, const char *newW) {
    char *result;
    int i, cnt = 0;
    int newWlen = strlen(newW);
    int oldWlen = strlen(oldW);

    // Counting the number of times old word
    // occur in the string
    for (i = 0; s[i] != '\0'; i++)
    {
        if (strstr(&s[i], oldW) == &s[i])
        {
            cnt++;

            // Jumping to index after the old word.
            i += oldWlen - 1;
        }
    }

    // Making new string of enough length
    //result = (char *)malloc(i + cnt * (newWlen - oldWlen) + 1);
    result = (char *)malloc(TAM_LINHA);

    i = 0;
    while (*s)
    {
        // compare the substring with the result
        if (strstr(s, oldW) == s)
        {
            strcpy(&result[i], newW);
            i += newWlen;
            s += oldWlen;
        }
        else
            result[i++] = *s++;
    }

    result[i] = '\0';
    return result;
}

//remover todas as ocorrencias de uma string dentro de outra
char* replace( char retirar[],  char linha[]){
    //char* novaFrase = (char*) malloc(strlen(linha)+1);


    char *novaFrase = (char*) malloc(strlen(linha)+1);
    memset(novaFrase, '\0', strlen(linha)+1);

    int contadorPosicaoFraseFinal = 0;
    int j;

    bool replaceEncontrado = true;

    for (int i = 0; i < strlen(linha); i++){
        // se o caracter i é igual ao zerissimo char de retirar
        if(linha[i] == retirar[0]){

            // o for serve pra verificar se são diferentes
            for(j = 1; j < strlen(retirar); j++){
                if(linha[i+j] != retirar[j]){
                    replaceEncontrado = false;
                    j = strlen(retirar);
                }
            }
            //pular x caracteres
            if(replaceEncontrado)
                i += strlen(retirar)-1;
                //resetar o bool
            else
                replaceEncontrado = true;
        }
        else{
            replaceEncontrado = true;
            novaFrase[contadorPosicaoFraseFinal] = linha[i];
            contadorPosicaoFraseFinal++;
        }
    }

    return (novaFrase);
}

char* substring(char frase[], int pos, int fim){
    char* novaFrase = (char*)malloc(strlen(frase)-pos +1);
    memset(novaFrase, '\0', strlen(frase) - pos +1);

    int posReal = 0;

    for (int i = pos; i <= fim; i++)
    {
        novaFrase[posReal] = frase[i];
        posReal++;
    }
    novaFrase[posReal+1] = '\0';
    return novaFrase;
}

char* substring(char frase[], int pos){
    char* novaFrase = (char*)malloc(strlen(frase)-pos +1);
    memset(novaFrase, '\0', strlen(frase) - pos +1);

    int posReal = 0;

    for (int i = pos; i < strlen(frase); i++)
    {
        novaFrase[posReal] = frase[i];
        posReal++;
    }
    novaFrase[posReal+1] = '\0';
    return novaFrase;
}

char* substring (char padrao[], char entrada[]){
    char* pointer = NULL;

    if(strstr(entrada, padrao) != NULL)
        pointer = strstr(entrada, padrao);
    else
        printf("\nALERTA! PONTEIRO NULO No padrão de ENTRADA %s\n", padrao);

    //printf("strdupp=======  %s \n", pointer);

    return pointer;
}
/*
Metodo recebe o nome do arquivo, le ele, retira a string e a filtra, retornando uma duplicata da string limpa
*/
char* filtrarString(char arquivo[]){

    char* linhaOriginal = (char*) malloc(TAM_LINHA);
    memset(linhaOriginal, '\0', TAM_LINHA);

    // guardar o conteudo da linha do arquivo
    strcpy(linhaOriginal, lerArquivo(arquivo));

    // substituicoes
    char* subst = (char*) "";
    char* troca1 = (char*) "&#91;note 1&#93;";
    char* troca2 = (char*) "note 1&#93;";
    char* troca3 = (char*) "1&#93";
    char* troca4 = (char*) "&#160;";
    char* troca5 = (char*) "&amp;";
    char* troca6 = (char*) "&#91;";
    char* troca7 = (char*) "2&#93";
    char* troca8 = (char*) "\"";

    char* temp = NULL;

    //alocacao da string de resposta
    char* stringSemiLimpa = (char*) malloc(TAM_LINHA);
    memset(stringSemiLimpa, '\0', TAM_LINHA);

    strcpy(stringSemiLimpa, linhaOriginal);

    //verificar se contem as strings especificas de troca
    if(strstr(linhaOriginal, troca1) != 0){
        temp = replaceWord(stringSemiLimpa, troca1, subst);

        memset(stringSemiLimpa, '\0', TAM_LINHA);
        strcpy(stringSemiLimpa , temp);

        //delete(temp);
    }

    if(strstr(linhaOriginal, troca2) != 0){
        temp = replaceWord(stringSemiLimpa, troca2, subst);

        memset(stringSemiLimpa, '\0', TAM_LINHA);
        strcpy(stringSemiLimpa , temp);

        //delete(temp);
    }

    if(strstr(linhaOriginal, troca3) != 0){
        temp = replaceWord(stringSemiLimpa, troca3, subst);

        memset(stringSemiLimpa, '\0', TAM_LINHA);
        strcpy(stringSemiLimpa , temp);

        //delete(temp);
    }

    if(strstr(linhaOriginal, troca4) != 0){
        temp = replaceWord(stringSemiLimpa, troca4, subst);

        memset(stringSemiLimpa, '\0', TAM_LINHA);
        strcpy(stringSemiLimpa , temp);

        //delete(temp);
    }
    if(strstr(linhaOriginal, troca5) != 0){
        temp = replaceWord(stringSemiLimpa, troca5, subst);

        memset(stringSemiLimpa, '\0', TAM_LINHA);
        strcpy(stringSemiLimpa , temp);

        //delete(temp);
    }


    if(strstr(linhaOriginal, troca6) != 0){
        temp = replaceWord(stringSemiLimpa, troca6, subst);

        memset(stringSemiLimpa, '\0', TAM_LINHA);
        strcpy(stringSemiLimpa , temp);

        //delete(temp);
    }
    if(strstr(linhaOriginal, troca7) != 0){
        temp = replaceWord(stringSemiLimpa, troca7, subst);

        memset(stringSemiLimpa, '\0', TAM_LINHA);
        strcpy(stringSemiLimpa , temp);

        //delete(temp);
    }
    if(strstr(linhaOriginal, troca8) != 0){
        temp = replaceWord(stringSemiLimpa, troca8, subst);

        memset(stringSemiLimpa, '\0', TAM_LINHA);
        strcpy(stringSemiLimpa , temp);

        //delete(temp);
    }


    delete(linhaOriginal);
    return (stringSemiLimpa);
}

/*
Alocará memoria para um objeto time e setara os atributos de acordo.
*/
Time* construtor(char arquivo[]){

    // alocacoes de memoria
    Time* ptr = (Time*) malloc(sizeof(Time));
    char* linhaCompleta= (char*) malloc(TAM_LINHA);
    //char* tmp = (char*) malloc(sizeof(linhaCompleta));
    char* stringTmp = NULL;

    // METODOS ENVOLVENDO O ARQUIVO EM SI

    ptr->paginaTam = pegarTamanhoPag(arquivo);
    // printf("Tamanho da pag: %ld\n", pegarTamanhoPag(arquivo));

    strcpy(ptr->nomeArquivo, arquivo);

    // METODOS ENVOLVENDO A LINHA INTEIRA

    // armazenar em linha completa a linha toda com o "vcard"
    //strcpy(linhaCompleta, lerArquivo(arquivo));
    stringTmp = filtrarString(arquivo);
    strcpy(linhaCompleta, stringTmp);

    //delete(stringTmp);

    stringTmp = pegarNome(linhaCompleta);
    strcpy(ptr->nome, stringTmp);
    delete(stringTmp);

    stringTmp = pegarApelido(linhaCompleta);
    strcpy(ptr->apelido, stringTmp );
    delete(stringTmp);

    stringTmp = pegarEstadio(linhaCompleta);
    strcpy(ptr->estadio, stringTmp);
    delete(stringTmp);

    stringTmp = pegarData(linhaCompleta);
    strcpy(ptr->datas, stringTmp);
    delete(stringTmp);

    stringTmp = pegarLiga(linhaCompleta);
    strcpy(ptr->liga, stringTmp);
    delete(stringTmp);

    stringTmp = pegarTecnico(linhaCompleta);
    strcpy(ptr->tecnico, stringTmp );
    delete(stringTmp);


    int* datas = filtrarData(pegarData(linhaCompleta));
    ptr->fundacaoDia = datas[0];
    ptr->fundacaoMes = datas[1];
    ptr->fundacaoAno = datas[2];
    // METODOS DE INTEIROS E LONGS

    ptr->capacidade = pegarCapacidade(linhaCompleta);

    //limpar char* alocado com malloc
    delete(linhaCompleta);
    return ptr;
}

// ler se FIM foi digitado e é de fato o fim da entrada.
bool ehFim(char entrada[]){
    return strlen(entrada) <= 3 && entrada[0] == 'F' && entrada[1] == 'I' && entrada[2] == 'M';
}