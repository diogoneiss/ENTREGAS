public class AlgebraBooleana{

	//tamanho max da entrada
	static final int TAM_ENTRADA = 1000;
	
public static void main(String[] args){

	//setar charset
	MyIO.setCharset("ISO-8859-1");

	//criar array 
	String[] entradas = new String[TAM_ENTRADA];
	
	//calculo do tamanho real. Começa em -1 pq vai ser incrementado
	int tamanhoRealEntradas = -1;

	

	do{
		
		tamanhoRealEntradas++;
		//ler uma entrada do input
		entradas[tamanhoRealEntradas] = MyIO.readLine();
		MyIO.println("A string foi lida corretamente. String lida: " +entradas[tamanhoRealEntradas]);
		
		//verificar se a entrada anterior nao era o fim da entrada
	}while(!estaNoFinal(entradas[tamanhoRealEntradas]));

	//iterar todas as entradas e executar os metodos adequados
	for(int i=0;i<tamanhoRealEntradas;i++){
		//chamada da funcao com o i-esimo item do array

		//limpeza e substituicao da string
		MyIO.println("String original, antes de limpar: "+entradas[i]);

		entradas[i] = substituirLetrasExpressao(entradas[i]);
		

		MyIO.println("Resposta final: "+ simplificarOperacao(entradas[i]));
		
		MyIO.println("terminei essa entrada, indo pra proxima");
	}

}


/*
Essa funcao vai servir para simplificar a operacao iterativamente, ate haver apenas um unico char
Para isso, vai andar na string de tras pra frente, retirar uma operacao e substituir por 0 ou 1
*/
public static String simplificarOperacao(String fraseOriginal){
	
	char bitDaOperacao;

	// chamada das funcoes booleanas, de tras pra frente, pra ir simplificando o arranjo. Preciso garantir que seja uma 
	//expressao simples, tipo not(A), e nao algo composto
	for(int x = fraseOriginal.length()-1; x >= 3; x--){
	
		/*
		Para chamar a funcao "substituirNaString", preciso passar as posicoes do 'a' e do ) final.
		*/

		// operacao not, com apenas um bit
		boolean ehOperacaoUnitaria = fraseOriginal.charAt(x-2) == '(' && fraseOriginal.charAt(x) == ')';

		// operacao simples é tipo and( A , B)
		boolean ehOperacaoSimples = fraseOriginal.charAt(x) == '(' && fraseOriginal.charAt(x+6) == ')';
		
		/*
		and(or(A , B) , C))
			  0123456
			  char da letra = x+1
			  char do x + 6 = )
			  char do x = (
				se tiver diferenca de 6 chars entre os parenteses, tenho uma operacao simples!!!!
		*/

		//o 'a' esta na posicao x-3 e o utimo parenteses na x+6, portando vou passalos nas funcoes
		if(ehOperacaoSimples && fraseOriginal.charAt(x-1) == 'd' && fraseOriginal.charAt(x-2) == 'n' && fraseOriginal.charAt(x-3) == 'a'){
			bitDaOperacao = operacaoAND(cortarString(fraseOriginal, x-3, x+6));	
			fraseOriginal = substituirNaString(fraseOriginal, x-3, x+6, bitDaOperacao);

		}

		// o 'o' esta no char x-2 e o parenteses final na posicao x+6. Portanto, devo passar na funcao de substituicao 
		else if(ehOperacaoSimples && fraseOriginal.charAt(x-1) == 'r' && fraseOriginal.charAt(x-2) == 'o'){
			bitDaOperacao = operacaoOR(cortarString(fraseOriginal, x-5, x+1));
			fraseOriginal = substituirNaString(fraseOriginal, x-2, x+6, bitDaOperacao);

		}

		//operacao not
		else if(ehOperacaoUnitaria && fraseOriginal.charAt(x-1) == 't' && fraseOriginal.charAt(x-2) == 'o' && fraseOriginal.charAt(x-2) == 'n'){
				bitDaOperacao = operacaoNOT(fraseOriginal);
				fraseOriginal = substituirNaString(fraseOriginal, x-3, x+6, bitDaOperacao);
		}
	}

	return fraseOriginal;
}	


/*
serve para transformar a string original, com A B e etcs em uma so com a expressao booleana, alem de cortar fora o começo
da string, deixando APENAS as instruções lógicas para evaluacao
*/
public static String substituirLetrasExpressao(String frase){

	String novaString = "";
	int numDeLetrasPegas = 0;
	int num = (int) frase.charAt(0);

	//o 3o item da string contem o primeiro valor booleano
	int posicaoLetraPega = 2;

	//substituir na string as letras maiusculas pelos seus respectivos valores booleanos
	for(int i=0;i< frase.length();i++){
	
		//detectar letras maiusculas, que sao as substituicoes de 0 e 1, e substituir propriamente por 0 e 1
		if(frase.charAt(i) >= 'A' && frase.charAt(i) <= 'Z' && numDeLetrasPegas < num){
			//o primeiro bit logico eh sempre a 3a posicao da string
			novaString += frase.charAt(posicaoLetraPega);

			//soma um para o espaco e um para a prox letra
			posicaoLetraPega += 2;
			//soma ao contador de letras pegas, ja que uma foi detectada
			numDeLetrasPegas++;
		}
		else
			novaString += frase.charAt(i);
	}
	// 1 0 not(A)
	// 0123456789ABCDEF
	// 2 0 0 or( A , B)

	//ja substitui os valores booleanos. agora falta cortar a string, removendo os valores booleanos de inicio
	//  2*num+2 pois a cada string inserida o primeiro char da expressao estara a 2 chars de distancia depois da
	// ultima expressao booleana, ou seja, 2*num +2

	String resposta = cortarString(novaString, num*2+posicaoLetraPega, novaString.length());
	MyIO.println("String com as letras substituidas: " + resposta);

	return resposta;

}



// cortar uma string incluindo todos os chars entre duas posicoes, excluindo essas duas posicoes
public static String cortarString(String frase, int posIn, int posFin){

	String novaString = "";

	//começa na posicao seguinte da inserida e termina antes da final
	if(posIn >= frase.length())
		MyIO.println("A posicao inicial estoura o length da string, tá dando pau nisso.");

	else if(posFin >= frase.length())
		MyIO.println("A posicao inicial estoura o length da string, tá dando pau nisso.");

	for (int i=posIn+1; i < posFin; i++) {
		novaString += frase.charAt(i);
	}

	return novaString;

}

/*
Essa funcao vai servir para dividir uma string em duas, remover o espaco entre duas posicoes e inserir um unico char, da reposta.
AnteriorFim e posteriorInicio estao sendo excluidas nas strings
*/
public static String substituirNaString(String fraseOriginal, int anteriorFim, int posteriorInicio, char substituir){

	String anterior = "";
	String posterior = "";
	String novaStringSubstituida = "";

	//salvar os chars da original aqui dentro
	for(int i = 0; i < anteriorFim; i++)
		anterior += fraseOriginal.charAt(i);

	for (int i = posteriorInicio+1; i < fraseOriginal.length(); i++) {
		posterior += fraseOriginal.charAt(i);
	}

	// adicionar as strings juntas agora. Nao fui direto antes para evitar arrayOutOfBounds, assim possuo os tamanhos adequados
	for(int i = 0;i<anterior.length(); i++)
		novaStringSubstituida += anterior.charAt(i);

	//agora que estou no meio posso jogar o que é pra inserir no meio
	novaStringSubstituida += substituir;

	//jogar o resto dentro, pra fechar a string e retornar
	for(int i = 0;i<posterior.length(); i++)
		novaStringSubstituida += anterior.charAt(i);

return novaStringSubstituida;

}

//executar a operacao AND com os operandos binarios dentro
public static char operacaoAND(String frase){
	char resultado = 1;
	//iterar a string pra procurar zeros
	for(int i=0;i<frase.length();i++){
		//se houver qualquer 0 o AND retornara false
		if(frase.charAt(i) == '0'){
			resultado = 0;
			i = frase.length();
		}
	}
	return resultado;
}

//executar a operacao OR com os operandos binarios dentro
public static char operacaoOR(String frase){
	char resultado = 0;
	//iterar a string pra procurar 1s
	for(int i=0;i<frase.length();i++){
		//se houver qualquer 1 o or retornara true
		if(frase.charAt(i) == '1'){
			resultado = 1;
			i = frase.length();
		}
	}

	return resultado;

}

//executar a operacao NOT com os operandos binarios dentro
public static char operacaoNOT(String frase){
	//flipar o valor binario da expressao
	char resultado = '0';
	if(frase.charAt(0) == '0')
		resultado = '1';

	return resultado;

}


//le se eh o final da entrada, para terminar os metodos de entrada e começar a printar as respostas

public static boolean estaNoFinal(String frase){
	//se for '0' havera strlen de 2 (0 e fim de linha)
	return (frase.length() <= 2 && frase.charAt(0) == '0');		
}

//fim da classe
}
