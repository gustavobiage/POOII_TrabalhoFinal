import javax.swing.*;
import java.awt.*;

public class Posicao extends JButton {

    private Quadrado quadrado;
    private Dimension dimension;
    private Peca pecaPresente;
    private boolean grifada;

    public boolean pegarGrifada() {
        return grifada;
    }

    public void informarGrifada(boolean grifada) {
        this.grifada = grifada;
    }
    public Posicao (int horizontal, int vertical) {
        dimension = new Dimension (horizontal,vertical);

        if((horizontal+vertical)%2 == 1) {
            quadrado = Quadrado.CLARO;
        } else {
            quadrado = Quadrado.ESCURO;
        }
    }
    
	public void AdicionarPeca (Peca peca) {
		pecaPresente = peca;
	}

    public void adicionarPecaAPosicao (Peca peca) {
        pecaPresente = peca;
    }

    public void RemoverPeca () {
        pecaPresente = null;
    }

    public Peca GetPeca () {
        return pecaPresente;
    }

    public Dimension GetDimension () {
        return dimension;
    }

    public Quadrado pegarQuadrado() {
        return this.quadrado;
    }

    enum Quadrado{
        CLARO, ESCURO
    }

    public void informarQuadrado(Quadrado quadrado) {this.quadrado = quadrado;}

    @Override
    public Posicao clone() {
        Posicao posicao = new Posicao(this.dimension.width,this.dimension.height);
        posicao.adicionarPecaAPosicao(this.pecaPresente);
        posicao.informarQuadrado(quadrado);

        return posicao;
    }
}
