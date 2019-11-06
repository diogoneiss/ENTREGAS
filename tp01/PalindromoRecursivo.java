public class PalindromoRecursivo{

// constante de tamanho
static final int TAM_ENTRADA = 1000;

public static void main(String[] args){
	//setar charset do verde
	MyIO.setCharset("UTF-8");

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
//metodo sobrecarregado
public static boolean ehPalindromo(String frase){
	return ehPalindromo(frase, 0);
}


// verifica se eh palindromo
public static boolean ehPalindromo(String frase, int contador){

	boolean serPalindromo = false;
	
	//tirar o eol da string
	int tam = (frase.length()-1)-contador;
	//MyIO.println("Contador: " +contador);

	if(contador < frase.length()/2){
	//sai do loop se for descoberto que nao eh palindromo
		//ultimo com primeiro
		if(frase.charAt(contador) == frase.charAt(tam)){
			serPalindromo = ehPalindromo(frase, contador+1);
		}
		else
			serPalindromo = false;
	}
	else
		serPalindromo = true;
	

	return serPalindromo;

}
//verifica se esta no fim da leitura
static boolean estaNoFim(String frase){
	return (frase.length() >= 3 && frase.charAt(0) == 'F' && frase.charAt(1) == 'I' && frase.charAt(2) == 'M'); 
}

}