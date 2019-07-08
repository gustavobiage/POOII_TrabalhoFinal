
public class Torre extends Peca implements DetectorDeMovimento {

	private boolean ativa = false;
	
	public Torre (Posicao posicao, int jogador) {
		super(posicao, jogador);
		this.informarNome("Torre");
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
	public void Desativar() { ativa = false;}


}
