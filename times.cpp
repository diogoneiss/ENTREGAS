#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <stdlib.h>
#include <time.h>

#define TAM_LINHA 8000
#define TAM_ATRIBUTO 500
#define TAM_DATA 150


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
int* letraNoMeioFrase(char linhaOriginal[]);
char *replaceWord(const char *s, const char *oldW, const char *newW); 
int* splitarData(char linha[]);
//fim prototipos


//classe principal dos tvimes
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

//prototipos envolvedo a classe
int recurSelectionSort(Time **a, int n, int index);
int recurSelectionSort(Time **a, int n);  
int minIndex(Time **a, int i, int j); 
// fim prototipos

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

            else{
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
    char fraseFiltrada[TAM_LINHA];

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

    strcpy(resposta, lerEntreAspasAteTD(substring(busca, linhaCompleta), strlen(busca)));
    //printf("\n Resposta eh %s\n", resposta);
    //printf("\nCheguei e sai da funcao PegarNome com sucesso.\n");
    
    bool tagAchada = false;
    bool leituraParenteses = false;

    int contadorPosicao = 0;
    char fraseFiltrada[3000];

/*
    for (int i = 0; i < strlen(linhaCompleta); i++)
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
            if(linhaCompleta[i] == '<' && linhaCompleta[i+2] != '>' && linhaCompleta[i+1] != '>' ){
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
        
    }*/
    
    return (resposta);
}

char* pegarApelido(char linhaCompleta[]){
    char busca[] = "nickname";
    char* resposta  = (char*) malloc(TAM_ATRIBUTO);
    memset(resposta, '\0', TAM_ATRIBUTO);

    strcpy(resposta, lerAPartirDaClasse(substring(busca, linhaCompleta)));
    //printf("\n Resposta eh %s\n", resposta);
    //printf("\nCheguei e sai da funcao pegarApelido com sucesso.\n");
    return (resposta);
}

char* pegarEstadio(char linhaCompleta[]){
    char busca[] = "Ground";
    char* resposta  = (char*) malloc(TAM_ATRIBUTO);
    memset(resposta, '\0', TAM_ATRIBUTO);

    strcpy(resposta, lerEntreAspasAteTD(substring(busca, linhaCompleta), strlen(busca)));
    //printf("\n Resposta eh %s\n", resposta);
    //printf("\nCheguei e sai da funcao pegarEstadio com sucesso.\n");
    return (resposta);

}

