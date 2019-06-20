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
	
	public ArrayList <Dimension> AdaptarMovimentos (Peça peça, Dimension dimension, Posição [][] posições) {
		ArrayList <Dimension> dimensions = new ArrayList <Dimension> ();
		Class classe;
		try {
			classe = peça.getClass();
		} catch (Exception e) {
			System.err.println("Nenhuma peça no quadrado selecionado");
			return dimensions;
		}
		System.out.println(classe + " " + dimension.width + " " + dimension.height);
		
		if (classe == Peao.class) {
			dimensions = VerificarMovimentosDePeão (peça, dimension, dimensions);
			
		} else if (classe == Cavalo.class) {
			dimensions = VerificarMovimentosDeCavalo (peça, dimension, dimensions);
			
		} else if (classe == Rei.class) {
			dimensions = VerificarMovimentosDeRei (peça, dimension, dimensions);
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
			dimensions = VerificarMovimentosLineares (peça, dimension, dimensions, direcoes);
		}
		return dimensions;
	}
	
	private ArrayList <Dimension> VerificarMovimentosLineares (Peça peça, Dimension dimension, ArrayList <Dimension> dimensions, 
			ArrayList <Direção> direções) {
		
		Pino pino = peça.GetPino();
		if (pino != null) {
			Direção direçãoPino = pino.GetDireção();
			if (!direções.contains(direçãoPino)) {
				return null;
			}
			direções.clear();
			direções.add(direçãoPino);
		}
		int jogador = peça.GetJogador();
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
						System.out.println(Tabuleiro.GetInstance().GetPosiçãoPorDimensão(d).GetPeça().GetJogador());
						if (Tabuleiro.GetInstance().GetPosiçãoPorDimensão(d).GetPeça().GetJogador() != jogador) {
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

	private ArrayList <Dimension> VerificarMovimentosDeRei (Peça peça, Dimension dimension, ArrayList <Dimension> dimensions) {
				
		ArrayList <Dimension> initialDimensions = new ArrayList <Dimension> ();
		Dimension d = new Dimension ();
		for (int i = 0; i < 8; i ++) {
			d = new Dimension (dimension.width + movimentosRei[i].width, dimension.height + movimentosRei[i].height);
			if (d.height >= 0 && d.width >= 0 && d.height <= 7 && d.width <= 7) {
				initialDimensions.add(d);
			}
		}
		
		int jogador = peça.GetJogador ();
		
		for (int i = 0; i < initialDimensions.size(); i ++) {
			d = initialDimensions.get(i);
			try {
				if (Tabuleiro.GetInstance().GetPosiçãoPorDimensão(d).GetPeça().GetJogador() != jogador) {
					dimensions.add(d);
				}
			}
			catch (Exception e) {
				dimensions.add(d);
			}
		}
		
		//Roque:
		DetectorDeMovimento dMovimento = (DetectorDeMovimento) peça;
		if (!dMovimento.JaMoveu()) {
			ArrayList <Torre> torres = new ArrayList <Torre> ();
			torres = Tabuleiro.GetInstance().GetTorres(jogador);
			int x;
			boolean caminhoLivre = true;
			
			for (Torre torre : torres) {
				if (!torre.JaMoveu()) {
					
					Dimension torreDimension = torre.GetPosição().GetDimension();
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
							if (Tabuleiro.GetInstance().GetPosiçãoPorDimensão(d).GetPeça() != null) {
								caminhoLivre = false;
							}
							d.width += x;
						}
						System.out.println("a");
						//Checar por zonas de ameaça:
						if (caminhoLivre) {
							System.out.println("b");
							d.width -= 2*x;
							dimensions.add(d);
						}
					}
				}
			}
		}
		
		return dimensions;
	}
	
	private ArrayList <Dimension> VerificarMovimentosDeCavalo (Peça peça, Dimension dimension, ArrayList <Dimension> dimensions) {
		if (peça.GetPino () != null) {
			return dimensions;
		}
		
		ArrayList <Dimension> initialDimensions = new ArrayList <Dimension> ();
		Dimension d;
		for (int i = 0; i < 8; i ++) {
			d = new Dimension (dimension.width + movimentosCavalo[i].width, dimension.height + movimentosCavalo[i].height);
			if (d.height >= 0 && d.width >= 0 && d.height <= 7 && d.width <= 7) {
				initialDimensions.add(d);
			}
		}
		
		int jogador = peça.GetJogador ();
		
		for (int i = 0; i < initialDimensions.size(); i ++) {
			d = initialDimensions.get(i);
			try {
				if (Tabuleiro.GetInstance().GetPosiçãoPorDimensão(d).GetPeça().GetJogador() != jogador) {
					dimensions.add(d);
				}
			}
			catch (Exception e) {
				dimensions.add(d);
			}
		}
		return dimensions;
	}
	
	private ArrayList <Dimension> VerificarMovimentosDePeão (Peça peça, Dimension dimension, ArrayList <Dimension> dimensions) {

		int jogador = peça.GetJogador ();
		
		ArrayList <Direção> direcoes = new ArrayList <Direção> ();
		direcoes.add(Direção.diagonalDireito);
		direcoes.add(Direção.diagonalEsquerdo);
		direcoes.add(Direção.vertical);
		Pino pino = peça.GetPino();
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
			
			if (d.height > 7) {
				System.out.println("a");
			}
			
			Posição da = Tabuleiro.GetInstance().GetPosiçãoPorDimensão(d);
			if (da.GetPeça() == null) {
				initialDimensions.add((Dimension) d.clone());
				DetectorDeMovimento dMovimento = (DetectorDeMovimento) peça;
				if (!dMovimento.JaMoveu()) {
					d.height += y;
					if (Tabuleiro.GetInstance().GetPosiçãoPorDimensão(d).GetPeça() == null) {
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
					if (Tabuleiro.GetInstance().GetPosiçãoPorDimensão(d).GetPeça().GetJogador() != jogador) {
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
					if (Tabuleiro.GetInstance().GetPosiçãoPorDimensão(d).GetPeça().GetJogador() != jogador) {
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
}
