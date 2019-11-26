
public class AlgebraBooleanaRecursiva {

    //tamanho max da entrada
    static final int TAM_ENTRADA = 10000;

    public static void main(String[] args){

        //criar array
        String[] entradas = new String[TAM_ENTRADA];

        //calculo do tamanho real. Começa em -1 pq vai ser incrementado
        int tamanhoRealEntradas = -1;

        do{

            tamanhoRealEntradas++;
            //ler uma entrada do input
            entradas[tamanhoRealEntradas] = MyIO.readLine();


            //verificar se a entrada anterior nao era o fim da entrada
        }while(!estaNoFinal(entradas[tamanhoRealEntradas]));

        //iterar todas as entradas e executar os metodos adequados
        for(int i=0;i<tamanhoRealEntradas;i++){
            //chamada da funcao com o i-esimo item do array

            //limpeza e substituicao da string
            entradas[i] = substituirLetrasExpressao(entradas[i]);

            //simplificar a operacao
            entradas[i] = simplificarOperacao(entradas[i]);

            //print final pro verde
            MyIO.println(entradas[i]);

        }

    }



    /*
    Essa funcao vai servir para simplificar a operacao iterativamente, ate haver simplificado o bastante
    Para isso, vai andar na string de tras pra frente, retirar uma operacao e substituir por 0 ou 1
    */
    public static String simplificarOperacao(String fraseOriginal){
        //remover os nots simples da string
        char bitDaOperacao;
        int iteracoes = 0;

        //repetir as operacoes ate o length ser de um unico bit
        while(fraseOriginal.length() > 2 && iteracoes < 10){
            iteracoes++;

            // for interno para varrer a string
            for (int i = fraseOriginal.length()-1; i >= 4; i--) {

                //significa que eh um parenteses simples, ou seja, há apenas uma operacao
                //length() > 2 é pra evitar o acesso do elemento i-1 caso haja apenas um elemento
                //i < length() é pra quando houber reescrita de string nao tentar acessar um indice proibido

                boolean podeSimplificar = i < fraseOriginal.length() && fraseOriginal.length() > 2 && fraseOriginal.charAt(i) == ')' && fraseOriginal.charAt(i-1) != ')';
                boolean parentesesAchado = false;

                // so pro javac nao encher com o fato de nao ter inicializado
                int posicaoAberturaParenteses = 5;
                int posicaoFechamentoParenteses = 7;

                //preciso agora achar o parenteses  de abertura
                if(podeSimplificar){
                    for (int j = i-1; j > 1; j--) {
                        if(fraseOriginal.charAt(j) == '('){
                            posicaoAberturaParenteses = j;
                            j = 0;
                            parentesesAchado = true;
                        }
                    }
                }

                //preciso agora achar o parenteses  de fechamento
                if(podeSimplificar){
                    for (int j = posicaoAberturaParenteses; j < fraseOriginal.length(); j++) {
                        if(fraseOriginal.charAt(j) == ')'){
                            posicaoFechamentoParenteses = j;
                            j = fraseOriginal.length();
                        }
                    }
                }

                /*
                ATENCAO
                Para passar a funcao reformatarString, é necessário passar a primeira letra da operacao ('a', por exemplo) e
                o parenteses de fechamento, já que a funcao usa as duas posicoes como demarcadores, nao os incluindo
                */

                //ja achei o parenteses de abertura e o de fechamento. Posso seguir com a respectiva operacao

                //operacao and
                if(podeSimplificar && parentesesAchado && fraseOriginal.charAt(posicaoAberturaParenteses-1) == 'd' && fraseOriginal.charAt(posicaoAberturaParenteses-2) == 'n' && fraseOriginal.charAt(posicaoAberturaParenteses-3) == 'a'){

                    bitDaOperacao = operacaoAND(cortarString(fraseOriginal, posicaoAberturaParenteses, posicaoFechamentoParenteses));
                    fraseOriginal = reformatarString(fraseOriginal, posicaoAberturaParenteses-3, posicaoFechamentoParenteses, bitDaOperacao);
                }

                //operacao or
                else if(podeSimplificar && parentesesAchado && fraseOriginal.charAt(posicaoAberturaParenteses-1) == 'r' && fraseOriginal.charAt(posicaoAberturaParenteses-2) == 'o'){

                    //cortaString() deixa apenas o conteudo entre os parenteses
                    bitDaOperacao = operacaoOR(cortarString(fraseOriginal, posicaoAberturaParenteses, posicaoFechamentoParenteses));
                    fraseOriginal = reformatarString(fraseOriginal, posicaoAberturaParenteses-2, posicaoFechamentoParenteses, bitDaOperacao);
                }
                //operacao not
                else if(podeSimplificar && parentesesAchado && fraseOriginal.charAt(posicaoAberturaParenteses-1) == 't' && fraseOriginal.charAt(posicaoAberturaParenteses-2) == 'o' && fraseOriginal.charAt(posicaoAberturaParenteses-3) == 'n'){

                    bitDaOperacao = operacaoNOT(fraseOriginal.charAt(posicaoAberturaParenteses+1));
                    fraseOriginal = reformatarString(fraseOriginal, posicaoAberturaParenteses-3, posicaoFechamentoParenteses, bitDaOperacao);
                }
            }
        }
        return fraseOriginal;
    }


