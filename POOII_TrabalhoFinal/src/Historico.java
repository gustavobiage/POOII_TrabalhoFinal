public class Historico {
    Jogada[] jogadas = new Jogada[100000];
    int posFinal;
    int posBusca;

    public Historico() {
        posBusca = 0;
        posFinal = 0;
    }

    public void desfazer() {
        if(posBusca == 0) return;

        posBusca--;
        //TODO Desfazer movimento
    }

    public void refazer() {
        if(posBusca == posFinal) return;

        //TODO Refazer movimento
        posBusca++;
    }

    public void escreverHistorico(Posicao posicaoAtual, Posicao posicaoNova) {
        Jogada jogada = new Jogada(posicaoAtual, posicaoNova);
        jogadas[posBusca] = jogada;
        posFinal = posBusca+1;
        posBusca++;
    }
}