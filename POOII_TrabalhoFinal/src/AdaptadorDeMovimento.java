import java.util.*;
import java.awt.*;

public class AdaptadorDeMovimento {
	
	private static final AdaptadorDeMovimento adaptadorDeMovimento = new AdaptadorDeMovimento ();
	
	private Dimension [] movimentosCavalo = {new Dimension (1,2), new Dimension (2,1), new Dimension (2,-1), new Dimension (1,-2), 
			new Dimension (-1,-2), new Dimension (-2,-1), new Dimension (-2,1), new Dimension (-1,2)};
	private Dimension [] movimentosRei = {new Dimension (0,1), new Dimension (1,1), new Dimension (1,0), new Dimension (1,-1), 
			new Dimension (0,-1), new Dimension (-1,-1), new Dimension (-1,0), new Dimension (-1,1)};
	
	private AdaptadorDeMovimento () {
		if (AdaptadorDeMovimento.GetInstance () != null) {
			return;
		}
	}
	
	public static AdaptadorDeMovimento GetInstance () {
		return adaptadorDeMovimento;
	}
	
	public ArrayList <Dimension> AdaptarMovimentos (Peca peca, Dimension dimension) {
		
		ArrayList <Dimension> dimensions = new ArrayList <Dimension> ();
		Class classe;
		try {
			classe = peca.getClass();
		} catch (Exception e) {
			System.err.println("Nenhuma peca no quadrado selecionado");
			return dimensions;
		}
		
		peca.RemoverPino ();
		VerificarPino (peca, dimension);
		
		if (classe == Peao.class) {
			dimensions = VerificarMovimentosDePeao (peca, dimension, dimensions);
		} else if (classe == Cavalo.class) {
			dimensions = VerificarMovimentosDeCavalo (peca, dimension, dimensions);
			
		} else if (classe == Rei.class) {
			dimensions = VerificarMovimentosDeRei (peca, dimension, dimensions);
		} else {
			ArrayList <Direcao> direcoes = new ArrayList <Direcao> ();
			if (classe == Dama.class) {
				direcoes.add(Direcao.horizontal);
				direcoes.add(Direcao.vertical);
				direcoes.add(Direcao.diagonalDireito);
				direcoes.add(Direcao.diagonalEsquerdo);
			} else if (classe == Torre.class) {
				 direcoes.add(Direcao.horizontal);
				direcoes.add(Direcao.vertical);
			} else if (classe == Bispo.class) {
				direcoes.add(Direcao.diagonalDireito);
				direcoes.add(Direcao.diagonalEsquerdo);
			}
			dimensions = VerificarMovimentosLineares (peca, dimension, dimensions, direcoes);
		}

		dimensions = VerificarMovimentosInvalidos (peca, dimension, dimensions);
		
		return dimensions;
	}
	
	private ArrayList <Dimension> VerificarMovimentosLineares (Peca peca, Dimension dimension, ArrayList <Dimension> dimensions, 
			ArrayList <Direcao> direcoes) {
		
		Pino pino = peca.GetPino();
		if (pino != null) {
			Direcao direcaoPino = pino.GetDirecao();
			if (!direcoes.contains(direcaoPino)) {
//				return dimensions;
			} else {
				direcoes.clear();
				direcoes.add(direcaoPino);
			}
		}

		int jogador = peca.GetJogador();
		int x = 0, y = 0;
		for (int n = 0; n < direcoes.size(); n ++) {
			
			switch (direcoes.get(n)) {
			case diagonalDireito:
				x = 1;
				y = 1;
				break;
			case diagonalEsquerdo:
				x = 1;
				y = -1;
				break;
			case horizontal:
				x = 1;
				y = 0;
				break;
			case vertical:
				x = 0;
				y = 1;
				break;
			}

			for (int i = 0; i < 2; i ++) {
				ArrayList <Dimension> initialDimensions = new ArrayList <Dimension> ();
				Dimension d = (Dimension) dimension.clone();
				
				d.width += x;
				d.height += y;
				
				while (d.width >= 0 && d.width <= 7 && d.height >= 0 && d.height <= 7) {
					initialDimensions.add((Dimension)d.clone());
					d.width += x;
					d.height += y;
				}

				boolean caminhoObstruido = false;
				for (int j = 0; j < initialDimensions.size() && !caminhoObstruido; j ++) {
					d = initialDimensions.get(j);
					try {
						if (Tabuleiro.GetInstance().GetPosicaoPorDimension(d).GetPeca().GetJogador() != jogador) {
							dimensions.add (d);
						}

						caminhoObstruido = true;

					}
					catch (Exception e) {
						dimensions.add(d);
					}
				}
				x = -x;
				y = -y;
			}
		}
		return dimensions;
	}

