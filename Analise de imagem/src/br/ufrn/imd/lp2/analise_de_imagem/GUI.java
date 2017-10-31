package br.ufrn.imd.lp2.analise_de_imagem;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;

/**
 * Classe GUI responsável pela criação da interface gráfica com sliders, buttons
 * e images.
 *
 * @author IvanAlisson e EstherBarbara
 */
public final class GUI {

    private JFrame frame;
    private JLabel picLabel, filenameLabel, statusLabel, blurLabel, colorLabel, sizeLabel;
    private JPanel segToolbar, noteToolbar, contentPanel, searchToolbar;
    private JButton refatorarButton, mapaButton, noteButton, plusButton, newMapButton, backButton;
    private JSlider blurSlider, colorSlider, sizeSlider;
    private JTextArea showNotes;
    private JTextField enterNote;
    private JMenuItem abrir;
    private JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
    
    private PrefixTree trie = new PrefixTree();

    //Valores padrão
    private final double[] parameters = {5, 50, 500};

    //Imagem a ser trabalhada
    private String img;

    private final Segmenta segmenta = new Segmenta();
    private final ImageFileIO imagefileio = new ImageFileIO();

    private final ArrayList<Integer> regClicadas = new ArrayList<>();

    /**
     * Construtor da classe GUI
     */
    public GUI() {
        makeFrame();
        SaveNote.bufferedReaderInputTree(trie);
    }

