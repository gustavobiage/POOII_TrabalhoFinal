public abstract class Pe�a {
	
	//1 branco, 2 preto
	private int jogador;
	private Pino pino;
	private Posi��o posi��o;
	
	public Pe�a (Posi��o posi��o, int jogador) {
		this.jogador = jogador;
		posi��o.AdicionarPe�aAPosi��o(this);
		this.posi��o = posi��o;
	}

	public int GetJogador () {
		return jogador;
	}
	
	public Pino GetPino () {
		return pino;
	}
	
	public Posi��o GetPosi��o () {
		return posi��o;
	}
	
	/*Remover m�todos a seguir:*/
	public void SetPino (Pino pino) {
		this.pino = pino;
	}
}