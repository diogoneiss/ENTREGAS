#include <stdio.h>
#include <stdlib.h>

int main(){
    
	int contador = 0;
	double entrada;
	
	//tamamho do num de linhas
	int tamanho;
    scanf("%d",&tamanho);

	//ptr pro arquivo
    FILE *arq;
    arq = fopen("exemplo.txt","wb");

	//loop pra escrita no arquivo, com a funcao fwrite
	/*
	O 1o paRâmetro é o ponteiro dos dados a serem escritos, qu é &entrada
	O 2o o tamanho de cada bloco, que é o tamanho de cada double
	o 3o o numero de elementos com o tamanho especificado em 2, que será, portanto, 1
	o 4o é o fileptr de destino
	 */
    for(int i = 0; i < tamanho; i++){
		//ler double
        scanf("%lf",&entrada);

        fwrite(&entrada, sizeof(double), 1, arq);
        contador++;
    }
    fclose(arq);

    arq = fopen("exemplo.txt","rb");

    double entrada_arq;
    int aux;

    //conveter o contador pra bytes
	contador *= sizeof(double);

    for(int i = 0; i < tamanho; i++){

		//voltar o contador do eof pra posicao do ultimo double
        contador -= sizeof(double);

		//andar contador bytes do começo do arquivo
        fseek(arq,contador,SEEK_SET);
		//ler dados 1 bloco de dados do tamanho do double e jogar no entrada_arq
        fread(&entrada_arq, sizeof(double), 1, arq);
		//se for igual a versao truncada, printar a versao truncada ( em int)
        if(entrada_arq == (int)entrada_arq){
            aux = (int)entrada_arq;
            printf("%d\n",aux);
        }
        else 
			printf("%g\n",entrada_arq);
    }
    fclose(arq);
}
