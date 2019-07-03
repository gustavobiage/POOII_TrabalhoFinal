import java.awt.*;

public class Controle {
	
	//M�todo main:
	public static void main (String args[]) {

//		Tabuleiro.novaInstancia();

		Posicao[][] posicao = Tabuleiro.GetInstance().getPosicoes();
//		for(int i = 0; i < posicao.length; i++) {
//			for(int j = 0; j < posicao[i].length; j++) {
//
//				System.out.println(posicao[i][j].getX()+", " + posicao[i][j].getY());
//			}
//		}
		TabuleiroFrame tf = new TabuleiroFrame(Tabuleiro.GetInstance().getPosicoes());
	}
}
