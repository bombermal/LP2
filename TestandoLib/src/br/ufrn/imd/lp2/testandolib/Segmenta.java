package br.ufrn.imd.lp2.testandolib;
import br.ufrn.imd.lp2.imagesegmentation.ImageInformation;
import br.ufrn.imd.lp2.imagesegmentation.ImageSegmentation;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 * Classe que usa a biblioteca de processamento de imagens ImageSegmentation para segmentar imagens
 * 
 * @author Esther Barbara
 * @data 15/10/2015
 */
public class Segmenta{
    
    //Imagem a ser trabalhada -> Ivan
    private String img = "imgs/imd.jpg";
    
    //Valores padrão -> Ivan
    private double BLUR_LEVEL = 0.5;
    private int COLOR_RADIUS = 50;
    private int MIN_SIZE = 500;
    
   
    // Segmentação com parâmetros 0.99, 50 e 1000
    ImageInformation seg = ImageSegmentation.performSegmentation(img, BLUR_LEVEL, COLOR_RADIUS, MIN_SIZE);// -> Ivan
    
    // Criação de um JFrame e inserção de 2 JLabels com cada uma das imagens.
    JFrame frame = new JFrame();
    
    //Criação menu de controle de parametros
    JPanel menu = new JPanel();
    
    //Imagem segmentada
    JLabel imagemSegmentada = new JLabel(new ImageIcon(seg.getRegionMarkedImage()));
    JLabel imagemFinal;
    
    //Sliders dos parametros
    JSlider colorRadiusSlider, blurLevelSlider, minSizeSlider;
    
    //Mostra a quantidade de regioes da imagem segmentada
    JLabel totalRegioesText;
    
    
    
    /**
     * Construtor da classe segmenta
     */
    public Segmenta(){
        frame.getContentPane().setLayout(new FlowLayout());
    }
    
    /**
     * Impressão na tela da quantidade de regiões resultantes da segmentação inicial
     */
    public void initialDefaultImagemSegmentation(){
        System.out.println("Total de regiões: " + seg.getTotalRegions());
        
        JLabel imagemOriginal = new JLabel(new ImageIcon(seg.getOriginalImage())); // Imagem original                
        frame.getContentPane().add(imagemOriginal);
        
        frame.getContentPane().add(imagemSegmentada);
    }
    
    /**
     * Cria slider do parametro blur level
     */
    public void createBlurLevelSlider(){
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        
        JPanel blurLevelSetting = new JPanel();
        blurLevelSetting.setLayout(new FlowLayout());
        
        JLabel colorRadiusText = new JLabel("Blur Level: ");
        blurLevelSetting.add(colorRadiusText);
        
        blurLevelSlider = new JSlider(0, 200);// No slide fala em trabalhar com valores Float, mas aqui usamos int, isso não seria um problema??
        blurLevelSlider.setPaintTicks(true);
        blurLevelSlider.setMinorTickSpacing(5);
        blurLevelSlider.setMajorTickSpacing(10);
        blurLevelSlider.setPaintLabels(true);
        blurLevelSlider.setLabelTable(blurLevelSlider.createStandardLabels(50));
        blurLevelSetting.add(blurLevelSlider);
        
        menu.add(blurLevelSetting);
        
        // separador de slider e slider
        menu.add(Box.createVerticalStrut(20));
    }
    
    /**
     * Cria slider do parametro color radius
     */
    public void createColorRadiusSlider(){
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        
        JPanel colorRadiusSetting = new JPanel();
        colorRadiusSetting.setLayout(new FlowLayout());
        
        JLabel colorRadiusText = new JLabel("Color Radius: ");
        colorRadiusSetting.add(colorRadiusText);
        
        colorRadiusSlider = new JSlider(0, 100);
        colorRadiusSlider.setPaintTicks(true);
        colorRadiusSlider.setMinorTickSpacing(5);
        colorRadiusSlider.setMajorTickSpacing(10);
        colorRadiusSlider.setPaintLabels(true);
        colorRadiusSlider.setLabelTable(colorRadiusSlider.createStandardLabels(25));
        colorRadiusSetting.add(colorRadiusSlider);
        
        menu.add(colorRadiusSetting);
        
        // separador de slider e slider
        menu.add(Box.createVerticalStrut(20));
    }
    
    /**
     * Cria slider do parametro minimun size
     */
    public void createMinSizeSlider(){
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        
        JPanel minSizeSetting = new JPanel();
        minSizeSetting.setLayout(new FlowLayout());
        
        JLabel colorRadiusText = new JLabel("MinSize: ");
        minSizeSetting.add(colorRadiusText);
        
        minSizeSlider = new JSlider(0, 2000);
        minSizeSlider.setPaintTicks(true);
        minSizeSlider.setMinorTickSpacing(50);
        minSizeSlider.setMajorTickSpacing(100);
        minSizeSlider.setPaintLabels(true);
        minSizeSlider.setLabelTable(minSizeSlider.createStandardLabels(500));
        minSizeSetting.add(minSizeSlider);
        
        menu.add(minSizeSetting);
        
        // separador de slider e text
        menu.add(Box.createVerticalStrut(20));
    }
    
    public void createText(){
        
        totalRegioesText = new JLabel("Total de regiões: " + seg.getTotalRegions());
        menu.add(totalRegioesText);
        
        // separador de text e button
        menu.add(Box.createVerticalStrut(20));
    }
    
    /**
     * Criacao do butão de refatoramento
     */
    public void createButtonRefatora(){
        JButton button1 = new JButton("Refatorar");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int blur = blurLevelSlider.getModel().getValue();
                int colorRadius = colorRadiusSlider.getModel().getValue();
                int minSize = minSizeSlider.getModel().getValue();
                
                // Segmentação com parâmetros 0.99, 40 e 1000
                ImageInformation seg = ImageSegmentation.performSegmentation(img, (double)blur/100, colorRadius, minSize);
                
                totalRegioesText.setText("Total de regiões: " + seg.getTotalRegions());
                
                imagemSegmentada.setIcon(new ImageIcon(seg.getRegionMarkedImage())); // Imagem segmentada
            } 
        });
        
        //posiciona botao no meio do eixo 
        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        menu.add(button1);
        
        // separador de button e button
        menu.add(Box.createVerticalStrut(20));
    }
    
    /**
     * Criacao do butão de refatoramento
     */
    public void createButtonMapaRotulos(){
        JButton button2 = new JButton("Mostrar mapa de rótulos");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               int[] a =  new int[100];

            } 
        });
        
        //posiciona botao no meio do eixo 
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        menu.add(button2);
        
    }
    
    public void setaFrame(){
        frame.getContentPane().add(menu);
        frame.pack();
        frame.setVisible(true);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        
        Segmenta seg = new Segmenta();
        seg.initialDefaultImagemSegmentation();
        
        seg.createBlurLevelSlider();
        seg.createColorRadiusSlider();
        seg.createMinSizeSlider();
        
        seg.createText();
        
        seg.createButtonRefatora();
        seg.createButtonMapaRotulos();
        seg.setaFrame();
    }
    
}
