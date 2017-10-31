/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desafioaula17;

import java.util.Scanner;

/**
 *
 * @author IvanAlisson
 */
public class DesafioAula17 {
    
    private int numDecendentes;
    private int numParticipantes;

    public DesafioAula17(int numDecendentes, int numParticipantes) {
        this.numDecendentes = numDecendentes;
        this.numParticipantes = numParticipantes;
    }

    public int getNumDecendentes() {
        return numDecendentes;
    }

    public void setNumDecendentes(int numDecendentes) {
        this.numDecendentes = numDecendentes;
    }

    public int getNumParticipantes() {
        return numParticipantes;
    }

    public void setNumParticipantes(int numParticipantes) {
        this.numParticipantes = numParticipantes;
    }

    public static void main(String[] args) {
        // TODO code application logic here
        int dec, part, idade, id;
        String nome;
        
        // Entrada de decendentes e participantes.
        System.out.println("Digite o Numero de decendetes");
        Scanner scan = new Scanner(System.in);
        dec = scan.nextInt();
        System.out.println("Digite o Numero de participantes");
        part = scan.nextInt();
        
        DesafioAula17 teste = new DesafioAula17(dec, part);
        
        // Entrada de nome e idade do Rei
        nome = scan.next();
        idade = scan.nextInt();
        
        //Entrada do nome e idade dos decendentes do Rei;
        
        //Entrada de relação parental
        
        //Entrada de decendentes qeu compareceram.
    }
    
}
