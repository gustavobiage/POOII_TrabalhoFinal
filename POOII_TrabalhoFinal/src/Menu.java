
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Menu extends JFrame {
	
	private JButton novoJogo, salvar, faca, desfaca, continuarJogo;
	private JPanel panel;
	private JLabel labelLateral;
	private Historico historico;

	public Historico getHistorico() {
		return historico;
	}

	public Menu() {
		
		
		continuarJogo = new JButton("Continuar Jogo");
		continuarJogo.setBackground(Color.WHITE);
		novoJogo = new JButton("New Game");
		novoJogo.setBackground(Color.WHITE);
		desfaca = new JButton("Desfazer");
		desfaca.setBackground(Color.WHITE);
		salvar = new JButton("Salvar");
		salvar.setBackground(Color.WHITE);		
		faca = new JButton("Fazer");
		faca.setBackground(Color.WHITE);
		
		labelLateral = new JLabel(" ");
		
		BufferedImage bi = null;
		try{
			File file = new File("Imagens/mesa.jpg");
			bi = ImageIO.read(file);
			   ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			   
		}	catch (Exception e) {
			
		}
		
		panel = new JPanel();
		panel.setBackground(Color.black);

		panel.setLayout(new BorderLayout());
		panel.setBackground(new Color(82, 48, 0));
		
		Container cont1 = new Container();
		cont1.setLayout(new FlowLayout());
		cont1.add(novoJogo);
		cont1.add(salvar);
		cont1.add(continuarJogo);
		cont1.add(faca);
		cont1.add(desfaca);
		
		Container cont2 = new Container();
		cont2.setLayout(new GridLayout(3,1,5,5));

		add(panel);
		panel.add(cont1, BorderLayout.NORTH);
		panel.add(labelLateral, BorderLayout.EAST);
		panel.add(labelLateral, BorderLayout.WEST);

		TabuleiroFrame tf = new TabuleiroFrame();
		ImagePanel wrapper = new ImagePanel(bi);
		wrapper.add(tf);
		wrapper.setBackground(Color.LIGHT_GRAY);
		panel.add(wrapper, BorderLayout.CENTER);

		historico = new Historico(tf);
		Tabuleiro.GetInstance().informarHistorico(historico);

		novoJogo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Tabuleiro.novaInstancia();
				historico = new Historico(tf);
				Tabuleiro.GetInstance().informarHistorico(historico);
				tf.revisarTodo();
			}
		});

		desfaca.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getHistorico().desfazer();
				tf.revisarTabuleiro();
			}
		});

		faca.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Jogada j = getHistorico().refazer();
				if (j != null) {
					Dimension dim = j.pegarPosicaoNova().GetDimension();
					tf.desgrifarQuadrado(dim.width, dim.height, Tabuleiro.GetInstance().getPosicoes());
					tf.desgrifarQuadrado(dim.height, dim.width, Tabuleiro.GetInstance().getPosicoes());
					dim = j.pegarPosicaoAtual().GetDimension();
					tf.desgrifarQuadrado(dim.width, dim.height, Tabuleiro.GetInstance().getPosicoes());
					tf.desgrifarQuadrado(dim.height, dim.width, Tabuleiro.GetInstance().getPosicoes());
				}
			}
		});

		salvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				historico.salvarHistorico("Teste.txt");
			}
		});

		continuarJogo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Tabuleiro.novaInstancia();
				historico = new Historico(tf);
				Tabuleiro.GetInstance().informarHistorico(historico);
				historico.carregarHistorico("Teste.txt");
				tf.revisarTodo();
			}
		});

		setVisible(true);
		setSize(700,550);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		Menu menu = new Menu();
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}	
}

class ImagePanel extends JPanel {

	Image bgImage;

	public ImagePanel(BufferedImage bi) {
		bgImage = bi;
	}

	@Override
	  protected void paintComponent(Graphics g) {

	    super.paintComponent(g);
	        g.drawImage(bgImage, 0, 0, null);
	}
}
