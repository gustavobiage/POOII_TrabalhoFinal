
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
	private JLabel labelLateral, labelInferior;
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
		
		labelInferior = new JLabel(" ");
		labelLateral = new JLabel(" ");
		
		BufferedImage bi = null;
		try{
			File file = new File("Imagens/mesa.jpg");
			bi = ImageIO.read(file);
			   ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			 
			
		}catch (Exception e) {
			
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
//		panel.add(labelInferior, BorderLayout.SOUTH);

		Posicao[][] posicao = Tabuleiro.GetInstance().getPosicoes();
		TabuleiroFrame tf = new TabuleiroFrame(Tabuleiro.GetInstance().getPosicoes());
//		tf.setPreferredSize(new Dimension(500, 500));
//		tf.setMaximumSize(new Dimension(500, 500));
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
				tf.revizarTabuleiro();
			}
		});

		faca.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Jogada j = getHistorico().refazer();
					Dimension dim = j.pegarPosicaoNova().GetDimension();
					tf.desgrifarQuadrado(dim.width, dim.height, Tabuleiro.GetInstance().getPosicoes());
					tf.desgrifarQuadrado(dim.height, dim.width, Tabuleiro.GetInstance().getPosicoes());
					dim = j.pegarPosicaoAtual().GetDimension();
					tf.desgrifarQuadrado(dim.width, dim.height, Tabuleiro.GetInstance().getPosicoes());
					tf.desgrifarQuadrado(dim.height, dim.width, Tabuleiro.GetInstance().getPosicoes());
				} catch (NullPointerException ex) {
					ex.printStackTrace();
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
//
//class ImagePanel extends JPanel {
//
//	Image bgImage;
//
//	public ImagePanel(BufferedImage bi) {
//		bgImage = bi;
//	}
//
//	@Override
//	  protected void paintComponent(Graphics g) {
//
//	    super.paintComponent(g);
//	        g.drawImage(bgImage, 0, 0, null);
//	}
//}
