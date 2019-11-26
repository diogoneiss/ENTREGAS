#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <stdlib.h>

//prototipos
bool estaNoFim(const char* frase);
bool ehPalindromo(const char* frase);
bool ehPalindromo(const char* frase, int contador);

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
	return ehPalindromo(frase, 0);
}


// verifica se eh palindromo
bool ehPalindromo(const char* frase, int contador){

	bool serPalindromo = false;
	
	//tirar o eol da string e o ultimo item
	int tam = (strlen(frase)-2)-contador;
	//MyIO.println("Contador: " +contador);

	if(contador < strlen(frase)/2){
	//sai do loop se for descoberto que nao eh palindromo
		//ultimo com primeiro
		if(frase[contador] == frase[tam]){
			serPalindromo = ehPalindromo(frase, contador+1);
		}
		else
			serPalindromo = false;
	}
	else
		serPalindromo = true;
	

	return serPalindromo;

}
//ve se esta no fim da leitura
bool estaNoFim(const char* frase){
	return (strlen(frase) >= 3 && frase[0] == 'F' && frase[1] == 'I' && frase[2] == 'M'); 
}
