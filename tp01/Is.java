public class Is {

//tamanho max da entrada
static final int TAM_ENTRADA = 10000;

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

		//verificar se a entrada anterior nao era o fim da entrada
	}while(!estaNoFim(entradas[tamanhoRealEntradas]));

	//iterar todas as entradas e executar os metodos adequados
	for(int i=0;i<tamanhoRealEntradas;i++){
		printarRespostas(entradas[i]);
	}

}
/*
 *Metodo para printar todas as respostas referentes a uma string, chamando
 os 4 metodos do enunciado
 */

public static void printarRespostas(String frase){

	//vogais
	if(somentePorVogais(frase))
		MyIO.print("SIM ");
	else
		MyIO.print("NAO ");

	
	//consoantes
	if(somentePorConsoantes(frase))
		MyIO.print("SIM ");
	else
		MyIO.print("NAO ");


	//inteiro
	if(ehUmNumeroInteiro(frase))
		MyIO.print("SIM ");
	else
		MyIO.print("NAO ");


	//real
	if(ehUmNumeroReal(frase))
		MyIO.println("SIM");
	else
		MyIO.println("NAO");


}
/*
O metodo vai evaluar se alguma letra da string NAO é uma vogal. Por padrão será constituida apenas por vogais,
e caso encontre um caractere do alfabeto nao vogal, retornará false.
*/

public static boolean somentePorVogais(String frase){

	//assumo que tudo é vogal até se provar o contrário
	boolean apenasVogais = true;	
	char[] conjuntoLetras  = {'a','e','i','o','u', 'A', 'E', 'I', 'O', 'U'};
	//preciso detectar se um char em especifico nao eh vogal

	//percorrer todas as letras
	for(int i = 0; i<frase.length();i++){
		boolean naoEhVogal = true;
		
		//descobrir se algum item do arranjo de chars é igual ao da string, ou seja, se há alguma vogal
		for(int j = 0; j < 10; j++){

			//se for um numero nao eh vogal
			boolean ehNumero = frase.charAt(i) >= '0' && frase.charAt(i) <= '9' ;

			if(ehNumero || frase.charAt(i) == conjuntoLetras[j] ){
				//MyIO.println("Achei a vogal "+conjuntoLetras[j]);
				naoEhVogal = false;
				j = 10;
			}
		}
		
		
		if(naoEhVogal){
			apenasVogais = false;
			i = frase.length();
		}
	}
	return apenasVogais;

}
/*
Metodo vai avaliar se ha apenas consoantes
*/

public static boolean somentePorConsoantes(String frase){
	//eh consoante ate provar o contrário
	boolean apenasConsoantes = true;
	
	//conjunto de vogais
	char [] conjuntoLetras  = {'a','e','i','o','u', 'A', 'E', 'I', 'O', 'U'};
	boolean ehVogal = false;

	//testar todas as letras da string
	for(int i = 0; i<frase.length();i++){

		//testar um char da string com todos os chars do conjuntoLetras	
		for(int j = 0; j < 10; j++){
			boolean ehNumero = frase.charAt(i) >= '0' && frase.charAt(i) <= '9';

			//se for vogal ou numero nao eh consoante
			if(ehNumero || frase.charAt(i) == conjuntoLetras[j] ){
				ehVogal = true;
				j = 10;
			}
		}
		
		
		//se alguma letra for vogal
		if(ehVogal){
			apenasConsoantes = false;
			i = frase.length();
		}
	}
	
	return apenasConsoantes;
}

//verificar se um numero é inteiro
public static boolean ehUmNumeroInteiro(String frase){

	boolean ehInteiro = true;
	//virgula ou ponto
	int quantosSeparadoresDecimais = 0;

	for (int i =0; i<frase.length(); i++) {
		//verificar se o char eh virgula ou ponto
		boolean temSeparadorDecimal = frase.charAt(i) == ',' || frase.charAt(i) == '.';

		//nums estao nesse intervalo
		boolean ehNumero = frase.charAt(i) >= '0' && frase.charAt(i) <= '9';

		if (temSeparadorDecimal) {
			quantosSeparadoresDecimais++;
		}
		//se um char nao eh numero ou separador decimal nao é um numero
		if(!ehNumero && !temSeparadorDecimal){
			ehInteiro = false;
			i = frase.length();
		}

	}
	//um inteiro so pode ter 0 separadores decimais
	if(quantosSeparadoresDecimais != 0)
		ehInteiro = false;

	return ehInteiro;


}
//verificar se um numero é real. Lembrando que todo inteiro é real
public static boolean ehUmNumeroReal(String frase){
	boolean ehReal = true;

	//virgula ou ponto
	int quantosSeparadoresDecimais = 0;
	
	
	for (int i =0; i<frase.length(); i++) {
		boolean temSeparadorDecimal = frase.charAt(i) == ',' || frase.charAt(i) == '.';

		boolean ehNumero = frase.charAt(i) >= '0' && frase.charAt(i) <= '9';

		if(temSeparadorDecimal) 
			quantosSeparadoresDecimais++;
		//se nao for nem numero nem separador decimal o que temos nao é um real
		if(!ehNumero && !temSeparadorDecimal){
			ehReal = false;
			i = frase.length();
		}

	}
	//um real só pode ter um separador decimal, porém pode ser igual a 0 tb ( caso dos inteiros)
	if(quantosSeparadoresDecimais > 1)
		ehReal = false;

	return ehReal;

}
//verificar fim da entrada
static boolean estaNoFim(String frase){
	return (frase.length() >= 3 && frase.charAt(0) == 'F' && frase.charAt(1) == 'I' && frase.charAt(2) == 'M'); 
}
}
