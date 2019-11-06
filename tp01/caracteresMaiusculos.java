
public class caracteresMaiusculos {
	//constantes globais de tamanho 
	static final int TAM_ENTRADAS = 100;

	public static void main(String[] args){
	//ao contrário do c o array pode ser unidimencional
	
		String[] entradas = new String[TAM_ENTRADAS];
		int numEntradas = -1;

		do{
			// vai ler a entrada e jogar dentro do i-esimo elemento
		
		numEntradas++;
		entradas[numEntradas] = MyIO.readLine();

		
		}while(!estaNoFinal(entradas[numEntradas]));

		// mostrar as respostas
		for (int i = 0; i < numEntradas; i++) {
			MyIO.println(quantasMaiusculas(entradas[i]));
		}

	}

	// o metodo verifica se a letra esta entre A ou Z, ou seja, se é maiuscula.
	public static boolean ehMaiuscula(char letra){
		boolean ehOuNaoeh = false;

		if(letra >= 'A' && letra <= 'Z')
			ehOuNaoeh = true;

		return ehOuNaoeh;
	}


	// o metodo vai iterar toda a frase, chamando um metodo de verificacao para cada item 
	// da string.
	
	public static int quantasMaiusculas(String frase){
	
		int total = 0;

		for(int i=0; i<frase.length();i++){
			if(ehMaiuscula(frase.charAt(i)) ) total++;
						
		}

	return total;
	}
	
	//le se eh o final da entrada, para terminar os metodos de entrada e começar a printar as respostas
	public static boolean estaNoFinal(String frase){
		boolean finalEntrada = false;

		if(frase.length() >= 3 && frase.charAt(0) == 'F' && frase.charAt(1) == 'I' && frase.charAt(2) == 'M')
			finalEntrada = true;

		return finalEntrada;
	}

}
