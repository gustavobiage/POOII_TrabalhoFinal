public abstract class Peca {

    private String nome;
    protected int jogador;
    private Lado lado;
    protected Posicao posicao;
    private Pino pino;

    public Peca (Posicao posicao, int jogador) {

        this.posicao = posicao;
    	this.jogador = jogador;
        posicao.AdicionarPeca (this);
        
        if (jogador == 1) {
            lado = Lado.BRANCAS;
        } else {
            lado = Lado.PRETAS;
        }
    }
    
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

    public int pegarX() {
        return posicao.GetDimension().width;
    }

    public int pegarY() {
        return posicao.GetDimension().height;
    }

    public void Mover (Posicao posicao) {
    	if (this.posicao.GetPeca() == this) {
    		this.posicao.RemoverPeca ();
    	}
        posicao.AdicionarPeca (this);
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
    
    public void SetPosicao (Posicao posicao) {
    	this.posicao = posicao;
    }
    
    enum Lado{
        BRANCAS, PRETAS;
    }
}
