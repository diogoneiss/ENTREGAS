import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class LeituraHTML {


    //tamanho max da entrada
    static final int TAM_ENTRADA = 10000;

    public static void main(String[] args) throws Exception{


        //setar charset
        MyIO.setCharset("ISO-8859-1");

        //criar array
        String[] entradas = new String[TAM_ENTRADA];

        //calculo do tamanho real. Começa em -1 pq vai ser incrementado
        int tamanhoRealEntradas = -1;

        do{
            tamanhoRealEntradas++;
            //ler o link e nome. Entradas impares sao link
            entradas[tamanhoRealEntradas] = MyIO.readLine();
            //verificar se a entrada anterior nao era o fim da entrada

        }while(!estaNoFim(entradas[tamanhoRealEntradas]));

        //iterar todas as entradas e executar os metodos adequados
        for(int i=0;i<tamanhoRealEntradas;i+=2){
            //pares sao os nomes e impares os links


            String address =  entradas[i+1];
            String line = "";
            String resp = "";

            //leitura de link, código dado pelo Max
            try {
                URL url = new URL(address);
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                line = br.readLine();
                while (line != null) {
                    resp += line;
                    line = br.readLine();
                }
                br.close();
            } catch (MalformedURLException excecao) {
                excecao.printStackTrace();
            } catch (IOException excecao) {
                excecao.printStackTrace();
            }

            int[] contadorGlobal = contadorLetras(resp);

            printarRespostas(resp, entradas[i], contadorGlobal);

        }

    }
    /*
     *Metodo para printar todas as respostas referentes a uma string, chamando
     os 4 metodos do enunciado
     */

    public static void printarRespostas(String frase, String pagina, int[] contadorGlobal){

        char [] conjuntoVogais = {'a','e','i','o','u','á','é','í','ó','ú','à','è','ì','ò','ù','ã','õ','â','ê','î','ô','û'};

        for (int i = 0; i < conjuntoVogais.length; i++) {
            MyIO.print(conjuntoVogais[i]+ "(" +contadorGlobal[i]+") ");
        }

        //printar contador de nao vogais
        MyIO.print("consoante(" +contadorGlobal[22]+") ");
        MyIO.print("<br>(" +contadorGlobal[23]+") ");
        MyIO.print("<table>(" +contadorGlobal[24]+") ");
        //printar nome pagina
        MyIO.println(pagina);

    }
    public static int[] contadorLetras(String codigo){
        //posicao na string html gigante

        int[] contadorLocal = new int[25];

        int posicaoCodigo;

        char [] conjuntoVogais    = {'a','e','i','o','u','á','é','í','ó','ú','à','è','ì','ò','ù','ã','õ','â','ê','î','ô','û'};

        //contador: 0 a 21 primeiras posicoes sao para contar vogais e variacoes
        //contador:  22 sao as consoantes
        //contador: 23 é <br>, 24 <table>, 25 nome da pagina


        //Passar pelo codigo comparando
        for(posicaoCodigo= 0; posicaoCodigo < codigo.length(); posicaoCodigo++){

            char charNaPosicao = codigo.charAt(posicaoCodigo);

            boolean ehTag = false;
            boolean ehVogal = false;

            //tag <br>
            if(ehIgualBr(codigo, posicaoCodigo)){
                contadorLocal[23]++;
                ehTag = true;
                posicaoCodigo += "<br>".length();
            }
            //tag <table>
            else if(ehIgualTable(codigo, posicaoCodigo)){
                contadorLocal[24]++;
                ehTag = true;
                posicaoCodigo += "<table>".length();
            }

            //contar as vogais
            if(!ehTag){
                for (int i = 0; i < conjuntoVogais.length; i++) {
                    if(charNaPosicao == conjuntoVogais[i]){
                        contadorLocal[i]++;
                        ehVogal = true;

                        // sair do for
                        i = conjuntoVogais.length;
                    }
                }
            }

            //contar as consoantes
            if(!ehTag && !ehVogal){
                if(charNaPosicao > 'a' && charNaPosicao <= 'z'){
                    contadorLocal[22]++;
                }
            }

        }//fim for
        return contadorLocal;
    }

    //metodo ehIgualBr
    //verifica se o char é < e os proximos br>
    public static boolean ehIgualBr(String codigo, int posicao){
        boolean serBr = false;
        //evitar erros
        if(posicao+3 < codigo.length()){
            serBr = codigo.charAt(posicao) == '<' && codigo.charAt(posicao+1) == 'b' && codigo.charAt(posicao+2) == 'r'
                    && codigo.charAt(posicao+3) == '>';
        }
        return serBr;
    }

    //metodo ehIgual table
    public static boolean ehIgualTable(String codigo, int posicao){
        boolean serTable = false;
        //evitar erros
        if(posicao+6 < codigo.length()){
            serTable = codigo.charAt(posicao) == '<' && codigo.charAt(posicao+1) == 't' && codigo.charAt(posicao+2) == 'a'
                    && codigo.charAt(posicao+3) == 'b' && codigo.charAt(posicao+4) == 'l' && codigo.charAt(posicao+5) == 'e'
                    && codigo.charAt(posicao+6) == '>';
        }
        return serTable;
    }


    //verificar fim da entrada
    static boolean estaNoFim(String frase){
        return (frase.length() >= 3 && frase.charAt(0) == 'F' && frase.charAt(1) == 'I' && frase.charAt(2) == 'M');
    }

}//fim classe
    