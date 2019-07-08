import java.io.*;

public class GestaoArquivoTexto {

    static private FileWriter fileWriter;
    static private BufferedWriter bufferedWriter;
    static private FileReader fileReader;
    static private BufferedReader bufferedReader;

    static private File file;

    public GestaoArquivoTexto() {}

    static void abrirArquivo(String endereco) {
            file = new File(endereco);

            if(!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                fileWriter = new FileWriter(file, true);
                fileReader = new FileReader(file);

                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedReader = new BufferedReader(fileReader);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    static void fecharArquivo() {

        file = null;

        try {
            bufferedWriter.close();
            bufferedReader.close();
            fileWriter.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String lerProximo() {
        String nextLine;
        try {
            nextLine = bufferedReader.readLine();
            if(nextLine != null) return  nextLine;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static void exreverProximaLinha(String line) {
        try {
            bufferedWriter.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void limparArquivo() {
        try {
            new FileWriter(file).write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
