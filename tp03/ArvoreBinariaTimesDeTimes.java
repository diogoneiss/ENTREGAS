import java.util.Date;
import java.io.*;
import java.io.File;

/**
 * PASSO A PASSO
 * 
 * Vou criar a árvore de arvores
 * 
 * Método de inserção na ÁrvoreNumérica: procurar o nó do número. Se ele nao
 * existir, criar o nó numérico adicionar o time na árvore dentro do nó
 * numerico.
 * 
 * 
 */
public class ArvoreBinariaTimesDeTimes {

	static int comparacoes = 0;
	private NoNumerico raiz; // Raiz da arvore.
	private AuxiliarPesquisa auxiliarPesquisa;

	/**
	 * Construtor da classe. Essa árvore conterá apenas nós numericos, e neles há
	 * raízes para árvores de times.
	 */
	public ArvoreBinariaTimesDeTimes() throws Exception {
		raiz = null;
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

		// array de referencias a objetos
		Time[] conjuntoTimes = new Time[quantidadeDeFrases];

		ArvoreBinariaTimesDeTimes arvoreBinaria = new ArvoreBinariaTimesDeTimes();

		// criar os objetos de acordo e inserir
		for (int i = 0; i < quantidadeDeFrases; i++) {
			// criar o objeto e chamar o construtor
			conjuntoTimes[i] = new Time(entradas[i]);
			// MyIO.println(conjuntoTimes[i].getNome());
			arvoreBinaria.inserir(conjuntoTimes[i]);
		}
		// ler do stdin as entradas a serem pesquisadas
		String listaPesquisa[] = new String[100];
		int contador = 0;
		do {
			listaPesquisa[contador] = MyIO.readLine();
			// MyIO.println(""+listaPesquisa[contador]);
		} while (!Time.estaNoTimeFinal(listaPesquisa[contador++]));

		contador--;

		// fazer a pesquisa nas i entradas
		for (int i = 0; i < contador; i++) {
			MyIO.print(listaPesquisa[i]);
			arvoreBinaria.pesquisarArvoreArvores(listaPesquisa[i]);
		}

		long fim = new Date().getTime();

		long execucao = fim - inicio;
		Arq.openWrite("649651_arvoreDeArvores.txt");

		Arq.print("649651\t" + execucao + "\t" + comparacoes + "\t");

		Arq.close();

		// fim da main
	}

	public void mostrarPre() {
		MyIO.println("");
		mostrarPre(raiz);
		MyIO.println("");
	}

	private void mostrarPre(NoNumerico x) {
		if (x != null) {
			MyIO.print(x.elemento + " ");
			mostrarPre(x.esq);
			mostrarPre(x.dir);
		}
	}

	/**
	 * @param chave : Nome do time, a ser pesquisado em TODOS os nós
	 * @return se encontrou ou nao a chave na árvore
	 */
	public boolean pesquisarArvoreArvores(String chave) {
		this.auxiliarPesquisa = new AuxiliarPesquisa();
		MyIO.print(" raiz");
		boolean encontrado = pesquisarArvoreArvores(this.raiz, chave);

		if (!encontrado) {
			System.out.printf(" N%cO\n", (char) 195);
		}
		return encontrado;
	}

	/**
	 * Metodo privado recursivo para pesquisar elementos.
	 * 
	 * @param chave chave de pesquisa
	 * @param i     NoTime em analise.
	 */
	private boolean pesquisarArvoreArvores(NoNumerico i, String chave) {
		boolean encontrado = false;
		
		if (i != null) {
		// pesquisa dentro árvore de times, que está dentro do no i
		encontrado = i.raiz.pesquisar(chave);
		comparacoes += i.raiz.comparacoes;
			// ida pra esquerda
			if (!encontrado && !this.auxiliarPesquisa.pesquisaConcluida) {
				MyIO.print(" ESQ");
				encontrado = pesquisarArvoreArvores(i.esq, chave); // Elementos da esquerda.
			}
			
			// ida pra direita
			if (!encontrado && !this.auxiliarPesquisa.pesquisaConcluida) {
				MyIO.print(" DIR");
				encontrado = pesquisarArvoreArvores(i.dir, chave); // Elementos da direita.
			}
			// encontrado
			if (encontrado && !this.auxiliarPesquisa.pesquisaConcluida) {
				MyIO.println(" SIM");
				this.auxiliarPesquisa.pesquisaConcluida = true;
				encontrado = true;
			}
		}

		return encontrado;
	}

	/**
	 * Inserção na árvore de árvores.
	 * 
	 * @param x: objeto Time
	 * @throws Exception
	 */
	public void inserir(Time x) throws Exception {
		// se nao tiver raiz, cria ela.
		if (raiz == null) {
			raiz = new NoNumerico(x.getFundacaoAno() % 15, x);
		}
		// verifica se o mod 15 é igual ao da raiz. Se for, insere na árvore dentro do
		// nó
		else if (x.getFundacaoAno() % 15 == raiz.elemento) {
			raiz.raiz.inserir(x);
		}
		// vai pra esquerda
		else if (x.getFundacaoAno() % 15 < raiz.elemento) {
			inserir(x, raiz.esq, raiz);
		}
		// vai pra direita
		else if (x.getFundacaoAno() % 15 > raiz.elemento) {
			inserir(x, raiz.dir, raiz);
		} else {
			throw new Exception("Erro ao inserir Dentro da versao com apenas um parâmetro!");
		}
	}

