import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
/*
Problemas:


*/

//celula: TAD com ponteiro para o proximo elemento, com o ultimo apontando como prox null.
class Celula {
    public TimeLista elemento;
    // elemento
    public Celula prox;

    public Celula() {

    }

    public Celula(TimeLista x) {
        this.elemento = x;
        this.prox = null;
    }
}

class ListaFlex {
    private Celula primeiro;
    private Celula ultimo;

    public ListaFlex() {
        primeiro = new Celula();
        ultimo = primeiro;
    }

    public ListaFlex(int n) {
        primeiro = new Celula();
        ultimo = primeiro;

    }

    public int tamanho() {
        Celula i = primeiro;
        int j = 0;
        // percorrer arranjo até que o prox seja null, ou seja, ultimo
        while (i.prox != null) {
            i = i.prox;
            j++;
        }
        return j;
    }

    public void inserirFim(TimeLista x) {
        Celula entrada = new Celula(x);
        ultimo.prox = entrada;
        ultimo = entrada;
        ultimo.prox = null;
    }

    public void inserirInicio(TimeLista x) {
        Celula tmp = new Celula(x);
        tmp.prox = primeiro.prox;
        primeiro.prox = tmp;
        if (primeiro == ultimo) {
            ultimo = tmp;
        }
        tmp = null;
    }

    public TimeLista removerFim() throws Exception {
        if (primeiro == ultimo)
            throw new Exception("Erro!");
        Celula i;
        for (i = primeiro; i.prox != ultimo; i = i.prox)
            ;

        TimeLista elemento = ultimo.elemento;
        ultimo = i;
        i = ultimo.prox = null;
        return elemento;
    }

    public TimeLista removerInicio() throws Exception {
        if (primeiro == ultimo)
            throw new Exception("Erro! Estrutura vazia");

        Celula tmp = primeiro;
        primeiro = primeiro.prox;
        TimeLista resp = primeiro.elemento;
        tmp.prox = null;
        tmp = null;
        return resp;

    }

    public void inserir(TimeLista x, int pos) throws Exception {
        // Inserir(6, 2)
        int tamanho = tamanho();

        if (pos < 0 || pos > tamanho)
            throw new Exception("Erro!");

        else if (pos == 0)
            inserirInicio(x);

        else if (pos == tamanho)
            inserirFim(x);

        else {
            Celula i = primeiro;

            for (int j = 0; j < pos; j++, i = i.prox)
                ;

            Celula tmp = new Celula(x);
            tmp.prox = i.prox;
            i.prox = tmp;
            tmp = i = null;
        }
    }

    public void mostrar() {
        Celula i = primeiro.prox;
        int j = 0;

        do{
            MyIO.print("["+j+"] ");
            i.elemento.imprimir();
            i = i.prox;
            j++;
        } while (i != null);

       // i.elemento.imprimir();
    }

    public TimeLista remover(int pos) throws Exception {
        // remover(1)
        TimeLista elemento;
        int tamanho = tamanho();
        if (primeiro == ultimo || pos < 0 || pos >= tamanho)
            throw new Exception("Erro!");
        else if (pos == 0)
            elemento = removerInicio();
        else if (pos == tamanho - 1)
            elemento = removerFim();
        else {
            Celula i = primeiro;
            for (int j = 0; j < pos; j++, i = i.prox);
            
            Celula tmp = i.prox;
            elemento = tmp.elemento;
            i.prox = tmp.prox;
            tmp.prox = null;
            i = tmp = null;
        }
        return elemento;
    }

}

class TimeLista {

    private String nome, apelido, estadio, tecnico, liga, nomeArquivo;
    private int capacidade, fundacaoDia, fundacaoMes, fundacaoAno;
    private long paginaTam;

    public TimeLista() {
        nome = apelido = estadio = tecnico = liga = nomeArquivo = "";
        paginaTam = capacidade = fundacaoDia = fundacaoMes = fundacaoAno = 0;
    }

