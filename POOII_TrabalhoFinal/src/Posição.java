import java.awt.*;

public class Posi��o {
	
	private Dimension dimension;
	private Pe�a pe�aPresente;
	
	public Posi��o (int horizontal, int vertical) {
		dimension = new Dimension (horizontal,vertical);
	}
	
	public void AdicionarPe�aAPosi��o (Pe�a pe�a) {
		pe�aPresente = pe�a;
	}
	
	public Pe�a GetPe�a () {
		return pe�aPresente;
	}
	
	public Dimension GetDimension () {
		return dimension;
	}
	
}
