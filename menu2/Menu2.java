package menu2;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import menuPrincipal.Menu;

public class Menu2 extends JFrame {
	JFrame frame = new JFrame();
	JButton botaoBispo, botaoDama, botaoTorre, botaoCavalo;
	
	public Menu2() {
		
		Container cont1 = new Container();
		cont1.setLayout(new GridLayout(1,4,0,0));
		cont1.add(botaoBispo);
		cont1.add(botaoDama);
		cont1.add(botaoTorre);
		cont1.add(botaoCavalo);
		
		setSize(500,500);
		setVisible(true);
				
		
	}
	public static void main(String[] args) {
		Menu2 menu2 = new Menu2();
		menu2.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
