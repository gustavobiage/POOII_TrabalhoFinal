public abstract class Peca {

    private String nome;
    private Lado lado;

    private int x, y;

    public String pegarNome() {
        return nome + "_" + lado;
    }

    public void informarNome(String nome) {
        this.nome = nome;
    }

    public void informarLado(Lado lado) {
        this.lado = lado;
    }

    public Lado pegarLado() {
        return this.lado;
    }

    public void movimentos(){}

    public int pegarX() {
        return posicao.GetDimension().width;
    }

    public int pegarY() {
        return posicao.GetDimension().height;
    }

    protected Posicao posicao;
    private Pino pino;

    public Peca (Posicao posicao, int jogador) {
        if(jogador == 1) {
            lado = Lado.BRANCAS;
        } else {
            lado = Lado.PRETAS;
        }

        posicao.adicionarPecaAPosicao(this);
        this.posicao = posicao;
    }

    public void Mover (Posicao posicao) {
        this.posicao.RemoverPeca ();
        posicao.adicionarPecaAPosicao(this);
        this.posicao = posicao;
    }

    public int GetJogador () {
        if(Lado.BRANCAS == this.lado) return 1;
        return 2;
    }

    public Pino GetPino () {
        return pino;
    }

    public void AddPino (Pino pino) {
        this.pino = pino;
    }

    public void RemoverPino () {
        pino = null;
    }

    public Posicao GetPosicao () {
        return posicao;
    }
    enum Lado{
        BRANCAS, PRETAS;
    }
}
