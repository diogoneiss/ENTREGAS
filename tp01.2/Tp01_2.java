import java.io.BufferedReader;
import java.io.FileReader;

//classe com os metodos de lista da questao3
class ListaTime{

    private int n;
    private Time[] listaDosTimes;

    public void inserirInicio(Time adicao)throws Exception{
        if(n >= listaDosTimes.length)
            throw new Exception("Erro! Tamanho excedido");

        for (int i = n; i > 0; i--) {
            listaDosTimes[i] = listaDosTimes[i-1];
        }

        listaDosTimes[0] = adicao;
        n++;
    }
    public void inserirFim(Time adicao)throws Exception{
        if(n >= listaDosTimes.length)
            throw new Exception("Erro! Tamanho excedido");

        listaDosTimes[n] = adicao;
        n++;
    }
    //remover o inicio e retornar o que ele era
    public Time removerInicio() throws Exception{
        if(n == 0)
            throw new Exception("Erro! não dá pra tirar o inicio de um array de tam 0");

        Time resp = listaDosTimes[0];
        n--;

        //puxar os times uma posicao pra baixo
        for (int i = 0; i < n; i++) {
            listaDosTimes[i] = listaDosTimes[i+1];
        }

        return resp;
    }

    public Time removerFim() throws Exception{
        if(n == 0)
            throw new Exception("Erro! não dá pra tirar o inicio de um array de tam 0");

        //subtrai do n e dois acessa a posicao
        return listaDosTimes[--n];
    }

    //mover os itens da pos até n uma posicao pra frente,
    //pra depois inserir em POS
    public void inserir(Time adicao, int pos)throws Exception{
        if(n >= listaDosTimes.length || pos < 0 || pos > n)
            throw new Exception("Erro! Tamanho excedido");

        for (int i = n; i > pos; i--) {
            listaDosTimes[i] = listaDosTimes[i-1];
        }

        listaDosTimes[pos] = adicao;
        n++;
    }
    public Time remover(int pos) throws Exception{
        if(n == 0 || pos < 0 || pos >= n)
            throw new Exception("Erro! não dá pra tirar essa posição");

        Time resp = listaDosTimes[pos];
        n--;

        //puxar os times uma posicao pra baixo, começando de pos
        for (int i = pos; i < n; i++) {
            listaDosTimes[i] = listaDosTimes[i+1];
        }

        return resp;
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
        this.listaDosTimes = new Time[num+1];
    }

}

