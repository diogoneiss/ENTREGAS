#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <stdlib.h>

/* PROBLEMAS

Algum malloc tá dando pau
Times especificos com problema:
/tmp/times/AS_Saint-Etienne.html

*/

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
char* str_replace(char *orig, char *rep, char *with);
int* splitarData(char linha[]);
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




typedef struct ListaTAD {
    int n;
    Time listaDosTimes[200];

    void inserirInicio(Time* adicao){
        if(n >= 200)
            printf("Erro! Tamanho excedido");

        for (int i = n; i > 0; i--) {
            listaDosTimes[i] = listaDosTimes[i-1];
        }

        listaDosTimes[0] = *adicao;
        n++;
    }
    void inserirFim(Time* adicao){
        if(n >= 200)
            printf("Erro! Tamanho excedido");

        listaDosTimes[n] = *adicao;
        n++;
    }

    //remover o inicio e retornar o que ele era
    Time* removerInicio() {

        Time* resp = (Time*) malloc(sizeof(Time));

        if(n == 0)
            printf("Erro! não dá pra tirar o inicio de um array de tam 0");

        resp = &listaDosTimes[0];
        n--;

        //puxar os times uma posicao pra baixo
        for (int i = 0; i < n; i++) {
            listaDosTimes[i] = listaDosTimes[i+1];
        }

        return resp;
    }

    Time* removerFim() {
        if(n == 0)
            printf("Erro! não dá pra tirar o inicio de um array de tam 0");

        Time* remocao = (Time*)malloc(sizeof(Time));
        //subtrai do n e dois acessa a posicao
        remocao = &listaDosTimes[--n];
        return remocao;
    }

    //mover os itens da pos até n uma posicao pra frente,
    //pra depois inserir em POS
    void inserir(Time* adicao, int pos){
        if(n >= 200 || pos < 0 || pos > n)
            printf("Erro! Tamanho excedido");

        for (int i = n; i > pos; i--) {
            listaDosTimes[i] = listaDosTimes[i-1];
        }

        listaDosTimes[pos] = *adicao;
        n++;
    }
    Time* remover(int pos) {
        if(n == 0 || pos < 0 || pos >= n)
            printf("Erro! não dá pra tirar essa posição");

        Time* resp = &listaDosTimes[pos];
        n--;

        //puxar os times uma posicao pra baixo, começando de pos
        for (int i = pos; i < n; i++) {
            listaDosTimes[i] = listaDosTimes[i+1];
        }

        return resp;
    }

    void mostrar(){
        //chamar a funcao de mostrar pros i elementos
        for (int i = 0; i < n; i++) {
            printf("[%d] ", i);
            listaDosTimes[i].imprimir();
        }
    }

}Lista;

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

char* lerEntreAspasAteTD(char linhaCompleta[], int inicio){
    bool tagAchada = false;
    bool leituraParenteses = false;

    int contadorPosicao = 0;
    char fraseFiltrada[3000];

    //printf("\nFrase recebida: %s\n", linhaCompleta);

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

    if(fraseFiltrada == NULL)
        printf("Erro! Null");
    //else
     //   printf("\nFrase filtrada: %s\n", fraseFiltrada);

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
    }
    */
    return resposta;
}

char* pegarApelido(char linhaCompleta[]){
    char busca[] = "nickname";
    char* resposta  = (char*) malloc(500);
    memset(resposta, '\0', 500);

    strcpy(resposta, lerAPartirDaClasse(substring(busca, linhaCompleta)));
    //printf("\n Resposta eh %s\n", resposta);
    //printf("\nCheguei e sai da funcao pegarApelido com sucesso.\n");
    return (resposta);
}

char* pegarEstadio(char linhaCompleta[]){
    char busca[] = "Ground";
    char* resposta  = (char*) malloc(500);
    memset(resposta, '\0', 500);

    strcpy(resposta, lerEntreAspasAteTD(substring(busca, linhaCompleta), strlen(busca)));
    //printf("\n Resposta eh %s\n", resposta);
    //printf("\nCheguei e sai da funcao pegarEstadio com sucesso.\n");
    return (resposta);

}

