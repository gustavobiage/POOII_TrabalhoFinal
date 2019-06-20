
public class Torre extends Peça implements DetectorDeMovimento {

	private boolean ativa = false;
	
	public Torre (Posição posição, int jogador) {
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
