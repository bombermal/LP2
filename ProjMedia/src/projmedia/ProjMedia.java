/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projmedia;

import java.util.Scanner;

/**
 *
 * @author IvanAlisson
 */
public class ProjMedia {

    private int nota1;
    private int nota2;
    private int nota3;


    public ProjMedia(int nota1, int nota2, int nota3) {
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.nota3 = nota3;
    }

    public double media() {
        return ((double) nota1 + (double) nota2 + (double) nota3) / 3.0;
    }

    public static void main(String[] args) {
        int n1, n2, n3;
        System.out.println("Digite a primeira nota");
        Scanner scan = new Scanner(System.in);
        n1 = scan.nextInt();
        System.out.println("Digite a segunda nota");
        n2 = scan.nextInt();
        System.out.println("Digite a terceira nota");
        n3 = scan.nextInt();
        ProjMedia med = new ProjMedia(n1, n2, n3);
        System.out.println(med.media());
    }

}
