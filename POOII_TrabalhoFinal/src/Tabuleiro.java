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
	private Posicao [][] posicoes = new Posicao [8] [8];
	private ArrayList <Peca> pecas = new ArrayList <Peca> ();
	private ArrayList <Rei> reis = new ArrayList <Rei> ();
	private int turno = 1;
	private int jogadorAtual = 1;
	private Peca pecaCheque = null;
	
	private Tabuleiro () {
		
		//Cria as posi��es
		CriarTabuleiro ();
		
		//Cria as pe�as as quais ir�o ser linkadas a uma posi��o ao serem criadas
		CriarPecas ();
		
		/*Remover linhas a seguir:*/
		//pecas.add(new Cavalo (posicoes [3] [3], 1));
		/*pecas.add(new Rei (posicoes [5] [5], 2));
		pecas.add(new Dama (posicoes [3] [3], 1));*/
		//posi��es[4][4].GetPe�a().SetPino(new Pino (Dire��o.horizontal));
		//VerificarMovimentos (new Dimension (4,4));
	}
	
	//Percorre a matriz tabuleiro criando 
	private void CriarTabuleiro () {
		
		for (int i = 0; i < posicoes[0].length; i++) {
			for (int j = 0; j < posicoes[1].length; j ++) {
				posicoes[i][j] = new Posicao (i, j);
				System.out.println(posicoes[i][j].getX() + ", " + posicoes[i][j].getY());
			}
		}
	}

	private void CriarPecas () {
		
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
		reis.add(new Rei (posicoes [4] [7], 2));
		pecas.add(reis.get(0));
		pecas.add(reis.get(1));
	}

	//TODO
//	CriarPeca (Tipo tipo, Posicao posicao) {
//
//	}
	
	//Retorna a posi��o de uma coordenada
	public Posicao GetPosicaoPorDimension (Dimension d) {
		return posicoes [d.width] [d.height];
	}
	
	public ArrayList<Posicao> VerificarMovimentos (Dimension posicaoInicial) {
		//Ocorre ao clicar na pe�a e demonstra os poss�veis movimentos com a pe�a
		Peca pecaAtual = GetPosicaoPorDimension(posicaoInicial).GetPeca();
		ArrayList <Dimension> dimensions = AdaptadorDeMovimento.GetInstance().AdaptarMovimentos(pecaAtual, posicaoInicial);
//		for (Dimension d: dimensions) {
//			Hightlight (d);
//		}

		ArrayList<Posicao> posssiveisMovimentos = new ArrayList<>();
		for(int i = 0; i < dimensions.size(); i++) {
			posssiveisMovimentos.add(GetPosicaoPorDimension(dimensions.get(i)));
		}

		return posssiveisMovimentos;
	}

	public void MoverPeca (Peca peca, Dimension dimension) {
		//TODO Pegar Historico.escreverHistorico(peca.GetDimension(), dimension);
		Posicao posicao = GetPosicaoPorDimension (dimension);
		pecas.remove(posicao.GetPeca());
		peca.Mover(posicao);
		ProximoTurno ();
		if(peca instanceof Peao) {
			((Peao)peca).TornarAtiva();
		} else if(peca instanceof Torre) {
			((Torre)peca).TornarAtiva();
		} else if(peca instanceof Rei) {
			((Rei)peca).TornarAtiva();
		}
	}
	
	public void MoverPecaSemRestricoes (Peca peca, Dimension dimension) {
		Posicao posicao = GetPosicaoPorDimension (dimension);
		peca.Mover(posicao);
	}
	
	public void RetornarJogada (Posicao posicaoPresente, Posicao posicaoDestino, Peca pecaRemovida) {
		turno --;
		AlternarJogador ();
		MoverPecaSemRestricoes(posicaoPresente.GetPeca(), posicaoDestino.GetDimension());
		if (pecaRemovida != null) {
			RecriarPeca (pecaRemovida);
		}
	}
	
	private void AlternarJogador () {
		jogadorAtual ++;
		if (jogadorAtual > 2) {
			jogadorAtual = 1;
		}
	}
	
	private void RecriarPeca (Peca pecaRemovida) {
		pecas.add(pecaRemovida);
		pecaRemovida.Mover(pecaRemovida.GetPosicao());
	}
	public int getTurno() {
		return turno;
	}
	private void ProximoTurno () {
		turno ++;
		AlternarJogador ();
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
	
	/*Remover os m�todos a seguir:*/
	public Posicao[][] getPosicoes() {
		return Tabuleiro.transposedMatrix(posicoes);
	}

	public static Posicao[][] transposedMatrix(Posicao[][] posicoes) {
		Posicao[][] posicoesT = new Posicao[8][8];

		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				posicoesT[i][j] = posicoes[j][i];
			}
		}

		return posicoesT;
	}

	public void MostrarTabuleiro () {
		for (int i = 0; i < posicoes.length; i++) {
			for (int j = 0; j < posicoes[1].length; j++) {
				System.out.print(posicoes[j][i].GetPeca() + " ");
			}
			System.out.println();
		}
		
	}
}