public class Tp01_2 {
    public static void main(String[] args) throws Exception{

        MyIO.setCharset("UTF-8");

        String[] entradas = new String[100];
        int quantidadeDeFrases = 0;


        //ler a entrada com os times até inserir FIM

        do{
            entradas[quantidadeDeFrases] = MyIO.readLine();
        }while(!estaNoFinal(entradas[quantidadeDeFrases++]));

        //tirar o fim da contagem
        quantidadeDeFrases--;
        

        //MyIO.println("Numero de modificacoes: "+quantidadeModificacoes);
        //array de referencias a objetos
        Time[] conjuntoTimes = new Time[quantidadeDeFrases];
        
        //criar os objetos de acordo.
        for (int i = 0; i < quantidadeDeFrases; i++) {
            //criar o objeto e chamar o construtor
            conjuntoTimes[i] = new Time(entradas[i]);
        }
        
        // lista da questao
        //ler a quantidade de operacoes a serem realizadas
        int quantidadeModificacoes = Integer.parseInt(MyIO.readLine());
        
        ListaTime listaDosTimes = new ListaTime(quantidadeDeFrases + quantidadeModificacoes);


        // adicionar os times mencionados previamente no fim da lista
        for (int i = 0; i < quantidadeDeFrases; i++) {
            listaDosTimes.inserirFim(conjuntoTimes[i]);
        }

//        listaDosTimes.mostrar();

        /*************MÉTODOS DA LISTA DAQUI EM DIANT**********/

        // arranjo com string das operacoes
        String operacoes[] = new String[quantidadeModificacoes];

        //MyIO.println("Terminei de criar o arranjo, tudo ok por aqui.");

        for(int i = 0; i < quantidadeModificacoes; i ++){
            operacoes[i] = MyIO.readLine();
        }

        //MyIO.println("Terminei de inserir as entradas, tudo ok por aqui.");
        // ler e executar as operacoes
        for(int j = 0; j< quantidadeModificacoes; j++){

            //metodos de insercao
            if(operacoes[j].charAt(0) == 'I'){
                //MyIO.println("Encontrei operacao de insercao");
                // criar e jogar o time no inicio
                if(operacoes[j].charAt(1) == 'I') {
                    Time novaEntrada = new Time(operacoes[j].substring(3));
                    listaDosTimes.inserirInicio(novaEntrada);
                }

                else if(operacoes[j].charAt(1) == 'F') {
                    Time novaEntrada = new Time(operacoes[j].substring(3));
                    listaDosTimes.inserirFim(novaEntrada);
                }
                //adicionar o time na posicao especificada
                else if(operacoes[j].charAt(1) == '*') {
                    Time novaEntrada = new Time(operacoes[j].substring(6));
                    int pos = Integer.parseInt(operacoes[j].substring(3, 5));
                    listaDosTimes.inserir(novaEntrada, pos);

                }
            }

            //metodos de remocao
            else if(operacoes[j].charAt(0) == 'R'){

                //MyIO.println("Encontrada operacao de remocao");

                if(operacoes[j].charAt(1) == 'I'){
                    Time removido = listaDosTimes.removerInicio();
                    System.out.printf("(R) %s\n", removido.getNome());
                }

                else if(operacoes[j].charAt(1) == 'F'){
                    Time removido = listaDosTimes.removerFim();
                    System.out.printf("(R) %s\n", removido.getNome());
                }

                //remover o time na posicao especificada
                else if(operacoes[j].charAt(1) == '*'){
                    int posRemover = Integer.parseInt(operacoes[j].substring(3, 5));
                    //MyIO.println("Pos removida: "+posRemover);
                    Time removido = listaDosTimes.remover(posRemover);
                    System.out.printf("(R) %s\n", removido.getNome());
                }
            }

        }

        // metodo de printar tudo
        listaDosTimes.mostrar();


        //fim da main
    }

    //ler se é fim de entrada
    public static boolean estaNoFinal(String frase){
        return (frase.length() >= 3 && frase.charAt(0) == 'F' && frase.charAt(1) == 'I' && frase.charAt(2) == 'M');
    }

}


class Time{

    private String nome;
    private String apelido;
    private String estadio;
    private String tecnico;
    private String liga;
    private String nomeArquivo;
    private int capacidade;
    private int fundacaoDia;
    private int funcacaoMes;
    private int fundacaoAno;
    private long paginaTam;

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

    public int getFuncacaoMes() {
        return funcacaoMes;
    }

