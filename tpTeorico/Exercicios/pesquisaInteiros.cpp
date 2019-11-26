#include <stdio.h>
#include <stdlib.h>

//prototipos
bool pesquisarNum(int pesquisa, int array[], int tamArray);
bool pesquisarNumBinario(int pesquisa, int array[], int tamArray);

int main(){

  int array[] = {1,2,3,4,5,6};
  int tamArray = (int) sizeof(array)/sizeof(array[0]);

  if(pesquisarNumBinario(4, array, tamArray))
     printf("SIM\n");
  else
     printf("NÃO\n");

}

//metodo otimizado linearmente
bool pesquisarNum(int pesquisa, int array[], int tamArray){
  printf("Tam do array inserido: %d\n", tamArray);  
  
  bool encontrado = false; 
  
  for(int i = 1; i<tamArray && !encontrado; i++){
    if(array[i] == pesquisa){
        encontrado = true;
        printf("Posicao do elemento [%d] no arranjo: [%d]\n", pesquisa, i);
    }
  }

  return encontrado;
}
//metodo otimizado linearmente
bool pesquisarNumBinario(int x, int array[], int tamArray){
  printf("Tam do array inserido: %d\n", tamArray);  
  int esq = 0, dir = tamArray-1, meio;
  bool encontrado = false; 
 while (esq<=dir){ 
  meio = (esq+dir)/2;
    if(array[meio] == x){
        encontrado = true;
        printf("Posicao do elemento [%d] no arranjo: [%d]\n", x, meio);
        esq = tamArray;
    }
 else if(x > array[meio])
   esq = meio+1;
 else
   dir = meio-1;
}

  return encontrado;
}
