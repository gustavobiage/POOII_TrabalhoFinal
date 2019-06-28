import java.util.*;
import java.awt.*;

public class AdaptadorDeMovimento {
	
	private static final AdaptadorDeMovimento adaptadorDeMovimento = new AdaptadorDeMovimento ();
	
	private Dimension [] movimentosCavalo = {new Dimension (1,2), new Dimension (2,1), new Dimension (2,-1), new Dimension (1,-2), 
			new Dimension (-1,-2), new Dimension (-2,-1), new Dimension (-2,1), new Dimension (-1,2)};
	private Dimension [] movimentosRei = {new Dimension (0,1), new Dimension (1,1), new Dimension (1,0), new Dimension (1,-1), 
			new Dimension (0,-1), new Dimension (-1,-1), new Dimension (-1,0), new Dimension (-1,1)};
	
	private AdaptadorDeMovimento () {
		if (adaptadorDeMovimento.GetInstance () != null) {
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
			System.err.println("Nenhuma peça no quadrado selecionado");
			return dimensions;
		}
		System.out.println(classe + " " + dimension.width + " " + dimension.height);
		
		peca.RemoverPino ();
		VerificarPino (peca, dimension);
		
		if (classe == Peao.class) {
			dimensions = VerificarMovimentosDePeão (peca, dimension, dimensions);
			
		} else if (classe == Cavalo.class) {
			dimensions = VerificarMovimentosDeCavalo (peca, dimension, dimensions);
			
		} else if (classe == Rei.class) {
			dimensions = VerificarMovimentosDeRei (peca, dimension, dimensions);
		} else {
			ArrayList <Direção> direcoes = new ArrayList <Direção> ();
			if (classe == Dama.class) {
				direcoes.add(Direção.horizontal);
				direcoes.add(Direção.vertical);
				direcoes.add(Direção.diagonalDireito);
				direcoes.add(Direção.diagonalEsquerdo);
			} else if (classe == Torre.class) {
				direcoes.add(Direção.horizontal);
				direcoes.add(Direção.vertical);
			} else if (classe == Bispo.class) {
				direcoes.add(Direção.diagonalDireito);
				direcoes.add(Direção.diagonalEsquerdo);
			}
			dimensions = VerificarMovimentosLineares (peca, dimension, dimensions, direcoes);
		}
		return dimensions;
	}
	
