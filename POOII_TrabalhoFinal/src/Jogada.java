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
}
