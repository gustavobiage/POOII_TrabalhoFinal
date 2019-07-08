import java.awt.*;

public class Historico {
    Jogada[] jogadas = new Jogada[100000];
    int posFinal;
    int posBusca;
    TabuleiroFrame frame;

    public Historico(TabuleiroFrame frame) {
        posBusca = 0;
        posFinal = 0;

        this.frame = frame;
    }

    public void desfazer() {
        if(posBusca == 0) return;

        posBusca--;
        Tabuleiro.GetInstance().voltarJogada(jogadas[posBusca], frame);
    }

    boolean write = true;

    public Jogada refazer() {
        if(posBusca == posFinal) return null;

        write = false;

        Jogada jog = jogadas[posBusca];
        Peca peca = Tabuleiro.GetInstance().GetPosicaoPorDimension(jogadas[posBusca].pegarPosicaoAtual().GetDimension()).GetPeca();
        Tabuleiro.GetInstance().MoverPeca(peca
                , jogadas[posBusca].pegarPosicaoNova().GetDimension());
        write = true;
        posBusca++;
        return jog;
    }

    public void escreverHistorico(Posicao posicaoAtual, Posicao posicaoNova) {
        if(write) {
            Jogada jogada = new Jogada(posicaoAtual, posicaoNova);
            jogadas[posBusca] = jogada;
            posFinal = posBusca+1;
            posBusca++;
        }
    }

    public void salvarHistorico(String endreco) {
        GestaoArquivoTexto.abrirArquivo(endreco);
        GestaoArquivoTexto.limparArquivo();

        String linha;
        Jogada jogada;
        for(int i = 0; i < posFinal; i++) {
            jogada = jogadas[i];
//            linha = jogada.pegarPosicaoAtual().GetPeca().pegarNome().charAt(0) + " " + jogada.pegarPosicaoAtual().GetDimension().width + " " + jogada.pegarPosicaoAtual().GetDimension().height
//                    + " " + jogada.pegarPosicaoNova().GetPeca().pegarNome().charAt(0) + " " + jogada.pegarPosicaoNova().GetDimension().width + " " + jogada.pegarPosicaoNova().GetDimension().height + "\n";
            linha = jogada.pegarPosicaoAtual().GetDimension().width + " " + jogada.pegarPosicaoAtual().GetDimension().height + " "
                    + jogada.pegarPosicaoNova().GetDimension().width + " " + jogada.pegarPosicaoNova().GetDimension().height;
            GestaoArquivoTexto.exreverProximaLinha(linha);
        }
        GestaoArquivoTexto.fecharArquivo();
    }

    public void carregarHistorico(String endereco) {
        posFinal = 0;
        posBusca = 0;

        GestaoArquivoTexto.abrirArquivo(endereco);
        String linha = GestaoArquivoTexto.lerProximo();

        int i1, j1, i2, j2;
//        char p1, p2;

        while(linha != null) {

//            p1 = linha.charAt(0);
            i1 = linha.charAt(0) - '0';
            i2 = linha.charAt(2) - '0';

//            p2 = linha.charAt(5);
            j1 = linha.charAt(3) - '0';
            j2 = linha.charAt(4) - '0';

//            jogadas[posBusca] = Jogada.indentificarJogada(p1, i1, i2, p2, i2, j2);

            //TODO Fazer movimento no xadrez
            Peca peca = Tabuleiro.GetInstance().GetPosicaoPorDimension(new Dimension(i1, i2)).GetPeca();
            Tabuleiro.GetInstance().MoverPeca(peca, new Dimension(j1, j2));
//            posBusca++;
//            posFinal++;

            linha = GestaoArquivoTexto.lerProximo();
        }

        GestaoArquivoTexto.fecharArquivo();
    }
}