    public void setFuncacaoMes(int funcacaoMes) {
        this.funcacaoMes = funcacaoMes;
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
    //pegar o arquivo e transformar em uma linha com as infos que eu quero
    public String lerTime(String nomeArquivo) throws Exception{
        //MyIO.setCharset("UTF-8");
        FileReader arquivoTime = new FileReader(nomeArquivo);
        BufferedReader br = new BufferedReader(arquivoTime);
        String html = "";
        String line = br.readLine();

        //ler até o fim do arquivo e dar append em outra string

        while(line != null){
            html += line;
            line = br.readLine();
        }

        br.close();
        arquivoTime.close();

        //cortar a string
        html = html.substring(html.indexOf("Full name"));
        html = html.substring(0, html.indexOf("<table style"));

        //limpar a string de todo & alguma coisa
        html = html.replace("&#91;1&#93;", "");
        html = html.replace("&#91", "");
        html = html.replace(";note 1&#93", "");
        html = html.replace(";1&#93", "");
        html = html.replace("&#91", "");
        html = html.replace("&amp;", "");


        return html;
    }

    public void ler(String nomeArquivo) throws Exception{

        String linhaCompleta = lerTime(nomeArquivo);
        
        setNome(MetodosHTML.pegarNomeCompleto(linhaCompleta));
        setApelido(MetodosHTML.pegarApelido(linhaCompleta));
        
        int[] dataFundacao = MetodosHTML.pegarDatas(linhaCompleta);
        // o metodo retorna ano, mes dia
        // NOTA: Caso seja menor que 10, preciso inserir o 0 antes manualmente
        setFundacaoAno(dataFundacao[0]);
        setFuncacaoMes(dataFundacao[1]);
        setFundacaoDia(dataFundacao[2]);

        setEstadio(MetodosHTML.pegarEstadio(linhaCompleta));
        setCapacidade(MetodosHTML.pegarCapacidade(linhaCompleta));
        setTecnico(MetodosHTML.pegarTecnico(linhaCompleta));
        setLiga(MetodosHTML.pegarLiga(linhaCompleta));
        setNomeArquivo(MetodosHTML.pegarNomeArquivo(nomeArquivo));
        setPaginaTam(MetodosHTML.pegarTamanhoArquivo(nomeArquivo));
        
    }


    public void imprimir(){

        MyIO.print(getNome());
        MyIO.print(" ## "+getApelido());

        System.out.printf(" ## %02d/%02d/%04d", getFundacaoDia(), getFuncacaoMes(), getFundacaoAno());
        MyIO.print(" ## "+getEstadio());




        MyIO.print(" ## "+getCapacidade());
        MyIO.print(" ## "+getTecnico());
        MyIO.print(" ## "+getLiga());
        MyIO.print(" ## "+getNomeArquivo());
        MyIO.println(" ## "+getPaginaTam() +" ## ");

    }

    //construtores

    Time(){

    }

    Time(String nomeArquivo) throws Exception{
        //criar e atribuir todos os parametros
        ler(nomeArquivo);

    }
}

// metodos de manipulação do html
class MetodosHTML {
    //metodo serve pra tirar todas as entradas com "&... ;"
    public static String limparAmpersands(String fraseFiltrar){
        String aux = "";

        boolean ampersandAchado = false;
        int tam = fraseFiltrar.length();

        for (int i = 0; i < tam; i++) {
            if(fraseFiltrar.charAt(i) == '&')
                ampersandAchado = true;
            else if(ampersandAchado && fraseFiltrar.charAt(i) == ';')
                ampersandAchado = false;
            else if(!ampersandAchado)
                aux += fraseFiltrar.charAt(i);
        }

        return aux;
    }

    //pegar o nome do time completo
    public static String pegarNomeCompleto(String nome){
        //Padrão: Full name</th><td>
        String fraseFiltrar = "";
        boolean aberturaTag = false;
        //to colocando -9 pra evitar outOfBounds
        for (int i = 0; i < (nome.length()-9); i++) {
            if(nome.charAt(i) == 'F' && nome.charAt(i+1) == 'u' && nome.charAt(i+2) == 'l'&& nome.charAt(i+3) == 'l'
                    && nome.charAt(i+5) == 'n' && nome.charAt(i+6) == 'a' && nome.charAt(i+7) == 'm' && nome.charAt(i+8) == 'e'){
                //dar append na string até encontrar a tag de fim
                //i+17 é o ultimo char '>', entao somo 18
                for(int j=i+18; j< nome.length(); j++){
                    //se for fim sai
                    //verificar se encontrou o fim da tag
                    if(nome.charAt(j) == '<' && nome.charAt(j+1) == '/' && nome.charAt(j+2) == 't' && nome.charAt(j+3) == 'd')
                        j = nome.length();

                    else if(nome.charAt(j) == '<')
                        aberturaTag = true;

                    else if(aberturaTag && nome.charAt(j) == '>')
                        aberturaTag = false;

                    else if(!aberturaTag)
                        fraseFiltrar += nome.charAt(j);
                }
                i = nome.length();
            }        }
        return fraseFiltrar;
    }

