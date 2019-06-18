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
	
	//Primeiro número largura do tabuleiro e segundo para a altura
	private Posição [][] posicoes = new Posição [8] [8];
	
	private ArrayList <Peça> peças = new ArrayList <Peça> ();
	
	private Tabuleiro () {
		
		//Cria as posições
		CriarTabuleiro ();
		
		//Cria as peças as quais irão ser linkadas a uma posição ao serem criadas
		CriarPeças ();
		
		/*Remover linhas a seguir:*/
		peças.add(new Dama(posicoes [4] [4], 2));
		//posições[4][4].GetPeça().SetPino(new Pino (Direção.horizontal));
		VerificarMovimentos (new Dimension (4,4));
	}
	
	//Percorre a matriz tabuleiro criando 
	private void CriarTabuleiro () {
		
		for (int i = 0; i < posicoes[0].length; i++) {
			for (int j = 0; j < posicoes[1].length; j ++) {
				posicoes[i][j] = new Posição (i, j);
			}
		}
	}
	
	private void CriarPeças () {
		
		for (int i = 0; i < 8; i ++) {
			peças.add(new Peão (posicoes [i] [1], 1));
			peças.add(new Peão (posicoes [i] [6], 2));
		}
		
		peças.add(new Torre (posicoes [0] [0], 1));
		peças.add(new Torre (posicoes [7] [0], 1));
		peças.add(new Torre (posicoes [0] [7], 2));
		peças.add(new Torre (posicoes [7] [7], 2));
		

		peças.add(new Cavalo (posicoes [1] [0], 1));
		peças.add(new Cavalo (posicoes [6] [0], 1));
		peças.add(new Cavalo (posicoes [1] [7], 2));
		peças.add(new Cavalo (posicoes [6] [7], 2));
		

		peças.add(new Bispo (posicoes [2] [0], 1));
		peças.add(new Bispo (posicoes [5] [0], 1));
		peças.add(new Bispo (posicoes [2] [7], 2));
		peças.add(new Bispo (posicoes [5] [7], 2));
		

		peças.add(new Dama (posicoes [3] [0], 1));
		peças.add(new Dama (posicoes [3] [7], 2));		
		peças.add(new Rei (posicoes [4] [0], 1));
		peças.add(new Rei (posicoes [4] [7], 2));
		
	}
	
	//Retorna a posição de uma coordenada
	public Posição GetPosiçãoPorDimensão (Dimension d) {
		return posicoes [d.width] [d.height];
	}
	
	public void VerificarMovimentos (Dimension posicaoInicial) {
		//Ocorre ao clicar na peça e demonstra os possíveis movimentos com a peça
		Peça peça = GetPosiçãoPorDimensão(posicaoInicial).GetPeça();
		ArrayList <Dimension> dimensions = AdaptadorDeMovimento.GetInstance().AdaptarMovimentos(peça, posicaoInicial, posicoes);
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
	
	/*Remover os métodos a seguir:*/
	
	public void MostrarTabuleiro () {
		for (int i = 0; i < posicoes.length; i++) {
			for (int j = 0; j < posicoes[1].length; j++) {
				System.out.print(posicoes[j][i].GetPeça() + " ");
			}
			System.out.println();
		}
		
	}
	
	public static void main (String args []) {
		//Tabuleiro tabuleiro = new Tabuleiro ();
	}
}