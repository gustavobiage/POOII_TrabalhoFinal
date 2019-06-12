import java.awt.*;
import java.util.*;

public class Tabuleiro {
	
	//Primeiro n�mero largura do tabuleiro e segundo para a altura
	private Posi��o [][] posi��es = new Posi��o [8] [8];
	
	private ArrayList <Pe�a> pe�as = new ArrayList <Pe�a> ();
	
	private ArrayList <Dimension> movimentosCavalo = new ArrayList <Dimension> ();
	private ArrayList <Dimension> movimentosRei = new ArrayList <Dimension> ();
	
	public Tabuleiro () {
		//Cria as posi��es
		CriarTabuleiro ();
		
		//Cria as pe�as as quais ir�o ser linkadas a uma posi��o ao serem criadas
		CriarPe�as ();
		
		//Atualemente movimentos do cavalo e do rei
		InicializarArrayListsDiversas ();
	}
	
	//Percorre a matriz tabuleiro criando 
	private void CriarTabuleiro () {
		
		for (int i = 0; i < posi��es[0].length; i++) {
			for (int j = 0; j < posi��es[1].length; j ++) {
				posi��es[i][j] = new Posi��o (i, j);
			}
		}
	}
	
	private void CriarPe�as () {
		
		for (int i = 0; i < 8; i ++) {
			pe�as.add(new Pe�o (posi��es [i] [1], 1));
			pe�as.add(new Pe�o (posi��es [i] [6], 2));
		}
		
		pe�as.add(new Torre (posi��es [0] [0], 1));
		pe�as.add(new Torre (posi��es [7] [0], 1));
		pe�as.add(new Torre (posi��es [0] [7], 2));
		pe�as.add(new Torre (posi��es [7] [7], 2));
		

		pe�as.add(new Cavalo (posi��es [1] [0], 1));
		pe�as.add(new Cavalo (posi��es [6] [0], 1));
		pe�as.add(new Cavalo (posi��es [1] [7], 2));
		pe�as.add(new Cavalo (posi��es [6] [7], 2));
		

		pe�as.add(new Bispo (posi��es [2] [0], 1));
		pe�as.add(new Bispo (posi��es [5] [0], 1));
		pe�as.add(new Bispo (posi��es [2] [7], 2));
		pe�as.add(new Bispo (posi��es [5] [7], 2));
		

		pe�as.add(new Dama (posi��es [3] [0], 1));
		pe�as.add(new Dama (posi��es [3] [7], 1));		
		pe�as.add(new Rei (posi��es [4] [0], 2));
		pe�as.add(new Rei (posi��es [4] [7], 2));
		
	}
	
	//Retorna a posi��o de uma coordenada
	public Posi��o GetPosi��oPorDimens�o (Dimension dimension) {
		return posi��es [dimension.width] [dimension.height];
	}
	
	public void VerificarMovimentos (Dimension dimension) {
		//Ocorre ao clicar na pe�a e demonstra os poss�veis movimentos com a pe�a
		Pe�a pe�a = GetPosi��oPorDimens�o(dimension).GetPe�a();
		Class classe = pe�a.getClass();
		
		if (classe == Pe�o.class) {
			VerificarMovimentosDePe�o (dimension, pe�a);
			
		} else if (classe == Torre.class) {
			VerificarMovimentosLineares (dimension, pe�a);
			
		} else if (classe == Cavalo.class) {
			VerificarMovimentosDeCavalo (dimension, pe�a);
			
		} else if (classe == Bispo.class) {
			VerificarMovimentosDiagonais (dimension, pe�a);
			
		} else if (classe == Dama.class) {
			VerificarMovimentosLineares (dimension, pe�a);
			VerificarMovimentosDiagonais (dimension, pe�a);
			
		} else {
			VerificarMovimentosDeRei (dimension, pe�a);
		}
	}
	
	private void VerificarMovimentosLineares (Dimension dimension, Pe�a pe�a) {
		
	}

	private void VerificarMovimentosDiagonais (Dimension dimension, Pe�a pe�a) {
	
	}

	private void VerificarMovimentosDeRei (Dimension dimension, Pe�a pe�a) {
		int jogador = pe�a.GetJogador ();
		
		ArrayList <Dimension> dimensions = GetMovimentosParaRei (dimension);
		Dimension d;
		
		for (int i = 0; i < dimensions.size(); i ++) {
			d = dimensions.get(i);
			try {
				if (GetPosi��oPorDimens�o(d).GetPe�a().GetJogador() != jogador) {
					Hightlight (d);
				}
			}
			finally {
			}
		}
	}
	
	private void VerificarMovimentosDeCavalo (Dimension dimension, Pe�a pe�a) {
		if (pe�a.GetPino () != null) {
			return;
		}
		
		int jogador = pe�a.GetJogador ();
		
		ArrayList <Dimension> dimensions = GetMovimentosParaCavalo (dimension);
		Dimension d;
		
		for (int i = 0; i < dimensions.size(); i ++) {
			d = dimensions.get(i);
			try {
				if (GetPosi��oPorDimens�o(d).GetPe�a().GetJogador() != jogador) {
					Hightlight (d);
				}
			}
			finally {
			}
		}
	}
	
	private void VerificarMovimentosDePe�o (Dimension dimension, Pe�a pe�a) {
		
	}
	
	private void Hightlight (Dimension dimension) {
		//Troca a cor dos quadrados os quais a pe�a pode se mover para
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
