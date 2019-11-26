#include <stdio.h>
#include <stdlib.h>

//prototipos
void printarMaiorMenor(int array[], int tamArray);

int main(){

  int array[] = {1,2,3,4,5,6};
  int tamArray = (int) sizeof(array)/sizeof(array[0]);

  printarMaiorMenor(array, tamArray);

}
//metodo ja otimizado
void printarMaiorMenor(int array[], int tamArray){
  int maior = array[0];
  int menor = array[0];
  printf("Tam do array inserido: %d\n", tamArray);  
  
  for(int i = 1; i<tamArray; i++){
    if(array[i] > maior){
      maior = array[i];
    }
    if(array[i] < menor){
      menor = array[i];
    } 

  }
  printf("Maior elemento do arranjo: [%d]\n", maior);
  printf("Menor elemento do arranjo: [%d]\n", menor);

}
