
public class Torre extends Peca implements DetectorDeMovimento {

	private boolean ativa = false;
	private int movimentos;
	
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

	@Override
	public void AdicionarMovimento () {
		movimentos ++;
		if (movimentos == 1) {
			TornarAtiva();
		}
	}
	
	@Override
	public void RemoverMovimento () {
		movimentos --;
		if (movimentos == 0) {
			Desativar();
		}
	}
}