	private ArrayList <Dimension> VerificarMovimentosDeRei (Peca peca, Dimension dimension, ArrayList <Dimension> dimensions) {
				
		ArrayList <Dimension> initialDimensions = new ArrayList <Dimension> ();
		Dimension d = new Dimension ();
		for (int i = 0; i < 8; i ++) {
			d = new Dimension (dimension.width + movimentosRei[i].width, dimension.height + movimentosRei[i].height);
			if (d.height >= 0 && d.width >= 0 && d.height <= 7 && d.width <= 7) {
				initialDimensions.add(d);
			}
		}
		
		int jogador = peca.GetJogador ();
		
		for (int i = 0; i < initialDimensions.size(); i ++) {
			d = initialDimensions.get(i);
			try {
				if (Tabuleiro.GetInstance().GetPosicaoPorDimension(d).GetPeca().GetJogador() != jogador) {
					dimensions.add(d);
				}
			}
			catch (Exception e) {
				dimensions.add(d);
			}
		}
		
		dimensions = VerificarMovimentosDeRoque (peca, dimension, dimensions, jogador);
		
		return dimensions;
	}
	
	private ArrayList <Dimension> VerificarMovimentosDeCavalo (Peca peca, Dimension dimension, ArrayList <Dimension> dimensions) {
		if (peca.GetPino () != null) {
			return dimensions;
		}
		
		ArrayList <Dimension> initialDimensions = GetMovimentosDeCavalo (dimension, dimensions);
		Dimension d;		
		int jogador = peca.GetJogador ();
		
		for (int i = 0; i < initialDimensions.size(); i ++) {
			d = initialDimensions.get(i);
			try {
				if (Tabuleiro.GetInstance().GetPosicaoPorDimension(d).GetPeca().GetJogador() != jogador) {
					dimensions.add(d);
				}
			}
			catch (Exception e) {
				dimensions.add(d);
			}
		}
		return dimensions;
	}
	
	private ArrayList <Dimension> GetMovimentosDeCavalo (Dimension dimension, ArrayList <Dimension> dimensions) {
		ArrayList <Dimension> initialDimensions = new ArrayList <Dimension> ();
		Dimension d;
		for (int i = 0; i < 8; i ++) {
			d = new Dimension (dimension.width + movimentosCavalo[i].width, dimension.height + movimentosCavalo[i].height);
			if (d.height >= 0 && d.width >= 0 && d.height <= 7 && d.width <= 7) {
				initialDimensions.add(d);
			}
		}
		return initialDimensions;
	}
	
