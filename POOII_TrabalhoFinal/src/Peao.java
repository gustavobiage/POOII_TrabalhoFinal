
public class Peao extends Peça implements DetectorDeMovimento {

	private boolean ativa = false;
	
	public Peao (Posição posição, int jogador) {
		super(posição, jogador);
	}

	@Override
	public void TornarAtiva() {
		ativa = true;
	}

	@Override
	public boolean JaMoveu() {
		return ativa;
	}

}
