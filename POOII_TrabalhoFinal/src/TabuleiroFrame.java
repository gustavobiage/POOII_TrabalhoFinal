import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TabuleiroFrame extends JPanel {

    private Map<JButton, Posicao> encontrar_posicao= new HashMap<>();
    JButton[][] posicoes_clicavel;
    private GestaoImagem gestaoImagem;
    Posicao posicaoSelecionada;
    ArrayList<Posicao> possiveis_movimentos;
    static TabuleiroFrame tabuleiroFrame;
    
    public static TabuleiroFrame GetInstance () {
    	return tabuleiroFrame;
    }
    
    
    public Posicao pegarPosicaoPorBotao(JButton botao) {
        return encontrar_posicao.get(botao);
    }

    public TabuleiroFrame() {

    	tabuleiroFrame = this;
    	
    	Posicao[][] matrix = Tabuleiro.GetInstance().getPosicoes();
    	
        setLayout(new GridLayout(8, 8));
        setSize(new Dimension(500, 500));

        posicoes_clicavel = new JButton[8][8];

        Container tabuleiro = new Container();
        tabuleiro.setLayout(new GridLayout(8, 8));

        gestaoImagem = new GestaoImagem();
        Posicao posicao;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {

                posicao = matrix[i][j];
                posicoes_clicavel[i][j] = new JButton("");
                posicoes_clicavel[i][j].setPreferredSize(new Dimension(57,57));
                posicoes_clicavel[i][j].setMaximumSize(new Dimension(57,57));
                encontrar_posicao.put(posicoes_clicavel[i][j], matrix[i][j]);

                String caminho_imagem;
                if(posicao.GetPeca() != null) caminho_imagem = gestaoImagem.pegarCaminhoObjeto((posicao.GetPeca().pegarNome() + "_quadrado_" +  posicao.pegarQuadrado()).toLowerCase());
                else caminho_imagem = gestaoImagem.pegarCaminhoObjeto(("quadrado_" +  posicao.pegarQuadrado()).toLowerCase());
                try {

                    File icone = new File(caminho_imagem);
                    BufferedImage bufferedImage = ImageIO.read(icone);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                    ImageIO.write(bufferedImage, caminho_imagem.substring(caminho_imagem.length()-3, caminho_imagem.length()), byteArrayOutputStream);
                    byte[] dados_imagem = byteArrayOutputStream.toByteArray();
                    posicoes_clicavel[i][j].setIcon(new ImageIcon(bufferedImage.getScaledInstance(57,57, Image.SCALE_DEFAULT)));

                    posicoes_clicavel[i][j].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //TODO acionar movimento das pecas
                            Posicao posicao = pegarPosicaoPorBotao((JButton)e.getSource());
                            if(possiveis_movimentos == null) possiveis_movimentos = new ArrayList<>();

                            int turn= Tabuleiro.GetInstance().getTurno();
                            Posicao[][] tabuleiro = Tabuleiro.GetInstance().getPosicoes();

                            Posicao p;
                            if(turn%2 == 1) {
                                //VEZ DAS BRANCAS
                                if(posicao.GetPeca() != null) {
                                    //TODO checar se pessa esta null
                                    if(posicao.GetPeca().pegarLado() == Peca.Lado.BRANCAS) {
                                        //APAGAR ANTIGOS MOVIMENTOS
                                        for(int i = 0; i < possiveis_movimentos.size(); i++) {
                                            p = possiveis_movimentos.get(i);
                                            desgrifarQuadrado(p.GetDimension().height, p.GetDimension().width, tabuleiro);
                                        }

                                        possiveis_movimentos = Tabuleiro.GetInstance().VerificarMovimentos(new Dimension(posicao.GetDimension().width, posicao.GetDimension().height));

                                        for(int i = 0; i < possiveis_movimentos.size(); i++) {
                                            p = possiveis_movimentos.get(i);
                                            grifarQuadrado(p.GetDimension().height, p.GetDimension().width, tabuleiro);
                                        }

                                        posicaoSelecionada = posicao;
                                    } else if(posicao.pegarGrifada()) {

                                        Tabuleiro.GetInstance().MoverPeca(Tabuleiro.GetInstance().getPosicoes()[posicaoSelecionada.GetDimension().height][posicaoSelecionada.GetDimension().width].GetPeca(), posicao.GetDimension());

                                        //DESGRIFAR ANTIGOS MOVIMENTOS
                                        for(int i = 0; i < possiveis_movimentos.size(); i++) {
                                            p = possiveis_movimentos.get(i);
                                            desgrifarQuadrado(p.GetDimension().height, p.GetDimension().width, tabuleiro);
                                        }
                                        desgrifarQuadrado(posicaoSelecionada.GetDimension().height, posicaoSelecionada.GetDimension().width, tabuleiro);
                                    }
                                } else if(posicao.pegarGrifada()) {

                                    //TODO MOVIMENTAR PECA E REMONTAR GRIFADA
                                    Tabuleiro.GetInstance().MoverPeca(Tabuleiro.GetInstance().getPosicoes()[posicaoSelecionada.GetDimension().height][posicaoSelecionada.GetDimension().width].GetPeca(), posicao.GetDimension());

                                    //DESGRIFAR ANTIGOS MOVIMENTOS
                                    for(int i = 0; i < possiveis_movimentos.size(); i++) {
                                        p = possiveis_movimentos.get(i);
                                        desgrifarQuadrado(p.GetDimension().height, p.GetDimension().width, tabuleiro);
                                    }
                                    desgrifarQuadrado(posicaoSelecionada.GetDimension().height, posicaoSelecionada.GetDimension().width, tabuleiro);
                                    //LIMPAR ARRAYLIST DE POSSIVEIS MOVIMENTOS ACELERAR A PROXIMA BUSCA
                                    possiveis_movimentos = new ArrayList<Posicao>();
                                    if(posicao.GetPeca() instanceof Rei) {
//                                    Dimension d = Tabuleiro.GetInstance().GetPosicaoPorDimension(new Dimension(0, 0);
                                        desgrifarQuadrado(0, 0, tabuleiro);
                                        desgrifarQuadrado(7, 0, tabuleiro);
                                        desgrifarQuadrado(0, 7, tabuleiro);
                                        desgrifarQuadrado(7, 7, tabuleiro);
                                    }
                                }
                            } else {
                                //VEZ DAS PRETAS
                                if(posicao.GetPeca() != null) {
                                    //TODO checar se pessa esta null
                                    if(posicao.GetPeca().pegarLado() == Peca.Lado.PRETAS) {
                                        //APAGAR ANTIGOS MOVIMENTOS
                                        for(int i = 0; i < possiveis_movimentos.size(); i++) {
                                            p = possiveis_movimentos.get(i);
                                            desgrifarQuadrado(p.GetDimension().height, p.GetDimension().width, tabuleiro);
                                        }

                                        possiveis_movimentos = Tabuleiro.GetInstance().VerificarMovimentos(new Dimension(posicao.GetDimension().width, posicao.GetDimension().height));

                                        for(int i = 0; i < possiveis_movimentos.size(); i++) {
                                            p = possiveis_movimentos.get(i);
                                            grifarQuadrado(p.GetDimension().height, p.GetDimension().width, tabuleiro);
                                        }

                                        posicaoSelecionada = posicao;
                                    } else if(posicao.pegarGrifada()) {

                                        Tabuleiro.GetInstance().MoverPeca(Tabuleiro.GetInstance().getPosicoes()[posicaoSelecionada.GetDimension().height][posicaoSelecionada.GetDimension().width].GetPeca(), posicao.GetDimension());

                                        //DESGRIFAR ANTIGOS MOVIMENTOS
                                        for(int i = 0; i < possiveis_movimentos.size(); i++) {
                                            p = possiveis_movimentos.get(i);
                                            desgrifarQuadrado(p.GetDimension().height, p.GetDimension().width, tabuleiro);
                                        }
                                        desgrifarQuadrado(posicaoSelecionada.GetDimension().height, posicaoSelecionada.GetDimension().width, tabuleiro);
                                    }
                                } else if(posicao.pegarGrifada()) {

                                    //TODO MOVIMENTAR PECA E REMONTAR GRIFADA
                                    Tabuleiro.GetInstance().MoverPeca(Tabuleiro.GetInstance().getPosicoes()[posicaoSelecionada.GetDimension().height][posicaoSelecionada.GetDimension().width].GetPeca(), posicao.GetDimension());

                                    //DESGRIFAR ANTIGOS MOVIMENTOS
                                    for(int i = 0; i < possiveis_movimentos.size(); i++) {
                                        p = possiveis_movimentos.get(i);
                                        desgrifarQuadrado(p.GetDimension().height, p.GetDimension().width, tabuleiro);
                                    }
                                    desgrifarQuadrado(posicaoSelecionada.GetDimension().height, posicaoSelecionada.GetDimension().width, tabuleiro);
                                    //LIMPAR ARRAYLIST DE POSSIVEIS MOVIMENTOS ACELERAR A PROXIMA BUSCA
                                    possiveis_movimentos = new ArrayList<Posicao>();

                                    if(posicao.GetPeca() instanceof Rei) {
//                                    Dimension d = Tabuleiro.GetInstance().GetPosicaoPorDimension(new Dimension(0, 0);
                                        desgrifarQuadrado(7, 0, tabuleiro);
                                        desgrifarQuadrado(0, 7, tabuleiro);
                                        desgrifarQuadrado(7, 7, tabuleiro);
                                        desgrifarQuadrado(0, 0, tabuleiro);
                                    }
                                }

                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    this.add(posicoes_clicavel[i][j], i, j);
                }
            }
        }
        
        setVisible(true);
    }
    
    public void DesgrifarQuadrados () {
    	for (Posicao p: possiveis_movimentos) {
    		desgrifarQuadrado (p.GetDimension().height, p.GetDimension().width, Tabuleiro.GetInstance().getPosicoes());
    	}
    }

    public void desgrifarQuadrado(int i, int j, Posicao[][] matrix) {
        String objeto = new String();
        if(matrix[i][j].GetPeca() != null) {
            objeto += matrix[i][j].GetPeca().pegarNome()+"_";
        }

        objeto += "quadrado_" + matrix[i][j].pegarQuadrado().toString();
        objeto = objeto.toLowerCase();

        try {

            String caminho_imagem = gestaoImagem.pegarCaminhoObjeto(objeto);
            File icone = new File(caminho_imagem);
            BufferedImage bufferedImage = ImageIO.read(icone);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            ImageIO.write(bufferedImage, caminho_imagem.substring(caminho_imagem.length()-3, caminho_imagem.length()), byteArrayOutputStream);
            byte[] dados_imagem = byteArrayOutputStream.toByteArray();

            posicoes_clicavel[i][j].setIcon(new ImageIcon(bufferedImage.getScaledInstance(57,57, Image.SCALE_DEFAULT)));
            matrix[i][j].informarGrifada(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    public void desgrifarQuadradoPeloTabuleiro (int i, int j, Posicao[][] matrix) {
        String objeto = new String();
        if(matrix[i][j].GetPeca() != null) {
            objeto += matrix[i][j].GetPeca().pegarNome()+"_";
        }

        objeto += "quadrado_" + matrix[i][j].pegarQuadrado().toString();
        objeto = objeto.toLowerCase();

        try {

            String caminho_imagem = gestaoImagem.pegarCaminhoObjeto(objeto);
            File icone = new File(caminho_imagem);
            BufferedImage bufferedImage = ImageIO.read(icone);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            ImageIO.write(bufferedImage, caminho_imagem.substring(caminho_imagem.length()-3, caminho_imagem.length()), byteArrayOutputStream);
            byte[] dados_imagem = byteArrayOutputStream.toByteArray();

            posicoes_clicavel[j][i].setIcon(new ImageIcon(bufferedImage.getScaledInstance(57,57, Image.SCALE_DEFAULT)));
            matrix[j][i].informarGrifada(false);
            matrix[i][j].informarGrifada(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void grifarQuadrado(int i, int j, Posicao[][] matrix) {

        String objeto = new String();
        if(matrix[i][j].GetPeca() != null) {
            objeto += matrix[i][j].GetPeca().pegarNome()+"_";
        }
        objeto += "quadrado_" + matrix[i][j].pegarQuadrado().toString();
        objeto += "_grifada";
        objeto = objeto.toLowerCase();

        try {
            String caminho_imagem = gestaoImagem.pegarCaminhoObjeto(objeto);
            File icone = new File(caminho_imagem);
            BufferedImage bufferedImage = ImageIO.read(icone);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            ImageIO.write(bufferedImage, caminho_imagem.substring(caminho_imagem.length()-3, caminho_imagem.length()), byteArrayOutputStream);
            byte[] dados_imagem = byteArrayOutputStream.toByteArray();

            posicoes_clicavel[i][j].setIcon(new ImageIcon(bufferedImage.getScaledInstance(57,57, Image.SCALE_DEFAULT)));
            matrix[i][j].informarGrifada(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void revisarTodo() {
        Posicao[][] posicoes = Tabuleiro.GetInstance().getPosicoes();
        encontrar_posicao.clear();
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                desgrifarQuadrado(i, j, posicoes);
                encontrar_posicao.put(posicoes_clicavel[i][j], posicoes[i][j]);
            }
        }
    }

    public void revisarTabuleiro() {
        Posicao[][] posicoes = Tabuleiro.GetInstance().getPosicoes();
        encontrar_posicao.clear();
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                encontrar_posicao.put(posicoes_clicavel[i][j], posicoes[i][j]);
            }
        }
    }


}