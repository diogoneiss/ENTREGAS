
public class caracteresMaiusculosRecursivo {
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
	//versao elegante
	public static int quantasMaiusculas(String frase){
		return quantasMaiusculas(frase, 0);
	}
	//versao recursiva
	public static int quantasMaiusculas(String frase, int posicao){
	int total = 0;
	int resultadoRetorno;
		//verificar se nao eh fim da string, pra parar a recursividade
		if(posicao < frase.length()){
			//incrementa se eh maiuscula
			if(ehMaiuscula(frase.charAt(posicao)))
				total++;					
			//seta a recursividade
			resultadoRetorno = total + quantasMaiusculas(frase, posicao+1);
			}
		//fim da recursividade
		else
			resultadoRetorno = total;
	
	return resultadoRetorno;
	}
	
	//le se eh o final da entrada, para terminar os metodos de entrada e começar a printar as respostas
	public static boolean estaNoFinal(String frase){
		boolean finalEntrada = false;

		if(frase.length() >= 3 && frase.charAt(0) == 'F' && frase.charAt(1) == 'I' && frase.charAt(2) == 'M')
			finalEntrada = true;

		return finalEntrada;
	}

}
