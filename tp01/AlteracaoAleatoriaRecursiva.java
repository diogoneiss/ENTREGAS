import java.util.Random;

public class AlteracaoAleatoriaRecursiva{

	//tamanho max da entrada
	static final int TAM_ENTRADA = 1000;
	
	public static void main(String[] args){
	
	//criando objeto e setando seed
	Random gerador = new Random();
	gerador.setSeed(4);

	//setar charset
	MyIO.setCharset("ISO-8859-1");

	//criar array 
	String[] entradas = new String[TAM_ENTRADA];
	
	//calculo do tamanho real
	int tamanhoRealEntradas = 0;

	do{
		//ler uma entrada do input
		entradas[tamanhoRealEntradas] = MyIO.readLine();
	 	
		tamanhoRealEntradas++;
		
		//verificar se a entrada anterior nao era o fim da entrada
	}while(!estaNoFinal(entradas[tamanhoRealEntradas-1]));

	// retirar o FIM 
	tamanhoRealEntradas--;

	//iterar todas as entradas e printar a frase cifrada
	for(int i=0;i<tamanhoRealEntradas;i++){
		//chamada da funcao com duas letras aleatorias sendo geradas
		//estou fazendo typecast pra passagem de args já ser em char
		MyIO.println(modificacaoAleatoria(entradas[i], (char) ('a' + Math.abs(gerador.nextInt() % 26)), (char) ('a'+ Math.abs(gerador.nextInt() % 26)) ));

	}

}
	//metodos de alterarem a string de modo a cumprirem a modificacao. O abaixo é sobrecarregado
	public static String modificacaoAleatoria(String fraseOriginal, char substituir, char substituida){
		return modificacaoAleatoria(fraseOriginal, substituir, substituida, 0);
	}

	public static String modificacaoAleatoria(String fraseOriginal, char substituir, char substituida, int contador){
	
		// recebe duas letras, geradas aleatoriamente, e substitui na string inteira a primeira pela segunda
		//atraves 
		String novaFrase = "";

		if(contador < fraseOriginal.length()){
			if(fraseOriginal.charAt(contador) == substituir)
				novaFrase += substituida;
			else	
				novaFrase += fraseOriginal.charAt(contador);

			novaFrase += modificacaoAleatoria(fraseOriginal, substituir, substituida, contador+1);
		}

		return novaFrase;
	}
	
	//le se eh o final da entrada, para terminar os metodos de entrada e começar a printar as respostas
	public static boolean estaNoFinal(String frase){
		return (frase.length() >= 3 && frase.charAt(0) == 'F' && frase.charAt(1) == 'I' && frase.charAt(2) == 'M');
					
	}
}