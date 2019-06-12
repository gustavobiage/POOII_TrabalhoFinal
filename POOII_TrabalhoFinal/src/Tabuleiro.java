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
		peças.add(new Dama (posições [3] [7], 1));		
		peças.add(new Rei (posições [4] [0], 2));
		peças.add(new Rei (posições [4] [7], 2));
		
	}
	
	//Retorna a posição de uma coordenada
	public Posição GetPosiçãoPorDimensão (Dimension dimension) {
		return posições [dimension.width] [dimension.height];
	}
	
	public void VerificarMovimentos (Dimension dimension) {
		//Ocorre ao clicar na peça e demonstra os possíveis movimentos com a peça
		Peça peça = GetPosiçãoPorDimensão(dimension).GetPeça();
		Class classe = peça.getClass();
		
		if (classe == Peão.class) {
			VerificarMovimentosDePeão (dimension, peça);
			
		} else if (classe == Torre.class) {
			VerificarMovimentosLineares (dimension, peça);
			
		} else if (classe == Cavalo.class) {
			VerificarMovimentosDeCavalo (dimension, peça);
			
		} else if (classe == Bispo.class) {
			VerificarMovimentosDiagonais (dimension, peça);
			
		} else if (classe == Dama.class) {
			VerificarMovimentosLineares (dimension, peça);
			VerificarMovimentosDiagonais (dimension, peça);
			
		} else {
			VerificarMovimentosDeRei (dimension, peça);
		}
	}
	
	private void VerificarMovimentosLineares (Dimension dimension, Peça peça) {
		
	}

	private void VerificarMovimentosDiagonais (Dimension dimension, Peça peça) {
	
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
			finally {
			}
		}
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
			finally {
			}
		}
	}
	
	private void VerificarMovimentosDePeão (Dimension dimension, Peça peça) {
		
	}
	
	private void Hightlight (Dimension dimension) {
		//Troca a cor dos quadrados os quais a peça pode se mover para
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
}