	private ArrayList <Dimension> VerificarMovimentosDePeao (Peca peca, Dimension dimension, ArrayList <Dimension> dimensions) {

		int jogador = peca.GetJogador ();
		
		ArrayList <Direcao> direcoes = new ArrayList <Direcao> ();
		direcoes.add(Direcao.diagonalDireito);
		direcoes.add(Direcao.diagonalEsquerdo);
		direcoes.add(Direcao.vertical);
		Pino pino = peca.GetPino();
		if (pino != null) {
			direcoes.clear();
			direcoes.add(pino.GetDirecao());
		}
		
		Dimension d;
		int x = 1;
		int y = 1;
		if (jogador == 2) {
			x = -1;
			y = -1;
		}		
		if (direcoes.contains(Direcao.vertical)) {
			d = (Dimension) dimension.clone();
			d.height += y;
			
			if (Tabuleiro.GetInstance().GetPosicaoPorDimension(d).GetPeca() == null) {
				dimensions.add((Dimension) d.clone());
				if (dimension.height == 1 + 5 * (jogador - 1)) {
					d.height += y;
					if (Tabuleiro.GetInstance().GetPosicaoPorDimension(d).GetPeca() == null) {
						dimensions.add((Dimension) d.clone());
					}
				}
			}			
		}
		
		if (direcoes.contains(Direcao.diagonalDireito)) {
			d = GetMovimentacaoDiagonalPeao (dimension,Direcao.diagonalDireito, jogador, x, y);
			if (d != null) {
				dimensions.add((Dimension)d.clone());
			}
		}
		
		if (direcoes.contains(Direcao.diagonalEsquerdo)) {
			d = GetMovimentacaoDiagonalPeao (dimension,Direcao.diagonalEsquerdo, jogador, x, y);
			if (d != null) {
				dimensions.add((Dimension)d.clone());
			}
		}
		
		return dimensions;
	}
	
	private Dimension GetMovimentacaoDiagonalPeao (Dimension dimension, Direcao direcao, int jogador, int x, int y) {
		Dimension d = (Dimension) dimension.clone();
		d.height += y;
		if (direcao == Direcao.diagonalDireito) {
			d.width += x;
		} else {
			d.width -= x;
		}
		if (d.width >= 0 && d.width <= 7) {
			try {
				if (Tabuleiro.GetInstance().GetPosicaoPorDimension(d).GetPeca().GetJogador() != jogador) {
					return d;
				}
			} catch (Exception e) {
			}
			//Movimento en passant:
			if (dimension.height == 4 - (jogador - 1)) {
				Jogada ultimaJogada = Tabuleiro.GetInstance().GetUltimaJogada();
				try {
					if (ultimaJogada.pegarPosicaoNova().GetPeca().getClass() == Peao.class &&
							Math.abs(ultimaJogada.pegarPosicaoNova().GetDimension().height - ultimaJogada.pegarPosicaoAtual().GetDimension().height) == 2 &&
							ultimaJogada.pegarPosicaoNova().GetDimension().width == d.width) {
						return d;
					}
				} catch (Exception e) {
					System.out.println("err");
				}
			}
		}
		
		return null;
	}

	private void VerificarPino (Peca peca, Dimension dimension) {
		int jogador = peca.GetJogador();
		Rei rei = Tabuleiro.GetInstance().GetRei(jogador);
		Dimension reiDimension = rei.GetPosicao().GetDimension();
		int x = 0, y = 0;
		Direcao direcao;
		
		if (reiDimension.height == dimension.height) {
			direcao = Direcao.horizontal;
			if (reiDimension.width > dimension.width) {
				x = -1;
			} else {
				x = 1;
			}
		} else if (reiDimension.width == dimension.width){
			direcao = Direcao.vertical;
			if (reiDimension.height > dimension.height) {
				y = -1;
			} else {
				y = 1;				
			}
		} else if (Math.abs(reiDimension.width - dimension.width) == Math.abs(reiDimension.height - dimension.height)) {
			if (reiDimension.width > dimension.width) {
				x = -1;
			} else { 
				x = 1;
			} if (reiDimension.height > dimension.height) {
				y = -1;
			} else { 
				y = 1;
			}
			if (x == 1 && y == 1 || x == -1 && y == -1) {
				direcao = Direcao.diagonalDireito;
			} else {
				direcao = Direcao.diagonalEsquerdo;
			}
		} else {
			return;
		}
		
		Dimension d = (Dimension) dimension.clone();
		d.width += x;
		d.height += y;
		Peca p;
		
		while (d.width >= 0 && d.width <= 7 && d.height >= 0 && d.height <= 7) {
			p = Tabuleiro.GetInstance().GetPosicaoPorDimension(d).GetPeca();
			
			if (p != null) {

				if (p.GetJogador() == jogador) {
					return;
				} else {
					if (direcao == Direcao.vertical || direcao == Direcao.horizontal) {
						if (p.getClass() == Torre.class || p.getClass() == Dama.class) {
							peca.AddPino(new Pino (direcao));
						}
					} else if (direcao == Direcao.diagonalDireito || direcao == Direcao.diagonalEsquerdo) {
						if (p.getClass() == Bispo.class || p.getClass() == Dama.class) {
							peca.AddPino(new Pino (direcao));
						}
					}
					return;
				}
			}
			
			d.width += x;
			d.height += y;
		}
	}
	