char* pegarLiga(char linhaCompleta[]){
    char busca[] = "League";
    char* resposta = lerEntreAspasAteTD(substring(busca, linhaCompleta), strlen(busca));
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

    return strdup(respostaFiltrada);
}

int* filtrarData(char linhaOriginal[]){
    char* stringSemiLimpa = (char*) malloc(strlen(linhaOriginal));
    memset(stringSemiLimpa, '\0', strlen(linhaOriginal));
    strcpy(stringSemiLimpa, linhaOriginal);

    int* datas = (int*)malloc(sizeof(int)*3);

    if(strstr(linhaOriginal, (char*) "January") != 0){
        stringSemiLimpa =  str_replace(stringSemiLimpa, (char*) "January", (char*) "-01-");
        
    }
    else if(strstr(linhaOriginal, (char*) "February") != 0){
        stringSemiLimpa =  str_replace(stringSemiLimpa, (char*) "February", (char*) "-02-");
    
    }
    else if(strstr(linhaOriginal, (char*) "March") != 0){
        stringSemiLimpa =  str_replace(stringSemiLimpa, (char*) "March", (char*) "-03-");
    
    }
    else if(strstr(linhaOriginal, (char*) "April") != 0){
        stringSemiLimpa =  str_replace(stringSemiLimpa, (char*) "April", (char*) "-04-");
    
    }
    else if(strstr(linhaOriginal, (char*) "May") != 0){
        stringSemiLimpa =  str_replace(stringSemiLimpa, (char*) "May", (char*) "-05-");

    }
    else if(strstr(linhaOriginal, (char*) "June") != 0){
        stringSemiLimpa =  str_replace(stringSemiLimpa, (char*) "June", (char*) "-06-");

    }
    else if(strstr(linhaOriginal, (char*) "July") != 0){
        stringSemiLimpa =  str_replace(stringSemiLimpa, (char*) "July", (char*) "-07-");

    }
    else if(strstr(linhaOriginal, (char*) "August") != 0){
        stringSemiLimpa =  str_replace(stringSemiLimpa, (char*) "August", (char*) "-08-");

    }
    else if(strstr(linhaOriginal, (char*) "September") != 0){
        stringSemiLimpa =  str_replace(stringSemiLimpa, (char*) "September", (char*) "09");

    }
    else if(strstr(linhaOriginal, (char*) "October") != 0){
        stringSemiLimpa =  str_replace(stringSemiLimpa, (char*) "September", (char*) "-10-");

    }
    else if(strstr(linhaOriginal, (char*) "November") != 0){
        stringSemiLimpa =  str_replace(stringSemiLimpa, (char*) "November", (char*) "-11-");

    }
    else if(strstr(linhaOriginal, (char*) "December") != 0){
        stringSemiLimpa =  str_replace(stringSemiLimpa, (char*) "December", (char*) "-12-");   
 
    }
    
    datas = splitarData(stringSemiLimpa);
    
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
        free(frase);
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
    char linha[10000];
    FILE *arquivo = fopen(endereco, "rb");
    size_t len = 0;
    char* aux = (char*)malloc(100000);
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
    
   // free(linha);
    fclose(arquivo);
    return (aux);
}

