public class Palindromo{

// constante de tamanho
static final int TAM_ENTRADA = 1000;

public static void main(String[] args){
	//setar charset do verde
	MyIO.setCharset("ISO-8859-1");

	//criar array de arrays
	String[] conjuntoEntrada = new String[TAM_ENTRADA] ;
	
	//leitura e armazenamento
	int tamanhoRealEntradas = 0;

	do{
		//ler uma entrada do input
		conjuntoEntrada[tamanhoRealEntradas] = MyIO.readLine();
		tamanhoRealEntradas++;
		
	}while(!estaNoFim(conjuntoEntrada[tamanhoRealEntradas -1]));

	// retirar o FIM 
	tamanhoRealEntradas--;

	// se a chamada retornar true printa SIM
	for (int i = 0; i < tamanhoRealEntradas; i++)
	{
		if(ehPalindromo(conjuntoEntrada[i]))
			MyIO.println("SIM");
		else
			MyIO.println("NAO");
	}
	
}

// verifica se eh palindromo
public static boolean ehPalindromo(String frase){

	boolean serPalindromo = true;
	//tirar o eol da string
	int j = frase.length()-1;
	int i=0;
	
	//sai do loop se for descoberto que nao eh palindromo
	while(i < j && serPalindromo){
		//ultimo com primeiro
		if(frase.charAt(i) != frase.charAt(j)){
			serPalindromo = false;
		}
		//decrementa o j e incrementa o i
		i++;
		j--;
	}

	return serPalindromo;

}
//verifica se esta no fim da leitura
static boolean estaNoFim(String frase){
	return (frase.length() >= 3 && frase.charAt(0) == 'F' && frase.charAt(1) == 'I' && frase.charAt(2) == 'M'); 
}

}