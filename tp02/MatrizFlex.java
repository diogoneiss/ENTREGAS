
public class MatrizFlex{

    private Celula inicio;
	private int linha, coluna;
	
	//inicio getters e setters
	public Celula getInicio() {
		return inicio;
	}
	public void setInicio(Celula inicio) {
		this.inicio = inicio;
	}
	public int getLinha() {
		return linha;
	}
	public void setLinha(int linha) {
		this.linha = linha;
	}
	public int getColuna() {
		return coluna;
	}
	public void setColuna(int coluna) {
	this.coluna = coluna;
	}
	//fim getters e setters

    public MatrizFlex (){
       this(3, 3);
    }
 
    public MatrizFlex (int linha, int coluna){
      setLinha(linha);
	   setColuna(coluna);
	   
	   //alocar a matriz com this.linha linhas e this.coluna colunas
	   inicio = new Celula();
	   Celula i = inicio;

		//criar as células a direita e ir conectando
		//começa em 1 pq já há o "inicio"
		for(int contadorColuna = 1; contadorColuna < getLinha(); contadorColuna++){
			i.dir = new Celula();
			i.dir.esq = i;
			i = i.dir;
		}

      //voltar i pro inicio
      Celula tmp = inicio;
		i = tmp;

		//criar as celulas abaixo para cada item a direita, fazendo as devidas conexões
		//também começa em 1 pq já há inicio
		for(int contadorLinha = 1; contadorLinha < getLinha(); contadorLinha++){
         
         Celula j = new Celula();
			i.inf = j;
			j.sup = i;
         
         //começa em 1 pq acabei de criar um j
         for(int contadorColuna = 1; contadorColuna < getColuna(); contadorColuna++){
            //movimentar i pra direita
            i = i.dir;

            j.dir = new Celula();
            //fazer a ligação
            j.dir.esq = j;

            //movimentar pra direita e conectar com o i acima
            j = j.dir;
            j.sup = i;
            i.inf = j;
         }
         //descer inicio
         tmp = tmp.inf;
         i = tmp;
		}



    }
    public void inserir(){
         Celula tmp = inicio;
         Celula inicioColuna = inicio;

      for(int linha = 0; linha < this.getLinha(); linha++){
         String linhaCompleta = MyIO.readLine();
         String arrayInteirosComoString[] = linhaCompleta.split(" ");
         //MyIO.println("Linha completa: "+linhaCompleta);

         for(int i = 0; i < arrayInteirosComoString.length; i++){
            int entrada = Integer.parseInt(arrayInteirosComoString[i]);
            //MyIO.println("Elemento inserido: "+entrada);
            tmp.elemento = entrada;
            //MyIO.println("Elemento inserido na celula: "+tmp.elemento);
            tmp = tmp.dir;

         } 

         inicioColuna = inicioColuna.inf;
         tmp = inicioColuna;
      }
    }
 
    public MatrizFlex soma (MatrizFlex m) {
       //criar matriz de resposta
      MatrizFlex resp = new MatrizFlex(m.getLinha(), m.getColuna());

      //inicio das 3 matrizes 
      Celula inicioM = m.inicio;
      Celula inicioThis = this.inicio;
      Celula inicioR = resp.inicio;

      //armazenarem a posicao na linha
      Celula tmpM = m.inicio;
      Celula tmpThis = this.inicio;
      Celula tmpR = resp.inicio;

      //verifica se tem larguras e comprimentos iguais
      if(this.linha == m.linha && this.coluna == m.coluna){
         
         //o for interior anda pela coluna, enquanto o exterior pelas linhas
         for (int i = 0; i < m.getLinha(); i++) {
            
            for (int j = 0; j < m.getColuna(); j++) {
               //soma de elementos
               inicioR.elemento = inicioM.elemento+inicioThis.elemento;
              
               //movimentação pra direita
               inicioR = inicioR.dir;
               inicioM = inicioM.dir;
               inicioThis = inicioThis.dir;
               
            }

            //descer as referencias
           
               tmpM = tmpM.inf;
               tmpR = tmpR.inf;
               tmpThis = tmpThis.inf;


               inicioR = tmpR;
               inicioM = tmpM;
               inicioThis = tmpThis;
            
         }
      }
 
       return resp;
    }
 
