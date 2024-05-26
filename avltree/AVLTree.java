/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package avltree;
import Actividades.ItemDuplicated;
import Actividades.ItemNoFound;

/**
 *
 * @author Miguel
 */
public class AVLTree <E extends Comparable<E>> extends BSTree<E>{
    class NodeAVL extends Node{
        protected int bf;
        public NodeAVL(E data) {
            super(data);
        }

        @Override
        public String toString() {
            return "NodeAVL{" + "data: " + data.toString() + ", bf: " + bf + '}';
        }
    }
    private boolean height;
    
    public void insert(E x) throws ItemDuplicated{
        this.height = false;
        this.root = insert(x, (NodeAVL)this.root);
    }
    
    protected Node insert(E x, NodeAVL node) throws ItemDuplicated{
        NodeAVL fat = node;
        if(node == null){
            this.height = true;
            fat = new NodeAVL(x);
        }else{
            int resC = ((E)node.data).compareTo(x);
            if(resC == 0){
                throw new ItemDuplicated (x + " ya se encuentra en el arbol");
            }
            if(resC < 0){
                fat.right = insert(x, (NodeAVL)node.right);
                if(this.height){
                    switch(fat.bf){
                        case -1:
                            fat.bf = 0;
                            this.height = false;
                            break;
                        case 0:
                            fat.bf = 1;
                            this.height = true;
                            break;
                        case 1:
                            fat = balanceToLeft(fat);
                            this.height = false;
                            break;
                    }
                }
            }else{
                fat.left = insert(x, (NodeAVL)node.left);
                if(this.height){
                    switch(fat.bf){
                        case -1:
                            fat = balanceToRight(fat);
                            this.height = false;
                            break;
                        case 0:
                            fat.bf = -1;
                            this.height = true;
                            break;
                        case 1:
                            fat.bf = 0;
                            this.height = false;
                            break;
                    }
                }
            }
        }
        return fat;
    }
    
    public void delete(E x) {
        if (root == null) {
            System.out.println("El arbol esta vacio");
        } else {
            try {
                root = delete(x, (NodeAVL) root);
            } catch (ItemNoFound e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private NodeAVL delete(E x, NodeAVL actual) throws ItemNoFound {
        NodeAVL fat = actual;
        if (actual == null) {
            throw new ItemNoFound("Item no encontrado");
        } else {
            int resC = ((E)actual.data).compareTo(x);
            if (resC == 0) {
                fat = deleteNode(actual);
                this.height = true;
            } else if (resC < 0) {
                fat.right = delete(x, (NodeAVL) actual.right);
                if (this.height) {
                    switch (fat.bf) {
                        case -1:
                            fat = balanceToRight(fat);
                            this.height = false;
                            break;
                        case 0:
                            fat.bf = -1;
                            this.height = true;
                            break;
                        case 1:
                            fat.bf = 0;
                            this.height = false;
                            break;
                    }
                }
            } else {
                fat.left = delete(x, (NodeAVL) actual.left);
                if (this.height) {
                    switch (fat.bf) {
                        case 1:
                            fat = balanceToLeft(fat);
                            this.height = false;
                            break;
                        case 0:
                            fat.bf = 1;
                            this.height = true;
                            break;
                        case -1:
                            fat.bf = 0;
                            this.height = false;
                            break;
                    }
                }
            }
        }
        return fat;
    }

    private NodeAVL deleteNode(NodeAVL node) throws ItemNoFound {
        if (node.left == null && node.right == null) {
            return null;
        } else if (node.left == null) {
            return (NodeAVL) node.right;
        } else if (node.right == null) {
            return (NodeAVL) node.left;
        } else {
            NodeAVL successor = findSuccessor((NodeAVL) node.right);
            node.data = successor.data;
            node.right = delete((E)successor.data, (NodeAVL) node.right);
            return node;
        }
    }

    private NodeAVL findSuccessor(NodeAVL node) {
        if (node.left == null) {
            return node;
        } else {
            return findSuccessor((NodeAVL) node.left);
        }
    }
    
    private NodeAVL balanceToLeft(NodeAVL node){
        NodeAVL hijo = (NodeAVL)node.right;
        switch(hijo.bf) {
            case 1:
                node.bf = 0;
                hijo.bf = 0;
                node = rotateSL(node);
                break;
            case -1:
                NodeAVL nieto = (NodeAVL)hijo.left;
                switch(nieto.bf) {
                    case -1: node.bf = 0; hijo.bf = 1; break;
                    case 0: node.bf = 0; hijo.bf = 0; break;
                    case 1: node.bf = 1; hijo.bf = 0; break;
                }
                nieto.bf =0;
                node.right = rotateSR(hijo);
                node = rotateSL(node);
        }
        return node;
    }
    
    private NodeAVL balanceToRight(NodeAVL node){
        NodeAVL hijo = (NodeAVL)node.left;
        switch(hijo.bf) {
            case -1:
                node.bf = 0;
                hijo.bf = 0;
                node = rotateSR(node);
                break;
            case 1:
                NodeAVL nieto = (NodeAVL)hijo.right;
                switch(nieto.bf) {
                    case -1: 
                        node.bf = 0; 
                        hijo.bf = 1; 
                        break;
                    case 0: 
                        node.bf = 0; 
                        hijo.bf = 0; 
                        break;
                    case 1: 
                        node.bf = 1; 
                        hijo.bf = 0; 
                        break;
                }
                nieto.bf =0;
                node.left = rotateSL(hijo);
                node = rotateSR(node);
        }
        return node;
    }

    private NodeAVL rotateSL(NodeAVL node){
        NodeAVL p = (NodeAVL)node.right;
        node.right = p.left;
        p.left = node;
        node = p;
        return node;
    }

    private NodeAVL rotateSR(NodeAVL node){
        NodeAVL p = (NodeAVL)node.left;
        node.left = p.right;
        p.right = node;
        node = p;
        return node;
    }
        
    public void recorrerAmplitud() {
        if(root == null){
            System.out.println("El arbol esta vacio");
            return;
        }
        int tamano = altura((NodeAVL) root);
        for (int i = 0; i <= tamano; i++) {
            recorrerAmplitudRecursivo((NodeAVL) root, i);
        }
        System.out.println("");
    }

    private void recorrerAmplitudRecursivo(NodeAVL node, int nivel) {
        if (node == null) {
            return;
        }
        if (nivel == 0) {
            System.out.print(node.data + " ");
        } else if (nivel > 0) {
            recorrerAmplitudRecursivo((NodeAVL) node.left, nivel - 1);
            recorrerAmplitudRecursivo((NodeAVL) node.right, nivel - 1);
        }
    }

    private int altura(NodeAVL node) {
        if (node == null) {
            return -1;
        } else {
            int izquierda = altura((NodeAVL) node.left);
            int derecha = altura((NodeAVL) node.right);
            return Math.max(izquierda, derecha) + 1;
        }
    }
    
    public void llamarNodos() {
        printNodos((NodeAVL) this.root);
    }

    private void printNodos(NodeAVL node) {
        if (node != null) {
            printNodos((NodeAVL) node.right);
            printNodos((NodeAVL) node.left);

            System.out.println(node.toString());
        }
    }
}
