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
	
	//Primeiro n�mero largura do tabuleiro e segundo altura
	private Posi��o [][] posicoes = new Posi��o [8] [8];
	private ArrayList <Pe�a> pe�as = new ArrayList <Pe�a> ();
	private int turno = 1;
	private int jogadorAtual = 1;
	
	private Tabuleiro () {
		
		//Cria as posi��es
		CriarTabuleiro ();
		
		//Cria as pe�as as quais ir�o ser linkadas a uma posi��o ao serem criadas
		CriarPe�as ();
		
		/*Remover linhas a seguir:*/
		pe�as.add(new Torre (posicoes [4] [4], 2));
		pe�as.add(new Rei (posicoes [5] [5], 2));
		pe�as.add(new Dama (posicoes [3] [3], 1));
		//posi��es[4][4].GetPe�a().SetPino(new Pino (Dire��o.horizontal));
		//VerificarMovimentos (new Dimension (4,4));
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
			pe�as.add(new Peao (posicoes [i] [1], 1));
			pe�as.add(new Peao (posicoes [i] [6], 2));
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
		//pe�as.add(new Rei (posicoes [4] [7], 2));
		
	}
	
	//Retorna a posi��o de uma coordenada
	public Posi��o GetPosi��oPorDimens�o (Dimension d) {
		return posicoes [d.width] [d.height];
	}
	
	public void VerificarMovimentos (Dimension posicaoInicial) {
		//Ocorre ao clicar na pe�a e demonstra os poss�veis movimentos com a pe�a
		Pe�a pe�a = GetPosi��oPorDimens�o(posicaoInicial).GetPe�a();
		ArrayList <Dimension> dimensions = AdaptadorDeMovimento.GetInstance().AdaptarMovimentos(pe�a, posicaoInicial);
		for (Dimension d: dimensions) {
			Hightlight (d);
		}
	}
	
	//Verifica se o jogador nao tera movimentos disponiveis resultando em um emapte:
	public void VerificarEmpate () {
		boolean empateNegado = false;
		for (Pe�a pe�a : pe�as) {
			if (pe�a.GetJogador() == jogadorAtual) {
				if (AdaptadorDeMovimento.GetInstance().AdaptarMovimentos(pe�a, pe�a.GetPosi��o().GetDimension()).size() > 0) {
					empateNegado = true;
					break;
				}
			}
		}
		if (!empateNegado) {
			Empatar ();
		}
	}
	
	public ArrayList <Torre> GetTorres (int jogador) {
		ArrayList <Torre> lista = new ArrayList <Torre> ();
		for (Pe�a pe�a: pe�as) {
			if (pe�a.getClass() == Torre.class && pe�a.GetJogador() == jogador) {
				lista.add((Torre)pe�a);
			}
		}
		return lista;
	}
	
	public Rei GetRei (int jogador) {
		for (Pe�a pe�a: pe�as) {
			if (pe�a.getClass() == Rei.class && pe�a.GetJogador() == jogador) {
				return (Rei) pe�a;
			}
		}
		System.err.println("Erro, rei nao encontrado.");
		return null;
	}
	
	public void Hightlight (Dimension dimension) {
		System.out.print(dimension.width + "," + dimension.height + "  ");
	}
	
	private void Empatar () {
		//Termina o jogo com um emapte
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
		tabuleiro.GetInstance().VerificarMovimentos (new Dimension (4,4));
	}
}