package menuPrincipal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Menu extends JFrame {
	
	private JButton novoJogo, salvar, faca, desfaca, continuarJogo;
	private ImagePanel panel;
	private JLabel labelLateral, labelInferior;
	
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
		
		panel = new ImagePanel(bi);
		
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.getHSBColor(60, 10, 96));
		
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
		panel.add(labelInferior, BorderLayout.SOUTH);
	
		setVisible(true);
		setSize(700,550);
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