    public static String pegarApelido(String nome){
        /*Padrão:
        class="nickname"><i>Der Club</i> (The Club)<br /><i>Die
        Legende</i> (The Legend)<br /><i>Der Ruhmreiche</i> (The Glorious)</td>
        */
        String fraseFiltrar = "";

        //to colocando -7 pra evitar outOfBounds
        //esse for é pra colocar a frase entre os tds, pra eu limpar depois

        boolean aberturaTag = false;

        for (int i = 0; i < (nome.length()-7); i++) {
            if(nome.charAt(i) == 'n' && nome.charAt(i+1) == 'i' && nome.charAt(i+2) == 'c'&& nome.charAt(i+3) == 'k'
                    && nome.charAt(i+4) == 'n' && nome.charAt(i+5) == 'a' && nome.charAt(i+6) == 'm' && nome.charAt(i+7) == 'e'){
                //dar append na string até encontrar a tag de fim
                //+13 pra começar exatemente depois do >
                for(int j=i+10;j<nome.length()-7; j++){

                    //verificar se encontrou o fim da tag
                    if(nome.charAt(j) == '<' && nome.charAt(j+1) == '/' && nome.charAt(j+2) == 't' && nome.charAt(j+3) == 'd')
                        j = nome.length();

                    else if(nome.charAt(j) == '<')
                        aberturaTag = true;

                    else if(aberturaTag && nome.charAt(j) == '>')
                        aberturaTag = false;

                    else if(!aberturaTag)
                        fraseFiltrar += nome.charAt(j);
                }
                i = nome.length();
            }

        }
        //remover as tags
        fraseFiltrar = fraseFiltrar.replaceAll("</i>", "");
        fraseFiltrar = fraseFiltrar.replaceAll("<i>", "");
        fraseFiltrar = fraseFiltrar.replaceAll("<br />", "");

        // retirar todas as aspas duplas da string
        fraseFiltrar = fraseFiltrar.replace("\"","");
        return fraseFiltrar;
    }

