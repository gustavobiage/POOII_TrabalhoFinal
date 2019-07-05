import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Menu2 extends JPanel {
	JButton botaoBispo, botaoDama, botaoTorre, botaoCavalo;
	
	public Menu2(String lado) {
		
		Container cont1 = new Container();
		cont1.setLayout(new GridLayout(1,4,0,0));
		botaoBispo = new JButton();
		botaoDama = new JButton();
		botaoTorre = new JButton();
		botaoCavalo = new JButton();
		imagemBotao(lado, botaoBispo, "bispo");
		imagemBotao(lado, botaoCavalo, "cavalo");
		imagemBotao(lado, botaoTorre, "torre");
		imagemBotao(lado, botaoDama, "dama");
		
		cont1.add(botaoBispo);
		cont1.add(botaoDama);
		cont1.add(botaoTorre);
		cont1.add(botaoCavalo);
		
		setSize(500,500);
		setVisible(true);
				
		
	}
	public void imagemBotao(String lado, JButton button, String peca) {
		
		GestaoImagem gi = new GestaoImagem();
		String caminho_imagem = gi.pegarCaminhoObjeto((peca + "_" + lado).toLowerCase());
		File icone = new File(caminho_imagem);
        BufferedImage bufferedImage = ImageIO.read(icone);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        ImageIO.write(bufferedImage, caminho_imagem.substring(caminho_imagem.length()-3, caminho_imagem.length()), byteArrayOutputStream);
        byte[] dados_imagem = byteArrayOutputStream.toByteArray();
        button.setIcon(new ImageIcon(bufferedImage.getScaledInstance(57,57, Image.SCALE_DEFAULT)));
	}


























