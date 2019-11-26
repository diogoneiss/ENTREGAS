import java.io.RandomAccessFile;

public class Arquivo
{
    public static void main(String[] args) throws Exception
    {
        // ler o num
        int num = MyIO.readInt();

        //variavel pra armazenar tudo
        double entrada;
        
        //criar o arquivo com read and write
        RandomAccessFile arq = new RandomAccessFile("exemplo.txt", "rw");

        for(int i = 0; i < num; i++){          

            entrada = MyIO.readDouble();

            arq.writeDouble(entrada);
        }
        //fechar arquivo
        arq.close();

        //ler o arquivo já escrito
        RandomAccessFile file = new RandomAccessFile("exemplo.txt", "r");

        double dadoArquivo;
        int truncarInteiro;
        for(int i = 0; i < num; i++){

            //ler de tras pra frente
            file.seek(file.length() - (i + 1) * 8);
            dadoArquivo = file.readDouble();
            //converter pra inteiro, se o inteiro for igual ao inteiro typecasted,
            //printar so como int
            if(dadoArquivo == (int) dadoArquivo){

                truncarInteiro = (int) dadoArquivo;
                MyIO.println(truncarInteiro);
            }
            
            else MyIO.println(dadoArquivo);
        }
        file.close();
    }
}