	public ArrayList <Dimension> VerificarMovimentosInvalidos (Peca peca, Dimension dimension, ArrayList <Dimension> dimensions) {
		
		if (dimensions.size() == 0) {
			return dimensions;
		}
		
		Posicao posicao = Tabuleiro.GetInstance().GetPosicaoPorDimension(dimension);
		Posicao newPosicao;
		Peca pecaAntiga;
		Dimension newDimension;
		int jogador = peca.GetJogador();
		
		for (int i = 0; i < dimensions.size(); i++) {
			if (!(peca.getClass() == Rei.class && peca.GetPosicao().GetDimension().width - dimension.width > 1)) {
				newDimension = dimensions.get(i);
				newPosicao = Tabuleiro.GetInstance().GetPosicaoPorDimension(newDimension);
				pecaAntiga = newPosicao.GetPeca();
				peca.Mover(newPosicao);
				posicao.RemoverPeca();
				if (VerificarAmeaca (Tabuleiro.GetInstance().GetRei(jogador).GetPosicao().GetDimension(), jogador) != null) {
					dimensions.remove(newDimension);
					i--;
				}
				peca.Mover(posicao);
				newPosicao.AdicionarPeca(pecaAntiga);
			}
		}
		
		return dimensions;
	}	
	
	public Peca VerificarAmeaca (Dimension dimension, int jogador) {
		
		Peca p;
		Dimension d;		

		//Checando por peoes:
		int y = 1;
		if (jogador == 2) {
			y = -1;
		}
		d = (Dimension) dimension.clone();
		d.height += y;
		if (d.getHeight() >= 0 && d.getHeight() <= 7) {
			d.width += 1;
			if (d.width <= 7) {
				p  = Tabuleiro.GetInstance().GetPosicaoPorDimension(d).GetPeca();
				if (p != null) {
					if (p.getClass() == Peao.class && p.GetJogador() != jogador) {
						return p;
					}
				}
			}
			d.width -= 2;
			if (d.width >= 0) {
				p  = Tabuleiro.GetInstance().GetPosicaoPorDimension(d).GetPeca();
				if (p != null) {
					if (p.getClass() == Peao.class && p.GetJogador() != jogador) {
						return p;
					}
				}
			}
		}
		
		//Checando por cavalos:
		ArrayList <Dimension> dimensions = new ArrayList <Dimension> ();
		dimensions = GetMovimentosDeCavalo (dimension, dimensions);
		for (Dimension d1: dimensions) {
			p  = Tabuleiro.GetInstance().GetPosicaoPorDimension(d1).GetPeca();
			if (p != null) {
				if (p.getClass() == Cavalo.class && p.GetJogador() != jogador) {
					return p;
				}
			}
		}
		
		//Checando para movimentos lineares:
		int x = 0;
		for (int i = 0; i < 4; i ++) {
			switch (i) {
			case 0:
				x = 1;
				y = 1;
				break;
			case 1:
				x = 1;
				y = -1;
				break;
			case 2:
				x = 1;
				y = 0;
				break;
			case 3:
				x = 0;
				y = 1;
				break;
			}
			
			for (int j = 0; j < 2; j ++) {
				ArrayList <Dimension> initialDimensions = new ArrayList <Dimension> ();
				d = (Dimension) dimension.clone();
				
				d.width += x;
				d.height += y;
				
				while (d.width >= 0 && d.width <= 7 && d.height >= 0 && d.height <= 7) {	
					initialDimensions.add((Dimension)d.clone());
					d.width += x;
					d.height += y;
				}
				
				boolean caminhoObstruido = false;
				boolean firstMove = true;
				for (int k = 0; k < initialDimensions.size() && !caminhoObstruido; k ++) {
					d = initialDimensions.get(k);					
					p  = Tabuleiro.GetInstance().GetPosicaoPorDimension(d).GetPeca();
					try {
						if (p.GetJogador() != jogador && (p.getClass() == Dama.class || (p.getClass() == Bispo.class && (i == 0 || i == 1))
								|| p.getClass() == Torre.class && (i == 2 || i == 3) || (p.getClass() == Rei.class && firstMove == true))) {
							return p;
						}
						caminhoObstruido = true;
					}
					catch (Exception e) {
					}
					firstMove = false;
				}
				x = -x;
				y = -y;
			}
		}
		return null;
	}
	
