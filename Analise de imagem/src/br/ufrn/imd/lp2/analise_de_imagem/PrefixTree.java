package br.ufrn.imd.lp2.analise_de_imagem;

import java.util.TreeSet;

/**
 * Classe PrefixTree implementa uma árvore de prefixos
 * 
 * @author IvanAlisson and EstherBarbara
 */
public class PrefixTree {
    private static final char[] alphabet = "abcdefghijklmnopqrstuvwxyz0123456789 ".toCharArray();
    
    private class Node {
        public TreeSet<String> fileList;
        public Node[] child;
        public boolean isTerminal;
        
        public Node() {
            child = new Node[alphabet.length];
            isTerminal = false;
        }
    }
    
    private Node root;
    
    public PrefixTree() {
        root = new Node();
    }
    
    public void put(String str, String filename) {
        put(root, str.toCharArray(), filename, 0);
    }
    
    private void put(Node node, char[] str, String filename, int currPos) {
        if (currPos == str.length) {
            node.isTerminal = true;
            if (node.fileList == null) {
                node.fileList = new TreeSet<String>();
            }
            node.fileList.add(filename);
        } else {
            int index;
            for (index = 0; index < alphabet.length; index++) {
                if (str[currPos] == alphabet[index]) {
                    break;
                }
            }

            if (index == alphabet.length) {
                return;
            } else {
                if (node.child[index] == null) {
                    node.child[index] = new Node();
                }
                
                put(node.child[index], str, filename, currPos+1);
            }
        }
    }
            
    public TreeSet<String> get(String str) {
        return get(root, str.toCharArray(), 0);
    }
    
    private TreeSet<String> get(Node node, char[] str, int currPos) {
        if (currPos == str.length) {
            return node.fileList;
        } else {
            int index;
            for (index = 0; index < alphabet.length; index++) {
                if (str[currPos] == alphabet[index]) {
                    break;
                }
            }

            if (index == alphabet.length || node.child[index] == null) {
                return null;
            } else {
                return get(node.child[index], str, currPos+1);
            }
        }
    }
}