    public TimeLista(String nomeArq) throws Exception {
        ler(nomeArq);
    }

    public static void main(String[] args) throws Exception {
        MyIO.setCharset("UTF-8");

        String[] entradas = new String[100];
        int quantidadeDeFrases = 0;

        // ler a entrada com os times até inserir FIM

        do {
            entradas[quantidadeDeFrases] = MyIO.readLine();
        } while (!estaNoFinal(entradas[quantidadeDeFrases++]));

        // tirar o fim da contagem
        quantidadeDeFrases--;

        // MyIO.println("Numero de modificacoes: "+quantidadeModificacoes);
        // array de referencias a objetos
        TimeLista[] conjuntoTimes = new TimeLista[quantidadeDeFrases];

        // criar os objetos de acordo.
        for (int i = 0; i < quantidadeDeFrases; i++) {
            // criar o objeto e chamar o construtor
            conjuntoTimes[i] = new TimeLista(entradas[i]);
        }

        // lista da questao
        // ler a quantidade de operacoes a serem realizadas
        int quantidadeModificacoes = Integer.parseInt(MyIO.readLine());

        ListaFlex listaDosTimes = new ListaFlex(quantidadeDeFrases + quantidadeModificacoes);

        // adicionar os times mencionados previamente no fim da lista
        for (int i = 0; i < quantidadeDeFrases; i++) {
            listaDosTimes.inserirFim(conjuntoTimes[i]);
        }

        //listaDosTimes.mostrar();

        /************* MÉTODOS DA LISTA DAQUI EM DIANT **********/

        // arranjo com string das operacoes
        String operacoes[] = new String[quantidadeModificacoes];

        // MyIO.println("Terminei de criar o arranjo, tudo ok por aqui.");

        for (int i = 0; i < quantidadeModificacoes; i++) {
            operacoes[i] = MyIO.readLine();
        }

        // MyIO.println("Terminei de inserir as entradas, tudo ok por aqui.");
        // ler e executar as operacoes
        for (int j = 0; j < quantidadeModificacoes; j++) {

            // metodos de insercao
            if (operacoes[j].charAt(0) == 'I') {
                // MyIO.println("Encontrei operacao de insercao");
                // criar e jogar o time no inicio
                if (operacoes[j].charAt(1) == 'I') {
                    TimeLista novaEntrada = new TimeLista(operacoes[j].substring(3));
                    listaDosTimes.inserirInicio(novaEntrada);
                }

                else if (operacoes[j].charAt(1) == 'F') {
                    TimeLista novaEntrada = new TimeLista(operacoes[j].substring(3));
                    listaDosTimes.inserirFim(novaEntrada);
                }
                // adicionar o time na posicao especificada
                else if (operacoes[j].charAt(1) == '*') {
                    TimeLista novaEntrada = new TimeLista(operacoes[j].substring(6));
                    int pos = Integer.parseInt(operacoes[j].substring(3, 5));
                    listaDosTimes.inserir(novaEntrada, pos);

                }
            }

            // metodos de remocao
            else if (operacoes[j].charAt(0) == 'R') {

                // MyIO.println("Encontrada operacao de remocao");

                if (operacoes[j].charAt(1) == 'I') {
                    TimeLista removido = listaDosTimes.removerInicio();
                    System.out.printf("(R) %s\n", removido.getNome());
                }

                else if (operacoes[j].charAt(1) == 'F') {
                    TimeLista removido = listaDosTimes.removerFim();
                    System.out.printf("(R) %s\n", removido.getNome());
                }

                // remover o time na posicao especificada
                else if (operacoes[j].charAt(1) == '*') {
                    int posRemover = Integer.parseInt(operacoes[j].substring(3, 5));
                    // MyIO.println("Pos removida: "+posRemover);
                    TimeLista removido = listaDosTimes.remover(posRemover);
                    System.out.printf("(R) %s\n", removido.getNome());
                }
            }

        }

        // metodo de printar tudo
        listaDosTimes.mostrar();

        // fim da main
    }