    /**
     * Configura o frame da aplicação, também configurando seus painéis
     * interiores.
     */
    public void makeFrame() {
        frame = new JFrame("Analisador de imagem");
        setFramePanel();

        // building is done - arrange the components      
        showFilename();
        setSegButtonsEnabled(false);
        setMapButtonsEnabled(false);
        setNoteButtonsEnabled(false);
        frame.pack();

        // Show Frame
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Configura o painel principal do frame para que contenha a imagem, info
     * sobre a segmentação e painel dos sliders e buttons panel.
     */
    private void setFramePanel() {
        contentPanel = (JPanel) frame.getContentPane();
        contentPanel.setBorder(new EmptyBorder(12, 12, 12, 12));

        makeMenuBar();

        // Specify the layout manager with nice spacing
        contentPanel.setLayout(new BorderLayout(6, 6));
        // Create the image pane in the center
        picLabel = new JLabel();
        contentPanel.add(picLabel, BorderLayout.CENTER);
        // Create two labels at top and bottom for the file name and status messages
        filenameLabel = new JLabel();
        contentPanel.add(filenameLabel, BorderLayout.SOUTH);

        statusLabel = new JLabel("Imagem não segmentada");
        contentPanel.add(statusLabel, BorderLayout.NORTH);

        createButtonsAndSlidersToolbar();
    }

    /**
     * Cria barra de menus com as opções abrir arquivo e fechar aplicação.
     */
    private void makeMenuBar() {
        final int SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);

        JMenu menu;
        JMenu pesquisa;
        JMenuItem item, buscar;

        menu = new JMenu("Arquivo");
        menubar.add(menu);
        pesquisa = new JMenu("Pesquisa");
        menubar.add(pesquisa);

        // create the Open menu
        abrir = new JMenuItem("Abrir");
        abrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, SHORTCUT_MASK));
        abrir.addActionListener((ActionEvent e) -> {
            try {
                statusLabel.setText("Imagem não segmentada");
                frame.remove(picLabel);
                backButton.doClick();
                int returnVal = fileChooser.showOpenDialog(frame);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    if (!open(selectedFile)) {
                        JOptionPane.showMessageDialog(frame,
                            "The file was not in a recognized image file format.",
                            "Image Load Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        menu.add(abrir);

        item = new JMenuItem("Sair");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
        item.addActionListener((ActionEvent e) -> {
            quit();
        });
        menu.add(item);

        // create the Search menu
        buscar = new JMenuItem("Buscar");
        buscar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, SHORTCUT_MASK));
        buscar.addActionListener((ActionEvent e) -> {
            segToolbar.setVisible(false);
            filenameLabel.setVisible(false);
            statusLabel.setVisible(false);

            noteToolbar.setVisible(false);
            picLabel.setVisible(false);

            abrir.setEnabled(false);
            setSearchPanel();
            frame.pack();
        });
        pesquisa.add(buscar);
    }

    /**
     * Create the toolbar with the buttons and Sliders
     *
     * @param contentPanel contentPanel must contain the created toolbar panel
     */
    private void createButtonsAndSlidersToolbar() {
        segToolbar = new JPanel();
        noteToolbar = new JPanel();
        segToolbar.setLayout(new GridLayout(0, 1, 0, 8));
        noteToolbar.setLayout(new GridLayout(0, 1, 0, 10));

        //Create Sliders and buttons
        setSliders();
        setSegAndMapButtons();
        setNoteButtom();
        createNotesSpace();

        // Add toolbar into panel with flow layout for spacing
        JPanel segFlow = new JPanel();
        JPanel noteFlow = new JPanel();
        segFlow.add(segToolbar);
        noteFlow.add(noteToolbar);
        contentPanel.add(segFlow, BorderLayout.WEST);
        contentPanel.add(noteFlow, BorderLayout.EAST);
    }

    /**
     * Seta os parametros dos sliders.
     *
     * @param toolbar a qual os sliders pertencem
     */
    private void setSliders() {
        blurLabel = new JLabel("Blur Level: ");
        blurSlider = new JSlider(1, 200);
        makeSliders(segToolbar, blurLabel, blurSlider, 10, 50, 50);

        colorLabel = new JLabel("Color Radius: ");
        colorSlider = new JSlider(0, 100);
        makeSliders(segToolbar, colorLabel, colorSlider, 5, 10, 50);

        sizeLabel = new JLabel("Min Size: ");
        sizeSlider = new JSlider(0, 2000);
        makeSliders(segToolbar, sizeLabel, sizeSlider, 100, 500, 500);
    }

    /**
     * Configura um slider.
     *
     * @param toolbar panel qual slider deve pertencer
     * @param configLabel label com o nome do slider
     * @param slider a ser configurado
     * @param min minorTickSpacing
     * @param maj majorTickSpacing
     * @param qua
     */
    private void makeSliders(JPanel toolbar, JLabel label, JSlider slider, int min, int maj, int qua) {
        //slider.set
        slider.setPaintTicks(true);
        slider.setMinorTickSpacing(min);
        slider.setMajorTickSpacing(maj);
        slider.setPaintLabels(true);
        slider.setLabelTable(slider.createStandardLabels(qua));

        String title = label.getText();
        label.setText(title + slider.getValue());
        toolbar.add(label);
        toolbar.add(slider);

        slider.addChangeListener((ChangeEvent ce) -> {
            label.setText(title + slider.getValue());
        });
    }

    /**
     * Seta os parametros e funcionalidades dos buttons de segmentação e
     * exibição de mapa de rótulos
     *
     * @param toolbar a qual os buttons pertencem
     */
    private void setSegAndMapButtons() {
        refatorarButton = new JButton("Segmentar");

        refatorarButton.addActionListener((ActionEvent e) -> {
            segmenta();
            setNewMapButtonsEnabled(true);
        });
        segToolbar.add(refatorarButton);

        mapaButton = new JButton("Mapa de rótulos");
        mapaButton.addActionListener((ActionEvent e) -> {
            mapaDeRotulos();
            setMapButtonsEnabled(false);
            setNewMapButtonsEnabled(false);
        });
        segToolbar.add(mapaButton);
    }

    /**
     * Seta os parametros e funcionalidades do buttom de notas
     *
     * @param segToolbar a qual os buttons da segmentação pertencem
     * @param noteToolbar a qual os buttons da anotação pertencem
     * @param contentPanel cujo deve ser criado e exibido no clique do botao
     */
    private void setNoteButtom() {
        noteButton = new JButton("Anotações");
        noteButton.addActionListener((ActionEvent e) -> {
            segToolbar.setVisible(false);
            noteToolbar.setVisible(true);
            frame.pack();
        });
        segToolbar.add(noteButton);
    }

    /**
     * Área de anotações
     */
    private void createNotesSpace() {
        //Cria Panel com a área de anotações
        JPanel notesBox = new JPanel();
        notesBox.setLayout(new BoxLayout(notesBox, BoxLayout.PAGE_AXIS));
        //Instancia textboxes e adiciona a Panel de anotações
        showNotes = new JTextArea();
        showNotes.setEditable(false);
        showNotes.setColumns(15);
        showNotes.setRows(6);
        notesBox.add(showNotes);

        enterNote = new JTextField();
        notesBox.add(enterNote);

        noteToolbar.add(notesBox);
        //Cria botões 
        JPanel noteButtonToolbar = new JPanel();
        noteButtonToolbar.setLayout(new BoxLayout(noteButtonToolbar, BoxLayout.PAGE_AXIS));

        setPlusButtom(noteButtonToolbar);
        setSelectAreaAndNewMapAndBackButtons(noteButtonToolbar);
        noteToolbar.setVisible(false);
    }

    /**
     * Seta os parametros e funcionalidades do buttom de adicionar uma nota
     *
     * @param noteButtonToolbar qual o buttom deve ser adicionado
     */
    private void setPlusButtom(JPanel noteButtonToolbar) {
        JPanel plusButtonToolbar = new JPanel();
        plusButtonToolbar.setLayout(new BoxLayout(plusButtonToolbar, BoxLayout.PAGE_AXIS));

        plusButton = new JButton("    +    ");
        plusButton.addActionListener((ActionEvent e) -> {
            addNote();
            showNote();
            trie.put(enterNote.getText(), filenameLabel.getText());
            frame.pack();
        });
        plusButtonToolbar.add(plusButton);
        noteButtonToolbar.add(plusButtonToolbar);

        plusButtonToolbar.add(Box.createVerticalStrut(50));

        plusButtonToolbar.add(Box.createVerticalStrut(50));
    }

    /**
     * Seta os parametros e funcionalidades do buttom de
     *
     * @param noteButtonToolbar qual os buttons devem ser adicionados
     */
    private void setSelectAreaAndNewMapAndBackButtons(JPanel noteButtonToolbar) {
        newMapButton = new JButton("Mapa de rótulos");
        newMapButton.addActionListener((ActionEvent e) -> {
            mapaDeRotulos();
            setMapButtonsEnabled(false);
            setNewMapButtonsEnabled(false);
        });
        noteButtonToolbar.add(newMapButton);

        backButton = new JButton("Voltar");
        backButton.addActionListener((ActionEvent e) -> {
            noteToolbar.setVisible(false);
            segToolbar.setVisible(true);
            frame.pack();
        });
        noteButtonToolbar.add(backButton);
        noteToolbar.add(noteButtonToolbar);
    }

    /**
     * Cria o Panel de busca
     */
    private void setSearchPanel() {

        searchToolbar = new JPanel();
        searchToolbar.setBorder(new EmptyBorder(10, 10, 10, 10));
        // Specify the layout manager with nice spacing
        searchToolbar.setLayout(new GridLayout(0, 1, 0, 8));//(new FlowLayout(12));

        JLabel label = new JLabel("Busca:");
        searchToolbar.add(label, BorderLayout.NORTH);
        
        String[] entradas = SaveNote.retornaEntradas(img);
        JComboBox combo = new JComboBox(entradas);
        combo.setEditable(true);
        AutoCompletion.enable(combo);

        searchToolbar.add(combo, BorderLayout.CENTER);
        JButton returnButton = new JButton("Voltar");
        returnButton.addActionListener((ActionEvent e) -> {
            searchToolbar.setVisible(false);

            segToolbar.setVisible(true);
            statusLabel.setVisible(true);
            filenameLabel.setVisible(true);
            abrir.setEnabled(true);

            picLabel.setVisible(true);
            frame.add(picLabel);
            frame.pack();
        });
        
        JButton search = new JButton("Procurar");
        search.addActionListener((ActionEvent e) -> {
            Set<String> files = trie.get((String)combo.getSelectedItem());
            JPanel list = new JPanel();
            list.setLayout(new BoxLayout(list, BoxLayout.PAGE_AXIS));
            for (String filename : files) {
                
                JLabel nome = new JLabel(filename + " (tag: " + (String)combo.getSelectedItem() + " )");
                nome.addMouseListener(new MouseListener() {
                    boolean mouseOnLabel;
                    
                    @Override
                    public void mouseClicked(MouseEvent me) {
                        if (mouseOnLabel) {
                            list.setVisible(false);
                            try {
                                //frame.remove(picLabel);
                                //frame.pack();
                                returnButton.doClick();
                                open(new File(filename));
                            } catch (IOException e) {
                                JOptionPane.showMessageDialog(frame,
                                    "The file could not be loaded.",
                                    "Image Load Error",
                                    JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent me) {
                    }

                    @Override
                    public void mouseReleased(MouseEvent me) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent me) {
                        mouseOnLabel = true;
                    }

                    @Override
                    public void mouseExited(MouseEvent me) {
                        mouseOnLabel = false;
                    }
                    
                });
                
                list.add(nome);
            }
            searchToolbar.add(list);
            frame.pack();
        });
        searchToolbar.add(search, BorderLayout.EAST);

        searchToolbar.add(Box.createVerticalStrut(15));

        
        searchToolbar.add(returnButton, BorderLayout.SOUTH);

        searchToolbar.setVisible(true);

        contentPanel.add(searchToolbar);
    }

    /**
     * Verifica se o botão de segmentar pode ser clicado.
     */
    private void setSegButtonsEnabled(boolean status) {
        refatorarButton.setEnabled(status);
    }

    /**
     * Verifica se o botão mapa de rotulos pode ser clicado.
     */
    private void setMapButtonsEnabled(boolean status) {
        mapaButton.setEnabled(status);
    }

    /**
     * Verifica se o botão que mostra aba de anotações pode ser clicado.
     */
    private void setNoteButtonsEnabled(boolean status) {
        noteButton.setEnabled(status);
    }

    /**
     * Verifica se o botão que exibe o mapa de rótulos pode ser clicado.
     */
    private void setNewMapButtonsEnabled(boolean status) {
        newMapButton.setEnabled(status);
    }

    /**
     * Abre uma imagem.
     *
     * @throws java.io.IOException
     */
    private boolean open(File selectedFile) throws IOException {
        if (imagefileio.openImage(selectedFile, frame, picLabel)) {
            frame.remove(picLabel);
            picLabel = imagefileio.getImage();
            setMapButtonsEnabled(false);
            setSegButtonsEnabled(true);
            setNoteButtonsEnabled(false);

            img = imagefileio.getName();
            showFilename();
            frame.add(picLabel);
            frame.pack();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Realiza a ação de segmentação de uma imagem dados seus parametros de
     * segmentação.
     */
    private void segmenta() {
        regClicadas.clear();
        parameters[0] = (double) (blurSlider.getValue()) / 100;
        parameters[1] = colorSlider.getValue();
        parameters[2] = sizeSlider.getValue();
        frame.remove(picLabel);

        picLabel = segmenta.segmentaAction(img, parameters);
        statusLabel.setText("Total de regiões: " + segmenta.getTotalRegions());
        statusLabel.setVisible(true);

        picLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                regClicadas.add(MouseTracker.retornaRegiao(e.getX(), e.getY(), segmenta.getWidth(), segmenta.getSegmentedImageMap()));
                selectArea(regClicadas);
            }
        });
        picLabel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                //.println(e.getX() + " " + e.getY());
            }
        });

        frame.add(picLabel);
        frame.pack();

        //habilita botao de geração de mapa de rótulos
        setMapButtonsEnabled(true);
        setNoteButtonsEnabled(true);
        showNote();
    }

    /**
     * Adiciona uma nota a um arquivo
     */
    private void addNote() {
        SaveNote.bufferedWriter(img, enterNote.getText());
        enterNote.setText("");
    }

    /**
     * Mostra as anotações salvas em arquivo de texto na tela.
     */
    private void showNote() {
        showNotes.setText(SaveNote.bufferedReader(img));
    }

    /**
     * Mostra o mapa de rótulos da imagem segmentada.
     */
    private void mapaDeRotulos() {
        frame.remove(picLabel);
        picLabel = segmenta.getImgMapRotulo();
        frame.add(picLabel);
        frame.pack();
    }

    /**
     * Mostra o nome da imagem aberta pela aplicação.
     */
    private void showFilename() {
        if (img == null) {
            filenameLabel.setText("Sem imagem.");
        } else {
            filenameLabel.setText("Arquivo: " + img);
        }
    }

    /**
     * Finaliza a execução da aplicação.
     */
    private void quit() {
        System.exit(0);
    }

    /**
     * Seleciona uma área a ser mostrada em destaque.
     *
     * @param rSelecionadas identificação da área.
     */
    private void selectArea(ArrayList<Integer> rSelecionadas) {
        frame.remove(picLabel);
        picLabel = segmenta.escureceImagem(rSelecionadas);
        picLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                regClicadas.add(MouseTracker.retornaRegiao(e.getX(), e.getY(), segmenta.getWidth(), segmenta.getSegmentedImageMap()));
                selectArea(regClicadas);
            }
        });
        frame.add(picLabel);
        frame.pack();

    }

}
