public abstract class Peca {
	
	//1 branco, 2 preto
	protected int jogador;
	protected Posicao posicao;
	private Pino pino;
	
	public Peca (Posicao posicao, int jogador) {
		this.jogador = jogador;
		posicao.AdicionarPecaAPosição(this);
		this.posicao = posicao;
	}
	
	public void Mover (Posicao posicao) {
		this.posicao.RemoverPeca ();
		posicao.AdicionarPecaAPosição(this);
		this.posicao = posicao;
	}

	public int GetJogador () {
		return jogador;
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
}