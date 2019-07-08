import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GestaoImagem {

    Map<String, String> repositorio_imagem = new HashMap<>();

    public GestaoImagem() {

//        //TODO arrumar extensao de imagens de fundo
//        //quadrado padrao
        repositorio_imagem.put("quadrado_claro", "Imagens/quadrado_claro.png");
        repositorio_imagem.put("quadrado_escuro", "Imagens/quadrado_escuro.png");
//
//        //quadrado clicado
        repositorio_imagem.put("quadrado_claro_grifada", "Imagens/quadrado_claro_grifada.jpg");
        repositorio_imagem.put("quadrado_escuro_grifada", "Imagens/quadrado_escuro_grifada.jpg");
//
//        //quadrado selecionavel
//        repositorio_imagem.put("quadrado_claro_selecionavel", "Imagens/quadrado_claro_selecionavel.");
//        repositorio_imagem.put("quadrado_escuro_selecionavel", "Imagens/quadrado_claro_selecionavel.");

        //Pecas

        //  dama
        repositorio_imagem.put("dama_pretas_quadrado_claro", "Imagens/dama_pretas_quadrado_claro.jpg");
        repositorio_imagem.put("dama_pretas_quadrado_claro_grifada", "Imagens/dama_pretas_quadrado_claro_grifada.jpg");
        repositorio_imagem.put("dama_pretas_quadrado_escuro", "Imagens/dama_pretas_quadrado_escuro.jpg");
        repositorio_imagem.put("dama_pretas_quadrado_escuro_grifada", "Imagens/dama_pretas_quadrado_escuro_grifada.jpg");

        repositorio_imagem.put("dama_brancas_quadrado_claro", "Imagens/dama_brancas_quadrado_claro.jpg");
        repositorio_imagem.put("dama_brancas_quadrado_claro_grifada", "Imagens/dama_brancas_quadrado_claro_grifada.jpg");
        repositorio_imagem.put("dama_brancas_quadrado_escuro", "Imagens/dama_brancas_quadrado_escuro.jpg");
        repositorio_imagem.put("dama_brancas_quadrado_escuro_grifada", "Imagens/dama_brancas_quadrado_escuro_grifada.jpg");

        //  rei
        repositorio_imagem.put("rei_pretas_quadrado_claro", "Imagens/rei_pretas_quadrado_claro.jpg");
        repositorio_imagem.put("rei_pretas_quadrado_claro_grifada", "Imagens/rei_pretas_quadrado_claro_grifada.jpg");
        repositorio_imagem.put("rei_pretas_quadrado_escuro", "Imagens/rei_pretas_quadrado_escuro.jpg");
        repositorio_imagem.put("rei_pretas_quadrado_escuro_grifada", "Imagens/rei_pretas_quadrado_escuro_grifada.jpg");

        repositorio_imagem.put("rei_brancas_quadrado_claro", "Imagens/rei_brancas_quadrado_claro.jpg");
        repositorio_imagem.put("rei_brancas_quadrado_claro_grifada", "Imagens/rei_brancas_quadrado_claro_grifada.jpg");
        repositorio_imagem.put("rei_brancas_quadrado_escuro", "Imagens/rei_brancas_quadrado_escuro.jpg");
        repositorio_imagem.put("rei_brancas_quadrado_escuro_grifada", "Imagens/rei_brancas_quadrado_escuro_grifada.jpg");


        //  bispo
        repositorio_imagem.put("bispo_pretas_quadrado_claro", "Imagens/bispo_pretas_quadrado_claro.jpg");
        repositorio_imagem.put("bispo_pretas_quadrado_claro_grifada", "Imagens/bispo_pretas_quadrado_claro_grifada.jpg");
        repositorio_imagem.put("bispo_pretas_quadrado_escuro", "Imagens/bispo_pretas_quadrado_escuro.jpg");
        repositorio_imagem.put("bispo_pretas_quadrado_escuro_grifada", "Imagens/bispo_pretas_quadrado_escuro_grifada.jpg");

        repositorio_imagem.put("bispo_brancas_quadrado_claro", "Imagens/bispo_brancas_quadrado_claro.jpg");
        repositorio_imagem.put("bispo_brancas_quadrado_claro_grifada", "Imagens/bispo_brancas_quadrado_claro_grifada.jpg");
        repositorio_imagem.put("bispo_brancas_quadrado_escuro", "Imagens/bispo_brancas_quadrado_escuro.jpg");
        repositorio_imagem.put("bispo_brancas_quadrado_escuro_grifada", "Imagens/bispo_brancas_quadrado_escuro_grifada.jpg");

        //  cavalo
        repositorio_imagem.put("cavalo_pretas_quadrado_claro", "Imagens/cavalo_pretas_quadrado_claro.jpg");
        repositorio_imagem.put("cavalo_pretas_quadrado_claro_grifada", "Imagens/cavalo_pretas_quadrado_claro_grifada.jpg");
        repositorio_imagem.put("cavalo_pretas_quadrado_escuro", "Imagens/cavalo_pretas_quadrado_escuro.jpg");
        repositorio_imagem.put("cavalo_pretas_quadrado_escuro_grifada", "Imagens/cavalo_pretas_quadrado_escuro_grifada.jpg");

        repositorio_imagem.put("cavalo_brancas_quadrado_claro", "Imagens/cavalo_brancas_quadrado_claro.jpg");
        repositorio_imagem.put("cavalo_brancas_quadrado_claro_grifada", "Imagens/cavalo_brancas_quadrado_claro_grifada.jpg");
        repositorio_imagem.put("cavalo_brancas_quadrado_escuro", "Imagens/cavalo_brancas_quadrado_escuro.jpg");
        repositorio_imagem.put("cavalo_brancas_quadrado_escuro_grifada", "Imagens/cavalo_brancas_quadrado_escuro_grifada.jpg");

        //torre
        repositorio_imagem.put("torre_pretas_quadrado_claro", "Imagens/torre_pretas_quadrado_claro.jpg");
        repositorio_imagem.put("torre_pretas_quadrado_claro_grifada", "Imagens/torre_pretas_quadrado_claro_grifada.jpg");
        repositorio_imagem.put("torre_pretas_quadrado_escuro", "Imagens/torre_pretas_quadrado_escuro.jpg");
        repositorio_imagem.put("torre_pretas_quadrado_escuro_grifada", "Imagens/torre_pretas_quadrado_escuro_grifada.jpg");

        repositorio_imagem.put("torre_brancas_quadrado_claro", "Imagens/torre_brancas_quadrado_claro.jpg");
        repositorio_imagem.put("torre_brancas_quadrado_claro_grifada", "Imagens/torre_brancas_quadrado_claro_grifada.jpg");
        repositorio_imagem.put("torre_brancas_quadrado_escuro", "Imagens/torre_brancas_quadrado_escuro.jpg");
        repositorio_imagem.put("torre_brancas_quadrado_escuro_grifada", "Imagens/torre_brancas_quadrado_escuro_grifada.jpg");

        //peao
        repositorio_imagem.put("peao_pretas_quadrado_claro", "Imagens/peao_pretas_quadrado_claro.jpg");
        repositorio_imagem.put("peao_pretas_quadrado_claro_grifada", "Imagens/peao_pretas_quadrado_claro_grifada.jpg");
        repositorio_imagem.put("peao_pretas_quadrado_escuro", "Imagens/peao_pretas_quadrado_escuro.jpg");
        repositorio_imagem.put("peao_pretas_quadrado_escuro_grifada", "Imagens/peao_pretas_quadrado_escuro_grifada.jpg");

        repositorio_imagem.put("peao_brancas_quadrado_claro", "Imagens/peao_brancas_quadrado_claro.jpg");
        repositorio_imagem.put("peao_brancas_quadrado_claro_grifada", "Imagens/peao_brancas_quadrado_claro_grifada.jpg");
        repositorio_imagem.put("peao_brancas_quadrado_escuro", "Imagens/peao_brancas_quadrado_escuro.jpg");
        repositorio_imagem.put("peao_brancas_quadrado_escuro_grifada", "Imagens/peao_brancas_quadrado_escuro_grifada.jpg");
    }


    public String pegarCaminhoObjeto(String objeto) {
        return this.repositorio_imagem.get(objeto);
    }


}
