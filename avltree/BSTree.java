/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package avltree;

import Actividades.ItemDuplicated;
import Actividades.ItemNoFound;
import Actividades.LinkBST;
import java.util.Stack;

/**
 *
 * @author Miguel
 */
public class BSTree<E extends Comparable<E>> implements LinkBST<E>{
    class Node<E>{
        protected E data;
        protected Node left, right;

        public Node(E data){
            this(data,null,null);
        }
        
        public Node(E data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }
    public Node root;
    public BSTree(){
        this.root = null;
    }
    
    @Override
    public void insert(E x) throws ItemDuplicated {
        Node<E> nodito = new Node<>(x);
        if (isEmpty()) {
            root = nodito;
        } else {
            Node<E> aux = root;
            Node<E> ant = null;
            while (aux != null) {
                ant = aux;
                if (x.compareTo(aux.data) < 0) {
                    aux = aux.left;
                } else {
                    aux = aux.right;
                }
            }
            if (x.compareTo(ant.data) < 0) {
                ant.left = nodito;
            } else {
                ant.right = nodito;
            }
        }
    }

    @Override
    public void remove(E x) {
        boolean isroot = true;
        if(root == null){
            return;
        }else if(x.compareTo((E)root.data) == 0){
           isroot = true;
        }else{
            boolean isleftchild = true;
            Node<E> padre = null;
            Node<E> aux = root;
            while(aux != null && x.compareTo(aux.data) != 0){
                padre = aux;
                if(x.compareTo(aux.data) > 0){
                    isleftchild = false;
                    aux = aux.right;
                }else{
                    isleftchild = true;
                    aux = aux.left;
                }
            }if(aux == null){
                return;
            // Caso 1
            }if(aux.left == null && aux.right == null){
                if(isleftchild){
                    padre.left = null;
                }else{
                    padre.right = null;
                }
            // Caso 2
            }if(aux.left == null){
                if(isleftchild){
                    padre.left = aux.right;
                }else{
                    padre.right = aux.right;
                }
            }else if(aux.right == null){
                if(isleftchild){
                    padre.left = aux.left;
                }else{
                    padre.right = aux.left;
                }
            // Caso 3
            }else{
                Node<E> nodito = subArbol(aux);
                aux.data = nodito.data;
                nodito.left = aux.left;
                nodito.right = aux.right;
                if(isleftchild){
                    padre.left = nodito;
                }else{
                    padre.right = nodito;
                }
            }
        }
    }
    
    public Node<E> subArbol(Node<E> n){
        Node<E> padre = n;
        Node<E> aux = n;
        aux = aux.right;
        while(aux.left != null){
            padre = aux;
            aux = aux.left;
        }
        padre.left = aux.right;
        return aux;
    }
    
    private Node<E> minRemove() throws ItemNoFound {
        if (root == null) {
            throw new ItemNoFound("El árbol está vacio");
        }

        Node<E> parent = null;
        Node<E> aux = root;

        while (aux.left != null) {
            parent = aux;
            aux = aux.left;
        }

        if (parent == null) {
            root = root.right;
        } else {
            parent.left = aux.right;
        }

        return aux;
    }

    @Override
    public E search(E x) throws ItemNoFound {
        E val = null;
        if (isEmpty()) {
            throw new ItemNoFound("Lista vacía");
        } else {
            Node<E> aux = root;
            while (aux != null) {
                if (x.compareTo(aux.data) == 0) {
                    val = aux.data;
                    return val;
                } else if (x.compareTo(aux.data) < 0) {
                    aux = aux.left;
                } else {
                    aux = aux.right;
                }
            }
            throw new ItemNoFound();
        }
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }
    
    //EJERCICIO 1 A
    public int countNodes() {
        return countNodesRecursivo(root);
    }
    
    private int countNodesRecursivo(Node<E> node) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return 0;
        }
        return 1 + countNodesRecursivo(node.left) + countNodesRecursivo(node.right);
    }
    
    //EJERCICIO 1 B
    public int height(E x) throws ItemNoFound {
        Node<E> aux = buscarNodoEnArbol(root, x);
        if(aux == null){
            throw new ItemNoFound("El valor no se encontro");
        }
        return heightRecursivo(aux);
    }
    
    public Node<E> buscarNodoEnArbol(Node<E> nodo, E x){
        if(nodo == null){
            return null;
        }
        if(x.compareTo(nodo.data) < 0){
            return buscarNodoEnArbol(nodo.left, x);
        }else if(x.compareTo(nodo.data) > 0){
            return buscarNodoEnArbol(nodo.right, x);
        }else{
            return nodo;
        }
    }

    private int heightRecursivo(Node<E> node) {
        if (node == null) {
            return -1;
        }
        int izquierda = heightRecursivo(node.left);
        int derecha = heightRecursivo(node.right);
        return Math.max(izquierda, derecha) + 1;
    }
    
    //EJERCICIO 2 A
    public int areaBST() {
        System.out.println("Nodos Hoja: "+ NodosHoja(root));
        System.out.println("Altura: " + altura(root));
        return NodosHoja(root) * altura(root);
    }
    
    public int NodosHoja(Node<E> nodo){
        if(nodo == null){
            return 0;
        }
        if(nodo.left == null && nodo.right == null){
            return 1;
        }
        return NodosHoja(nodo.left) + NodosHoja(nodo.right);
    }
    
    private int altura(Node<E> node){
        if (node== null){
            return 0;
        }
        if (node.left == null && node.right == null){
            return 0;
        }
        return 1 + Math.max(altura(node.left), altura(node.right));
    }
    
    //EJERCICIO 3
    public void iterativePreOrder() {
        if (root == null) {
            return;
        }

        Stack<Node> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node current = stack.pop();
            System.out.print(current.data + " ");
            if (current.right != null) {
                stack.push(current.right);
            }
            if (current.left != null) {
                stack.push(current.left);
            }
        }
    }
    
    //EJERCICIO 4
    public int totalNodos() {
        return totalNodosRecursividad(root);
    }

    private int totalNodosRecursividad(Node<E> nodo) {
        int total = 0;
        if (nodo == null) {
            return total;
        }
        total++;
        return total + totalNodosRecursividad(nodo.left) + totalNodosRecursividad(nodo.right);
    }
}
