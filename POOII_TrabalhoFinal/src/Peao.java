
public class Peao extends Peca {

	private boolean ativa = false;
	
	public Peao (Posicao posicao, int jogador) {
		super(posicao, jogador);
		this.informarNome("Peao");
	}

	@Override
	public void Mover(Posicao posicao) {
		super.Mover(posicao);
		if (posicao.GetDimension().height == 7 - 7*(this.GetJogador()-1)) {
			Evoluir ();
		}
	}

	
	public boolean ChecarEvolucao () {
		return posicao.GetDimension().height == 6 - 5*(this.GetJogador()-1);
	}
	
	public boolean ChecarEnPassant (Posicao posicao) {
		return posicao.GetPeca() == null && posicao.GetDimension().width != this.posicao.GetDimension().width;
	}

	private void Evoluir () {
		posicao.RemoverPeca();
		Tabuleiro.GetInstance().RemoverPecaDaLista(this);
		Tabuleiro.GetInstance().CriarPeca("Dama", posicao.GetDimension(), jogador);
	}
}
