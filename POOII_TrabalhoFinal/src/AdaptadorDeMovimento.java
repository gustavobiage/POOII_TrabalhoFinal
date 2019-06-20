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
	
	public ArrayList <Dimension> AdaptarMovimentos (Pe�a pe�a, Dimension dimension, Posi��o [][] posi��es) {
		ArrayList <Dimension> dimensions = new ArrayList <Dimension> ();
		Class classe;
		try {
			classe = pe�a.getClass();
		} catch (Exception e) {
			System.err.println("Nenhuma pe�a no quadrado selecionado");
			return dimensions;
		}
		System.out.println(classe + " " + dimension.width + " " + dimension.height);
		
		if (classe == Peao.class) {
			dimensions = VerificarMovimentosDePe�o (pe�a, dimension, dimensions);
			
		} else if (classe == Cavalo.class) {
			dimensions = VerificarMovimentosDeCavalo (pe�a, dimension, dimensions);
			
		} else if (classe == Rei.class) {
			dimensions = VerificarMovimentosDeRei (pe�a, dimension, dimensions);
		} else {
			ArrayList <Dire��o> direcoes = new ArrayList <Dire��o> ();
			if (classe == Dama.class) {
				direcoes.add(Dire��o.horizontal);
				direcoes.add(Dire��o.vertical);
				direcoes.add(Dire��o.diagonalDireito);
				direcoes.add(Dire��o.diagonalEsquerdo);
			} else if (classe == Torre.class) {
				direcoes.add(Dire��o.horizontal);
				direcoes.add(Dire��o.vertical);
			} else if (classe == Bispo.class) {
				direcoes.add(Dire��o.diagonalDireito);
				direcoes.add(Dire��o.diagonalEsquerdo);
			}
			dimensions = VerificarMovimentosLineares (pe�a, dimension, dimensions, direcoes);
		}
		return dimensions;
	}
	
	private ArrayList <Dimension> VerificarMovimentosLineares (Pe�a pe�a, Dimension dimension, ArrayList <Dimension> dimensions, 
			ArrayList <Dire��o> dire��es) {
		
		Pino pino = pe�a.GetPino();
		if (pino != null) {
			Dire��o dire��oPino = pino.GetDire��o();
			if (!dire��es.contains(dire��oPino)) {
				return null;
			}
			dire��es.clear();
			dire��es.add(dire��oPino);
		}
		int jogador = pe�a.GetJogador();
		int x = 0, y = 0;
		for (int n = 0; n < dire��es.size(); n ++) {
			
			switch (dire��es.get(n)) {
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
				
				boolean caminhoObstru�do = false;
				for (int j = 0; j < initialDimensions.size() && !caminhoObstru�do; j ++) {
					d = initialDimensions.get(j);
					try {
						System.out.println(Tabuleiro.GetInstance().GetPosi��oPorDimens�o(d).GetPe�a().GetJogador());
						if (Tabuleiro.GetInstance().GetPosi��oPorDimens�o(d).GetPe�a().GetJogador() != jogador) {
							dimensions.add (d);
						}
						caminhoObstru�do = true;
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

	private ArrayList <Dimension> VerificarMovimentosDeRei (Pe�a pe�a, Dimension dimension, ArrayList <Dimension> dimensions) {
				
		ArrayList <Dimension> initialDimensions = new ArrayList <Dimension> ();
		Dimension d = new Dimension ();
		for (int i = 0; i < 8; i ++) {
			d = new Dimension (dimension.width + movimentosRei[i].width, dimension.height + movimentosRei[i].height);
			if (d.height >= 0 && d.width >= 0 && d.height <= 7 && d.width <= 7) {
				initialDimensions.add(d);
			}
		}
		
		int jogador = pe�a.GetJogador ();
		
		for (int i = 0; i < initialDimensions.size(); i ++) {
			d = initialDimensions.get(i);
			try {
				if (Tabuleiro.GetInstance().GetPosi��oPorDimens�o(d).GetPe�a().GetJogador() != jogador) {
					dimensions.add(d);
				}
			}
			catch (Exception e) {
				dimensions.add(d);
			}
		}
		
		//Roque:
		DetectorDeMovimento dMovimento = (DetectorDeMovimento) pe�a;
		if (!dMovimento.JaMoveu()) {
			ArrayList <Torre> torres = new ArrayList <Torre> ();
			torres = Tabuleiro.GetInstance().GetTorres(jogador);
			int x;
			boolean caminhoLivre = true;
			
			for (Torre torre : torres) {
				if (!torre.JaMoveu()) {
					
					Dimension torreDimension = torre.GetPosi��o().GetDimension();
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
							if (Tabuleiro.GetInstance().GetPosi��oPorDimens�o(d).GetPe�a() != null) {
								caminhoLivre = false;
							}
							d.width += x;
						}
						System.out.println("a");
						//Checar por zonas de amea�a:
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
	
	private ArrayList <Dimension> VerificarMovimentosDeCavalo (Pe�a pe�a, Dimension dimension, ArrayList <Dimension> dimensions) {
		if (pe�a.GetPino () != null) {
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
		
		int jogador = pe�a.GetJogador ();
		
		for (int i = 0; i < initialDimensions.size(); i ++) {
			d = initialDimensions.get(i);
			try {
				if (Tabuleiro.GetInstance().GetPosi��oPorDimens�o(d).GetPe�a().GetJogador() != jogador) {
					dimensions.add(d);
				}
			}
			catch (Exception e) {
				dimensions.add(d);
			}
		}
		return dimensions;
	}
	
	private ArrayList <Dimension> VerificarMovimentosDePe�o (Pe�a pe�a, Dimension dimension, ArrayList <Dimension> dimensions) {

		int jogador = pe�a.GetJogador ();
		
		ArrayList <Dire��o> direcoes = new ArrayList <Dire��o> ();
		direcoes.add(Dire��o.diagonalDireito);
		direcoes.add(Dire��o.diagonalEsquerdo);
		direcoes.add(Dire��o.vertical);
		Pino pino = pe�a.GetPino();
		if (pino != null) {
			direcoes.clear();
			direcoes.add(pino.GetDire��o());
		}
		
		ArrayList <Dimension> initialDimensions = new ArrayList <Dimension> ();
		Dimension d;
		int x = 1;
		int y = 1;
		if (jogador == 2) {
			x = -1;
			y = -1;
		}		
		if (direcoes.contains(Dire��o.vertical)) {
			d = (Dimension) dimension.clone();
			d.height += y;
			
			if (d.height > 7) {
				System.out.println("a");
			}
			
			Posi��o da = Tabuleiro.GetInstance().GetPosi��oPorDimens�o(d);
			if (da.GetPe�a() == null) {
				initialDimensions.add((Dimension) d.clone());
				DetectorDeMovimento dMovimento = (DetectorDeMovimento) pe�a;
				if (!dMovimento.JaMoveu()) {
					d.height += y;
					if (Tabuleiro.GetInstance().GetPosi��oPorDimens�o(d).GetPe�a() == null) {
						initialDimensions.add((Dimension) d.clone());
					}
				}
			}			
		}
		
		if (direcoes.contains(Dire��o.diagonalDireito)) {
			d = (Dimension) dimension.clone();
			d.height += y;
			d.width += x;
			if (d.width >= 0 && d.width <= 7) {
				try {
					if (Tabuleiro.GetInstance().GetPosi��oPorDimens�o(d).GetPe�a().GetJogador() != jogador) {
						initialDimensions.add((Dimension) d.clone());
					}
				} catch (Exception e) {
				}
			}
		}
		
		if (direcoes.contains(Dire��o.diagonalEsquerdo)) {
			d = (Dimension) dimension.clone();
			d.height += y;
			d.width -= x;
			if (d.width >= 0 && d.width <= 7) {
				try {
					if (Tabuleiro.GetInstance().GetPosi��oPorDimens�o(d).GetPe�a().GetJogador() != jogador) {
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