	private ArrayList <Dimension> VerificarMovimentosDeRoque (Peca peca, Dimension dimension, ArrayList <Dimension> dimensions, int jogador) {
		
		DetectorDeMovimento dMovimento = (DetectorDeMovimento) peca;
		Dimension d;
		if (!dMovimento.JaMoveu()) {
			ArrayList <Torre> torres;
			torres = Tabuleiro.GetInstance().GetTorres(jogador);
			int x;
			boolean caminhoLivre = true;
			
			for (Torre torre : torres) {
				if (!torre.JaMoveu()) {
					
					Dimension torreDimension = torre.GetPosicao().GetDimension();
					x = 0;
					
					if (torreDimension.height == dimension.height) {
						
						if (torreDimension.width < dimension.width) {
							x = 1;
						} else {
							x = -1;
						}
						d = (Dimension) torreDimension.clone();
						d.width += x;
						caminhoLivre = true;
						while (caminhoLivre && !d.equals(dimension)) {
							if (Tabuleiro.GetInstance().GetPosicaoPorDimension(d).GetPeca() != null) {
								caminhoLivre = false;
							}
							d.width += x;
						}
						
						d = (Dimension) dimension.clone();
						d.width -= 2*x;
						while (caminhoLivre && d.width != dimension.width + x) {
							if (VerificarAmeaca(d, jogador) != null) {
								caminhoLivre = false;
							}
							d.width += x;
						}

						d = (Dimension) dimension.clone();
						d.width -= x;
						if (caminhoLivre && !MovimentoDeRoqueAgressivo (d, jogador)) {
							d.width -= x;
							dimensions.add(d);
						}
					}
				}
			}
		}
		
		return dimensions;
	}
	
	public boolean MovimentoDeRoqueAgressivo (Dimension dimension, int jogador) {
		int y = 1;
		if (jogador == 2) {
			y = -1;
		}
		
		Dimension d = (Dimension) dimension.clone();
		d.height += y;
		Peca p;
		boolean caminhoObstruido = false;
		
		while (d.height >= 0 && d.height <= 8 && !caminhoObstruido) {
			try {
				p = Tabuleiro.GetInstance().GetPosicaoPorDimension(d).GetPeca();
				if (p.GetJogador() != jogador && p.getClass() == Rei.class) {
					return true;
				}
				caminhoObstruido = true;
			} catch (Exception e) {
			}
			d.height += y;
		}
		
		return false;
	}
	
}
