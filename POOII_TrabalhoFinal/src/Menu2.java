
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class Menu2 extends JFrame {
	Menu2 frame;
	JButton botaoBispo, botaoDama, botaoTorre, botaoCavalo;
	Peca.Lado l;
	public Menu2(Peca.Lado lado, Dimension dimension) {
		l = lado;
		frame = this;

		Container cont1 = new Container();
		cont1.setLayout(new FlowLayout());

		botaoBispo = new JButton();
		botaoBispo.setPreferredSize(new Dimension(57, 57));
		this.colocarImagem(botaoBispo, "Bispo", lado.toString());

		botaoBispo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(l == Peca.Lado.BRANCAS) Tabuleiro.GetInstance().evoluitPeao(dimension, "Bispo", 1, TabuleiroFrame.getLastInstance());
				else Tabuleiro.GetInstance().evoluitPeao(dimension, "Bispo", 2, TabuleiroFrame.getLastInstance());
				frame.closeWindown();
			}
		});

		botaoCavalo = new JButton();
		botaoCavalo.setPreferredSize(new Dimension(57, 57));
		this.colocarImagem(botaoCavalo, "Cavalo", lado.toString());
		botaoCavalo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(l == Peca.Lado.BRANCAS) Tabuleiro.GetInstance().evoluitPeao(dimension, "Cavalo", 1, TabuleiroFrame.getLastInstance());
				else Tabuleiro.GetInstance().evoluitPeao(dimension, "Cavalo", 2, TabuleiroFrame.getLastInstance());
				frame.closeWindown();
			}
		});

		botaoTorre = new JButton();
		botaoTorre.setPreferredSize(new Dimension(57, 57));
		this.colocarImagem(botaoTorre, "Torre", lado.toString());
		botaoTorre.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(l == Peca.Lado.BRANCAS) Tabuleiro.GetInstance().evoluitPeao(dimension, "Torre", 1, TabuleiroFrame.getLastInstance());
				else Tabuleiro.GetInstance().evoluitPeao(dimension, "Torre", 2, TabuleiroFrame.getLastInstance());
				frame.closeWindown();
			}
		});

		botaoDama = new JButton();
		botaoDama.setPreferredSize(new Dimension(57, 57));
		this.colocarImagem(botaoDama, "Dama", lado.toString());
		botaoDama.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(l == Peca.Lado.BRANCAS) Tabuleiro.GetInstance().evoluitPeao(dimension, "Dama", 1, TabuleiroFrame.getLastInstance());
				else Tabuleiro.GetInstance().evoluitPeao(dimension, "Dama", 2, TabuleiroFrame.getLastInstance());
				frame.closeWindown();
			}
		});

		cont1.add(botaoBispo);
		cont1.add(botaoDama);
		cont1.add(botaoTorre);
		cont1.add(botaoCavalo);

		add(cont1);
		cont1.setSize(300, 100);
		setSize(300,110);
		setVisible(true);
	}

	public void closeWindown() {
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	public void colocarImagem(JButton button, String peca, String lado) {
		GestaoImagem gi = new GestaoImagem();
		String objeto = peca + "_" + lado;
		objeto = objeto.toLowerCase();
		String caminho_imagem = gi.pegarCaminhoObjeto(objeto);

		File file = new File(caminho_imagem);
		try {
			BufferedImage bi =  ImageIO.read(file);
			button.setIcon(new ImageIcon(bi.getScaledInstance(57, 57, Image.SCALE_DEFAULT)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//For test only
//	public static void main(String[] args) {
//		Menu2 menu2 = new Menu2(Peca.Lado.BRANCAS, new Dimension(0, 0));
//		menu2.setDefaultCloseOperation(EXIT_ON_CLOSE);
//	}
}
