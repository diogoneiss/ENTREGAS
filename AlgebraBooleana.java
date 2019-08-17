public class AlgebraBooleana {

    //tamanho max da entrada
    static final int TAM_ENTRADA = 100;

    public static void main(String[] args){

        //setar charset
        MyIO.setCharset("ISO-8859-1");

        //criar array
        String[] entradas = new String[TAM_ENTRADA];

        //calculo do tamanho real. Começa em -1 pq vai ser incrementado
        int tamanhoRealEntradas = -1;



        do{

            tamanhoRealEntradas++;
            //ler uma entrada do input
            entradas[tamanhoRealEntradas] = MyIO.readLine();
            MyIO.println("A string foi lida corretamente. String lida: " +entradas[tamanhoRealEntradas]);

            //verificar se a entrada anterior nao era o fim da entrada
        }while(!estaNoFinal(entradas[tamanhoRealEntradas]));

        //iterar todas as entradas e executar os metodos adequados
        for(int i=0;i<tamanhoRealEntradas;i++){
            //chamada da funcao com o i-esimo item do array

            //limpeza e substituicao da string
            MyIO.println("String original, antes de limpar: "+entradas[i]);

            entradas[i] = substituirLetrasExpressao(entradas[i]);
            entradas[i] = simplificarNOT(entradas[i]);
            entradas[i] = simplificarOperacao(entradas[i]);

            MyIO.println("Resposta final: "+ entradas[i]);

            MyIO.println("terminei essa entrada, indo pra proxima");
        }

    }

    /*
    Essa funcao serve para simplificar NOTs unicos no codigo
    */
    public static String simplificarNOT(String fraseOriginal){
        char bitDaOperacao;

        for (int i = fraseOriginal.length()-1; i >= 5; i--) {

            //significa que eh um parenteses simples, ou seja, há apenas uma operacao
            boolean podeSimplificar = fraseOriginal.charAt(i) == ')' && fraseOriginal.charAt(i-1) != ')';

            //not(A)
            //0123456789


            //operacao not e seus metodos
            if(podeSimplificar && fraseOriginal.charAt(i-3) == 't' && fraseOriginal.charAt(i-4) == 'o' && fraseOriginal.charAt(i-4) == 'n'){

                bitDaOperacao = operacaoNOT(fraseOriginal.charAt(i-1));
                fraseOriginal = reformatarString(fraseOriginal, i-5, i, bitDaOperacao);
            }
        }
        return fraseOriginal;
    }

    /*
    Essa funcao vai servir para simplificar a operacao iterativamente, ate haver simplificado o bastante
    Para isso, vai andar na string de tras pra frente, retirar uma operacao e substituir por 0 ou 1
    */
    public static String simplificarOperacao(String fraseOriginal){

        char bitDaOperacao;

        //not(A)
        //0123456789
        // or( A , B)

        // for externo para varrer a string multiplas vezes
        for(int k = fraseOriginal.length()-1; k > 5 && fraseOriginal.length() >= 5; k--){

            for (int i = fraseOriginal.length(); i > 9; i--) {

                //significa que eh um parenteses simples, ou seja, há apenas uma operacao
                boolean podeSimplificar = fraseOriginal.charAt(i) == ')' && fraseOriginal.charAt(i-1) != ')';
                boolean parentesesAchado = false;

                // so pro javac nao encher com o fato de nao ter inicializado
                int posicaoAberturaParenteses = 5;

                //preciso agora achar o parenteses  de abertura
                if(podeSimplificar){
                    for (int j = i-1; j > 5; j++) {
                        parentesesAchado = fraseOriginal.charAt(j) == '(';
                        if(parentesesAchado)
                            posicaoAberturaParenteses = j;
                    }
                }

                    /*
                    ATENCAO
                    Para passar na funcao reformatarString, é necessário passar a primeira letra da operacao ('a', por exemplo) e
                    o parenteses de fechamento, já que a funcao usa as duas posicoes como demarcadores, nao os incluindo
                    */

                //ja achei o parenteses de abertura e o de fechamento. Posso seguir com a respectiva operacao


                //operacao and e seus metodos
                if(podeSimplificar && parentesesAchado && fraseOriginal.charAt(posicaoAberturaParenteses-1) == 'd' && fraseOriginal.charAt(posicaoAberturaParenteses-2) == 'n' && fraseOriginal.charAt(posicaoAberturaParenteses-3) == 'a'){

                    bitDaOperacao = operacaoAND(cortarString(fraseOriginal, posicaoAberturaParenteses, i));
                    fraseOriginal = reformatarString(fraseOriginal, posicaoAberturaParenteses-2, i, bitDaOperacao);
                }

                //operacao or
                else if(podeSimplificar && parentesesAchado && fraseOriginal.charAt(posicaoAberturaParenteses-1) == 'r' && fraseOriginal.charAt(posicaoAberturaParenteses-2) == 'o'){

                    bitDaOperacao = operacaoOR(cortarString(fraseOriginal, posicaoAberturaParenteses, i));
                    fraseOriginal = reformatarString(fraseOriginal, posicaoAberturaParenteses-2, i, bitDaOperacao);
                }
                //operacao not
                else if(podeSimplificar && parentesesAchado && fraseOriginal.charAt(posicaoAberturaParenteses-1) == 't' && fraseOriginal.charAt(posicaoAberturaParenteses-2) == 'o' && fraseOriginal.charAt(posicaoAberturaParenteses-3) == 'n'){

                    bitDaOperacao = operacaoNOT(fraseOriginal.charAt(posicaoAberturaParenteses+1));
                    fraseOriginal = reformatarString(fraseOriginal, posicaoAberturaParenteses-3, i, bitDaOperacao);
                }
            }
        }

        // uma ultima varredura com o simplificar not
        fraseOriginal = simplificarNOT(fraseOriginal);

        return fraseOriginal;


    }


    /*
    serve para transformar a string original, com A B e etcs em uma so com a expressao booleana, alem de cortar fora o começo
    da string, deixando APENAS as instruções lógicas para evaluacao

    Então, "1 0 not(A)" viraria apenas "not(0)"

    */
    public static String substituirLetrasExpressao(String frase){

        String novaString = "";
        int numDeLetrasPegas = 0;

        //tirando 49 pela representação ascii
        int num = ((int) frase.charAt(0)) -48;

        //o 3o item da string contem o primeiro valor booleano
        int posicaoLetraPega = 2;

        //substituir na string as letras maiusculas pelos seus respectivos valores booleanos
        for(int i=0;i< frase.length();i++){

            //detectar letras maiusculas, que sao as substituicoes de 0 e 1, e substituir propriamente por 0 e 1
            if(frase.charAt(i) >= 'A' && frase.charAt(i) <= 'Z' && numDeLetrasPegas < num){
                //o primeiro bit logico eh sempre a 3a posicao da string
                novaString += frase.charAt(posicaoLetraPega);

                //soma um para o espaco e um para a prox letra
                posicaoLetraPega += 2;
                //soma ao contador de letras pegas, ja que uma foi detectada
                numDeLetrasPegas++;
            }
            else
                novaString += frase.charAt(i);
        }
        //tirando dois pra deixr na posicao da letra pega e nao no da proxima
        posicaoLetraPega -= 2;

        // 1 0 not(A)
        // 0123456789ABCDEF
        // 2 0 0 or( A , B)

        //ja substitui os valores booleanos. agora falta cortar a string, removendo os valores booleanos de inicio
        //  2*num+2 pois a cada string inserida o primeiro char da expressao estara a 2 chars de distancia depois da
        // ultima expressao booleana, ou seja, 2*num +2

        //quero que pare um char ANTES do n
        int inicioCorte = (num*2)+posicaoLetraPega -1;

        String resposta = cortarString(novaString, inicioCorte, novaString.length());
        MyIO.println("String com as letras substituidas: " + resposta);

        return resposta;

    }



    // cortar uma string incluindo todos os chars entre duas posicoes, excluindo essas duas posicoes
    public static String cortarString(String frase, int posIn, int posFin){

        String novaString = "";

        //começa na posicao seguinte da inserida e termina antes da final
        if(posIn > frase.length())
            MyIO.println("A posicao inicial estoura o length da string, tá dando pau nisso.");

        else if(posFin >  frase.length())
            MyIO.println("A posicao final estoura o length da string, tá dando pau nisso.");

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

        //salvar os chars da original aqui dentro
        for(int i = 0; i < anteriorFim; i++)
            anterior += fraseOriginal.charAt(i);

        for (int i = posteriorInicio+1; i < fraseOriginal.length(); i++) {
            posterior += fraseOriginal.charAt(i);
        }

        // adicionar as strings juntas agora. Nao fui direto antes para evitar arrayOutOfBounds, assim possuo os tamanhos adequados
        for(int i = 0;i<anterior.length(); i++)
            novaStringSubstituida += anterior.charAt(i);

        //agora que estou no meio posso jogar o que é pra inserir no meio
        novaStringSubstituida += substituir;

        //jogar o resto dentro, pra fechar a string e retornar
        for(int i = 0;i<posterior.length(); i++)
            novaStringSubstituida += anterior.charAt(i);

        return novaStringSubstituida;

    }

    //executar a operacao AND com os operandos binarios dentro
    public static char operacaoAND(String frase){
        char resultado = 1;
        //iterar a string pra procurar zeros
        for(int i=0;i<frase.length();i++){
            //se houver qualquer 0 o AND retornara false
            if(frase.charAt(i) == '0'){
                resultado = 0;
                i = frase.length();
            }
        }
        return resultado;
    }

    //executar a operacao OR com os operandos binarios dentro
    public static char operacaoOR(String frase){
        char resultado = 0;
        //iterar a string pra procurar 1s
        for(int i=0;i<frase.length();i++){
            //se houver qualquer 1 o or retornara true
            if(frase.charAt(i) == '1'){
                resultado = 1;
                i = frase.length();
            }
        }

        return resultado;

    }

    
    // operacao not
    public static char operacaoNOT(char bit){
        //flipar o valor binario da expressao
        //not(0)
        //01234
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
