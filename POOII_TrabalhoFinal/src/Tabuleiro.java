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
		
		/*Remover linhas a seguir:*/
		pe�as.add(new Bispo (posi��es [4] [4], 2));
		//posi��es[4][4].GetPe�a().SetPino(new Pino (Dire��o.horizontal));
		VerificarMovimentos (new Dimension (4,4));
		
		/*pe�as.add(new Torre (posi��es [5] [4], 2));

		VerificarMovimentos (new Dimension (5,4));*/
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
		pe�as.add(new Dama (posi��es [3] [7], 2));		
		pe�as.add(new Rei (posi��es [4] [0], 1));
		pe�as.add(new Rei (posi��es [4] [7], 2));
		
	}
	
	//Retorna a posi��o de uma coordenada
	public Posi��o GetPosi��oPorDimens�o (Dimension dimension) {
		return posi��es [dimension.width] [dimension.height];
	}
	
	public void VerificarMovimentos (Dimension dimension) {
		//Ocorre ao clicar na pe�a e demonstra os poss�veis movimentos com a pe�a
		Pe�a pe�a = GetPosi��oPorDimens�o(dimension).GetPe�a();
		Class classe;
		try {
			classe = pe�a.getClass();
		} catch (Exception e) {
			System.err.println("Nenhuma pe�a no quadrado selecionado");
			return;
		}
		System.out.println(classe + " " + dimension.width + " " + dimension.height);
		
		if (classe == Pe�o.class) {
			VerificarMovimentosDePe�o (dimension, pe�a);
			
		} else if (classe == Cavalo.class) {
			VerificarMovimentosDeCavalo (dimension, pe�a);
			
		} else if (classe == Rei.class) {
			VerificarMovimentosDeRei (dimension, pe�a);
		} else {
			ArrayList <Dire��o> dire��es = new ArrayList <Dire��o> ();
			if (classe == Dama.class) {
				dire��es.add(Dire��o.horizontal);
				dire��es.add(Dire��o.vertical);
				dire��es.add(Dire��o.diagonalDireito);
				dire��es.add(Dire��o.diagonalEsquerdo);
			} else if (classe == Torre.class) {
				dire��es.add(Dire��o.horizontal);
				dire��es.add(Dire��o.vertical);
			} else if (classe == Bispo.class) {
				dire��es.add(Dire��o.diagonalDireito);
				dire��es.add(Dire��o.diagonalEsquerdo);
			}
			VerificarMovimentosLineares (dire��es, dimension, pe�a);
		}
	}
	
	private void VerificarMovimentosLineares (ArrayList <Dire��o> dire��es, Dimension dimension, Pe�a pe�a) {
		
		Pino pino = pe�a.GetPino();
		if (pino != null) {
			Dire��o dire��oPino = pino.GetDire��o();
			if (!dire��es.contains(dire��oPino)) {
				return;
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
				
				ArrayList <Dimension> dimensions = new ArrayList <Dimension> ();
				Dimension d = (Dimension) dimension.clone();
				
				while (d.width > 0 && d.width < 7 && d.height > 0 && d.height < 7) {
					d.width += x;
					d.height += y;
					dimensions.add((Dimension)d.clone());
				}
				
				boolean caminhoObstru�do = false;
				for (int j = 0; j < dimensions.size() && !caminhoObstru�do; j ++) {
					d = dimensions.get(j);
					try {
						if (GetPosi��oPorDimens�o(d).GetPe�a().GetJogador() != jogador) {
							Hightlight (d);
						}
						caminhoObstru�do = true;
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
	
	private void VerificarMovimentosDePe�o (Dimension dimension, Pe�a pe�a) {
		
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
	
	/*Remover os m�todos a seguir:*/
	
	public void MostrarTabuleiro () {
		for (int i = 0; i < posi��es.length; i++) {
			for (int j = 0; j < posi��es[1].length; j++) {
				System.out.print(posi��es[j][i].GetPe�a() + " ");
			}
			System.out.println();
		}
		
	}
	
	public static void main (String args []) {
		Tabuleiro tabuleiro = new Tabuleiro ();
	}
}
