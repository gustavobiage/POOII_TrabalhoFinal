import java.awt.*;

public class Jogada {

    private Posicao posicaoAtual;
    private Posicao posicaoNova;

    public Jogada(Posicao posicaoAtual, Posicao posicaoNova) {
        this.posicaoAtual = posicaoAtual.clone();
        this.posicaoNova = posicaoNova.clone();
    }

    public Posicao pegarPosicaoAtual() {
        return this.posicaoAtual;
    }

    public Posicao pegarPosicaoNova() {
        return this.posicaoNova;
    }

    public void informarPosicaoAtual(Posicao posicaoAtual) {
        this.posicaoAtual = posicaoAtual;
    }

    public void informarNovaPosicao() {
        this.posicaoNova = posicaoNova;
    }

//    public static Jogada indentificarJogada(char p1, int i1, int j1, char p2, int i2, int j2) {
//
//        Posicao posicaoAtual = new Posicao(i1, j1);
//
//        switch (p1) {
//            case 'D':
//                new Dama(posicaoAtual, );
//                break;
//            case 'R':
//                posicaoAtual.adicionarPecaAPosicao(new Rei(posicaoAtual, ));
//                break;
//            case 'P':
//                posicaoAtual.informarPeca(new Peao());
//                break;
//            case 'T':
//                posicaoAtual.informarPeca(new Torre());
//                break;
//            case'B':
//                posicaoAtual.informarPeca(new Bispo());
//                break;
//            case 'C':
//                posicaoAtual.informarPeca(new Cavalo());
//                break;
//            case 'O':
//                posicaoAtual.informarPeca(null);
//                break;
//        }
//
//        Posicao novaPosicao = new Posicao(new Dimension(i2, j2));
//
//        switch (p2) {
//            case 'D':
//                novaPosicao.informarPeca(new Dama());
//                break;
//            case 'R':
//                novaPosicao.informarPeca(new Rei());
//                break;
//            case 'P':
//                novaPosicao.informarPeca(new Peao());
//                break;
//            case 'T':
//                novaPosicao.informarPeca(new Torre());
//                break;
//            case'B':
//                novaPosicao.informarPeca(new Bispo());
//                break;
//            case 'C':
//                novaPosicao.informarPeca(new Cavalo());
//                break;
//            case 'O':
//                novaPosicao.informarPeca(null);
//                break;
//        }
//
//        return new Jogada(posicaoAtual, novaPosicao);
//    }
}