char* str_replace(char *orig, char *rep, char *with) {
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


//remover todas as ocorrencias de uma string dentro de outra
char* replace( char retirar[],  char linha[]){
    //char* novaFrase = (char*) malloc(strlen(linha)+1);
    
    
    char novaFrase[strlen(linha)];
    memset(novaFrase, '\0', strlen(linha));
    
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
 
    return strdup(novaFrase);
}

char* substring(char frase[], int pos){
    char* novaFrase = (char*)malloc(strlen(frase)-pos +1);
    memset(novaFrase, '\0', strlen(frase)-pos +1);

    int posReal = 0;
    for (int i = pos; i < strlen(frase); i++)
    {
        novaFrase[posReal] = frase[i];
        posReal++; 
    }

    return strdup(novaFrase);
    
}

char* substring(char frase[], int pos, int fim){
    char* novaFrase = (char*)malloc(strlen(frase)-pos +1);
    memset(novaFrase, '\0', strlen(frase)-pos +1);

    int posReal = 0;
    for (int i = pos; i <= fim; i++)
    {
        novaFrase[posReal] = frase[i];
        posReal++;
    }

    return strdup(novaFrase);

}

char* substring (char padrao[], char entrada[]){
    char* pointer = strstr(entrada, padrao);

    if(pointer == NULL)
        printf("Ponteiro vazio");

    return strdup(pointer);
}
/*
Metodo recebe o nome do arquivo, le ele, retira a string e a filtra, retornando uma duplicata da string limpa
*/
char* filtrarString(char arquivo[]){

    char* linhaOriginal = (char*) malloc(10000);
    // guardar o conteudo da linha do arquivo
    strcpy(linhaOriginal, lerArquivo(arquivo));

    // substituicoes
    char* subst = (char*) "";
    char* troca1 = (char*) "&#91;";
    char* troca2 = (char*) ";note 1&#93;";
    char* troca3 = (char*) "1&#93";
    char* troca4 = (char*) "&#160;";
    char* troca5 = (char*) "&amp;";
    char* troca6 = (char*) ";";

    //alocacao da string de resposta
    char* stringSemiLimpa = (char*) malloc(strlen(linhaOriginal));
    memset(stringSemiLimpa, '\0', strlen(linhaOriginal));

    strcpy(stringSemiLimpa, linhaOriginal);

    //verificar se contem as strings especificas de troca
    if(strstr(linhaOriginal, troca1) != 0)
        stringSemiLimpa = str_replace(linhaOriginal, troca1, subst);

    if(strstr(linhaOriginal, troca2) != 0)
        stringSemiLimpa =  str_replace(stringSemiLimpa, troca2, subst);

    if(strstr(linhaOriginal, troca3) != 0)
        stringSemiLimpa =  str_replace(stringSemiLimpa, troca3, subst);

    if(strstr(linhaOriginal, troca4) != 0)
        stringSemiLimpa =  str_replace(stringSemiLimpa, troca4, subst);

    if(strstr(linhaOriginal, troca5) != 0)
        stringSemiLimpa =  str_replace(stringSemiLimpa, troca5, subst);

    if(strstr(linhaOriginal, troca6) != 0)
        stringSemiLimpa =  str_replace(stringSemiLimpa, troca6, subst);

    return strdup(stringSemiLimpa);
}

Lista* construtorLista(int tam){
    Lista* listaComTimes = (Lista*) malloc(sizeof(Lista));
    listaComTimes->n = 0;
    //listaComTimes->listaDosTimes = (Time*) malloc(200*sizeof(Time));

    return listaComTimes;
}

/*
Alocará memoria para um objeto time e setara os atributos de acordo.
*/
Time* construtor( char arquivo[]){

    // alocacoes de memoria
    Time* ptr = (Time*) malloc(sizeof(Time));
    char* linhaCompleta= (char*) malloc(10000);
    char* tmp = (char*) malloc(sizeof(linhaCompleta));
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
    free(stringTmp);

    stringTmp = pegarNome(linhaCompleta);
    strcpy(ptr->nome, stringTmp);
    free(stringTmp);

    stringTmp = pegarApelido(linhaCompleta);
    strcpy(ptr->apelido, stringTmp );
    free(stringTmp);

    stringTmp = pegarEstadio(linhaCompleta);
    strcpy(ptr->estadio, stringTmp);
    free(stringTmp);

    stringTmp = pegarData(linhaCompleta);
    strcpy(ptr->datas,stringTmp);
    free(stringTmp);

    stringTmp = pegarLiga(linhaCompleta);
    strcpy(ptr->liga, stringTmp);
    free(stringTmp);
    
    stringTmp = pegarTecnico(linhaCompleta);
    strcpy(ptr->tecnico, stringTmp );
    free(stringTmp);


    int* datas = filtrarData(pegarData(linhaCompleta));
    ptr->fundacaoDia = datas[0];
    ptr->fundacaoMes = datas[1];
    ptr->fundacaoAno = datas[2];
    // METODOS DE INTEIROS E LONGS


    ptr->capacidade = pegarCapacidade(linhaCompleta);

    //limpar char* alocado com malloc
    //free(linhaCompleta);
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


    Time* conjuntoTimes[quantidadeEntradas];

    for (int i = 0; i < quantidadeEntradas; i++){

        // alocar memoria, atribuir atributos com a funcao construtor e imprimir
        conjuntoTimes[i] = construtor(entradaTimes[i]);
        //conjuntoTimes[i]->imprimir();
    }

    /*********** lista da questao ******************/

    //ler a quantidade de operacoes a serem realizadas
    //printf("\nInsira a quantidade de modificacoes: ");
    
    int quantidadeModificacoes;
    scanf("%d", &quantidadeModificacoes);


    // construtor da lista de times
    Lista* listaTimesMain = construtorLista(quantidadeEntradas + quantidadeModificacoes);


    // adicionar os times mencionados previamente no fim da lista
    for (int i = 0; i < quantidadeEntradas; i++) {
        listaTimesMain->inserirFim(conjuntoTimes[i]);
    }

    //listaTimesMain->mostrar();

    /*************MÉTODOS DA LISTA DAQUI EM DIANT**********/

    // arranjo com string das operacoes
    char operacoes[100][100];

    //printf("\nTerminei de criar o arranjo, tudo ok por aqui.\n");
    getchar();

    for(int i = 0; i < quantidadeModificacoes; i ++){
        fgets(operacoes[i], 100, stdin);
        operacoes[i][strlen(operacoes[i])-1] = '\0';
        // a entrada de operacoes está correta
        //printf("Operacao inserida: %s\n", operacoes[i]);
    }

    // ler e executar as operacoes
    for(int j = 0; j< quantidadeModificacoes; j++){
        //printf("\n%d operacao: %s", j , operacoes[j]);

        //metodos de insercao
        if(operacoes[j][0] == 'I'){
            //MyIO.println("Encontrei operacao de insercao");
            // criar e jogar o time no inicio
            if(operacoes[j][1] == 'I') {
                Time* novaEntrada = construtor(substring(operacoes[j], 3));
                listaTimesMain->inserirInicio(novaEntrada);
            }

            else if(operacoes[j][1] == 'F') {
                Time* novaEntrada = construtor(substring(operacoes[j], 3));
                listaTimesMain->inserirFim(novaEntrada);
            }
                //adicionar o time na posicao especificada
            else if(operacoes[j][1] == '*') {
                Time* novaEntrada = construtor(substring(operacoes[j], 6));
                int pos = atoi(substring(operacoes[j], 3, 5));
                listaTimesMain->inserir(novaEntrada, pos);

            }
        }

            //metodos de remocao
        else if(operacoes[j][0] == 'R'){

            //MyIO.println("Encontrada operacao de remocao");

            if(operacoes[j][1] == 'I'){
                Time* removido = listaTimesMain->removerInicio();
                printf("(R) %s\n", removido->nome);
            }

            else if(operacoes[j][1] == 'F'){
                Time* removido = listaTimesMain->removerFim();
                printf("(R) %s\n", removido->nome);
            }

                //remover o time na posicao especificada
            else if(operacoes[j][1] == '*'){
                int posRemover =atoi(substring(operacoes[j], 3, 5));
                //MyIO.println("Pos removida: "+posRemover);
                Time* removido = listaTimesMain->remover(posRemover);
                printf("(R) %s\n", removido->nome);
            }
        }

    }

    // metodo de printar tudo
    listaTimesMain->mostrar();


    //fim da main




}
