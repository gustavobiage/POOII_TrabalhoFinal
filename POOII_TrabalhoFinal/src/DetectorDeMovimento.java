
public interface DetectorDeMovimento {
	
	abstract void TornarAtiva ();
	abstract boolean JaMoveu ();
	abstract void Desativar();
	abstract void AdicionarMovimento();
	abstract void RemoverMovimento();
}
