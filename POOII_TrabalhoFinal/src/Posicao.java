import java.awt.*;

public class Posicao {
	
	private Dimension dimension;
	private Peca pecaPresente;
	
	public Posicao (int horizontal, int vertical) {
		dimension = new Dimension (horizontal,vertical);
	}
	
	public void AdicionarPecaAPosição (Peca peca) {
		pecaPresente = peca;
	}
	
	public Peca GetPeca () {
		return pecaPresente;
	}
	
	public Dimension GetDimension () {
		return dimension;
	}
	
}
