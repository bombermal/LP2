package br.ufrn.imd.lp2.analise_de_imagem;

/**
 * Classe responsável por retornar uma região dada coordenadas do mouse
 * @author Ivan Alisson e EstherBarbara
 */
public class MouseTracker {

    /**
     * Recebe as coordenadas do mouse e retonam o indice da região da imagem
     *
     * @param cordX mouse X
     * @param cordY mouse Y
     * @param width largura da imagem
     * @param aux vetor que possui as regiões correspondentes a cada pixel da
     * imgem.
     * @return indice do vetor aux
     */
    static public int retornaRegiao(int cordX, int cordY, int width,  int[] aux) {
        //cordY += 1;
        //cordX += 1;
        if (((cordY*width) + cordX) > aux.length) {
            return 0;
        }
        return aux[(cordY*width) + cordX];
    }
}