char* pegarLiga(char linhaCompleta[]){
    char busca[] = "League";
    char* resposta = (char*)malloc(TAM_ATRIBUTO);
    memset(resposta, '\0', TAM_ATRIBUTO);
    strcpy(resposta, lerEntreAspasAteTD(substring(busca, linhaCompleta), strlen(busca)) );
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
        tmp =  lerEntreAspas(substring(busca2, linhaCompleta), strlen(busca2));
        strcpy(resposta ,tmp);
        delete(tmp);
    }

    //printf("\nCheguei e sai da funcao pegarData com sucesso.\n");

    // agora que tenho a resposta, preciso filtrá-la. O ideal é parar depois que achar algum char que não seja letra, dia ou traço
    char respostaFiltrada[strlen(resposta)];
    //printf("\nResposta nao filtrada: %s\n", resposta);

    for(int i = 0; i < strlen(resposta); i++){
        bool ehLetra = (resposta[i] >= 'a' && resposta[i] <= 'z') || (resposta[i] >= 'A' && resposta[i] <= 'Z');
        bool ehNum = resposta[i] >= '0' && resposta[i] <= '9';

        bool continuar = ehLetra || ehNum || resposta[i] == '-' || resposta[i] == ' ';
        
        if(!continuar){
            //printf("\nChar de parada: %c\n", resposta[i]);
            respostaFiltrada[i] = '\0';
            i = strlen(resposta);
        }

        else
            respostaFiltrada[i] = resposta[i];
    }
    //printf("\nResposta filtrada bugadona: %s\n", respostaFiltrada);
    delete(resposta);
    return strdup(respostaFiltrada);
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
    else
        datas[2] = atoi(linha);

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
    printf("Arquivo atual: %s\n", arquivo);
    FILE *f = fopen(arquivo, "rb");

    fseek(f, 0, SEEK_END); // seek to end of file
    long size = ftell(f); // get current file pointer
    printf("Li o tamanho. Tamanho: %ld\n", size);
    

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
char *replaceWord(const char *s, const char *oldW, const char *newW) 
{ 
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
/*

FUNCAO NAO UTILIZADA MAIS
SUBSTITUIDA PELA ACIMA

char* st_replace(char *orig, char *rep, char *with) {
    char *result; // the return string
    char *ins;    // the next insert point
    char *tmp;    // varies
    int len_rep;  // length of rep (the string to remove)
    int len_with; // length of with (the string to replace rep with)
    int len_front; // distance between rep and end of last rep
    int count;    // number of replacements

    // sanity checks and initialization
    if (!orig || !rep)
        return NULL;
    len_rep = strlen(rep);

    if (len_rep == 0)
        return NULL; // empty rep causes infinite loop during count
    
    if (!with)
        with = (char*) "";
    len_with = strlen(with);

    // count the number of replacements needed
    ins = orig;
    for (count = 0; tmp = strstr(ins, rep); ++count) {
        ins = tmp + len_rep;
    }

    tmp = result = (char*) malloc(strlen(orig) + (len_with - len_rep) * count + 1);

    if (!result)
        return NULL;

    // first time through the loop, all the variable are set correctly
    // from here on,
    //    tmp points to the end of the result string
    //    ins points to the next occurrence of rep in orig
    //    orig points to the remainder of orig after "end of rep"
    while (count--) {
        ins = strstr(orig, rep);
        len_front = ins - orig;
        tmp = strncpy(tmp, orig, len_front) + len_front;
        tmp = strcpy(tmp, with) + len_with;
        orig += len_front + len_rep; // move to next "end of rep"
    }
    strcpy(tmp, orig);
    
    return strdup(result);
}
*/


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
    memset(linhaOriginal, '\0', sizeof(*linhaOriginal));

    // guardar o conteudo da linha do arquivo
    strcpy(linhaOriginal, lerArquivo(arquivo));

    // substituicoes
    char* subst = (char*) "";
    char* troca1 = (char*) "&#91;";
    char* troca2 = (char*) ";note 1&#93;";
    char* troca3 = (char*) "1&#93";
    char* troca4 = (char*) "&#160;";
    char* troca5 = (char*) "&amp;";
    //char* troca6 = (char*) ";";

    char* temp = NULL;

    //alocacao da string de resposta
    char* stringSemiLimpa = (char*) malloc(strlen(linhaOriginal));
    memset(stringSemiLimpa, 0, strlen(linhaOriginal));

    strcpy(stringSemiLimpa, linhaOriginal);

    //verificar se contem as strings especificas de troca
    if(strstr(linhaOriginal, troca1) != 0){
        temp = replaceWord(stringSemiLimpa, troca1, subst);

        memset(stringSemiLimpa, '\0', strlen(stringSemiLimpa));
        strcpy(stringSemiLimpa , temp);
        
        //delete(temp);
    }
        
    if(strstr(linhaOriginal, troca2) != 0){
        temp = replaceWord(stringSemiLimpa, troca2, subst);

        memset(stringSemiLimpa, '\0', strlen(stringSemiLimpa));
        strcpy(stringSemiLimpa , temp);
        
        //delete(temp);
    }
       
    if(strstr(linhaOriginal, troca3) != 0){
        temp = replaceWord(stringSemiLimpa, troca3, subst);

        memset(stringSemiLimpa, '\0', strlen(stringSemiLimpa));
        strcpy(stringSemiLimpa , temp);
        
        //delete(temp);
    }   

    if(strstr(linhaOriginal, troca4) != 0){
        temp = replaceWord(stringSemiLimpa, troca4, subst);

        memset(stringSemiLimpa, '\0', strlen(stringSemiLimpa));
        strcpy(stringSemiLimpa , temp);
        
        //delete(temp);
    }
    if(strstr(linhaOriginal, troca5) != 0){
        temp = replaceWord(stringSemiLimpa, troca5, subst);

        memset(stringSemiLimpa, '\0', strlen(stringSemiLimpa));
        strcpy(stringSemiLimpa , temp);
        
        //delete(temp);
    }
        
    /*
    if(strstr(linhaOriginal, troca6) != 0){
        temp = replaceWord(stringSemiLimpa, troca6, subst);

        memset(stringSemiLimpa, '\0', strlen(stringSemiLimpa));
        strcpy(stringSemiLimpa , temp);

        delete(temp);
    }
      */  

    return (stringSemiLimpa);
}


