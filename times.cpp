#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <stdlib.h>


//Prototipos de metodos
char* lerEntreAspas(char linhaCompleta[], int inicio);
char* lerAPartirDaClasse(char linhaCompleta[]);
char* lerArquivo(const char* endereco);
char* pegarNome(char linhaCompleta[]);
char* pegarApelido(char linhaCompleta[]);
char* pegarEstadio(char linhaCompleta[]);
char* pegarLiga(char linhaCompleta[]);
char* pegarTecnico(char linhaCompleta[]);
char* pegarManager(char linhaCompleta[]);
char* substring(char padrao[], char entrada[]);
char* replace(char retirar[], char linha[]);

//fim prototipos


//classe principal dos times
typedef struct ClasseTime{
    char nome[500];
    char apelido[500];
    char estadio[500];
    char tecnico[500];
    char liga[500];
    char nomeArquivo[500];

    char datas[500];

    int capacidade;
    int fundacaoDia;
    int fundacaoMes;
    int fundacaoAno;
    long paginaTam;

    void imprimir(){

        printf("\nNome: %s", nome);
        printf(" ##\nApelido: %s", apelido);
        //printf(" ##\n Data: %02d/%02d/%04d",fundacaoDia, fundacaoMes, fundacaoAno);
        printf(" ##\nData: %s", datas);
        printf(" ##\nEstadio: %s", estadio);
        printf(" ##\nCapacidade: %d", capacidade);
        printf(" ##\nTecnico: %s", tecnico);
        printf(" ##\nLiga: %s", liga);
        printf(" ##\nNome arquivo: %s", nomeArquivo);
        printf(" ##\nTamanho pagina: %ld ##\n\n", paginaTam);

    }

}Time;

//a funcao vai pegar o que tiver dentro da primeira aspas, ler e retornar, desconsidersando tags;
char* lerEntreAspas(char linhaCompleta[], int inicio){
    bool tagAchada = false;
    bool leituraParenteses = false;

    int contadorPosicao = 0;
    char fraseFiltrada[3000];

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

            else{
                fraseFiltrada[contadorPosicao] = linhaCompleta[i];
                contadorPosicao++;
                leituraParenteses = true;
            }
        }
    }
    return strdup(fraseFiltrada);

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
    char resposta[1000];
    memset(resposta, '\0', 1000);

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

    return strdup(resposta);
}

char* pegarNome(char linhaCompleta[]){

    char busca[] = "Full name";
    char* resposta  = (char*) malloc(500);
    memset(resposta, '\0', 500);

    strcpy(resposta, lerEntreAspas(substring(busca, linhaCompleta), strlen(busca)));
    //printf("\n Resposta eh %s\n", resposta);
    //printf("\nCheguei e sai da funcao PegarNome com sucesso.\n");
    return strdup(resposta);
}

char* pegarApelido(char linhaCompleta[]){
    char busca[] = "nickname";
    char* resposta  = (char*) malloc(500);
    memset(resposta, '\0', 500);

    strcpy(resposta, lerAPartirDaClasse(substring(busca, linhaCompleta)));
    //printf("\n Resposta eh %s\n", resposta);
    //printf("\nCheguei e sai da funcao pegarApelido com sucesso.\n");
    return strdup(resposta);
}

char* pegarEstadio(char linhaCompleta[]){
    char busca[] = "Ground";
    char* resposta  = (char*) malloc(500);
    memset(resposta, '\0', 500);

    strcpy(resposta, lerEntreAspas(substring(busca, linhaCompleta), strlen(busca)));
    //printf("\n Resposta eh %s\n", resposta);
    //printf("\nCheguei e sai da funcao pegarEstadio com sucesso.\n");
    return strdup(resposta);

}

char* pegarLiga(char linhaCompleta[]){
    char busca[] = "League";
    char* resposta = lerEntreAspas(substring(busca, linhaCompleta), strlen(busca));
    //printf("\nCheguei e sai da funcao pegarLiga com sucesso.\n");
    return strdup(resposta);
}

char* pegarTecnico(char linhaCompleta[]){
    //printf("\nCheguei na funcao de pegar tecnico. \n");
    char* resposta = NULL;
    char busca[] = "Coach";
    char busca2[]= "Head coach";

    // procura o head coach
    if(strstr(linhaCompleta, (char*) busca2) != 0)
        resposta = lerEntreAspas(substring(busca2, linhaCompleta), strlen(busca2));

        //verificar se nao tem tecnico mesmo
    else if(strstr(linhaCompleta, (char*) busca) == 0){
        // se nao tiver Coach procurar por manager
        resposta = pegarManager(linhaCompleta);
    }

    else
        resposta = lerEntreAspas(substring(busca, linhaCompleta), strlen(busca));


    //printf("\nCheguei e sai da funcao PegarCoach com sucesso. saida: %s\n", resposta);
    return strdup(resposta);
}

