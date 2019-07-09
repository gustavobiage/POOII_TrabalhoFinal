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

	public void CriarPeca (String tipo, Dimension dimension, int jogador) {
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
	
	//Retorna a posicao de uma coordenada
	public Posicao GetPosicaoPorDimension (Dimension d) {
		return posicoes [d.width] [d.height];
	}
	
	public Jogada GetUltimaJogada () {
		return historico.GetUltimaJogada();
	}
	
	public ArrayList<Posicao> VerificarMovimentos (Dimension posicaoInicial) {
		//Ocorre ao clicar na peca e demonstra os posiveis movimentos com a peca
		Peca pecaAtual = GetPosicaoPorDimension(posicaoInicial).GetPeca();
		ArrayList <Dimension> dimensions = AdaptadorDeMovimento.GetInstance().AdaptarMovimentos(pecaAtual, posicaoInicial);

		ArrayList<Posicao> posssiveisMovimentos = new ArrayList<>();
		for(int i = 0; i < dimensions.size(); i++) {
			posssiveisMovimentos.add(GetPosicaoPorDimension(dimensions.get(i)));
		}

		return posssiveisMovimentos;
	}
	
	public void MoverPeca (Peca peca, Dimension dimension) {
		//TODO Pegar Historico.escreverHistorico(peca.GetDimension(), dimension);
		String tipoDeJogada = "normal";
		Posicao antigaPosicao = peca.GetPosicao();
		Posicao posicao = GetPosicaoPorDimension (dimension);
		Peca pecaDestruida = posicao.GetPeca();
		
		//Movimento de Roque
		if (peca.getClass() == Rei.class && (Math.abs(dimension.width-peca.GetPosicao().GetDimension().width) > 1)) {
			tipoDeJogada = "roque";
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
			torre.AdicionarMovimento();
			((Rei) peca).AdicionarMovimento();
			
		} else if (peca.getClass() == Torre.class) {
			((Torre) peca).AdicionarMovimento();
		} else if (peca.getClass() == Rei.class) {
			((Rei) peca).AdicionarMovimento();
		}
		
		//Movimentos Especiais de Peao
		if (peca.getClass() == Peao.class) {
			if (((Peao)peca).ChecarEvolucao()) {
				tipoDeJogada = "evolucao";
			} else if (((Peao) peca).ChecarEnPassant(posicao)) {
				tipoDeJogada = "en passant";
				Dimension d = (Dimension) dimension.clone();
				if (peca.GetJogador() == 1) {
					d.height -= 1;
				} else {
					d.height += 1;
				}
				pecaDestruida = GetPosicaoPorDimension (d).GetPeca();
			}
		}
		
		if (pecaDestruida != null) {
			Posicao p = pecaDestruida.GetPosicao();
			TabuleiroFrame frame = TabuleiroFrame.GetInstance();
			p.RemoverPeca();
			frame.desgrifarQuadrado(p.GetDimension().width, p.GetDimension().height, transposedMatrix(this.posicoes));
			frame.desgrifarQuadrado(p.GetDimension().height, p.GetDimension().width, transposedMatrix(this.posicoes));
		}
		peca.Mover(posicao);
		pecas.remove(pecaDestruida);
		historico.escreverHistorico(antigaPosicao, peca.GetPosicao(), tipoDeJogada, pecaDestruida);
		ProximoTurno ();
	}
	
	public void RemoverPecaDaLista (Peca peca) {
		pecas.remove(peca);
	}

	public void voltarJogada(Jogada jogada) {
		
		System.out.println(GetPosicaoPorDimension(new Dimension (5,7)).GetPeca());
		
		TabuleiroFrame.GetInstance().DesgrifarQuadrados ();

		Posicao posicaoAtual = jogada.pegarPosicaoNova();
		Posicao posicaoParaRetorno = jogada.pegarPosicaoAtual();

		Peca peca = posicaoAtual.GetPeca();
		
		System.out.println(peca);
		
		if (jogada.GetTipoDeJogada() == "evolucao") {
			Dimension d = posicaoAtual.GetDimension();
			GetPosicaoPorDimension(d).RemoverPeca();
			RemoverPecaDaLista(peca);
			CriarPeca("Peao", d, peca.GetJogador());
			peca = GetPosicaoPorDimension(d).GetPeca();
			
		} else if (jogada.GetTipoDeJogada() == "roque") {
			Dimension dimensionAtualDaTorre = (Dimension) posicaoParaRetorno.GetDimension().clone();
			Dimension dimensionRetornoDaTorre = (Dimension) posicaoAtual.GetDimension().clone();
			if (posicaoAtual.GetDimension().width > 5) {
				dimensionAtualDaTorre.width++;
				dimensionRetornoDaTorre.width = 7;
			} else {
				dimensionAtualDaTorre.width--;
				dimensionRetornoDaTorre.width = 0;
			}
			Peca torre = GetPosicaoPorDimension (dimensionAtualDaTorre).GetPeca();
			((DetectorDeMovimento) peca).RemoverMovimento();
			((DetectorDeMovimento) torre).RemoverMovimento();
			MoverPecaSemRestricoes (torre, dimensionRetornoDaTorre);
			AtualizarPosicao (dimensionAtualDaTorre);
			AtualizarPosicao (dimensionRetornoDaTorre);
			
		} else if (peca.getClass() == Rei.class || peca.getClass() == Torre.class) {
			((DetectorDeMovimento) peca).RemoverMovimento();;
		};
		
		System.out.println(peca.GetPosicao().GetDimension() +  " " +  peca +" " + posicaoParaRetorno.GetDimension());

		MoverPecaSemRestricoes(peca, posicaoParaRetorno.GetDimension());
		
		System.out.println(peca.GetPosicao().GetDimension());

		
		Peca pecaRenascida = jogada.GetPecaDestruida();
		System.out.println(pecaRenascida);
		if (pecaRenascida != null) {
			Dimension destino = (Dimension) posicaoAtual.GetDimension().clone();
			if (jogada.GetTipoDeJogada() == "en passant") {
				if (peca.GetJogador() == 1) {
					destino.height -= 1;
				} else {
					destino.height += 1;
				}
			}
			pecas.add(pecaRenascida);
			MoverPecaSemRestricoes (pecaRenascida, destino);
			pecaRenascida.SetPosicao(GetPosicaoPorDimension(destino));
			AtualizarPosicao (destino);
		}

		System.out.println("FInal: " + GetPosicaoPorDimension(new Dimension (5,7)).GetPeca());
		
		AtualizarPosicao (posicaoParaRetorno.GetDimension());
		AtualizarPosicao (posicaoAtual.GetDimension());
		System.out.println(GetPosicaoPorDimension(new Dimension( 6,6)).GetPeca());

		System.out.println();		
		this.turnoAnterior();
	}
	
	private void AtualizarPosicao (Dimension dimension) {
		TabuleiroFrame.GetInstance().desgrifarQuadrado(dimension.height, dimension.width, transposedMatrix(this.posicoes));
	}

	public void MoverPecaSemRestricoes (Peca peca, Dimension dimension) {
		Posicao posicao = GetPosicaoPorDimension (dimension);
		peca.Mover(posicao);
	}
	
	private void AlternarJogador () {
		jogadorAtual ++;
		if (jogadorAtual > 2) {
			jogadorAtual = 1;
		}
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
	
	//Verifica se o jogador nao tera movimentos disponiveis resultando em um empate:
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
		int jogadorVencedor = jogadorPerdedor -1;
		if (jogadorVencedor == 0) {
			jogadorVencedor = 2;
		}
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
		return transposedMatrix(posicoes);
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
}