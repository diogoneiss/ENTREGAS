import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.Date;

/**
 * Arvore binaria de pesquisa
 * 
 * @author Max do Val Machado
 */
public class ArvoreBinariaTimes {
	private No raiz; // Raiz da arvore.

	/**
	 * Construtor da classe.
	 */
	public ArvoreBinariaTimes() {
		raiz = null;
	}

	public static void main(String[] args) throws Exception {
      ArvoreBinariaTimes arvoreBinaria = new ArvoreBinariaTimes();

	  MyIO.setCharset("UTF-8");

      long inicio = new Date().getTime();

        String[] entradas = new String[100];
        int quantidadeDeFrases = 0;

        // ler a entrada com os times até inserir FIM
        do {
            entradas[quantidadeDeFrases] = MyIO.readLine();
        } while (!Time.estaNoFinal(entradas[quantidadeDeFrases++]));

        // tirar o fim da contagem
        quantidadeDeFrases--;

        // array de referencias a objetos
        Time[] conjuntoTimes = new Time[quantidadeDeFrases];

        // inserir na árvore numérica os times.
        for (int i = 0; i < quantidadeDeFrases; i++) {
            // criar o objeto e chamar o construtor
			conjuntoTimes[i] = new Time(entradas[i]);
			arvoreBinaria.inserir2(conjuntoTimes[i]);
        }
		//arranjo com os times que serão pesquisados
		String listaPesquisa[] = new String[100];
		int contador = 0;
		do {
            listaPesquisa[contador] = MyIO.readLine();
        } while (!Time.estaNoFinal(listaPesquisa[contador++]));
		
		contador--;

		int comparacoes = 0;

		//pesquisa propriamente dita
		for(int i = 0; i < contador; i++){
			comparacoes += arvoreBinaria.pesquisar(listaPesquisa[i]);
				
		}
		//armazenar tempo de execução, comparações e etcs.
        long fim = new Date().getTime();

        long execucao = fim-inicio;
        Arq.openWrite("649651_arvoreBinaria.txt");

        Arq.print("649651\t"+execucao+"\t"+comparacoes+"\t");

        Arq.close();

        // fim da main
   }

	/**
	 * Metodo publico iterativo para pesquisar elemento.
	 * 
	 * @param x Elemento que sera procurado.
	 * @return <code>true</code> se o elemento existir, <code>false</code> em caso
	 *         contrario.
	 */
	public int pesquisar(String chave) {
		MyIO.print(chave + " raiz");
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
	private int pesquisar(String chave, No i) {
		int contador = 0;
		if (i == null) {
			System.out.printf(" N%cO\n", (char)199);
			contador++;

		} else if (chave.compareTo(i.elemento.getNome()) == 0) {
			System.out.println(" SIM");
			contador++;

		} else if (chave.compareTo(i.elemento.getNome()) < 0) {
			
			MyIO.print(" esq");
			contador += pesquisar(chave, i.esq);

		} else {

			MyIO.print(" dir");
			contador += pesquisar(chave, i.dir);
		}
		return contador;
	}

	/**
	 * Metodo publico iterativo para exibir elementos.
	 */
	public void mostrarCentral() {
		System.out.print("[ ");
		mostrarCentral(raiz);
		System.out.println("]");
	}

	/**
	 * Metodo privado recursivo para exibir elementos.
	 * 
	 * @param i No em analise.
	 */
	private void mostrarCentral(No i) {
		if (i != null) {
			mostrarCentral(i.esq); // Elementos da esquerda.
			System.out.println(i.elemento.getNome()); // Conteudo do no.
			mostrarCentral(i.dir); // Elementos da direita.
		}
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
	 * @param i No em analise.
	 */
	private void mostrarPre(No i) {
		if (i != null) {
			System.out.println(i.elemento.getNome()); // Conteudo do no.
			mostrarPre(i.esq); // Elementos da esquerda.
			mostrarPre(i.dir); // Elementos da direita.
		}
	}

	/**
	 * Metodo publico iterativo para exibir elementos.
	 */
	public void mostrarPos() {
		System.out.print("[ ");
		mostrarPos(raiz);
		System.out.println("]");
	}

	/**
	 * Metodo privado recursivo para exibir elementos.
	 * 
	 * @param i No em analise.
	 */
	private void mostrarPos(No i) {
		if (i != null) {
			mostrarPos(i.esq); // Elementos da esquerda.
			mostrarPos(i.dir); // Elementos da direita.
			System.out.print(i.elemento.getNome() + " "); // Conteudo do no.
		}
	}

	/**
	 * Metodo publico iterativo para inserir elemento.
	 * 
	 * @param x Elemento a ser inserido.
	 * @throws Exception Se o elemento existir.
	 */
	public void inserir(Time x) throws Exception {
		raiz = inserir(x, raiz);
	}

	/**
	 * Metodo privado recursivo para inserir elemento.
	 * 
	 * @param x Elemento a ser inserido.
	 * @param i No em analise.
	 * @return No em analise, alterado ou nao.
	 * @throws Exception Se o elemento existir.
	 */
	private No inserir(Time x, No i) throws Exception {
		if (i == null) {
			i = new No(x);

		} else if (x.getNome().compareTo(i.elemento.getNome()) < 0) {
			i.esq = inserir(x, i.esq);

		} else if (x.getNome().compareTo(i.elemento.getNome()) > 0) {
			i.dir = inserir(x, i.dir);

		} else {

			throw new Exception("Erro ao inserir! Elemento repetido.");
		}

		return i;
	}
	/**
	 * Metodo publico para inserir elemento.
	 * @param x Elemento a ser inserido.
	 * @throws Exception Se o elemento existir.
	 */
	public void inserir2(Time x) throws Exception {
		if(raiz == null){
		   raiz = new No(x);
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
	   * @param i No em analise.
	   * @param pai No superior ao em analise.
	   * @throws Exception Se o elemento existir.
	   */
	  private void inserir2(Time x, No i, No pai) throws Exception {
		  if (i == null) {
		   if(x.getNome().compareTo(raiz.elemento.getNome()) < 0){
			  pai.esq = new No(x);
		   } else {
			  pai.dir = new No(x);
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
 * No da arvore binaria
 * 
 * @author Max do Val Machado
 */
class No {
	public Time elemento; // Conteudo do no.
	public No esq, dir; // Filhos da esq e dir.

	/**
	 * Construtor da classe.
	 * 
	 * @param elemento Conteudo do no.
	 */
	public No(Time elemento) {
		this(elemento, null, null);
	}

	/**
	 * Construtor da classe.
	 * 
	 * @param elemento Conteudo do no.
	 * @param esq      No da esquerda.
	 * @param dir      No da direita.
	 */
	public No(Time elemento, No esq, No dir) {
		this.elemento = elemento;
		this.esq = esq;
		this.dir = dir;
	}
}

class Time {

	private String nome, apelido, estadio, tecnico, liga, nomeArquivo;
	private int capacidade, fundacaoDia, fundacaoMes, fundacaoAno;
	private long paginaTam;

	public Time() {
		nome = apelido = estadio = tecnico = liga = nomeArquivo = "";
		paginaTam = capacidade = fundacaoDia = fundacaoMes = fundacaoAno = 0;
	}

	public Time(String nomeArq) throws Exception {
		ler(nomeArq);
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

		File f = new File(nomeArquivo);
		setPaginaTam(f.length());

	}

	public static boolean estaNoFinal(String frase) {
		return (frase.length() >= 3 && frase.charAt(0) == 'F' && frase.charAt(1) == 'I' && frase.charAt(2) == 'M');
	}

}