char* pegarManager(char linhaCompleta[]){
    char busca[] = "Manager";
    char* resp = lerEntreAspas(substring(busca, linhaCompleta), strlen(busca));
    return strdup(resp);
}

char* pegarData(char linhaCompleta[]){
    char busca[] = "bday";
    char busca2[] = "Founded";
    char* resposta = NULL;

    //ver se tem bday. Se nao tiver procura Founded
    if(strstr(linhaCompleta, (char*)busca) != 0)
        resposta = lerAPartirDaClasse(substring(busca, linhaCompleta));
    else
        resposta = lerEntreAspas(substring(busca2, linhaCompleta), strlen(busca2));

    //printf("\nCheguei e sai da funcao pegarData com sucesso.\n");

    return strdup(resposta);
}

int pegarCapacidade(char linhaCompleta[]){

    //printf("\nCheguei em capacidade.\n");
    char busca[] = "Capacity";
    char* resposta = (char*) malloc(2000);
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
    return atoi(aux);
}

long pegarTamanhoPag(char arquivo[]){
    FILE *f = fopen(arquivo, "rb");

    fseek(f, 0, SEEK_END); // seek to end of file
    int size = (int) ftell(f); // get current file pointer

    fseek(f, 0, SEEK_SET); // seek back to beginning of file
    fclose(f);
    //printf("\nCheguei e sai da funcao pegarTamanhoPag com sucesso.\n");
    return size;
}

char* lerArquivo(char endereco[]){
    char* linha = NULL;
    FILE *arquivo = fopen(endereco, "rb");
    size_t len = 0;

    if(arquivo == NULL){
        printf("Error! File is empty");
        exit(1);
    }
    else{
        getline(&linha, &len, arquivo);
        //ler enquanto nao for eof e nao tiver "vcard"
        while(!feof(arquivo) && strstr(linha, "vcard") == 0){
            getline(&linha, &len, arquivo);
        }
    }
    return linha;
}


//remover todas as ocorrencias de uma string dentro de outra
char* replace( char retirar[],  char linha[]){
    char* novaFrase = (char*) malloc(strlen(linha)+1);
    int contadorPosicaoFraseFinal = 0;
    int j;

    bool replaceEncontrado = true;

    for (int i = 0; i < strlen(linha); i++){
        if(linha[i] == retirar[0]){

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
            novaFrase[contadorPosicaoFraseFinal] = linha[i];
            contadorPosicaoFraseFinal++;
        }
    }
    //terminar
    novaFrase[contadorPosicaoFraseFinal+1] = '\0';
    return strdup(novaFrase);
}

char* substring (char padrao[], char entrada[]){
    char* pointer = strstr(entrada, padrao);

    return strdup(pointer);
}

/*
Alocará memoria para um objeto time e setara os atributos de acordo.
*/
Time* construtor( char arquivo[]){

    // alocacoes de memoria
    Time* ptr = (Time*) malloc(sizeof(Time));
    char* linhaCompleta = (char*) malloc(10000);


    // METODOS ENVOLVENDO O ARQUIVO EM SI

    ptr->paginaTam = pegarTamanhoPag(arquivo);
    // printf("Tamanho da pag: %ld\n", pegarTamanhoPag(arquivo));

    strcat(ptr->nomeArquivo, arquivo);

    // METODOS ENVOLVENDO A LINHA INTEIRA

    // armazenar em linha completa a linha toda com o "vcard"
    strcpy(linhaCompleta, lerArquivo(arquivo));

    strcpy(ptr->nome, pegarNome(linhaCompleta));
    strcpy(ptr->apelido, pegarApelido(linhaCompleta));
    strcpy(ptr->estadio, pegarEstadio(linhaCompleta));
    strcpy(ptr->datas, pegarData(linhaCompleta));
    strcpy(ptr->liga, pegarLiga(linhaCompleta));
    strcpy(ptr->tecnico, pegarTecnico(linhaCompleta));

    // METODOS DE INTEIROS E LONGS

    ptr->capacidade = pegarCapacidade(linhaCompleta);

    return ptr;
}

// ler se FIM foi digitadi e é de fato o fim da entrada.

bool ehFim(char entrada[]){
    return strlen(entrada) <= 3 && entrada[0] == 'F' && entrada[1] == 'I' && entrada[2] == 'M';
}

int main(){

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

    //remover o FIM
    //quantidadeEntradas--;

    Time* conjuntoTimes[quantidadeEntradas];

    for (int i = 0; i < quantidadeEntradas; i++){

        // alocar memoria, atribuir atributos com a funcao construtor e imprimir
        conjuntoTimes[i] = construtor(entradaTimes[i]);
        conjuntoTimes[i]->imprimir();
     
        //free(conjuntoTimes[i]);
    }



}
