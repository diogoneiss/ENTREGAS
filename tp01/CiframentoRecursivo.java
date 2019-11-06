public class CiframentoRecursivo{

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
		//chamada da funcao
		MyIO.println(ciframentoDeCesar(entradas[i]));
		
		
	}

}
/*
Método principal, que faz o ciframento de césar recursivamente. A versão aqui abaixo é a sobrecarregada,
destinada a chamar a funcao com contador zero
*/
public static String ciframentoDeCesar(String fraseOriginal){
	return ciframentoDeCesar(fraseOriginal, 0);
}

public static String ciframentoDeCesar(String fraseOriginal, int contador){
	
		//movimento dado no enunciado
		int movimento = 3;

		//frase vazia pra dar append
		String fraseModificada  = "";;
		int tam = fraseOriginal.length();

		String valorRetorno = "";
		// soma o movimento no valor do charAt e faz typecast de volta pra char, fazendo append na stirng vazia
		if(contador < tam){
			fraseModificada += (char)(fraseOriginal.charAt(contador) + movimento);
			valorRetorno = fraseModificada + ciframentoDeCesar(fraseOriginal, contador+1);
		
		}

		return valorRetorno;
	}
	


	//le se eh o final da entrada, para terminar os metodos de entrada e começar a printar as respostas
	public static boolean estaNoFinal(String frase){
		return (frase.length() >= 3 && frase.charAt(0) == 'F' && frase.charAt(1) == 'I' && frase.charAt(2) == 'M');
	}
}