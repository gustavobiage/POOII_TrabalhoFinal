import java.awt.*;
import java.util.*;

public class Tabuleiro {
	
	//Primeiro número largura do tabuleiro e segundo para a altura
	private Posição [][] posições = new Posição [8] [8];
	
	private ArrayList <Peça> peças = new ArrayList <Peça> ();
	
	private ArrayList <Dimension> movimentosCavalo = new ArrayList <Dimension> ();
	private ArrayList <Dimension> movimentosRei = new ArrayList <Dimension> ();
	
	public Tabuleiro () {
		//Cria as posições
		CriarTabuleiro ();
		
		//Cria as peças as quais irão ser linkadas a uma posição ao serem criadas
		CriarPeças ();
		
		//Atualemente movimentos do cavalo e do rei
		InicializarArrayListsDiversas ();
		
		/*Remover linhas a seguir:*/
		peças.add(new Bispo (posições [4] [4], 2));
		//posições[4][4].GetPeça().SetPino(new Pino (Direção.horizontal));
		VerificarMovimentos (new Dimension (4,4));
		
		/*peças.add(new Torre (posições [5] [4], 2));

		VerificarMovimentos (new Dimension (5,4));*/
	}
	
	//Percorre a matriz tabuleiro criando 
	private void CriarTabuleiro () {
		
		for (int i = 0; i < posições[0].length; i++) {
			for (int j = 0; j < posições[1].length; j ++) {
				posições[i][j] = new Posição (i, j);
			}
		}
	}
	
	private void CriarPeças () {
		
		for (int i = 0; i < 8; i ++) {
			peças.add(new Peão (posições [i] [1], 1));
			peças.add(new Peão (posições [i] [6], 2));
		}
		
		peças.add(new Torre (posições [0] [0], 1));
		peças.add(new Torre (posições [7] [0], 1));
		peças.add(new Torre (posições [0] [7], 2));
		peças.add(new Torre (posições [7] [7], 2));
		

		peças.add(new Cavalo (posições [1] [0], 1));
		peças.add(new Cavalo (posições [6] [0], 1));
		peças.add(new Cavalo (posições [1] [7], 2));
		peças.add(new Cavalo (posições [6] [7], 2));
		

		peças.add(new Bispo (posições [2] [0], 1));
		peças.add(new Bispo (posições [5] [0], 1));
		peças.add(new Bispo (posições [2] [7], 2));
		peças.add(new Bispo (posições [5] [7], 2));
		

		peças.add(new Dama (posições [3] [0], 1));
		peças.add(new Dama (posições [3] [7], 2));		
		peças.add(new Rei (posições [4] [0], 1));
		peças.add(new Rei (posições [4] [7], 2));
		
	}
	
	//Retorna a posição de uma coordenada
	public Posição GetPosiçãoPorDimensão (Dimension dimension) {
		return posições [dimension.width] [dimension.height];
	}
	
	public void VerificarMovimentos (Dimension dimension) {
		//Ocorre ao clicar na peça e demonstra os possíveis movimentos com a peça
		Peça peça = GetPosiçãoPorDimensão(dimension).GetPeça();
		Class classe;
		try {
			classe = peça.getClass();
		} catch (Exception e) {
			System.err.println("Nenhuma peça no quadrado selecionado");
			return;
		}
		System.out.println(classe + " " + dimension.width + " " + dimension.height);
		
		if (classe == Peão.class) {
			VerificarMovimentosDePeão (dimension, peça);
			
		} else if (classe == Cavalo.class) {
			VerificarMovimentosDeCavalo (dimension, peça);
			
		} else if (classe == Rei.class) {
			VerificarMovimentosDeRei (dimension, peça);
		} else {
			ArrayList <Direção> direções = new ArrayList <Direção> ();
			if (classe == Dama.class) {
				direções.add(Direção.horizontal);
				direções.add(Direção.vertical);
				direções.add(Direção.diagonalDireito);
				direções.add(Direção.diagonalEsquerdo);
			} else if (classe == Torre.class) {
				direções.add(Direção.horizontal);
				direções.add(Direção.vertical);
			} else if (classe == Bispo.class) {
				direções.add(Direção.diagonalDireito);
				direções.add(Direção.diagonalEsquerdo);
			}
			VerificarMovimentosLineares (direções, dimension, peça);
		}
	}
	
