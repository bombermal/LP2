/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desafioaula17;

/**
 *
 * @author IvanAlisson
 */
public abstract class Node {

    private int indice;
    private Node esquerda = null;
    private Node direita = null;

    public Node(int indice) {
        this.indice = indice;
    }
   
    
}
