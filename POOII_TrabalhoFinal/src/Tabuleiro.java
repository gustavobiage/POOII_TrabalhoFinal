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
	private Historico historico;

	private Tabuleiro () {
		
		//Cria as posicoes
		CriarTabuleiro ();
		
		//Cria as pecas as quais irao ser linkadas a uma posicao ao serem criadas
		CriarPecas ();
	}

	public void informarHistorico(Historico historico) {
		this.historico = historico;
	}

	//Percorre a matriz tabuleiro criando 
	private void CriarTabuleiro () {
		posicoes = new Posicao[8][8];

		for (int i = 0; i < posicoes[0].length; i++) {
			for (int j = 0; j < posicoes[1].length; j ++) {
				posicoes[i][j] = new Posicao (i, j);
			}
		}
	}

	private void CriarPecas () {
		
		for (int i = 0; i < 2; i ++) {
			for (int j = 0; j < 8; j ++) {
				CriarPeca ("Peao", new Dimension (j, 1 + 5 * i), 1 + i);
			}
			
			CriarPeca ("Torre", new Dimension (0, 7 * i), 1 + i);
			CriarPeca ("Torre", new Dimension (7, 7 * i), 1 + i);
			
			CriarPeca ("Cavalo", new Dimension (1, 7 * i), 1 + i);
			CriarPeca ("Cavalo", new Dimension (6, 7 * i), 1 + i);
			
			CriarPeca ("Bispo", new Dimension (2, 7 * i), 1 + i);
			CriarPeca ("Bispo", new Dimension (5, 7 * i), 1 + i);
			
			CriarPeca ("Dama", new Dimension (3, 7 * i), i + 1);
			
			CriarPeca ("Rei", new Dimension (4, 7 * i), i + 1);
		}
	}

	private void CriarPeca (String tipo, Dimension dimension, int jogador) {
		if (GetPosicaoPorDimension (dimension).GetPeca() != null) {
			System.err.println("Tentando criar peca em um local ja ocupado.");
			return;
		}
		Peca peca;
		switch (tipo) {
		case "Rei":
			peca = new Rei (GetPosicaoPorDimension (dimension), jogador);
			reis.add((Rei) peca);
			break;
		case "Dama":
			peca = new Dama (GetPosicaoPorDimension (dimension), jogador);
			break;
		case "Bispo":
			peca = new Bispo (GetPosicaoPorDimension (dimension), jogador);
			break;
		case "Cavalo":
			peca = new Cavalo (GetPosicaoPorDimension (dimension), jogador);
			break;
		case "Torre":
			peca = new Torre (GetPosicaoPorDimension (dimension), jogador);
			break;
		default:
			peca = new Peao (GetPosicaoPorDimension (dimension), jogador);
			break;
		}
		pecas.add(peca);
	}
	
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
		Posicao nova = this.GetPosicaoPorDimension(dimension);
		historico.escreverHistorico(peca.GetPosicao(), nova);

		//Movimento de Rock
		if (peca.getClass() == Rei.class && (Math.abs(dimension.width-peca.GetPosicao().GetDimension().width) > 1)) {
			Torre torre;
			Dimension destinoDaTorre = (Dimension) dimension.clone();
			if (dimension.width < peca.GetPosicao().GetDimension().width) {
				torre = (Torre) GetPosicaoPorDimension (new Dimension (0, 7 * (jogadorAtual - 1))).GetPeca();
				destinoDaTorre.width++;
			} else {
				torre = (Torre) GetPosicaoPorDimension (new Dimension (7, 7 * (jogadorAtual - 1))).GetPeca();
				destinoDaTorre.width--;
			}
			MoverPecaSemRestricoes(torre, destinoDaTorre);
			torre.TornarAtiva();
		}
		
		Posicao posicao = GetPosicaoPorDimension (dimension);
		pecas.remove(posicao.GetPeca());
		peca.Mover(posicao);
		ProximoTurno ();
		try {
			((DetectorDeMovimento) peca).TornarAtiva();
		}
		catch (Exception e) {
		}
	}

	public void voltarJogada(Jogada jogada, TabuleiroFrame frame) {
//		Posicao posicao1 = this.GetPosicaoPorDimension(Posicao.posicaoTransposta(jogada.pegarPosicaoAtual()));
		Posicao posicao1 = jogada.pegarPosicaoAtual();
		Posicao posicao2 = this.GetPosicaoPorDimension(Posicao.posicaoTransposta(jogada.pegarPosicaoNova()));

//		Peca peca_pos1 = this.GetPosicaoPorDimension(posicao1.GetDimension()).GetPeca();

		Peca peca = this.GetPosicaoPorDimension(Posicao.posicaoTransposta(posicao2)).GetPeca();
		this.MoverPecaSemRestricoes(peca, new Dimension(posicao1.GetDimension().width, posicao1.GetDimension().height));
		peca = this.GetPosicaoPorDimension(posicao1.GetDimension()).GetPeca();

		if(peca instanceof Peao && ((posicao1.GetDimension().height == 1 && peca.pegarLado()== Peca.Lado.BRANCAS) || (posicao1.GetDimension().height == 6 && peca.pegarLado()==Peca.Lado.PRETAS))) {
			((Peao) peca).Desativar();
		}
		try {

			Peca reviver = jogada.pegarPosicaoNova().GetPeca();
			String object = "";
			if(reviver instanceof Rei) {
				object = "Rei";
			} else if(reviver instanceof Dama) {
				object = "Dama";
			} else if(reviver instanceof Peao) {
				object = "Peao";

			} else if(reviver instanceof Torre) {
				object = "Torre";

			} else if(reviver instanceof Bispo) {
				object = "Bispo";

			} else if(reviver instanceof Cavalo) {
				object = "Cavalo";
			}

			this.CriarPeca(object, Posicao.posicaoTransposta(posicao2), reviver.GetJogador());

		} catch (NullPointerException e) {
			System.out.println("Nenhuma peca foi revivida");
		}

		//TODO checar se devemos trocar width com height
//		frame.desgrifarQuadrado(posicao1.GetDimension().width, posicao1.GetDimension().height, this.transposedMatrix(this.posicoes));
		frame.desgrifarQuadrado(posicao1.GetDimension().height, posicao1.GetDimension().width, this.transposedMatrix(this.posicoes));
		frame.desgrifarQuadrado(posicao2.GetDimension().width, posicao2.GetDimension().height, this.transposedMatrix(this.posicoes));
		frame.desgrifarQuadrado(posicao2.GetDimension().height, posicao2.GetDimension().width, this.transposedMatrix(this.posicoes));

		this.turnoAnterior();
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

	private void turnoAnterior() {
		turno --;
		AlternarJogador ();
		VerificarCheque ();
		VerificarFimDeJogo ();
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

	/*Remover os metodos a seguir:*/

	public void MostrarTabuleiro () {
		for (int i = 0; i < posicoes.length; i++) {
			for (int j = 0; j < posicoes[1].length; j++) {
				System.out.print(posicoes[j][i].GetPeca() + " ");
			}
			System.out.println();
		}
		
	}
}