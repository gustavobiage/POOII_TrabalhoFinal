import java.awt.*;
import java.util.*;

public class Tabuleiro {
	
	private static Tabuleiro tabuleiro = new Tabuleiro ();
	
	public static Tabuleiro GetInstance () {
		return tabuleiro;
	}
	
	public static void novaInstancia() {
		tabuleiro = new Tabuleiro ();
	}
	
	//Primeiro n�mero largura do tabuleiro e segundo para a altura
	private Posi��o [][] posicoes = new Posi��o [8] [8];
	
	private ArrayList <Pe�a> pe�as = new ArrayList <Pe�a> ();
	
	private Tabuleiro () {
		
		//Cria as posi��es
		CriarTabuleiro ();
		
		//Cria as pe�as as quais ir�o ser linkadas a uma posi��o ao serem criadas
		CriarPe�as ();
		
		/*Remover linhas a seguir:*/
		pe�as.add(new Dama(posicoes [4] [4], 2));
		//posi��es[4][4].GetPe�a().SetPino(new Pino (Dire��o.horizontal));
		VerificarMovimentos (new Dimension (4,4));
	}
	
	//Percorre a matriz tabuleiro criando 
	private void CriarTabuleiro () {
		
		for (int i = 0; i < posicoes[0].length; i++) {
			for (int j = 0; j < posicoes[1].length; j ++) {
				posicoes[i][j] = new Posi��o (i, j);
			}
		}
	}
	
	private void CriarPe�as () {
		
		for (int i = 0; i < 8; i ++) {
			pe�as.add(new Pe�o (posicoes [i] [1], 1));
			pe�as.add(new Pe�o (posicoes [i] [6], 2));
		}
		
		pe�as.add(new Torre (posicoes [0] [0], 1));
		pe�as.add(new Torre (posicoes [7] [0], 1));
		pe�as.add(new Torre (posicoes [0] [7], 2));
		pe�as.add(new Torre (posicoes [7] [7], 2));
		

		pe�as.add(new Cavalo (posicoes [1] [0], 1));
		pe�as.add(new Cavalo (posicoes [6] [0], 1));
		pe�as.add(new Cavalo (posicoes [1] [7], 2));
		pe�as.add(new Cavalo (posicoes [6] [7], 2));
		

		pe�as.add(new Bispo (posicoes [2] [0], 1));
		pe�as.add(new Bispo (posicoes [5] [0], 1));
		pe�as.add(new Bispo (posicoes [2] [7], 2));
		pe�as.add(new Bispo (posicoes [5] [7], 2));
		

		pe�as.add(new Dama (posicoes [3] [0], 1));
		pe�as.add(new Dama (posicoes [3] [7], 2));		
		pe�as.add(new Rei (posicoes [4] [0], 1));
		pe�as.add(new Rei (posicoes [4] [7], 2));
		
	}
	
	//Retorna a posi��o de uma coordenada
	public Posi��o GetPosi��oPorDimens�o (Dimension d) {
		return posicoes [d.width] [d.height];
	}
	
	public void VerificarMovimentos (Dimension posicaoInicial) {
		//Ocorre ao clicar na pe�a e demonstra os poss�veis movimentos com a pe�a
		Pe�a pe�a = GetPosi��oPorDimens�o(posicaoInicial).GetPe�a();
		ArrayList <Dimension> dimensions = AdaptadorDeMovimento.GetInstance().AdaptarMovimentos(pe�a, posicaoInicial, posicoes);
		for (Dimension d: dimensions) {
			Hightlight (d);
		}
	}
	
	public void Hightlight (Dimension dimension) {
		System.out.print(dimension.width + "," + dimension.height + "  ");
	}
	
	public void RemoveHightlights () {
		//Remove o highlight dos quadrados que estiverem com hightlight
	}
	
	/*Remover os m�todos a seguir:*/
	
	public void MostrarTabuleiro () {
		for (int i = 0; i < posicoes.length; i++) {
			for (int j = 0; j < posicoes[1].length; j++) {
				System.out.print(posicoes[j][i].GetPe�a() + " ");
			}
			System.out.println();
		}
		
	}
	
	public static void main (String args []) {
		//Tabuleiro tabuleiro = new Tabuleiro ();
	}
}