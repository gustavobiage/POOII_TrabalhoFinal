
public class Peao extends Peca implements DetectorDeMovimento {

	private boolean ativa = false;
	
	public Peao (Posicao posicao, int jogador) {
		super(posicao, jogador);
	}

	@Override
	public void TornarAtiva() {
		ativa = true;
	}

	@Override
	public boolean JaMoveu() {
		return ativa;
	}
	
	@Override
	public void Mover(Posicao posicao) {
		super.Mover(posicao);
		if (posicao.GetDimension().height == 7 - 7*(jogador-1)) {
			Evoluir ();
		}
	}
	
	private void Evoluir () {
		//Apaga o peão e cria uma nova peça no local.
	}
}