	// inserir da árvore de árvores privado
	private void inserir(Time x, NoNumerico i, NoNumerico pai) throws Exception {

		int ano = x.getFundacaoAno() % 15;
		// se i for null cria o no, de acordo com a posicao do pai
		if (i == null) {
			// o construtor do no automaticamente insere na arvore o time.
			if (ano < pai.elemento) {
				pai.esq = new NoNumerico(ano, x);
			} else {
				pai.dir = new NoNumerico(ano, x);
			}

		}
		// se for igual ao elemento, inserir na árvore dele
		else if (ano == i.elemento) {
			i.raiz.inserir(x);
		}
		// navegando pra esquerda
		else if (ano < i.elemento) {
			inserir(x, i.esq, i);
		}
		// navegando pra direita
		else if (ano > i.elemento) {
			inserir(x, i.dir, i);
		} else {
			x.imprimir();
			throw new Exception("Erro ao inserir!");
		}
	}

}

/*
 * NoTime da arvore de arvores
 * 
 */
class NoNumerico {
	public int elemento; // Conteudo do NoTime.
	public NoNumerico esq, dir; // Filhos da esq e dir.
	public ArvoreTimes raiz;

	public NoNumerico(int elemento) throws Exception {
		this.elemento = elemento;
		this.esq = null;
		this.dir = null;
		this.raiz = new ArvoreTimes();
	}

	/**
	 * Construtor da classe.
	 * 
	 * @param elemento ano % 15 do NoTime.
	 * @param x        objeto Time
	 */
	public NoNumerico(int elemento, Time x) throws Exception {
		this.elemento = elemento;
		this.esq = null;
		this.dir = null;
		this.raiz = new ArvoreTimes();
		this.raiz.inserir(x);
	}

}

/**
 * Árvore de times. Cada "noNumerico" contêm uma referencia para um objeto
 * ArvoreTimes
 */
class ArvoreTimes {
	int comparacoes;
	public NoTime raiz;

	ArvoreTimes() {
		this.comparacoes = 0;
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
			// MyIO.println("\nTime atual: "+i.elemento.getNome());
			MyIO.print(" esq");
			achado = pesquisar(chave, i.esq);

		} else {
			// MyIO.println("\nTime atual: "+i.elemento.getNome());
			MyIO.print(" dir");
			achado = pesquisar(chave, i.dir);
		}
		return achado;
	}

	/**
	 * Metodo publico recursivo para pesquisar dentre todos os elementos.
	 */
	public boolean mostrarPre(String chave) {
		return mostrarPre(raiz, chave);		
	}

	/**
	 * Metodo privado recursivo para pesquisar dentre todos os elementos.
	 * 
	 * @param i NoTime em analise.
	 */
	private boolean mostrarPre(NoTime i, String chave) {
		boolean resp = false;
		if (i != null) {
			
			if(!resp){
				resp = mostrarPre(i.esq, chave); // Elementos da esquerda.
				MyIO.print(" esq");
			}
			resp = i.elemento.getNome().compareTo(chave) == 0; // Conteudo do NoTime.
			if(!resp){
				resp = mostrarPre(i.dir, chave); // Elementos da direita.
				MyIO.print(" dir");
			}
		}
		return resp;
	}

	/**
	 * Metodo publico para inserir elemento.
	 * 
	 * @param x Elemento a ser inserido.
	 * @throws Exception Se o elemento existir.
	 */
	public void inserir(Time x) throws Exception {
		if (raiz == null) {
			raiz = new NoTime(x);
		} else if (x.getNome().compareTo(raiz.elemento.getNome()) < 0) {

			inserir(x, raiz.esq, raiz);
		} else if (x.getNome().compareTo(raiz.elemento.getNome()) > 0) {
			inserir(x, raiz.dir, raiz);

		} else {
			throw new Exception("Erro ao inserir!");
		}
	}

	/**
	 * Metodo privado recursivo para inserir elemento.
	 * 
	 * @param x   Elemento a ser inserido.
	 * @param i   NoTime em analise.
	 * @param pai NoTime superior ao em analise.
	 * @throws Exception Se o elemento existir.
	 */
	private void inserir(Time x, NoTime i, NoTime pai) throws Exception {
		if (i == null) {
			if (x.getNome().compareTo(raiz.elemento.getNome()) < 0) {
				pai.esq = new NoTime(x);
			} else {
				pai.dir = new NoTime(x);
			}
		} else if (x.getNome().compareTo(i.elemento.getNome()) < 0) {
			inserir(x, i.esq, i);
		} else if (x.getNome().compareTo(i.elemento.getNome()) > 0) {
			inserir(x, i.dir, i);
		} else {
			throw new Exception("Erro ao inserir!");
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

class AuxiliarPesquisa {
	public int nosPesquisados;
	public boolean encontrado;
	public boolean pesquisaConcluida;

	AuxiliarPesquisa() {
		nosPesquisados = 0;
		encontrado = pesquisaConcluida = false;
	}
}
