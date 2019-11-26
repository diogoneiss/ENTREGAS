#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <stdlib.h>

//prototipos
bool estaNoFim(const char* frase);
bool ehPalindromo(const char* frase);

//tamamhos
const int TAM_ENTRADA = 1000;
const int TAM_LINHA = 1000;
	

int main(){

	//criar array de arrays
	char conjuntoEntrada[TAM_ENTRADA][TAM_LINHA];
	//array de entrada
	char fraseInserida[TAM_LINHA];

	//leitura e armazenamento

	int tamanhoRealEntradas = 0;

	do{
		//ler uma entrada de tamanho TAM_LINHA do stdin
		fgets(conjuntoEntrada[tamanhoRealEntradas], TAM_LINHA, stdin);
		tamanhoRealEntradas++;
		
	}while(!estaNoFim(conjuntoEntrada[tamanhoRealEntradas -1]));

	// retirar o FIM 
	tamanhoRealEntradas--;

	// se for palindromo printa sim
	for (int i = 0; i < tamanhoRealEntradas; i++)
	{
		if(ehPalindromo(conjuntoEntrada[i]))
			printf("SIM\n");
		else
			printf("NAO\n");
	}
	
	return 0;

}

bool ehPalindromo(const char* frase){

	bool serPalindromo = true;

	//-2 pra tirar \n e \0 que sao contabilizados no strlen
	int j = strlen(frase) -2;
	int i=0;
	
	//sai do loop se for descoberto que nao eh palindromo
	while(i < j && serPalindromo){
		//ultimo com primeiro
		if(frase[i] != frase[j]){
			serPalindromo = false;
		}

		//aumenta o inicio e decrementa o fim
		i++;
		j--;
	}

	return serPalindromo;

}
//ve se esta no fim da leitura
bool estaNoFim(const char* frase){
	return (strlen(frase) >= 3 && frase[0] == 'F' && frase[1] == 'I' && frase[2] == 'M'); 
}
