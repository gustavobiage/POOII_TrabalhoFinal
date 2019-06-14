
public abstract class Peça {
	
	private Posição posição;
	private Tabuleiro tabuleiro;
	//1 branco, 2 preto
	private int jogador;
	private Pino pino;
	private boolean jáMoveu = false;
	
	public Peça (Posição posição, int jogador) {
		this.posição = posição;
		this.jogador = jogador;
		posição.AdicionarPeçaAPosição(this);
	}
	
	protected void Mover (Posição novaPosição) {
		this.posição = novaPosição;
	}
	
	public int GetJogador () {
		return jogador;
	}
	
	public Pino GetPino () {
		return pino;
	}
	
	public boolean JáMoveu () {
		return jáMoveu;
	}
	
	/*Remover métodos a seguir:*/
	public void SetPino (Pino pino) {
		this.pino = pino;
	}
}
