import java.awt.*;

public class Posição {
	
	private Dimension posição;
	private Peça peçaPresente;
	
	public Posição (int horizontal, int vertical) {
		posição = new Dimension (horizontal,vertical);
	}
	
	public void AdicionarPeçaAPosição (Peça peça) {
		peçaPresente = peça;
	}
	
	public Peça GetPeça () {
		return peçaPresente;
	}
	
}
