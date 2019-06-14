
public abstract class Pe�a {
	
	private Posi��o posi��o;
	private Tabuleiro tabuleiro;
	//1 branco, 2 preto
	private int jogador;
	private Pino pino;
	private boolean j�Moveu = false;
	
	public Pe�a (Posi��o posi��o, int jogador) {
		this.posi��o = posi��o;
		this.jogador = jogador;
		posi��o.AdicionarPe�aAPosi��o(this);
	}
	
	protected void Mover (Posi��o novaPosi��o) {
		this.posi��o = novaPosi��o;
	}
	
	public int GetJogador () {
		return jogador;
	}
	
	public Pino GetPino () {
		return pino;
	}
	
	public boolean J�Moveu () {
		return j�Moveu;
	}
	
	/*Remover m�todos a seguir:*/
	public void SetPino (Pino pino) {
		this.pino = pino;
	}
}
