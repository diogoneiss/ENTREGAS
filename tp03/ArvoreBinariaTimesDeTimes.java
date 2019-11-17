import java.util.Date;
import java.io.*;
import java.io.File;
/**
 * PASSO A PASSO
 * 
 * Vou criar a árvore de arvores
 * 
 * vou inserir nela as árvores de numeros, de 0 a 14
 * 
 * vou voltar o controle pra main e inserir os times e iniciar as procuras
 */
public class ArvoreBinariaTimesDeTimes {

	static int comparacoes = 0;
	private NoNumerico raiz; // Raiz da arvore.
	private AuxiliarPesquisa auxiliarPesquisa;

	/**
	 * Construtor da classe.
	 * Essa árvore conterá apenas nós numericos, e neles há raízes para árvores de times.
	 * Devo inserir na árvores nós vazios, sem as referências da árvore
	 */
	public ArvoreBinariaTimesDeTimes() throws Exception {
		raiz = null;
	}
	public ArvoreBinariaTimesDeTimes(int numeroSubarvores) throws Exception {
		raiz = null;
		
		for(int i = 0; i < numeroSubarvores; i++){
			this.inserirNumerico(i);
		}
	}

	public static void main(String[] args) throws Exception {

      long inicio = new Date().getTime();

        MyIO.setCharset("UTF-8");

        String[] entradas = new String[100];
        int quantidadeDeFrases = 0;

        // ler a entrada com os times até inserir FIM

        do {
            entradas[quantidadeDeFrases] = MyIO.readLine();
        } while (!Time.estaNoTimeFinal(entradas[quantidadeDeFrases++]));

        // tirar o fim da contagem
        quantidadeDeFrases--;

        // MyIO.println("Numero de modificacoes: "+quantidadeModificacoes);
        // array de referencias a objetos
		Time[] conjuntoTimes = new Time[quantidadeDeFrases];
		
		ArvoreBinariaTimesDeTimes arvoreBinaria = new ArvoreBinariaTimesDeTimes(15);

        // criar os objetos de acordo e inserir
        for (int i = 0; i < quantidadeDeFrases; i++) {
            // criar o objeto e chamar o construtor
			conjuntoTimes[i] = new Time(entradas[i]);
			//MyIO.println(conjuntoTimes[i].getNome());
			arvoreBinaria.inserir2(conjuntoTimes[i]);
		}
		//ler do stdin as entradas a serem pesquisadas
		String listaPesquisa[] = new String[100];
		int contador = 0;
		do {
			listaPesquisa[contador] = MyIO.readLine();
			//MyIO.println(""+listaPesquisa[contador]);
        } while (!Time.estaNoTimeFinal(listaPesquisa[contador++]));
		
		contador--;

		//fazer a busca em si
		for(int i = 0; i < contador; i++){
			MyIO.print(listaPesquisa[i]);	
			arvoreBinaria.pesquisarPreNumerico(listaPesquisa[i]);
		}

        long fim = new Date().getTime();

        long execucao = fim-inicio;
        Arq.openWrite("649651_arvoreDeArvores.txt");

        Arq.print("649651\t"+execucao+"\t"+comparacoes+"\t");

        Arq.close();

        // fim da main
   }
   
   public void mostrarPre(){
	   MyIO.println("");
	   mostrarPre(raiz);
	   MyIO.println("");
   }
   private void mostrarPre(NoNumerico x){
		if(x != null){
			MyIO.print(x.elemento+" ");
			mostrarPre(x.esq);
			mostrarPre(x.dir);
		}
	}

	/**
	 * @param chave : Nome do time, a ser pesquisado em TODOS os nós
	 * @return se encontrou ou nao a chave na árvore
	 */
	public boolean pesquisarPreNumerico(String chave) {
		auxiliarPesquisa = new AuxiliarPesquisa();
		MyIO.print(" raiz");
		boolean encontrado = pesquisarPreNumerico(this.raiz, chave);

		if(!auxiliarPesquisa.encontrado && !auxiliarPesquisa.pesquisaConcluida){
			MyIO.println(" NÃO");
		}
		return encontrado;
	}