    public MatrizFlex multiplicacao (MatrizFlex m) {
       //matriz de resposta
      MatrizFlex resp = new MatrizFlex(m.getLinha(), this.getLinha());
      /*

      METODO NAO FUNCIONA, REESCREVI ABAIXO BASEADO NAS SUGESTOES DE UM COLEGA

      Celula inicioThis = this.inicio;
      Celula inicioM = m.getInicio();
      Celula inicioR = resp.getInicio();
      Celula posicaoColunaAnterior;

      Celula tmpinicioThis = this.inicio;
      Celula tmpinicioM = m.getInicio();
      Celula tmpnicioR = resp.getInicio();
      Celula tmpposicaoColunaAnterior;

      

 
         //verificando possibilidade de resposta
      if(this.getLinha() == m.getColuna() && this.getColuna() == m.getLinha()){
         
         //eixo x da matriz
         for (int i = 0; i < resp.getLinha(); i++) {

            posicaoColunaAnterior = inicioR;
            //eixo y da matriz
            for (int j = 0; j < resp.getColuna(); j++) {
               int total = 0;

               //contador de quantas vezes será necessário varrer a matriz multiplicando e somando
               for (int k = 0; k < m.getLinha(); k++) {

                  //soma da multiplicacao do elemento ik com jk
                  total += inicioThis.elemento * inicioM.elemento;
                  //direita em um e descer no outro
                  inicioThis = inicioThis.dir;
                  inicioM = inicioM.inf;
               }
               tmpinicioThis = tmpinicioThis.inf;
               inicioThis = tmpinicioThis;

               tmpinicioM = tmpinicioM.dir;
               inicioM = tmpinicioM;
               //armazenar o total
               inicioR.elemento = total;
               inicioR = inicioR.dir;
            }
            //i é pras linhas, preciso voltar pra onde estava na coluna anteriormente e descer
            inicioR = posicaoColunaAnterior.inf;
         }
      }
      else
         MyIO.println("Matrizes inválidas para a operação de multiplicação.");
*/

      int soma = 0;

      Celula a1L = this.inicio;
      Celula a2L = m.inicio;
      Celula a3L = resp.inicio;

      //controladores
      Celula a1C;
      Celula a2C;
      Celula a3C;
      Celula a2X;

      for (int controleLinha = 0; controleLinha < this.linha; controleLinha++) {
         //setar os ponteiros de coluna na linha correta
         a1C = a1L;
         a2C = a2L;
         a3C = a3L;
         for (int controleColuna = 0; controleColuna < this.coluna; controleColuna++) {
            
            //fazer a soma
            for(a2X = a2C; a2X != null; a2X = a2X.inf, a1C=a1C.dir)
               soma += a1C.elemento*a2X.elemento;

            //armazenar a soma
            a3C.elemento = soma;
            a3C = a3C.dir;
            soma = 0;
            
            a2C = a2C.dir;

            a1C = a1L;
         }
         //descer os ponteiros
         a3L = a3L.inf;
         a1L = a1L.inf;
         
      }

      return resp;
    }
 
    public boolean isQuadrada(){
       return (this.linha == this.coluna);
    }
 
    public void mostrarDiagonalPrincipal (){
       if(isQuadrada() == true){
         
         Celula i = inicio;

          //desce e vai pra direita
          while(i != null){
             if(i != null){
               System.out.print(i.elemento+ " ");
               i = i.dir;
               if(i != null)
                  i = i.inf;
            }
         }
         
          MyIO.println("");
      }
      else
         MyIO.println("Erro! Matriz nao é quadrada.");
    }
 
    public void mostrarDiagonalSecundaria (){
       if(isQuadrada() == true){
        
         Celula inicioDiagSeg = inicio;

         //mover pra direita até chegar no fim, que é quando dir é null
         do{
            inicioDiagSeg = inicioDiagSeg.dir; 
         }while(inicioDiagSeg.dir != null);
        

         //desce e vai pra esquerda
         Celula i = inicioDiagSeg;

         do{
            System.out.print(i.elemento+" ");
            i = i.esq;
            //evitar nullptr execp
            if(i != null)
               i = i.inf;
            
         }while(i != null);
         MyIO.println("");
       }
       else
         MyIO.println("Erro! Matriz nao é quadrada.");
    }
 
    public void mostrar(){
      
       //ir pra baixo
      Celula i = inicio;
      Celula tmp = inicio;

      while(i != null){
         while(i != null){
            MyIO.print(i.elemento+" ");
            i = i.dir;
         }
         MyIO.print("\n");
         tmp = tmp.inf;
         i = tmp;
      }

         //MyIO.print("\n");
      
   
   }
   public static void main(String[] args) {
   
      MyIO.setCharset("UTF-8");
      MatrizFlex arranjo[] = leituraMatrizes();

      for (int i = 0; i < arranjo.length; i++) {
        // arranjo[i].mostrar();
         if(i % 2 == 0){
            arranjo[i].mostrarDiagonalPrincipal();
            arranjo[i].mostrarDiagonalSecundaria();
            MatrizFlex soma = arranjo[i].soma(arranjo[i+1]);
            soma.mostrar();
            MatrizFlex mult = arranjo[i].multiplicacao(arranjo[i+1]);
            mult.mostrar();
         }
      }

      
   }
   public static MatrizFlex[] leituraMatrizes(){
         //ler a primeira linha, contendo os casos de teste. O numero de referencias é igual a primeira linha *2
         MatrizFlex arranjoMatrizes[] = new MatrizFlex[MyIO.readInt()*2];
   
         for (int i = 0; i < arranjoMatrizes.length; i++) {
            //ler tamanho das linhas e colunas e criar a matriz
            arranjoMatrizes[i] = new MatrizFlex(MyIO.readInt(), MyIO.readInt());
            //ler da tela todas as entradas
            arranjoMatrizes[i].inserir();
         }

         return arranjoMatrizes;
      }
 }

class Celula {
    public int elemento;
    public Celula inf, sup, esq, dir;
 
    public Celula(){
       this(0, null, null, null, null);
    }
 
    public Celula(int elemento){
       this(elemento, null, null, null, null);
    }
 
    public Celula(int elemento, Celula inf, Celula sup, Celula esq, Celula dir){
       this.elemento = elemento;
       this.inf = inf;
       this.sup = sup;
       this.esq = esq;
       this.dir = dir;
    }
 }

	