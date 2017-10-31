package br.ufrn.imd.lp2.analise_de_imagem;

import br.ufrn.imd.lp2.imagesegmentation.ImageInformation;
import br.ufrn.imd.lp2.imagesegmentation.ImageSegmentation;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Classe segmenta é responsável pelas funções de segmentação e visualização da
 * imagem.
 *
 * @author IvanAlisson e EstherBarbara
 */
public class Segmenta {

    ImageInformation seg;
    JLabel imagemSegmentada;
    int[] aux, markedPx;
    int white = 16777215; //Equivalente INT ao RGB (255,255,255)
    Color color;
    ArrayList<Integer> imgOriginal = new ArrayList<Integer>();

    /**
     * Realiza a segmentação da imagem.
     *
     * @param path diretorio em que imagem se encontra
     * @param parameters parametros de segmentação de imagem
     * @return
     */
    public JLabel segmentaAction(String path, double[] parameters) {
        seg = ImageSegmentation.performSegmentation(path, parameters);
        imagemSegmentada = new JLabel(new ImageIcon(seg.getRegionMarkedImage()));
        
        imgOriginal.clear();
        makeCopyImgOriginal(); //Cria cópia da imagem original;
        
        return imagemSegmentada;
    }

    /**
     * @return o mapa de rótulos de uma imagem segmentada.
     */
    public JLabel getImgMapRotulo() {
        BufferedImage map = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_USHORT_GRAY);
        aux = getSegmentedImageMap();

        for (int i = 0; i < aux.length; i++) {
            aux[i] = white / (aux[i] + 1);
        }

        map.setRGB(0, 0, getWidth(), getHeight(), aux, 0, getWidth());

        return (new JLabel(new ImageIcon(map)));
    }

    public JLabel escureceImagem(ArrayList<Integer> rSelecionadas) {
        BufferedImage map = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        aux = getSegmentedImageMap();//Zonas 0-N
        markedPx = new int[imgOriginal.size()];
        
        for ( int i = 0; i < imgOriginal.size(); i++){
            markedPx[i] = imgOriginal.get(i);
        }
        

        for (int i = 0; i < aux.length; i++) {
            for (int j = 0; j < rSelecionadas.size(); j++) {
                color = new Color(markedPx[i], true);
                if (aux[i] != rSelecionadas.get(j)) {
                    color = new Color(color.getRed() / 2, color.getGreen() / 2, color.getBlue() / 2); // Escurece a Imagem em 75%   
                } else {
                    j += rSelecionadas.size();
                }
            }
            markedPx[i] = color.getRGB();
        }
        

        map.setRGB(0, 0, getWidth(), getHeight(), markedPx, 0, getWidth());
        return (new JLabel(new ImageIcon(map)));
    }

    /**
     * @return o total de regiões geradas pela segmentação
     */
    public int getTotalRegions() {
        return seg.getTotalRegions();
    }

    /**
     * @return mapa de regiões da segmentação[0-N]
     */
    public int[] getSegmentedImageMap() {
        return seg.getSegmentedImageMap();
    }

    /**
     *
     * @return os pixels RGB da imagem segmentada
     */
    public int[] getRegionMarkedPixels() {
        return seg.getRegionMarkedPixels();
    }

    /**
     *
     * @return width da imagem
     */
    public int getWidth() {
        return seg.getWidth();
    }

    /**
     * @return height da imagem
     */
    public int getHeight() {
        return seg.getHeight();
    }

    /**
     * @return imagem original
     */
    public BufferedImage getOrImage() {
        return seg.getOriginalImage();
    }

    /**
     * @return a imagem segmentada
     */
    public BufferedImage getRMarkedImage() {
        return seg.getRegionMarkedImage();
    }

    public int[] markedSalva() {
        return getRegionMarkedPixels();
    }
    
    private void makeCopyImgOriginal(){
        markedPx = getRegionMarkedPixels();
        for ( int i = 0; i < markedPx.length; i++ ){
            imgOriginal.add(markedPx[i]);
        }
    }
}