	/**
	 * Metodo privado recursivo para pesquisar elementos.
	 * @param chave chave de pesquisa
	 * @param i NoTime em analise.
	 */
	private boolean pesquisarPreNumerico(NoNumerico i, String chave) {
		
		if (i != null) {
			//MyIO.println("Numero do no atual: "+i.elemento);
			this.auxiliarPesquisa.nosPesquisados++;
			this.auxiliarPesquisa.encontrado = i.raiz.pesquisar(chave); // Conteudo do NoTime.
			comparacoes += i.raiz.comparacoes;

			if(!this.auxiliarPesquisa.encontrado && !this.auxiliarPesquisa.pesquisaConcluida){
				MyIO.print(" ESQ");
				this.auxiliarPesquisa.encontrado = pesquisarPreNumerico(i.esq, chave); // Elementos da esquerda.
				
			}
			if(!this.auxiliarPesquisa.encontrado && !this.auxiliarPesquisa.pesquisaConcluida){
				MyIO.print(" DIR");
				this.auxiliarPesquisa.encontrado = pesquisarPreNumerico(i.dir, chave); // Elementos da direita.
			}
			if(this.auxiliarPesquisa.encontrado && !this.auxiliarPesquisa.pesquisaConcluida){
				MyIO.println(" SIM");
				this.auxiliarPesquisa.pesquisaConcluida = true;
				this.auxiliarPesquisa.encontrado = true;
			}
		}
		
		return this.auxiliarPesquisa.encontrado;
	}

	
	public void inserir2(Time x) throws Exception {
		if(raiz == null){
		   raiz = new NoNumerico(x.getFundacaoAno() % 15, x);
		} else if(x.getFundacaoAno() % 15 < raiz.elemento){
			 inserir2(x, raiz.esq, raiz);
		} else if(x.getFundacaoAno() % 15 >= raiz.elemento){
			 inserir2(x, raiz.dir, raiz);
		} else {
			MyIO.println("Mod 15 comflitante: "+x.getFundacaoAno() %15);
			inserir2(x, raiz.dir, raiz);
		   throw new Exception("Erro ao inserir2 Dentro da versao com apenas um parâmetro!");
		}
	  }

	  private void inserir2(Time x, NoNumerico i, NoNumerico pai) throws Exception {

		int ano = x.getFundacaoAno() % 15;

		  if (i == null) {
		   if(ano < pai.elemento){
			  pai.esq = new NoNumerico(ano, x);
		   } else {
			  pai.dir = new NoNumerico(ano, x);
		   }
		} else if (ano < i.elemento) {
		   inserir2(x, i.esq, i);
		} else if (ano >= i.elemento) {
		   inserir2(x, i.dir, i);
		} else {
			x.imprimir();
		   throw new Exception("Erro ao inserir2!");
		}
	  }

	  /**
	 * Metodo publico para inserir elemento.
	 * @param x Elemento a ser inserido.
	 * @throws Exception Se o elemento existir.
	 */
	public void inserirNumerico(int x) throws Exception {
		if(raiz == null){
		   raiz = new NoNumerico(x); 
		   raiz.raiz = new ArvoreTimes(x);
		} else if(x < raiz.elemento){
			inserirNumerico(x, raiz.esq, raiz);
		} else if(x > raiz.elemento){
			inserirNumerico(x, raiz.dir, raiz);
		} else {
		   throw new Exception("Erro ao inserir2!");
		}
	  }
  
	  /**
	   * Metodo privado recursivo para inserir2 elemento.
	   * @param x Elemento a ser inserido.
	   * @param i NoTime em analise.
	   * @param pai NoTime superior ao em analise.
	   * @throws Exception Se o elemento existir.
	   */
	  private void inserirNumerico(int x, NoNumerico i, NoNumerico pai) throws Exception {
		  if (i == null) {
		   if(x< raiz.elemento){
			  pai.esq = new NoNumerico(x);
		   } else {
			  pai.dir = new NoNumerico(x);
		   }
		} else if (x< raiz.elemento) {
			inserirNumerico(x, i.esq, i);
		} else if (x> raiz.elemento) {
			inserirNumerico(x, i.dir, i);
		} else {
		   throw new Exception("Erro ao inserir2!");
		}
	  }

}

/*
 * NoTime da arvore binaria
 * 
 * @author Max do Val Machado
 */
class NoNumerico {
	public int elemento; // Conteudo do NoTime.
	public NoNumerico esq, dir; // Filhos da esq e dir.
	public ArvoreTimes raiz;

	/**
	 * Construtor da classe.
	 * 
	 * @param elemento Conteudo do NoTime.
	 */
	public NoNumerico(int elemento) throws Exception {
		this.elemento = elemento;
		this.esq = null;
		this.dir = null;
		this.raiz = new ArvoreTimes(elemento);
	}
	public NoNumerico(int elemento, Time x) throws Exception {
		//this(elemento, null, null, x);
		this.elemento = elemento;
		this.esq = null;
		this.dir = null;
		this.raiz = new ArvoreTimes(elemento);
		this.raiz.inserir2(x);
	}

