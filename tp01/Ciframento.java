public class Ciframento{

	//tamanho max da entrada
	static final int TAM_ENTRADA = 1000;
	
	public static void main(String[] args){

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
		//chamada da funcao com ciframento 3
		MyIO.println(ciframentoDeCesar(entradas[i], 3));
		
		
	}

}

public static String ciframentoDeCesar(String fraseOriginal, int movimento){
	
		//frase vazia pra dar append
		String fraseModificada = "";

		int tam = fraseOriginal.length();
		
		// soma o movimento no valor do charAt e faz typecast de volta pra char, fazendo append na stirng vazia
		for(int x = 0; x < tam; x++){
			fraseModificada += (char)(fraseOriginal.charAt(x) + movimento);
		}

		return fraseModificada;
	}
	


	//le se eh o final da entrada, para terminar os metodos de entrada e começar a printar as respostas
	public static boolean estaNoFinal(String frase){

		return (frase.length() >= 3 && frase.charAt(0) == 'F' && frase.charAt(1) == 'I' && frase.charAt(2) == 'M');
			

		
	}
}