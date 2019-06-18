    

public abstract class Peça {
	
	//1 branco, 2 preto
	private int jogador;
	private Pino pino;
	private boolean jáMoveu = false;
	
	public Peça (Posição posição, int jogador) {
		this.jogador = jogador;
		posição.AdicionarPeçaAPosição(this);
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