    public static String pegarEstadio(String nome){
        /*
        Padrão: Ground</th><td class="label">
        <ahref="/wiki/Max-Morlock-Stadion" title="Max-Morlock-Stadion">Max-Morlock-Stadion</a>
        */

        String fraseFiltrar = "";
        int posicaoGround = 0;

        //to colocando -5 pra evitar outOfBounds
        //esse for é pra achar o Ground, que logo em segida haverá o nome do estádio
        for (int i = 0; i < (nome.length()-6); i++) {
            if(nome.charAt(i) == 'G' && nome.charAt(i+1) == 'r' && nome.charAt(i+2) == 'o'&& nome.charAt(i+3) == 'u'
                    && nome.charAt(i+4) == 'n' && nome.charAt(i+5) == 'd'){
                posicaoGround = i+6;
                i = nome.length();
            }
        }
        //agora que achei o "Ground", posso procurar o title e as apsas francesas

        boolean ehTag = false;

        for(int j = posicaoGround; j < nome.length()-3; j++){

            //quando chegar em </td acabou a tag
            boolean fimDaLeitura = nome.charAt(j) == '<' && nome.charAt(j+1) == '/' && nome.charAt(j+2) == 't'
                    && nome.charAt(j+3) == 'd' ;

            if(fimDaLeitura)
                j = nome.length();

            else if(nome.charAt(j) == '<')
                ehTag = true;

            else if(ehTag && nome.charAt(j) == '>')
                ehTag = false;

            else if(!ehTag)
                fraseFiltrar += nome.charAt(j);
        }

        return fraseFiltrar;
    }
    public static int[] pegarDatas(String nome){
        /*
        Padrão: class="bday dtstart published updated">1900-05-04<
        */
        String fraseFiltrar = "";
        int posicaoBday= 0;

        //to colocando -3 pra evitar outOfBounds
        //esse for é pra achar o Ground, que logo em segida haverá o nome do estádio
        for (int i = 0; i < (nome.length()-3); i++) {
            if(nome.charAt(i) == 'b' && nome.charAt(i+1) == 'd' && nome.charAt(i+2) == 'a'&& nome.charAt(i+3) == 'y'){
                posicaoBday = i+3;
                i = nome.length();
            }
        }
        //agora que achei o "bday", posso procurar as aspas francesas.
        for (int i = posicaoBday; i < nome.length(); i++) {
            if(nome.charAt(i) == '>'){
                posicaoBday = i+1;
                i = nome.length();
            }
        }

        //adicionarei ano, mes e dia, respectivamente, num int[] só.

        //Falta recortar a string com tudo
        for (int i = posicaoBday; i < nome.length(); i++) {

            if(nome.charAt(i) == '<')
                i = nome.length();
            else
                fraseFiltrar += nome.charAt(i);
        }

        /*
        Essa parte abaixo serve para o caso de alguns times que nao tem o "bday" na classe
        */

        //se eu nao tiver achado bday significa que so há founded no .html
        if(fraseFiltrar.length() < 3){
            for (int i = 0; i < (nome.length()-3); i++) {
                if(nome.charAt(i) == 'F' && nome.charAt(i+1) == 'o' && nome.charAt(i+2) == 'u'&& nome.charAt(i+3) == 'n' && nome.charAt(i+4) == 'd'
                        && nome.charAt(i+5) == 'e' && nome.charAt(i+6) == 'd'){
                    posicaoBday = i+3;
                    i = nome.length();
                }
            }
            //agora que achei o "Founded", posso procurar os <td>.
            for (int i = posicaoBday; i < nome.length(); i++) {
                if(nome.charAt(i) == 'd' && nome.charAt(i+1) == '>'){
                    posicaoBday = i+2;
                    i = nome.length();
                }
            }
            //adicionarei ano, mes e dia, respectivamente num int[] só.
            //Falta recortar a string com tudo
            for (int i = posicaoBday; i < nome.length(); i++) {

                if(nome.charAt(i) == '<')
                    i = nome.length();
                else
                    fraseFiltrar += nome.charAt(i);
            }

        }
        if(fraseFiltrar.contains("th"))
            fraseFiltrar = fraseFiltrar.replace("th", "");

        //agora vou verificar se há algum mes no meio da string.
        for(int j = 0; j <fraseFiltrar.length(); j++){
            boolean ehLetra = fraseFiltrar.charAt(j) >= 'a' && fraseFiltrar.charAt(j) <= 'z';
            if(ehLetra && j<4){
                j = fraseFiltrar.length();
                //chamar funcao pra reformatar a string, já que ha um mes no comeco
                fraseFiltrar = MetodosHTML.mesNoComeco(fraseFiltrar);
            }
            else if(ehLetra){
                //significa que a string de mes está no meio
                j = fraseFiltrar.length();
                fraseFiltrar = MetodosHTML.mesNoMeio(fraseFiltrar);
            }

        }


        //adicionar 00-00 caso seja uma frase somente com ano
        if(fraseFiltrar.length() < 5)
            fraseFiltrar += "-00-00";
        else if (fraseFiltrar.length() <= 8)
            fraseFiltrar += "-00";

        //splitar no separador
        String[] conjuntoData = fraseFiltrar.split("-");


        //array pra guardar ano, mes e dia, respectivamente
        int[] inteirosData = new int[3];

        for(int i = 0; i<3;i++)
            inteirosData[i] = Integer.parseInt(conjuntoData[i]);

        //se nao tiver data é pra setar 0 de dia e mes


        //retorno na forma ano, mes, dia
        return inteirosData;
    }
    public static String mesNoComeco(String fraseFiltrar){

        //replaces de mes por num correspondente
        if(fraseFiltrar.contains("January"))
            fraseFiltrar = fraseFiltrar.replace("January", "01");

        else if(fraseFiltrar.contains("February"))
            fraseFiltrar = fraseFiltrar.replace("February", "02");

        else if(fraseFiltrar.contains("March"))
            fraseFiltrar = fraseFiltrar.replace("March", "03");

        else if(fraseFiltrar.contains("April"))
            fraseFiltrar = fraseFiltrar.replace("April", "04");

        else if(fraseFiltrar.contains("May"))
            fraseFiltrar = fraseFiltrar.replace("May", "05");

        else if(fraseFiltrar.contains("June"))
            fraseFiltrar = fraseFiltrar.replace("June", "06");

        else if(fraseFiltrar.contains("July"))
            fraseFiltrar = fraseFiltrar.replace("July", "07");

        else if(fraseFiltrar.contains("August"))
            fraseFiltrar = fraseFiltrar.replace("August", "08");

        else if(fraseFiltrar.contains("September"))
            fraseFiltrar = fraseFiltrar.replace("September", "09");

        else if(fraseFiltrar.contains("October"))
            fraseFiltrar = fraseFiltrar.replace("October", "10");

        else if(fraseFiltrar.contains("November"))
            fraseFiltrar = fraseFiltrar.replace("November", "11");

        else
            fraseFiltrar = fraseFiltrar.replace("December", "12");

        //apagar virgula se necessário
        if(fraseFiltrar.contains(","))
            fraseFiltrar = fraseFiltrar.replace(",", "");

        fraseFiltrar = fraseFiltrar.replace(" ", "-");
        // 07 Januray 1914
        // 01/07/0914
        String temp = "";

        //reorganizar a string
        String datas[] = fraseFiltrar.split("-");

        temp += datas[2];
        temp += '-';
        temp += datas[1];
        temp += '-';
        temp += datas[0];

        fraseFiltrar = temp;

        return fraseFiltrar;
    }

