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
	
	//Primeiro número largura do tabuleiro e segundo altura
	private Posição [][] posicoes = new Posição [8] [8];
	private ArrayList <Peça> peças = new ArrayList <Peça> ();
	private int turno = 1;
	private int jogadorAtual = 1;
	
	private Tabuleiro () {
		
		//Cria as posições
		CriarTabuleiro ();
		
		//Cria as peças as quais irão ser linkadas a uma posição ao serem criadas
		CriarPeças ();
		
		/*Remover linhas a seguir:*/
		peças.add(new Torre (posicoes [4] [4], 2));
		peças.add(new Rei (posicoes [5] [5], 2));
		peças.add(new Dama (posicoes [3] [3], 1));
		//posições[4][4].GetPeça().SetPino(new Pino (Direção.horizontal));
		//VerificarMovimentos (new Dimension (4,4));
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
			peças.add(new Peao (posicoes [i] [1], 1));
			peças.add(new Peao (posicoes [i] [6], 2));
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
		//peças.add(new Rei (posicoes [4] [7], 2));
		
	}
	
	//Retorna a posição de uma coordenada
	public Posição GetPosiçãoPorDimensão (Dimension d) {
		return posicoes [d.width] [d.height];
	}
	
	public void VerificarMovimentos (Dimension posicaoInicial) {
		//Ocorre ao clicar na peça e demonstra os possíveis movimentos com a peça
		Peça peça = GetPosiçãoPorDimensão(posicaoInicial).GetPeça();
		ArrayList <Dimension> dimensions = AdaptadorDeMovimento.GetInstance().AdaptarMovimentos(peça, posicaoInicial);
		for (Dimension d: dimensions) {
			Hightlight (d);
		}
	}
	
	//Verifica se o jogador nao tera movimentos disponiveis resultando em um emapte:
	public void VerificarEmpate () {
		boolean empateNegado = false;
		for (Peça peça : peças) {
			if (peça.GetJogador() == jogadorAtual) {
				if (AdaptadorDeMovimento.GetInstance().AdaptarMovimentos(peça, peça.GetPosição().GetDimension()).size() > 0) {
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
		for (Peça peça: peças) {
			if (peça.getClass() == Torre.class && peça.GetJogador() == jogador) {
				lista.add((Torre)peça);
			}
		}
		return lista;
	}
	
	public Rei GetRei (int jogador) {
		for (Peça peça: peças) {
			if (peça.getClass() == Rei.class && peça.GetJogador() == jogador) {
				return (Rei) peça;
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
		tabuleiro.GetInstance().VerificarMovimentos (new Dimension (4,4));
	}
}