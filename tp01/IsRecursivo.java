public class IsRecursivo {

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
//versao sobrecarregada
public static boolean somentePorVogais(String frase){
	return somentePorVogais(frase, 0);
}
    public static boolean somentePorVogais(String frase , int contador){

        //assumo que tudo é vogal até se provar o contrário
        boolean apenasVogais = true;
        

        if(contador < frase.length()){
			//se há um char dentro da string nao é vogal entao nao é constituido somente por vogais
				if(ehVogal(frase.charAt(contador), 0) == 0)
					apenasVogais = false;

				else
					apenasVogais = somentePorVogais(frase, contador+1);	
		}
        
        return apenasVogais;

	}
	//verificam se um char é vogal. A versao abaixo é sobrecarregada
	public static int ehVogal(char letra){
		return ehVogal(letra, 0);
	}
	public static int ehVogal(char letra, int contador){
		char[] conjuntoLetras  = {'a','e','i','o','u', 'A', 'E', 'I', 'O', 'U'};
		boolean ehNumero = letra >= '0' && letra <= '9';

		int vogais = 0;
		if(ehNumero)
			contador = conjuntoLetras.length;
		
		else if(contador <conjuntoLetras.length){

			if(letra == conjuntoLetras[contador])
				vogais++;

			vogais += ehVogal(letra, contador+1);	
		}
		

		return vogais;
	}

//Os dois abaixo servem praverificar se so ha consoantes, recursivamente
//versao sobrecarregada
public static boolean somentePorConsoantes(String frase){
	return somentePorConsoantes(frase, 0);
}

public static boolean somentePorConsoantes(String frase,int contador){
        //eh consoante ate provar o contrário
		boolean apenasConsoantes = true;

		//parar ao chegar ao fim da frase
		if(contador < frase.length()){
			if(!ehConsoante(frase.charAt(contador)))
				apenasConsoantes = false;
			else
				//caso da recursao
				apenasConsoantes = somentePorConsoantes(frase, contador+1);	
	}			
	
	
    return apenasConsoantes;
}
/*
Metodo vai avaliar se  um char é consoante
*/
public static boolean ehConsoante(char letra){
	
	boolean ehNumero = letra >= '0' && letra <= '9';
	boolean ehLetra = (letra >= 'a' && letra <= 'z') ||( letra >= 'A' && letra <= 'Z');
	boolean ehConsoante;

	//se nao for numero ou nao for letra sai direto
	if(ehNumero || !ehLetra)
		ehConsoante = false;	
	
	else 
		 ehConsoante = ehVogal(letra, 0) == 0 && ehLetra;	
		//verifica se ehVogal retorna 0 (ou seja, 0 vogais) e é letra
	

	return ehConsoante;
}

	//verificar se um numero é inteiro
	//versao sobrecarregada
	public static boolean ehUmNumeroInteiro(String frase){
		return ehUmNumeroInteiro(frase, 0);
	}
    public static boolean ehUmNumeroInteiro(String frase, int contador){

        boolean ehInteiro = true;
		
		//virgula ou ponto
		int quantosSeparadoresDecimais = contadorSeparadores(frase);
		boolean temSeparadorDecimal = quantosSeparadoresDecimais != 0;
        //verificar se o char eh virgula ou ponto
		if(temSeparadorDecimal)
			ehInteiro = false;
		
		else if(contador < frase.length()){
            //nums estao nesse intervalo
            boolean ehNumero = frase.charAt(contador) >= '0' && frase.charAt(contador) <= '9';
	
			if(!ehNumero)
				ehInteiro = false;
			else
				ehInteiro = ehUmNumeroInteiro(frase, contador+1);

        }
       
        return ehInteiro;


    }
	//Os dois abaixo servem pra verificar se um numero é real. Lembrando que todo inteiro é real
	//versao sobrecarregada
	public static boolean ehUmNumeroReal(String frase){
		return ehUmNumeroReal(frase, 0);
	}

    public static boolean ehUmNumeroReal(String frase, int contador){
		boolean ehReal = true;
		//armazena o num de separadores
        int quantosSeparadoresDecimais = contadorSeparadores(frase);
       

        if(contador < frase.length()) {
            boolean ehNumero = frase.charAt(contador) >= '0' && frase.charAt(contador) <= '9';

            //se nao for nem numero nem separador decimal o que temos nao é um real
            if(!ehNumero && frase.charAt(contador) != ',' && frase.charAt(contador) != '.'){
                ehReal = false;
                contador = frase.length();
            }
            else if(quantosSeparadoresDecimais > 1)
                ehReal = false;

            else
                ehReal = ehUmNumeroReal(frase, contador+1);
        }
        //um real só pode ter um separador decimal, porém pode ser igual a 0 tb ( caso dos inteiros)

        return ehReal;

	}
	//servem pra contar '.' ou ','
	//versao sobrecarregada
	public static int contadorSeparadores(String frase){
		return contadorSeparadores(frase, 0);
	}

    public static int contadorSeparadores(String frase, int contador){
        int separador = 0;
        int retorno = 0;

        if(contador < frase.length()){
            if(frase.charAt(contador) == ',' || frase.charAt(contador) == '.'){
                separador++;
            }

            retorno = separador + contadorSeparadores(frase, contador+1);
        }
        return retorno;
    }

    //verificar fim da entrada
    static boolean estaNoFim(String frase){
        return (frase.length() >= 3 && frase.charAt(0) == 'F' && frase.charAt(1) == 'I' && frase.charAt(2) == 'M');
    }
}
