
public class Rei extends Peca implements DetectorDeMovimento {
	
	private boolean ativa = false;
	
	public Rei(Posicao posicao, int jogador) {
		super(posicao, jogador);
		this.informarNome("Rei");
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
	public void Desativar() {ativa = false;}
}