	/**
	 * Construtor da classe.
	 * 
	 * @param elemento Conteudo do NoTime.
	 * @param esq      NoTime da esquerda.
	 * @param dir      NoTime da direita.
	 */
	public NoNumerico(int elemento, NoNumerico esq, NoNumerico dir, Time x) throws Exception{
		this.elemento = elemento;
		this.esq = esq;
		this.dir = dir;
		this.raiz.inserir2(x);
	}
}
class ArvoreTimes {
	int comparacoes;
	int numeroElemento;
	public NoTime raiz;
	
	ArvoreTimes(int num){
		this.comparacoes = 0;
		this.numeroElemento = num;
	}
	/**
	 * Metodo publico iterativo para pesquisar elemento.
	 * 
	 * @param x Elemento que sera procurado.
	 * @return <code>true</code> se o elemento existir, <code>false</code> em caso
	 *         contrario.
	 */
	public boolean pesquisar(String chave) {
		return pesquisar(chave, this.raiz);
	}

	/**
	 * Metodo privado recursivo para pesquisar elemento.
	 * 
	 * @param x Elemento que sera procurado.
	 * @param i No em analise.
	 * @return <code>true</code> se o elemento existir, <code>false</code> em caso
	 *         contrario.
	 */
	private boolean pesquisar(String chave, NoTime i) {
		boolean achado = false;
		if (i == null) {
			comparacoes++;

		} else if (chave.compareTo(i.elemento.getNome()) == 0) {
			achado = true;
			comparacoes++;

		} else if (chave.compareTo(i.elemento.getNome()) < 0) {
			//MyIO.println("\nTime atual: "+i.elemento.getNome());
			MyIO.print(" esq");
			achado = pesquisar(chave, i.esq);

		} else {
			//MyIO.println("\nTime atual: "+i.elemento.getNome());
			MyIO.print(" dir");
			achado = pesquisar(chave, i.dir);
		}
		return achado;
	}

	/**
	 * Metodo publico iterativo para exibir elementos.
	 */
	public void mostrarPre() {
		System.out.print("[ ");
		mostrarPre(raiz);
		System.out.println("]");
	}
	/**
	 * Metodo privado recursivo para exibir elementos.
	 * 
	 * @param i NoTime em analise.
	 */
	private void mostrarPre(NoTime i) {
		if (i != null) {
			MyIO.println(i.elemento.getNome()); // Conteudo do NoTime.
			mostrarPre(i.esq); // Elementos da esquerda.
			mostrarPre(i.dir); // Elementos da direita.
		}
	}

	/**
	 * Metodo publico iterativo para exibir elementos.
	 */
	public boolean pesquisarPre(String chave) {
		//System.out.print("[ ");
		boolean achado = false;
		achado = pesquisarPre(raiz, chave);
		//System.out.println("]");
		return achado;
	}
	/**
	 * Metodo privado recursivo para exibir elementos.
	 * 
	 * @param i NoTime em analise.
	 */
	private boolean pesquisarPre(NoTime i, String chave) {
		boolean achado = false;
		if (i != null) {
			//se for igual printa e sai
			if(i.elemento.getNome().compareTo(chave) == 0){
				MyIO.println("\n\n\n\n\n\n\n\n\n\nSIM, achei essa buceta\n\n\n\n\n");
				//raiz.chaveAchadaNoNumerico = true;
				achado = true;
				
			}
			//senao vai pra esquerda e direita
			else{
				achado = pesquisarPre(i.esq, chave); // Elementos da esquerda.
				MyIO.println(" esquerda, no Nó time ");
				if(!achado){
					achado = pesquisarPre(i.dir, chave); // Elementos da direita.
					MyIO.println(" direita, no Nó time ");
				}
			}
		}
		else{
			MyIO.println("\tNÃO, NO NÓ TIME");
		}
		return achado;
	}

	/**
	 * Metodo publico para inserir elemento.
	 * @param x Elemento a ser inserido.
	 * @throws Exception Se o elemento existir.
	 */
	public void inserir2(Time x) throws Exception {
		if(raiz == null){
		   raiz = new NoTime(x);
		} else if(x.getNome().compareTo(raiz.elemento.getNome()) < 0){
			
			 inserir2(x, raiz.esq, raiz);
		} else if(x.getNome().compareTo(raiz.elemento.getNome()) > 0){
			 inserir2(x, raiz.dir, raiz);
			
		} else {
		   throw new Exception("Erro ao inserir2!");
		}
	  }
  
	  /**
	   * Metodo privado recursivo para inserir2 elemento.
	   * @param x Elemento a ser inserido.
	   * @param i NoTime em analise.
	   * @param pai NoTime superior ao em analise.
	   * @throws Exception Se o elemento existir.
	   */
	  private void inserir2(Time x, NoTime i, NoTime pai) throws Exception {
		  if (i == null) {
		   if(x.getNome().compareTo(raiz.elemento.getNome()) < 0){
			  pai.esq = new NoTime(x);
		   } else {
			  pai.dir = new NoTime(x);
		   }
		} else if (x.getNome().compareTo(i.elemento.getNome()) < 0) {
		   inserir2(x, i.esq, i);
		} else if (x.getNome().compareTo(i.elemento.getNome()) > 0) {
		   inserir2(x, i.dir, i);
		} else {
		   throw new Exception("Erro ao inserir2!");
		}
	  }

      

    }