    /*
    serve para transformar a string original, com A B e etcs em uma so com a expressao booleana, alem de cortar fora o começo
    da string, deixando APENAS as instruções lógicas para evaluacao

    Então, "1 0 not(A)" viraria apenas "not(0)"

    */
    public static String substituirLetrasExpressao(String frase){

        String novaString = "";

        //armazenar o numero de substituicoes binarias a serem realizadas
        char num = frase.charAt(0);
        //o 3o item da string contem o primeiro valor booleano

        //no 8 comeca a primeira letra da operacao e eu a quero inclui-la
        int inicioCorte = 7;

        //substituir na string as letras maiusculas pelos seus respectivos valores booleanos


        /*
        1 0 not(A)
        0123456789ABCDEF
        2 0 0 or( A , B)

        2a posicao = valor do A
        4a posicao = valor do B
        6a posicao = valor do C
        */

        // caso de haver apenas uma letra
        if (num == '1') {
            for (int i = 0; i < frase.length(); i++) {

                inicioCorte = 3;

                if (frase.charAt(i) == 'A')
                    novaString += frase.charAt(2);

                else
                    novaString += frase.charAt(i);
            }
        }

        // caso de haver apenas duas letras
        else if (num == '2') {
            for (int i = 0; i < frase.length(); i++) {

                inicioCorte = 5;

                if (frase.charAt(i) == 'A')
                    novaString += frase.charAt(2);

                else if(frase.charAt(i) == 'B')
                    novaString += frase.charAt(4);

                else
                    novaString += frase.charAt(i);
            }
        }

        // caso restante, de haver apenas 3 letras
        else if (num == '3'){
            for (int i = 0; i < frase.length(); i++) {

                inicioCorte = 7;

                if (frase.charAt(i) == 'A')
                    novaString += frase.charAt(2);

                else if(frase.charAt(i) == 'B')
                    novaString += frase.charAt(4);

                else if(frase.charAt(i) == 'C')
                    novaString += frase.charAt(6);
                else
                    novaString += frase.charAt(i);

            }

        }

        //ja substitui os valores booleanos. agora falta cortar a string, removendo os valores booleanos de inicio
        String resposta = cortarString(novaString, inicioCorte, novaString.length());

        return resposta;

    }



    // cortar uma string incluindo todos os chars entre duas posicoes, excluindo essas duas posicoes
    public static String cortarString(String frase, int posIn, int posFin){

        String novaString = "";

        //começa na posicao seguinte da inserida e termina antes da final
        //excluir posIn e posFin da string cortada
        for (int i=posIn+1; i < posFin; i++) {
            novaString += frase.charAt(i);
        }

        return novaString;

    }

    /*
    Essa funcao vai servir para dividir uma string em duas, remover o espaco entre duas posicoes e inserir um unico char, da reposta.
    AnteriorFim e posteriorInicio estao sendo excluidas nas strings
    */
    public static String reformatarString(String fraseOriginal, int anteriorFim, int posteriorInicio, char substituir){

        String anterior = "";
        String posterior = "";
        String novaStringSubstituida = "";

        //salvar os chars da original até anteriorFim
        for(int i = 0; i < anteriorFim; i++)
            anterior += fraseOriginal.charAt(i);

        //salvar os chars posteriores até o fim
        for (int i = posteriorInicio+1; i < fraseOriginal.length(); i++) {
            posterior += fraseOriginal.charAt(i);
        }

        // adicionar as strings juntas agora, fazendo anterior + meio + fim, sendo o meio um unico char
        for(int i = 0;i<anterior.length(); i++)
            novaStringSubstituida += anterior.charAt(i);

        //agora que estou no meio posso jogar o char que é pra inserir no meio
        novaStringSubstituida += substituir;

        //jogar o resto dentro, pra fechar a string e retornar
        for(int i = 0;i<posterior.length(); i++)
            novaStringSubstituida += posterior.charAt(i);

        return novaStringSubstituida;

    }

    //executar a operacao AND com os operandos binarios dentro
    public static char operacaoAND(String frase){
        char resultado = '1';
        //iterar a string pra procurar zeros
        for(int i=0;i<frase.length();i++){
            //se houver qualquer 0 o AND retornara false
            if(frase.charAt(i) == '0'){
                resultado = '0';
                i = frase.length();
            }
        }
        return resultado;
    }

    //executar a operacao OR com os operandos binarios dentro
    public static char operacaoOR(String frase){
        char resultado = '0';
        //iterar a string pra procurar 1s
        for(int i=0;i<frase.length();i++){
            //se houver qualquer 1 o or retornara true
            if(frase.charAt(i) == '1'){
                resultado = '1';
                i = frase.length();
            }
        }
        return resultado;
    }

    // operacao not
    public static char operacaoNOT(char bit){
        //flipar o valor binario da expressao
        char resultado = '0';

        if(bit == '0')
            resultado = '1';

        return resultado;

    }

    //le se eh o final da entrada, para terminar os metodos de entrada e começar a printar as respostas
    public static boolean estaNoFinal(String frase){
        //se for '0' havera strlen de 2 (0 e fim de linha)
        return (frase.length() <= 2 && frase.charAt(0) == '0');
    }

//fim da classe


}
