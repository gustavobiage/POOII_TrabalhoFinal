public abstract class Peça {
	
	//1 branco, 2 preto
	private int jogador;
	private Pino pino;
	private Posição posição;
	
	public Peça (Posição posição, int jogador) {
		this.jogador = jogador;
		posição.AdicionarPeçaAPosição(this);
		this.posição = posição;
	}

	public int GetJogador () {
		return jogador;
	}
	
	public Pino GetPino () {
		return pino;
	}
	
	public Posição GetPosição () {
		return posição;
	}
	
	/*Remover métodos a seguir:*/
	public void SetPino (Pino pino) {
		this.pino = pino;
	}
}