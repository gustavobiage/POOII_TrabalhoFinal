
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Menu extends JFrame {
	
	private JButton novoJogo, salvar, faca, desfaca, continuarJogo;
	private JPanel panel;
	private JLabel labelLateral, labelInferior;
	private JPanel cemiterio1, imagem, cemiterio2;
	private Historico historico;

	public Historico getHistorico() {
		return historico;
	}

	public Menu() {

		continuarJogo = new JButton("Continuar Jogo");
		continuarJogo.setBackground(Color.pink);
		novoJogo = new JButton("New Game");
		novoJogo.setBackground(Color.pink);
		desfaca = new JButton("Desfazer");
		desfaca.setBackground(Color.pink);
		salvar = new JButton("Salvar");
		salvar.setBackground(Color.pink);		
		faca = new JButton("Fazer");
		faca.setBackground(Color.pink);
		
		labelInferior = new JLabel(" ");
		labelLateral = new JLabel(" ");
		
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.LIGHT_GRAY);
		
		//Botoes pra come�ar novo jogo, recomecar e carregar jogo antigo
		Container cont1 = new Container();
		cont1.setLayout(new FlowLayout());
		cont1.add(novoJogo);
		cont1.add(salvar);
		cont1.add(continuarJogo);
		cont1.add(faca);
		cont1.add(desfaca);
		
		Container cont2 = new Container();
		cont2.setLayout(new GridLayout(3,1,5,5));
		//TODO adicionar retangulos nos flowlayouts
		
		//lugares para mandar as pe�as comidas
		cemiterio1 = new JPanel();
		cemiterio1.setLayout(new GridLayout(6,3));
		cemiterio1.setBackground(Color.pink);
		cemiterio1.setMinimumSize(new Dimension(100,100));
		cemiterio1.setPreferredSize(new Dimension(100,100));
		cemiterio1.setMaximumSize(new Dimension(100,100));
		
		
		imagem = new JPanel();
		imagem.setLayout(new FlowLayout());
		imagem.setBackground(Color.lightGray);
		imagem.setMinimumSize(new Dimension(100,50));
		imagem.setPreferredSize(new Dimension(100,50));
		imagem.setMaximumSize(new Dimension(100,50));
		
		cemiterio2 = new JPanel();
		cemiterio2.setLayout(new GridLayout(6,3));
		cemiterio2.setBackground(Color.pink);
		cemiterio2.setMinimumSize(new Dimension(100,100));
		cemiterio2.setPreferredSize(new Dimension(100,100));
		cemiterio2.setMaximumSize(new Dimension(100,100));
		
		cont2.add(cemiterio1);
		cont2.add(imagem);
		cont2.add(cemiterio2);
		
		add(panel);
		panel.add(cont1, BorderLayout.NORTH);
		panel.add(cont2, BorderLayout.EAST);
		panel.add(labelLateral, BorderLayout.WEST);
		panel.add(labelInferior, BorderLayout.SOUTH);
		//TODO
		//panel.add(--Tabuleiro, BorderLayout.CENTER);
		
		for(int i = 0; i < 18; i++) {
			botoes_cemiterio1[i] = new JButton();
			cemiterio1.add(botoes_cemiterio1[i]);
			
			botoes_cemiterio2[i] = new JButton();
			cemiterio2.add(botoes_cemiterio2[i]);
		}
		
		try {
			File file = new File("bispo_brancas_quadrado_claro.jpg");
			BufferedImage bi = ImageIO.read(file);
			this.adicionarCemiterio1(bi);
			this.adicionarCemiterio1(bi);
			this.adicionarCemiterio1(bi);
		} catch(Exception e) {
			e.printStackTrace();
		}

		Posicao[][] posicao = Tabuleiro.GetInstance().getPosicoes();
		TabuleiroFrame tf = new TabuleiroFrame(Tabuleiro.GetInstance().getPosicoes());
//		tf.setPreferredSize(new Dimension(500, 500));
//		tf.setMaximumSize(new Dimension(500, 500));
		JPanel wrapper = new JPanel();
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
	}
	JButton botoes_cemiterio1[] = new JButton[18];
	JButton botoes_cemiterio2[] = new JButton[18];
	int buttonCount = 0;
	
	public void adicionarCemiterio1(BufferedImage pecaMorta) {
		
		JButton button = botoes_cemiterio1[buttonCount];
		buttonCount++;
//		JButton button = new JButton();
		button.setIcon(new ImageIcon(pecaMorta.getScaledInstance(30,  30, Image.SCALE_DEFAULT)));
//		button.setMinimumSize(new Dimension(10, 10));
//		button.setPreferredSize(new Dimension(10, 10));
//		cemiterio1.add(button);
	}
	
	public void limparCemiterio1() {
		cemiterio1.removeAll();
	}
	
	public void adicionarCemiterio2(BufferedImage pecaMorta) {
		JButton button = new JButton();
		button.setIcon(new ImageIcon(pecaMorta.getScaledInstance(25,  25, Image.SCALE_DEFAULT)));
		
		cemiterio2.add(button);
	}
	
	public void limparCemiterio2() {
		cemiterio2.removeAll();
	}
//	public static void main(String[] args) {
//		Menu menu = new Menu();
//		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}
}