/*
Alocará memoria para um objeto time e setara os atributos de acordo.
*/
Time* construtor( char arquivo[]){

    // alocacoes de memoria
    Time* ptr = (Time*) malloc(sizeof(Time));
    char* linhaCompleta= (char*) malloc(10000);
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
    strcat(linhaCompleta, stringTmp);
    
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

// ler se FIM foi digitadi e é de fato o fim da entrada.

bool ehFim(char entrada[]){
    return strlen(entrada) <= 3 && entrada[0] == 'F' && entrada[1] == 'I' && entrada[2] == 'M';
}
//funcao de realizar o swap de dois itens de um array recebido por param
    void swap(Time **array, int menor, int i){
        Time aux = *array[i];
        *array[i] = *array[menor];
        *array[menor] = aux;

        //return array;
    }

    //selecao propriamente dita. Retorna o numero de comparacoes realizadas
     
// Return minimum index 
int minIndex(Time **a, int i, int j) 
{ 
    if (i == j) 
        return i; 
  
    // Find minimum of remaining elements 
    int k = minIndex(a, i + 1, j); 
  
    // Return minimum of current and remaining. 
    return (a[i]->paginaTam < a[k]->paginaTam)? i : k; 
} 
  
// Recursive selection sort. n is size of a[] and index 
// is index of starting element. 
int recurSelectionSort(Time **a, int n){
    return recurSelectionSort(a, n, 0);
}


int recurSelectionSort(Time **a, int n, int index) 
{ 
    int contador = 0;
    // Return when starting and size are same 
    if (index == n) 
       contador = 1; 

    else {
        // calling minimum index function for minimum index 
        int k = minIndex(a, index, n-1); 
    
        // Swapping when index nd minimum index are not same 
        if (k != index){ 
        swap(a, k, index); 
            contador++;
        }
        // Recursively calling selection sort function 
        contador += recurSelectionSort(a, n, ++index); 
    }
    return contador;
} 

int main(){
    time_t comeco = time(NULL);

    //arranjo de nomes de arquivos.
    char entradaTimes[100][100];
    int quantidadeEntradas = -1;
    
    /*
    printf("Teste do replaceWord: \n");
    char linhaTeste[] = "ABC ABC ABC ABC";
    char* teste = replaceWord(linhaTeste, (char*)"BC", (char*)"D");
    printf("Resultado: %s\n", teste);
    */

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
        printf("Entrada numero %d impressa com sucesso\n\n", i);
        //delete(conjuntoTimes[i]);
    }
    
    int tam = sizeof(conjuntoTimes)/sizeof(conjuntoTimes[0]);

    int comparacoes = recurSelectionSort(conjuntoTimes, tam);

    for (int i = 0; i < quantidadeEntradas; i++)
    {
        conjuntoTimes[i]->imprimir();
    }
    
    time_t final = time(NULL);
    long tempo = (long) final - (long) comeco;
    
    FILE *arq = fopen("649651_SelecaoRecursiva", "w");
    fprintf(arq, "649651\t");
    fprintf(arq, "%d\t" ,comparacoes);
    fprintf(arq, "%ld\t" ,tempo);
    fclose(arq);
    
}