    public static String mesNoMeio(String fraseFiltrar){

        if(fraseFiltrar.contains("January"))
            fraseFiltrar = fraseFiltrar.replace("January", "01");

        else if(fraseFiltrar.contains("February"))
            fraseFiltrar = fraseFiltrar.replace("February", "02");

        else if(fraseFiltrar.contains("March"))
            fraseFiltrar = fraseFiltrar.replace("March", "03");

        else if(fraseFiltrar.contains("April"))
            fraseFiltrar = fraseFiltrar.replace("April", "04");

        else if(fraseFiltrar.contains("May"))
            fraseFiltrar = fraseFiltrar.replace("May", "05");

        else if(fraseFiltrar.contains("June"))
            fraseFiltrar = fraseFiltrar.replace("June", "06");

        else if(fraseFiltrar.contains("July"))
            fraseFiltrar = fraseFiltrar.replace("July", "07");

        else if(fraseFiltrar.contains("August"))
            fraseFiltrar = fraseFiltrar.replace("August", "08");

        else if(fraseFiltrar.contains("September"))
            fraseFiltrar = fraseFiltrar.replace("September", "09");

        else if(fraseFiltrar.contains("October"))
            fraseFiltrar = fraseFiltrar.replace("October", "10");

        else if(fraseFiltrar.contains("November"))
            fraseFiltrar = fraseFiltrar.replace("November", "11");

        else
            fraseFiltrar = fraseFiltrar.replace("December", "12");

        //apagar virgula se necessário
        if(fraseFiltrar.contains(","))
            fraseFiltrar = fraseFiltrar.replace(",", "");

        fraseFiltrar = fraseFiltrar.replace(" ", "-");
        //11-25-1939
        fraseFiltrar = formatarData(fraseFiltrar);

        return fraseFiltrar;
    }

    //funcao serve pra converter datas na forma DD//MM/YYYY pra YYYY/MM/DD, que é o padrao que eu quero.
    public static String formatarData(String nome){

        //separador é o -
        String dia ="";
        String mes ="";
        String ano ="";

        String resposta = "";

        dia += nome.charAt(0);
        dia += nome.charAt(1);

        mes += nome.charAt(3);
        mes += nome.charAt(4);

        ano += nome.charAt(6);
        ano += nome.charAt(7);
        ano += nome.charAt(8);
        ano += nome.charAt(9);

        resposta += ano+ "-" + mes + "-" + dia;

        return resposta;
    }