	private void VerificarMovimentosLineares (ArrayList <Direção> direções, Dimension dimension, Peça peça) {
		
		Pino pino = peça.GetPino();
		if (pino != null) {
			Direção direçãoPino = pino.GetDireção();
			if (!direções.contains(direçãoPino)) {
				return;
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
				
				ArrayList <Dimension> dimensions = new ArrayList <Dimension> ();
				Dimension d = (Dimension) dimension.clone();
				
				while (d.width > 0 && d.width < 7 && d.height > 0 && d.height < 7) {
					d.width += x;
					d.height += y;
					dimensions.add((Dimension)d.clone());
				}
				
				boolean caminhoObstruído = false;
				for (int j = 0; j < dimensions.size() && !caminhoObstruído; j ++) {
					d = dimensions.get(j);
					try {
						if (GetPosiçãoPorDimensão(d).GetPeça().GetJogador() != jogador) {
							Hightlight (d);
						}
						caminhoObstruído = true;
					}
					catch (Exception e) {
						Hightlight (d);
					}
				}
				x = -x;
				y = -y;
				System.out.println();
			}
		}
	}

	private void VerificarMovimentosDeRei (Dimension dimension, Peça peça) {
		int jogador = peça.GetJogador ();
		
		ArrayList <Dimension> dimensions = GetMovimentosParaRei (dimension);
		Dimension d;
		
		for (int i = 0; i < dimensions.size(); i ++) {
			d = dimensions.get(i);
			try {
				if (GetPosiçãoPorDimensão(d).GetPeça().GetJogador() != jogador) {
					Hightlight (d);
				}
			}
			catch (Exception e) {
				Hightlight (d);
			}
		}
	}
	
	private ArrayList <Dimension> GetMovimentosParaRei (Dimension dimension) {
		ArrayList <Dimension> dimensions = new ArrayList <Dimension> ();
		Dimension d;
		for (int i = 0; i < 8; i ++) {
			dimensions.add(new Dimension (dimension.width + movimentosRei.get(i).width, dimension.height + movimentosRei.get(i).height));
			d = dimensions.get(dimensions.size() - 1);
			if (d.height < 0 || d.width < 0 || d.height > 7 || d.width > 7) {
				dimensions.remove(dimensions.size() - 1);
			}
		}
		return dimensions;
	}
	
	private void VerificarMovimentosDeCavalo (Dimension dimension, Peça peça) {
		if (peça.GetPino () != null) {
			return;
		}
		
		int jogador = peça.GetJogador ();
		
		ArrayList <Dimension> dimensions = GetMovimentosParaCavalo (dimension);
		Dimension d;
		
		for (int i = 0; i < dimensions.size(); i ++) {
			d = dimensions.get(i);
			try {
				if (GetPosiçãoPorDimensão(d).GetPeça().GetJogador() != jogador) {
					Hightlight (d);
				}
			}
			catch (Exception e) {
				Hightlight (d);
			}
		}
	}
	
	private ArrayList <Dimension> GetMovimentosParaCavalo (Dimension dimension) {
		ArrayList <Dimension> dimensions = new ArrayList <Dimension> ();
		Dimension d;
		for (int i = 0; i < 8; i ++) {
			dimensions.add(new Dimension (dimension.width + movimentosCavalo.get(i).width, dimension.height + movimentosCavalo.get(i).height));
			d = dimensions.get(dimensions.size() - 1);
			if (d.height < 0 || d.width < 0 || d.height > 7 || d.width > 7) {
				dimensions.remove(dimensions.size() - 1);
			}
		}
		return dimensions;
	}
	
	private void VerificarMovimentosDePeão (Dimension dimension, Peça peça) {
		
	}
	
	private void Hightlight (Dimension dimension) {
		System.out.print(dimension.width + "," + dimension.height + "  ");
	}
	
	private void RemoveHightlights () {
		//Remove o highlight dos quadrados que estiverem com hightlight
	}
	
	private void InicializarArrayListsDiversas () {
		movimentosCavalo.add(new Dimension (1,2));
		movimentosCavalo.add(new Dimension (2,1));
		movimentosCavalo.add(new Dimension (2, -1));
		movimentosCavalo.add(new Dimension (1, -2));
		movimentosCavalo.add(new Dimension (-1, -2));
		movimentosCavalo.add(new Dimension (-2, -1));
		movimentosCavalo.add(new Dimension (-2, 1));
		movimentosCavalo.add(new Dimension (-1, 2));

		movimentosRei.add(new Dimension (0,1));
		movimentosRei.add(new Dimension (1,1));
		movimentosRei.add(new Dimension (1,0));
		movimentosRei.add(new Dimension (1,-1));
		movimentosRei.add(new Dimension (0,-1));
		movimentosRei.add(new Dimension (-1,-1));
		movimentosRei.add(new Dimension (-1,0));
		movimentosRei.add(new Dimension (-1,1));
	}
	
	/*Remover os métodos a seguir:*/
	
	public void MostrarTabuleiro () {
		for (int i = 0; i < posições.length; i++) {
			for (int j = 0; j < posições[1].length; j++) {
				System.out.print(posições[j][i].GetPeça() + " ");
			}
			System.out.println();
		}
		
	}
	
	public static void main (String args []) {
		Tabuleiro tabuleiro = new Tabuleiro ();
	}
}
