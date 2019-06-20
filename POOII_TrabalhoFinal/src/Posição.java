import java.awt.*;

public class Posição {
	
	private Dimension dimension;
	private Peça peçaPresente;
	
	public Posição (int horizontal, int vertical) {
		dimension = new Dimension (horizontal,vertical);
	}
	
	public void AdicionarPeçaAPosição (Peça peça) {
		peçaPresente = peça;
	}
	
	public Peça GetPeça () {
		return peçaPresente;
	}
	
	public Dimension GetDimension () {
		return dimension;
	}
	
}