	private ArrayList <Dimension> VerificarMovimentosLineares (Peca peca, Dimension dimension, ArrayList <Dimension> dimensions, 
			ArrayList <Direção> direções) {
		
		Pino pino = peca.GetPino();
		if (pino != null) {
			Direção direçãoPino = pino.GetDireção();
			if (!direções.contains(direçãoPino)) {
				return dimensions;
			}
			direções.clear();
			direções.add(direçãoPino);
		}
		int jogador = peca.GetJogador();
		int x = 0, y = 0;
		for (int n = 0; n < direções.size(); n ++) {
			
			switch (direções.get(n)) {
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
				
				while (d.width > 0 && d.width < 7 && d.height > 0 && d.height < 7) {
					d.width += x;
					d.height += y;
					initialDimensions.add((Dimension)d.clone());
				}
				
				boolean caminhoObstruído = false;
				for (int j = 0; j < initialDimensions.size() && !caminhoObstruído; j ++) {
					d = initialDimensions.get(j);
					try {
						if (Tabuleiro.GetInstance().GetPosicaoPorDimension(d).GetPeca().GetJogador() != jogador) {
							dimensions.add (d);
						}
						caminhoObstruído = true;
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
		
		//Roque:
		DetectorDeMovimento dMovimento = (DetectorDeMovimento) peca;
		if (!dMovimento.JaMoveu()) {
			ArrayList <Torre> torres = new ArrayList <Torre> ();
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
						d = torreDimension;
						d.width += x;
						caminhoLivre = true;
						while (!d.equals(dimension) && caminhoLivre) {
							if (Tabuleiro.GetInstance().GetPosicaoPorDimension(d).GetPeca() != null) {
								caminhoLivre = false;
							}
							d.width += x;
						}
						//Checar por zonas de ameaça:
						if (caminhoLivre) {
							d.width -= 2*x;
							dimensions.add(d);
						}
					}
				}
			}
		}
		
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
	
	private ArrayList <Dimension> VerificarMovimentosDePeão (Peca peca, Dimension dimension, ArrayList <Dimension> dimensions) {

		int jogador = peca.GetJogador ();
		
		ArrayList <Direção> direcoes = new ArrayList <Direção> ();
		direcoes.add(Direção.diagonalDireito);
		direcoes.add(Direção.diagonalEsquerdo);
		direcoes.add(Direção.vertical);
		Pino pino = peca.GetPino();
		if (pino != null) {
			direcoes.clear();
			direcoes.add(pino.GetDireção());
		}
		
		ArrayList <Dimension> initialDimensions = new ArrayList <Dimension> ();
		Dimension d;
		int x = 1;
		int y = 1;
		if (jogador == 2) {
			x = -1;
			y = -1;
		}		
		if (direcoes.contains(Direção.vertical)) {
			d = (Dimension) dimension.clone();
			d.height += y;
			
			if (Tabuleiro.GetInstance().GetPosicaoPorDimension(d).GetPeca() == null) {
				initialDimensions.add((Dimension) d.clone());
				DetectorDeMovimento dMovimento = (DetectorDeMovimento) peca;
				if (!dMovimento.JaMoveu()) {
					d.height += y;
					if (Tabuleiro.GetInstance().GetPosicaoPorDimension(d).GetPeca() == null) {
						initialDimensions.add((Dimension) d.clone());
					}
				}
			}			
		}
		
		if (direcoes.contains(Direção.diagonalDireito)) {
			d = (Dimension) dimension.clone();
			d.height += y;
			d.width += x;
			if (d.width >= 0 && d.width <= 7) {
				try {
					if (Tabuleiro.GetInstance().GetPosicaoPorDimension(d).GetPeca().GetJogador() != jogador) {
						initialDimensions.add((Dimension) d.clone());
					}
				} catch (Exception e) {
				}
			}
		}
		
		if (direcoes.contains(Direção.diagonalEsquerdo)) {
			d = (Dimension) dimension.clone();
			d.height += y;
			d.width -= x;
			if (d.width >= 0 && d.width <= 7) {
				try {
					if (Tabuleiro.GetInstance().GetPosicaoPorDimension(d).GetPeca().GetJogador() != jogador) {
						initialDimensions.add((Dimension) d.clone());
					}
				} catch (Exception e) {
				}
			}
		}
		
		for (int i = 0; i < initialDimensions.size(); i ++) {
			dimensions.add(initialDimensions.get(i));
		}
		return dimensions;
	}	
	
	private void VerificarPino (Peca peca, Dimension dimension) {
		int jogador = peca.GetJogador();
		Rei rei = Tabuleiro.GetInstance().GetRei(jogador);
		Dimension reiDimension = rei.GetPosicao().GetDimension();
		int x = 0, y = 0;
		Direção direção;
		
		if (reiDimension.height == dimension.height) {
			direção = Direção.horizontal;
			if (reiDimension.width > dimension.width) {
				x = -1;
			} else {
				x = 1;
			}
		} else if (reiDimension.width == dimension.width){
			direção = Direção.vertical;
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
				direção = Direção.diagonalDireito;
			} else {
				direção = Direção.diagonalEsquerdo;
			}
		} else {
			return;
		}
		
		Dimension d = (Dimension) dimension.clone();
		d.width += x;
		d.height += y;
		Peca p;
		
		while (d.width >= 0 && d.width <= 7 && d.height >= 0 && d.height <= 7) {
			System.out.println("Width " + d.width + ". Heigth: " + d.height);
			p = Tabuleiro.GetInstance().GetPosicaoPorDimension(d).GetPeca();
			
			if (p != null) {

				if (p.GetJogador() == jogador) {
					return;
				} else {
					if (direção == Direção.vertical || direção == Direção.horizontal) {
						if (p.getClass() == Torre.class || p.getClass() == Dama.class) {
							peca.AddPino(new Pino (direção));
						}
					} else if (direção == Direção.diagonalDireito || direção == Direção.diagonalEsquerdo) {
						if (p.getClass() == Bispo.class || p.getClass() == Dama.class) {
							peca.AddPino(new Pino (direção));
						}
					}
					return;
				}
			}
			
			d.width += x;
			d.height += y;
		}
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
				
				while (d.width > 0 && d.width < 7 && d.height > 0 && d.height < 7) {
					d.width += x;
					d.height += y;
					initialDimensions.add((Dimension)d.clone());
				}
				
				boolean caminhoObstruído = false;
				for (int k = 0; k < initialDimensions.size() && !caminhoObstruído; k ++) {
					d = initialDimensions.get(k);					
					p  = Tabuleiro.GetInstance().GetPosicaoPorDimension(d).GetPeca();
					try {
						if (p.GetJogador() != jogador && (p.getClass() == Dama.class || (p.getClass() == Bispo.class && (i == 0 || i == 1))
								|| p.getClass() == Torre.class && (i == 2 || i == 3))) {
							return p;
						}
						caminhoObstruído = true;
					}
					catch (Exception e) {
					}
				}
				x = -x;
				y = -y;
			}
		}
		return null;
	}
	
}