    public static String pegarTecnico(String nome){
        /*
        Padrão: left">Head coach</th><td class="agent"><a href="/wiki/Damir_Canadi" title="Damir 
        Canadi">Damir Canadi</a>        
        */

        String fraseFiltrar = "";
        int posicaoHeadCoach = 0;

        //to colocando -5 pra evitar outOfBounds
        //esse for é pra achar o HeadCoach, que logo em segida haverá o nome do estágio
        for (int i = 0; i < (nome.length()-5); i++) {
            boolean temHeadCoach = nome.charAt(i) == 'H' && nome.charAt(i+1) == 'e' && nome.charAt(i+2) == 'a'&& nome.charAt(i+3) == 'd'
            && nome.charAt(i+5) == 'c' && nome.charAt(i+6) == 'o' && nome.charAt(i+7) == 'a' && nome.charAt(i+8) == 'c'
            && nome.charAt(i+9) == 'h';
            
            boolean temCoach = nome.charAt(i) == 'C' && nome.charAt(i+1) == 'o' && nome.charAt(i+2) == 'a'
            && nome.charAt(i+3) == 'c' && nome.charAt(i+4) == 'h';

            if(temHeadCoach || temCoach){
                posicaoHeadCoach = i+9;
    
            
            //agora que achei o "HeadCoach", posso procurar o title.
            for(i = posicaoHeadCoach; i < nome.length()-4;i++){
                if(nome.charAt(i) == 't' && nome.charAt(i+1) == 'i' && nome.charAt(i+2) == 't' && nome.charAt(i+3) == 'l'
                && nome.charAt(i+4) == 'e'){

                    boolean aspasAchadas = false;
                    //vou selecionar tudo dentro de title=""
                    //achei o title, falta agora jogar dentro da string
                    for (int j = i+7; j < nome.length()-4; j++) {
                        //quando chegar em aspas acabou a tag
                        if(nome.charAt(j) == '>')
                            aspasAchadas = true;

                        else if(aspasAchadas && nome.charAt(j) == '<')
                            j = nome.length();

                        else if(aspasAchadas)
                            fraseFiltrar += nome.charAt(j);
                    }
                    i = nome.length();
                }
            }
            i = nome.length();
            }//fim do if
        }

        //essa parte aqui serve para o caso de nao haver head coach e sim manager
        if(fraseFiltrar.length() < 5){
            fraseFiltrar = pegarManager(nome);  
        }
        return fraseFiltrar;
    }
    public static String pegarManager(String nome){
        String fraseFiltrar = "";
        
        int posicaoManager = 0;

		for (int i = 0; i < (nome.length()-5); i++) {
			if(nome.charAt(i) == 'M' && nome.charAt(i+1) == 'a' && nome.charAt(i+2) == 'n' && nome.charAt(i+3) == 'a'
			&& nome.charAt(i+4) == 'g' && nome.charAt(i+5) == 'e' && nome.charAt(i+6) == 'r' ){
                posicaoManager = i+7;
                
            //agora que achei o "Manager", posso procurar o começo das aspas.
			for(i = posicaoManager; i < nome.length()-4;i++){
                   boolean aspasAchadas = false;
					
					for (int j = i+7; j < nome.length()-4; j++) {
						//quando chegar em aspas acabou a tag
						if(nome.charAt(j) == '>' && nome.charAt(j+1) != '<')
                            aspasAchadas = true;

                        else if(aspasAchadas && nome.charAt(j) == '<')
                            j = nome.length();

						else if(aspasAchadas)
							fraseFiltrar += nome.charAt(j);
					}
					i = nome.length();
				
			}
			i = nome.length();
			}//fim do if
        }
        return fraseFiltrar;
    }
    
