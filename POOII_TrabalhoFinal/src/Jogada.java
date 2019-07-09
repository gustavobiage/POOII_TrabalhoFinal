import java.awt.*;

public class Jogada {

    private Posicao posicaoAtual;
    private Posicao posicaoNova;
    private String tipoDeJogada;
    private Peca pecaDestruida;
    
    public Jogada(Posicao posicaoAtual, Posicao posicaoNova, String tipoDeJogada, Peca pecaDestruida) {
        this.posicaoAtual = posicaoAtual.clone();
        this.posicaoNova = posicaoNova.clone();
        this.tipoDeJogada = tipoDeJogada;
        this.pecaDestruida = pecaDestruida;
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
    
    public Peca GetPecaDestruida() {
    	return pecaDestruida;
    }
    
    public String GetTipoDeJogada () {
    	return tipoDeJogada;
    }
}