    

public abstract class Pe�a {
	
	//1 branco, 2 preto
	private int jogador;
	private Pino pino;
	private boolean j�Moveu = false;
	
	public Pe�a (Posi��o posi��o, int jogador) {
		this.jogador = jogador;
		posi��o.AdicionarPe�aAPosi��o(this);
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