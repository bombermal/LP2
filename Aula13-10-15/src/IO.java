
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author IvanAlisson
 */
public class IO {

    public void leitura(String nome) {
        String[] parts;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(nome));
            String linha = reader.readLine();
            while (linha != null) {
                if (linha.length() !=3 ){
                    System.out.println("Não foi possivel calcular");
                } else {
                    if (linha.contains("+")){
                        parts = linha.split(Pattern.quote("+"));
                        System.out.println(Integer.parseInt(parts[0])+Integer.parseInt(parts[1]));
                    } else if (linha.contains("-")){
                        parts = linha.split(Pattern.quote("-"));
                        System.out.println(Integer.parseInt(parts[0])-Integer.parseInt(parts[1]));
                    } else if (linha.contains("*")){
                        parts = linha.split(Pattern.quote("*"));
                        System.out.println(Integer.parseInt(parts[0])*Integer.parseInt(parts[1]));
                    } else if (linha.contains("/")){
                        parts = linha.split(Pattern.quote("/"));
                        if (parts[1].equals("0")){
                            System.out.println("Não foi possivel calcular");
                        } else {
                          System.out.println(Integer.parseInt(parts[0])/Integer.parseInt(parts[1]));  
                        }
                    }
                   
                }
                linha = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
        } catch (IOException e) {
            System.out.println("Não foi possovel ler o arquivo");
        }
    }
    

    public void escrita(String nome, String saida) {
        int tamanho = saida.length();
        try {
            FileWriter writer = new FileWriter(nome);
            while (tamanho >0) {
                writer.write(saida);
                tamanho--;
            }
        } catch (IOException e) {
            System.out.println("Não foi possivel escrever no arquivo");
        }
    }


    }
