import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
import java.io.File;
/*
Pesquisa Sequencial em Java: Faça a inserção de alguns registros no final de uma Lista e,
em seguida, faça algumas pesquisas sequenciais. A chave primária de pesquisa será o atributo
nome. A entrada padrão é composta por duas partes onde a primeira é igual a entrada da
primeira questão. As demais linhas correspondem a segunda parte. A segunda parte é composta
por várias linhas. Cada uma possui um elemento que deve ser pesquisado na Lista. A última
linha terá a palavra FIM. A saı́da padrão será composta por várias linhas contendo as palavras
SIM/NÃO para indicar se existe cada um dos elementos pesquisados. Além disso, crie um
arquivo de log na pasta corrente com o nome matrı́cula sequencial.txt com uma única linha
contendo sua matrı́cula, tempo de execução do seu algoritmo e número de comparações. Todas
as informações do arquivo de log devem ser separadas por uma tabulação ’\t’.
*/


//classe com os metodos de lista da questao3
class ListaTime{

    private int n;
    private TimePesquisa[] listaDosTimes;

    public void inserirInicio(TimePesquisa adicao)throws Exception{
        if(n >= listaDosTimes.length)
            throw new Exception("Erro! Tamanho excedido");

        for (int i = n; i > 0; i--) {
            listaDosTimes[i] = listaDosTimes[i-1];
        }

        listaDosTimes[0] = adicao;
        n++;
    }
    public void inserirFim(TimePesquisa adicao)throws Exception{
        if(n >= listaDosTimes.length)
            throw new Exception("Erro! Tamanho excedido");

        listaDosTimes[n] = adicao;
        n++;
    }
    //remover o inicio e retornar o que ele era
    public TimePesquisa removerInicio() throws Exception{
        if(n == 0)
            throw new Exception("Erro! não dá pra tirar o inicio de um array de tam 0");

        TimePesquisa resp = listaDosTimes[0];
        n--;

        //puxar os times uma posicao pra baixo
        for (int i = 0; i < n; i++) {
            listaDosTimes[i] = listaDosTimes[i+1];
        }

        return resp;
    }

    public TimePesquisa removerFim() throws Exception{
        if(n == 0)
            throw new Exception("Erro! não dá pra tirar o inicio de um array de tam 0");

        //subtrai do n e dois acessa a posicao
        return listaDosTimes[--n];
    }

    //mover os itens da pos até n uma posicao pra frente,
    //pra depois inserir em POS
    public void inserir(TimePesquisa adicao, int pos)throws Exception{
        if(n >= listaDosTimes.length || pos < 0 || pos > n)
            throw new Exception("Erro! Tamanho excedido");

        for (int i = n; i > pos; i--) {
            listaDosTimes[i] = listaDosTimes[i-1];
        }

        listaDosTimes[pos] = adicao;
        n++;
    }
    public TimePesquisa remover(int pos) throws Exception{
        if(n == 0 || pos < 0 || pos >= n)
            throw new Exception("Erro! não dá pra tirar essa posição");

        TimePesquisa resp = listaDosTimes[pos];
        n--;

        //puxar os times uma posicao pra baixo, começando de pos
        for (int i = pos; i < n; i++) {
            listaDosTimes[i] = listaDosTimes[i+1];
        }

        return resp;
    }


    public int pesquisar(String chave) throws Exception{
        boolean igualdade = false;
        int comparacoes = 0;
       
        for(int i = 0; i < listaDosTimes.length; i++){  
    
            //MyIO.println("\tComparação atual: "+listaDosTimes[i].getNome()+ " vs " + chave);
            if(listaDosTimes[i].getNome().contains(chave)){
                //MyIO.println("\nSão iguais\n");
                igualdade = true;
                i = listaDosTimes.length;
            }
            comparacoes++;
        }

        // printar resultados da comparação
        if(igualdade)
            System.out.println("SIM");

        else 
            System.out.println("NÃO");

        return comparacoes;
    }

    public void mostrar(){
        //chamar a funcao de mostrar pros i elementos
        for (int i = 0; i < n; i++) {
            MyIO.print("[" + i + "] ");
            listaDosTimes[i].imprimir();
        }
    }


    ListaTime(){
    }

    ListaTime(int num){
        //setar o num pra zero
        this.n = 0;
        this.listaDosTimes = new TimePesquisa[num];
    }
}


class TimePesquisa {

    private String nome, apelido, estadio, tecnico, liga, nomeArquivo;
    private int capacidade, fundacaoDia, fundacaoMes, fundacaoAno;
    private long paginaTam;

    public TimePesquisa() {
        nome = apelido = estadio = tecnico = liga = nomeArquivo = "";
        paginaTam = capacidade = fundacaoDia = fundacaoMes = fundacaoAno = 0;
    }
    public TimePesquisa(String nomeArq) throws Exception{
        ler(nomeArq);
    }

    public static void main(String[] args) throws Exception {

        long inicio = new Date().getTime();
        MyIO.setCharset("UTF-8");

        String[] entradas = new String[1000];
        int quantidadeDeFrases = 0;


        //ler a entrada com os times até inserir FIM
        do{
            entradas[quantidadeDeFrases] = MyIO.readLine();
        }while(!estaNoFinal(entradas[quantidadeDeFrases++]));

        //tirar o fim da contagem
        quantidadeDeFrases--;
        

        //array de referencias a objetos de "Time"
        TimePesquisa[] conjuntoTimes = new TimePesquisa[quantidadeDeFrases];
        
        //criar os objetos de acordo.
        for (int i = 0; i < quantidadeDeFrases; i++) {
            //criar o objeto e chamar o construtor
            conjuntoTimes[i] = new TimePesquisa(entradas[i]);
        }
        
        // criar a lista da questao 
        ListaTime listaDosTimes = new ListaTime(quantidadeDeFrases);


        // adicionar os times mencionados previamente no fim da lista
        for (int i = 0; i < quantidadeDeFrases; i++) {
            listaDosTimes.inserirFim(conjuntoTimes[i]);
        }


        /*************MÉTODOS DA PESQUISA DAQUI EM DIANT**********/

        //resetar o contador;
        quantidadeDeFrases = 0;

        //entradas conterá as chaves de pesquisa.
        //ler a entrada com os times até inserir FIM
        do{
            entradas[quantidadeDeFrases] = MyIO.readLine();
        }while(!estaNoFinal(entradas[quantidadeDeFrases++]));

        //tirar o fim da contagem
        quantidadeDeFrases--;

        // armazenar quantas comparacoes foram feitas
        int totalComparacoes = 0;

        //executar a busca na lista e printar
        for (int i = 0; i < quantidadeDeFrases; i++) {
            //MyIO.println("Entrada atual: "+i);
            totalComparacoes += listaDosTimes.pesquisar(entradas[i]);
        }

        long fim = new Date().getTime();

        long execucao = fim-inicio;
        Arq.openWrite("649651_sequencial.txt");
        
        Arq.print("649651\t"+execucao+"\t"+totalComparacoes+"\t");

        Arq.close();
        //fim da main
    }

    //metodos set e get

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
    //metodo clone
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
			//replace &nbsp; with space
			str = str.replace("&nbsp;", " ");
			//replace &amp; with &
			str = str.replace("&amp;", "&");
			*/

        //OR remove all HTML entities
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
    

    public static boolean estaNoFinal(String frase){
        return (frase.length() >= 3 && frase.charAt(0) == 'F' && frase.charAt(1) == 'I' && frase.charAt(2) == 'M');
    }

}
