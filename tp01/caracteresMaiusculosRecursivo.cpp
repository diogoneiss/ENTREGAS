#include <stdio.h>
#include <stdlib.h>
#include <string.h>

//prototipos de funcoes

bool estaNoFinal(char frase[]);
bool ehMaiuscula(char letra);
int quantasMaiusculas(char frase[], int posicao);
int quantasMaiusculas(char frase[]);


// constante global do tamanho max do charArray

const int TAM_LINHA = 1000;
const int TOTAL_ENTRADAS = 1000;

int main(int argc, char** argv){

	//arranjo contendo todas as entradas e cada linha, respectivamente
	char conjuntoEntradas[TOTAL_ENTRADAS][TAM_LINHA];
	
	//contador de entradas inseridas
	int numEntrada = 0;

	do{
		//pegar do stdin e jogar dentro de frase inserida
		fgets(conjuntoEntradas[numEntrada], TAM_LINHA, stdin);
		numEntrada++;

	//subtrai um do num entrada na verificacao pq ele esta sendo incrementado acima, sem inserir outra frase
	}while(!estaNoFinal(conjuntoEntradas[numEntrada-1]));

	
	//print da resposta
	for(int i = 0;i+1<numEntrada;i++){
		printf("%d\n", quantasMaiusculas(conjuntoEntradas[i]));
	}

	return 0;

}

//sobrecarga pra deixar mais elegante
int quantasMaiusculas(char frase[]){
	return quantasMaiusculas(frase, 0);
}

int quantasMaiusculas(char frase[], int posicao){
int total = 0;
int resultadoRetorno;
		//se nao for fim do array executa
		if(posicao < strlen(frase)){
			//incrementa apenas se for maiuscula
			if(ehMaiuscula(frase[posicao]))
				total++;					
			//recursividade
			resultadoRetorno = total + quantasMaiusculas(frase, posicao+1);
			}
		//fim da recursividade
		else
			resultadoRetorno = total;
	
	return resultadoRetorno;
	}

//ve se eh maiuscula
bool ehMaiuscula(char letra){
	// se está entre A e Z é maiuscula, ora bolas
	return letra >= 'A' && letra <= 'Z';
}

//verifica se eh o final
bool estaNoFinal(char frase[]){
	return (strlen(frase) >=3 && frase[0] == 'F' && frase[1] == 'I' && frase[2] == 'M') ;
}