    // metodos set e get

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public String getLiga() {
        return liga;
    }

    public void setLiga(String liga) {
        this.liga = liga;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public int getFundacaoDia() {
        return fundacaoDia;
    }

    public void setFundacaoDia(int fundacaoDia) {
        this.fundacaoDia = fundacaoDia;
    }

    public int getFundacaoMes() {
        return fundacaoMes;
    }

    public void setFundacaoMes(int funcacaoMes) {
        this.fundacaoMes = funcacaoMes;
    }

    public int getFundacaoAno() {
        return fundacaoAno;
    }

    public void setFundacaoAno(int fundacaoAno) {
        this.fundacaoAno = fundacaoAno;
    }

    public long getPaginaTam() {
        return paginaTam;
    }

    public void setPaginaTam(long paginaTam) {
        this.paginaTam = paginaTam;
    }

    // metodo clone
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void imprimir() {
        System.out.println(toString());
    }

    public String toString() {
        String resp = nome + " ## " + apelido + " ## ";
        resp += ((fundacaoDia > 9) ? "" : "0") + fundacaoDia + ((fundacaoMes > 9) ? "/" : "/0") + fundacaoMes + "/"
                + fundacaoAno + " ## ";
        resp += estadio + " ## " + capacidade + " ## " + tecnico + " ## " + liga + " ## " + nomeArquivo + " ## "
                + paginaTam + " ## ";
        return resp;
    }

    public String removePunctuation(String campo) {
        campo = campo.replace(".", "");
        campo = campo.replace(",", "");
        campo = campo.replace(";", "");
        String resp = "";
        for (int i = 0; i < campo.length(); i++) {
            if (Character.isDigit(campo.charAt(i)))
                resp += campo.charAt(i);
            else
                i = campo.length();
        }

        return resp;
    }

    public int getMes(String campo) {
        int resp = 0;
        if (campo.contains("january") == true) {
            resp = 1;
        } else if (campo.contains("february") == true) {
            resp = 2;
        } else if (campo.contains("march") == true) {
            resp = 3;
        } else if (campo.contains("april") == true) {
            resp = 4;
        } else if (campo.contains("may") == true) {
            resp = 5;
        } else if (campo.contains("june") == true) {
            resp = 6;
        } else if (campo.contains("july") == true) {
            resp = 7;
        } else if (campo.contains("august") == true) {
            resp = 8;
        } else if (campo.contains("september") == true) {
            resp = 9;
        } else if (campo.contains("october") == true) {
            resp = 10;
        } else if (campo.contains("november") == true) {
            resp = 11;
        } else if (campo.contains("december") == true) {
            resp = 12;
        }
        return resp;
    }

    public String getSubstringEntre(String s, String antes, String depois) {
        String resp = "";
        int posInicio, posFim;

        posInicio = s.indexOf(antes) + antes.length();

        if (antes.compareTo(depois) != 0) {
            posFim = s.indexOf(depois);
        } else {
            posFim = s.indexOf(depois, posInicio);
        }

        if (0 <= posInicio && posInicio < posFim && posFim < s.length()) {
            resp = s.substring(posInicio, posFim);
        }

        return resp;
    }

    public String removeTags(String str) {

        String strRegEx = "<[^>]*>";

        str = str.replace("&#91;8&#93;", "");
        str = str.replace("\"", "");
        str = str.replace("&#91;1&#93;", "");
        str = str.replace(";note 1&#93;", " ");
        str = str.replace("&#91;1&#93;", " ");
        str = str.replace("&amp;", " ");
        str = str.replace("&#91;", " ");
        str = str.replace("&#91", " ");
        str = str.replace("1&#93", " ");
        str = str.replace("&#160;", " ");

        str = str.replaceAll(strRegEx, "");
        /*
         * //replace &nbsp; with space str = str.replace("&nbsp;", " "); //replace &amp;
         * with & str = str.replace("&amp;", "&");
         */

        // OR remove all HTML entities
        str = str.replaceAll("&.*?;", " ");

        return str;
    }

    public void ler(String nomeArquivo) throws Exception {

        FileReader file = new FileReader(nomeArquivo);
        BufferedReader buffer = new BufferedReader(file);
        String html = "";
        String line = buffer.readLine();
        while (line != null) {
            html += line;
            line = buffer.readLine();
        }

        buffer.close();
        file.close();

        html = html.substring(html.indexOf("Full name"));
        html = html.substring(0, html.indexOf("<table style"));
        String campos[] = html.split("<tr>");

        this.nomeArquivo = nomeArquivo;

        for (String campo : campos) {
            // Full name
            if (removeTags(campo).contains("Full name")) {
                campo = removeTags(campo);
                this.nome = campo.substring(9).trim();

                // Nickname(s)
            } else if (removeTags(campo).contains("Nickname(s)")) {
                campo = removeTags(campo);
                this.apelido = campo.substring(11).trim();

                // Founded
            } else if (removeTags(campo).toLowerCase().contains("founded")) {
                campo = removeTags(campo.split("<br />")[0]);
                this.fundacaoMes = this.getMes(campo.toLowerCase());

                if (this.fundacaoMes == 0) {
                    this.fundacaoDia = 0;
                    campo = campo.substring(7);
                    this.fundacaoAno = Integer.parseInt(campo.substring(0, 4));
                } else {
                    campo = campo.substring(7);
                    String data[] = campo.split(" ");
                    if (data.length < 3) {
                        this.fundacaoDia = 0;
                        this.fundacaoAno = Integer.parseInt(data[1].substring(0, 4));
                    } else {
                        if (campo.contains(",")) {
                            this.fundacaoDia = Integer.parseInt(data[1].replace("th", "").replace(",", ""));
                            this.fundacaoAno = Integer.parseInt(data[2].substring(0, 4));
                        } else if (Character.isDigit(data[0].charAt(0))) {
                            this.fundacaoDia = Integer.parseInt(data[0]);
                            this.fundacaoAno = Integer.parseInt(data[2].substring(0, 4));
                        } else {
                            this.fundacaoDia = 0;
                            this.fundacaoAno = Integer.parseInt(data[1].substring(0, 4));
                        }
                    }
                }

                // Ground
            } else if (removeTags(campo).toLowerCase().contains("ground")) {
                campo = removeTags(campo);
                this.estadio = campo.substring(6).trim();

                // Capacity
            } else if (removeTags(campo).toLowerCase().contains("capacity")) {
                campo = campo.split("<br")[0];
                campo = removeTags(campo.split("</td>")[0].replace(" ", ""));
                this.capacidade = Integer.parseInt(removePunctuation(campo.substring(8).split(";")[0]));

                // Coach
            } else if (removeTags(campo).toLowerCase().contains("coach") || campo.toLowerCase().contains("manager")) {
                campo = removeTags(campo).replace("(es)", "").trim();

                if (campo.toLowerCase().contains("coach")) {
                    int index = campo.toLowerCase().indexOf("coach");
                    this.tecnico = campo.substring(index + 5).trim();
                }

                else if (campo.toLowerCase().contains("manager") && (this.tecnico == null || this.tecnico.isEmpty())) {
                    int index = campo.toLowerCase().indexOf("manager");
                    this.tecnico = campo.substring(index + 7).trim();
                }
                // League
            } else if (removeTags(campo).contains("League") && (this.liga == null || this.liga.isEmpty())) {
                campo = removeTags(campo);
                this.liga = campo.substring(6).trim();
            }
        }

        File f = new File(nomeArquivo);
        setPaginaTam(f.length());

    }

    public static boolean estaNoFinal(String frase) {
        return (frase.length() >= 3 && frase.charAt(0) == 'F' && frase.charAt(1) == 'I' && frase.charAt(2) == 'M');
    }

}
