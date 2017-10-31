package br.ufrn.imd.lp2.analise_de_imagem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Classe SaveNote responsável por
 *
 * @author IvanAlisson and EstherBarbara
 */
public class SaveNote {

    static public void bufferedWriter(String fileName, String note) {
        try {
            FileWriter writer = new FileWriter("Tags.txt", true);
            writer.write("@" + fileName + ":" + note);
            writer.close();
        } catch (IOException e) {
            System.out.println("something went wrong");
        }
    }
    
    /**
     * Le de um arquivo
     * @param fileName
     * @return 
     */
    @SuppressWarnings("empty-statement")
    static public String bufferedReader(String fileName) {
        //String[] aux = fileName.split("/", 2);
        try {
            String linha;
            try (BufferedReader reader = new BufferedReader(new FileReader("Tags.txt"))) {
                linha = "";
                while (reader.ready()) {
                    linha = linha + reader.readLine();
                };
            }
            linha = linha.replace("@img/", "\r\n");
            return linha;
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        } catch (IOException e) {
            System.out.println("wrong IO");
        }
        return null;
    }
    
    /**
     * Le de um arquivo e insere tags e nome de arquivo na arvore
     * @param trie 
     */
    @SuppressWarnings("empty-statement")
    static public void bufferedReaderInputTree(PrefixTree trie) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Tags.txt"));
            String linha = "";
            while (reader.ready()) {
                linha = linha + reader.readLine();
            };
            reader.close();
            
            String[] aux = linha.split("@");
            for(int i=1; i<aux.length; i++)
            {
                String[] aux2 = aux[i].split(":");
                trie.put(aux2[1], aux2[0]);
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        } catch (IOException e) {
            System.out.println("wrong IO");
        }
    }

    static public String[] retornaEntradas(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Tags.txt"));
            String linha = "";
            while (reader.ready()) {
                linha = linha + reader.readLine();
            };
            reader.close();

            String[] aux = linha.split("@");
            String[] tags = new String[aux.length-1];
            for (int i=1 ; i<aux.length; i++){
                String[] aux2 = aux[i].split(":");
                tags[i-1] = aux2[1];
            }

            return tags;
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        } catch (IOException e) {
            System.out.println("wrong IO");
        }
        return null;
    }
}
