
public class Torre extends Pe�a implements DetectorDeMovimento {

	private boolean ativa = false;
	
	public Torre (Posi��o posi��o, int jogador) {
		super(posi��o, jogador);
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
