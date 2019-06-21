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
	private Posicao [][] posicoes = new Posicao [8] [8];
	private ArrayList <Peca> pecas = new ArrayList <Peca> ();
	private ArrayList <Rei> reis = new ArrayList <Rei> ();
	private int turno = 1;
	private int jogadorAtual = 1;
	private Peca pecaAtual;
	private Peca pecaCheque = null;
	
	private Tabuleiro () {
		
		//Cria as posições
		CriarTabuleiro ();
		
		//Cria as peças as quais irão ser linkadas a uma posição ao serem criadas
		CriarPeças ();
		
		/*Remover linhas a seguir:*/
		pecas.add(new Cavalo (posicoes [3] [3], 1));
		/*pecas.add(new Rei (posicoes [5] [5], 2));
		pecas.add(new Dama (posicoes [3] [3], 1));*/
		//posições[4][4].GetPeça().SetPino(new Pino (Direção.horizontal));
		//VerificarMovimentos (new Dimension (4,4));
	}
	
	//Percorre a matriz tabuleiro criando 
	private void CriarTabuleiro () {
		
		for (int i = 0; i < posicoes[0].length; i++) {
			for (int j = 0; j < posicoes[1].length; j ++) {
				posicoes[i][j] = new Posicao (i, j);
			}
		}
	}
	
	private void CriarPeças () {
		
		for (int i = 0; i < 8; i ++) {
			pecas.add(new Peao (posicoes [i] [1], 1));
			pecas.add(new Peao (posicoes [i] [6], 2));
		}
		
		pecas.add(new Torre (posicoes [0] [0], 1));
		pecas.add(new Torre (posicoes [7] [0], 1));
		pecas.add(new Torre (posicoes [0] [7], 2));
		pecas.add(new Torre (posicoes [7] [7], 2));
		

		pecas.add(new Cavalo (posicoes [1] [0], 1));
		pecas.add(new Cavalo (posicoes [6] [0], 1));
		pecas.add(new Cavalo (posicoes [1] [7], 2));
		pecas.add(new Cavalo (posicoes [6] [7], 2));
		

		pecas.add(new Bispo (posicoes [2] [0], 1));
		pecas.add(new Bispo (posicoes [5] [0], 1));
		pecas.add(new Bispo (posicoes [2] [7], 2));
		pecas.add(new Bispo (posicoes [5] [7], 2));
		

		pecas.add(new Dama (posicoes [3] [0], 1));
		pecas.add(new Dama (posicoes [3] [7], 2));	
		
		
		reis.add(new Rei (posicoes [4] [0], 1));
		reis.add(new Rei (posicoes [4] [7], 1));
		pecas.add(reis.get(0));
		pecas.add(reis.get(1));
	}
	
	//Retorna a posição de uma coordenada
	public Posicao GetPosicaoPorDimension (Dimension d) {
		return posicoes [d.width] [d.height];
	}
	
	public void VerificarMovimentos (Dimension posicaoInicial) {
		//Ocorre ao clicar na peça e demonstra os possíveis movimentos com a peça
		pecaAtual = GetPosicaoPorDimension(posicaoInicial).GetPeca();
		ArrayList <Dimension> dimensions = AdaptadorDeMovimento.GetInstance().AdaptarMovimentos(pecaAtual, posicaoInicial);
		for (Dimension d: dimensions) {
			Hightlight (d);
		}
	}
	
	public void MoverPeça (Dimension dimension) {
		Posicao posicao = GetPosicaoPorDimension (dimension);
		pecas.remove(posicao.GetPeca());
		pecaAtual.Mover(posicao);
		ProximoTurno ();	
	}
	
	private void ProximoTurno () {
		turno ++;
		jogadorAtual ++;
		if (jogadorAtual > 2) {
			jogadorAtual = 1;
		}
		VerificarCheque ();
		VerificarFimDeJogo ();
	}
	
	private void VerificarCheque () {
		Rei rei = GetRei (jogadorAtual);
		pecaCheque = AdaptadorDeMovimento.GetInstance().VerificarAmeaca(rei.GetPosicao().GetDimension(), jogadorAtual);
	}
	
	//Verifica se o jogador nao tera movimentos disponiveis resultando em um emapte:
	private void VerificarFimDeJogo () {
		boolean empateNegado = false;
		for (Peca peca : pecas) {
			if (peca.GetJogador() == jogadorAtual) {
				if (AdaptadorDeMovimento.GetInstance().AdaptarMovimentos(peca, peca.GetPosicao().GetDimension()).size() > 0) {
					empateNegado = true;
					break;
				}
			}
		}
		if (!empateNegado) {
			if (pecaCheque != null) {
				TerminarJogo (jogadorAtual);
			} else {
				Empatar ();
			}
		}
	}
	
	private void TerminarJogo (int jogadorPerdedor) {
		//Termina o jogo com o numero do jogador como perdedor.
	}

	private void Empatar () {
		//Termina o jogo com um emapte
	}
	
	public ArrayList <Torre> GetTorres (int jogador) {
		ArrayList <Torre> lista = new ArrayList <Torre> ();
		for (Peca peca: pecas) {
			if (peca.getClass() == Torre.class && peca.GetJogador() == jogador) {
				lista.add((Torre)peca);
			}
		}
		return lista;
	}
	
	public Rei GetRei (int jogador) {
		for (Rei rei: reis) {
			if (rei.GetJogador() == jogador) {
				return rei;
			}
		}
		System.err.println("Erro, rei nao encontrado.");
		return null;
	}
	
	public Peca GetPecaCheque () {
		return pecaCheque;
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
				System.out.print(posicoes[j][i].GetPeca() + " ");
			}
			System.out.println();
		}
		
	}
	
	public static void main (String args []) {
		//Tabuleiro.GetInstance().VerificarMovimentos (new Dimension (4,4));
		if (AdaptadorDeMovimento.GetInstance().VerificarAmeaca(new Dimension (4,5), 2)!=null) {
			System.out.println("a");
		}
	}
}