    public static String pegarLiga(String nome){
    /*
    Padrão: >League</th><td><a href="/wiki/2._Bundesliga" title="2. Bundesliga">
    */
        String fraseFiltrar = "";
        int posicaoLeague = 0;

        //to colocando -5 pra evitar outOfBounds
        //esse for é pra achar o League, que logo em segida haverá o nome da liga
        for (int i = 0; i < (nome.length()-8); i++) {
            if(nome.charAt(i) == 'L' && nome.charAt(i+1) == 'e' && nome.charAt(i+2) == 'a'&& nome.charAt(i+3) == 'g'
                    && nome.charAt(i+4) == 'u' && nome.charAt(i+5) == 'e' && nome.charAt(i+6) == '<' && nome.charAt(i+7) == '/'
                    && nome.charAt(i+8) == 't'){
                posicaoLeague = i+8;
                i = nome.length();
            }
        }
        //agora que achei o "League", posso procurar o title.
        for(int i = posicaoLeague; i < nome.length()-4;i++){
            if(nome.charAt(i) == 't' && nome.charAt(i+1) == 'i' && nome.charAt(i+2) == 't' && nome.charAt(i+3) == 'l'
                    && nome.charAt(i+4) == 'e'){
                //vou selecionar tudo dentro de title=""
                //achei o title, falta agora achar a tag de abertura da string e fuçar la dentro
                posicaoLeague = i+7;
                boolean aspasAchadas = false;
                for (int j = i+7; j < nome.length()-4; j++) {
                    //quando chegar em aspas acabou a tag
                    if(nome.charAt(j) == '>')
                        aspasAchadas = true;
                    else if(nome.charAt(j) == '<'){
                        aspasAchadas = false;
                        j = nome.length();
                    }
                    else if (aspasAchadas)
                        fraseFiltrar += nome.charAt(j);
                }
                i = nome.length();
            }
        }

        return fraseFiltrar;
    }

    public static String pegarNomeArquivo(String link){
        return link;
    }

    public static int pegarCapacidade(String nome){
        /*
        Padrão: >League</th><td><a href="/wiki/2._Bundesliga" title="2. Bundesliga">
        */
        String fraseFiltrar = "";
        int posicaoCapacity = 0;

        //to colocando -5 pra evitar outOfBounds
        //esse for é pra achar o Capacity, que logo em segida haverá o int
        for (int i = 0; i < (nome.length()-8); i++) {
            if(nome.charAt(i) == 'C' && nome.charAt(i+1) == 'a' && nome.charAt(i+2) == 'p'&& nome.charAt(i+3) == 'a'
                    && nome.charAt(i+4) == 'c' && nome.charAt(i+5) == 'i' && nome.charAt(i+6) == 't' && nome.charAt(i+7) == 'y'
                    && nome.charAt(i+8) == '<'){
                posicaoCapacity = i+8;
                i = nome.length();
            }
        }
        //agora que achei o "Capacity", posso procurar o <td>, que depois dele a capacidade se encontra.
        for(int i = posicaoCapacity; i < nome.length()-3;i++){
            if(nome.charAt(i) == '<' && nome.charAt(i+1) == 't' && nome.charAt(i+2) == 'd' && nome.charAt(i+3) == '>'){
                //vou selecionar tudo dentro da tag <td>

                for (int j = i+4; j < nome.length(); j++) {
                    //quando chegar em um caractere que nao seja num, ponto ou virgula acabou a leitura

                    boolean serNum = (nome.charAt(j) >= '0' && nome.charAt(j) <= '9')
                            || nome.charAt(j) == '.' || nome.charAt(j) == ',';

                    //sair se chegar em um char que nao eh de num
                    if(!serNum)
                        j = nome.length();
                    else
                        fraseFiltrar += nome.charAt(j);
                }
                i = nome.length();
            }
        }
        //pegar a capacidade, retirando separador decimal , e .

        if(fraseFiltrar.contains(".")){
            fraseFiltrar = fraseFiltrar.replace(".", "");
        }
        else if(fraseFiltrar.contains(",")){
            fraseFiltrar = fraseFiltrar.replace(",", "");
        }
        int capacidade = Integer.parseInt(fraseFiltrar);


        return capacidade;
    }

    public static long pegarTamanhoArquivo(String link){
        Arq.openRead(link);

        long tam = Arq.length();
        Arq.close();
        return tam;
    }


}
