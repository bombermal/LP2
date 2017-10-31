package br.ufrn.imd.lp2.analise_de_imagem;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * Classe ImageFileIO é responsável por realizar a abertura de uma imagem das imagens salvas em disco
 * @author IvanAlisson e EstherBarbara
 */
public class ImageFileIO {
    
    private String fileName;
    private JLabel image;

    /** Construtor da classe imagemFileIO.
     * @param frame a interface gráfica da aplicação a qual a abertura da imagem é associada.
     * @param picLabel
     * @return true se abriu corretamente uma imagem
     * @throws java.io.IOException 
     */
    public boolean openImage(File file, JFrame frame, JLabel picLabel) throws IOException{
        BufferedImage pic = javax.imageio.ImageIO.read(file);

        if (pic == null) {   // image file was not a valid image
            return false;
        }
        picLabel = new JLabel(new ImageIcon(pic));
        setName(file.getName());
        setImage(picLabel);
        
        return true;
    }

    /**
     * Seta a imagem
     * @param image imagem a ser atribuída ao atributo image
     */
    public void setImage(JLabel image){
        this.image = image;
    }

    /**
     * @return atributo imagem
     */
    public JLabel getImage(){
        return image;
    }
      
    /**
     * Seta o nome do arquivo.
     * @param fileName o nome qual deve ser atribuído ao atributo fileName
     */
    public void setName(String fileName){
        this.fileName = fileName;
    }
    
    /**
     * @return atributo nome
     */
    public String getName(){
        return "img/"+fileName;
    }
}