/*
 * NoTime da arvore binaria
 * 
 * @author Max do Val Machado
 */
class NoTime {
	public Time elemento; // Conteudo do NoTime.
	public NoTime esq, dir; // Filhos da esq e dir.
	public boolean chaveAchadaNoNumerico;
	/**
	 * Construtor da classe.
	 * 
	 * @param elemento Conteudo do NoTime.
	 */
	public NoTime(Time elemento) {
		this(elemento, null, null);
	}

	/**
	 * Construtor da classe.
	 * 
	 * @param elemento Conteudo do NoTime.
	 * @param esq      NoTime da esquerda.
	 * @param dir      NoTime da direita.
	 */
	public NoTime(Time elemento, NoTime esq, NoTime dir) {
		this.elemento = elemento;
		this.esq = esq;
		this.dir = dir;
	}
}

class Time {

	private String NoTimeme, apelido, estadio, tecnico, liga, NoTimemeArquivo;
	private int capacidade, fundacaoDia, fundacaoMes, fundacaoANoTime;
	private long paginaTam;

	public Time() {
		NoTimeme = apelido = estadio = tecnico = liga = NoTimemeArquivo = "";
		paginaTam = capacidade = fundacaoDia = fundacaoMes = fundacaoANoTime = 0;
	}

	public Time(String NoTimemeArq) throws Exception {
		ler(NoTimemeArq);
	}

	// metodos set e get

	public String getNome() {
		return NoTimeme;
	}

	public void setNome(String NoTimeme) {
		this.NoTimeme = NoTimeme;
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
		return NoTimemeArquivo;
	}

	public void setNomeArquivo(String NoTimemeArquivo) {
		this.NoTimemeArquivo = NoTimemeArquivo;
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
		return fundacaoANoTime;
	}

	public void setFundacaoAno(int fundacaoANoTime) {
		this.fundacaoANoTime = fundacaoANoTime;
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
		String resp = NoTimeme + " ## " + apelido + " ## ";
		resp += ((fundacaoDia > 9) ? "" : "0") + fundacaoDia + ((fundacaoMes > 9) ? "/" : "/0") + fundacaoMes + "/"
				+ fundacaoANoTime + " ## ";
		resp += estadio + " ## " + capacidade + " ## " + tecnico + " ## " + liga + " ## " + NoTimemeArquivo + " ## "
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
		str = str.replace(";NoTimete 1&#93;", " ");
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

	public void ler(String NoTimemeArquivo) throws Exception {

		FileReader file = new FileReader(NoTimemeArquivo);
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

		this.NoTimemeArquivo = NoTimemeArquivo;

		for (String campo : campos) {
			// Full name
			if (removeTags(campo).contains("Full name")) {
				campo = removeTags(campo);
				this.NoTimeme = campo.substring(9).trim();

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
					this.fundacaoANoTime = Integer.parseInt(campo.substring(0, 4));
				} else {
					campo = campo.substring(7);
					String data[] = campo.split(" ");
					if (data.length < 3) {
						this.fundacaoDia = 0;
						this.fundacaoANoTime = Integer.parseInt(data[1].substring(0, 4));
					} else {
						if (campo.contains(",")) {
							this.fundacaoDia = Integer.parseInt(data[1].replace("th", "").replace(",", ""));
							this.fundacaoANoTime = Integer.parseInt(data[2].substring(0, 4));
						} else if (Character.isDigit(data[0].charAt(0))) {
							this.fundacaoDia = Integer.parseInt(data[0]);
							this.fundacaoANoTime = Integer.parseInt(data[2].substring(0, 4));
						} else {
							this.fundacaoDia = 0;
							this.fundacaoANoTime = Integer.parseInt(data[1].substring(0, 4));
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
				campo = removeTags(campo.split("</td>")[0]);
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

		File f = new File(NoTimemeArquivo);
		setPaginaTam(f.length());

	}

	public static boolean estaNoTimeFinal(String frase) {
		return (frase.length() >= 3 && frase.charAt(0) == 'F' && frase.charAt(1) == 'I' && frase.charAt(2) == 'M');
	}

}
class AuxiliarPesquisa{
	public int nosPesquisados;
	public boolean encontrado;
	public boolean pesquisaConcluida;

	AuxiliarPesquisa(){
		nosPesquisados = 0;
		encontrado = pesquisaConcluida = false;
	}
}

