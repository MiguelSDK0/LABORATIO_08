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
public class TestAVL {
    public static void main(String[] args) throws ItemDuplicated {
        AVLTree<Integer> avlTree = new AVLTree<>();
        System.out.println("-- AVL");
        avlTree.insert(64);
        avlTree.insert(32);
        avlTree.insert(16);
        avlTree.insert(128);
        avlTree.insert(40);
        avlTree.insert(8);
        avlTree.insert(20);
        avlTree.insert(140);
        avlTree.llamarNodos();
        System.out.print("-- Amplitud AVL: ");
        avlTree.recorrerAmplitud();
//        try{
//            System.out.println("-- Altura AVL: " + avlTree.height(64));
//        }catch(ItemNoFound e){
//            System.out.println("No existe el nodo");
//        }
        System.out.println("-- Eliminar 20");
        avlTree.remove(20);
        avlTree.llamarNodos();
//        System.out.print("-- Amplitud AVL: ");
//        avlTree.recorrerAmplitud();
        System.out.println("-- Eliminar 40");
        avlTree.remove(40);
        avlTree.llamarNodos();
//        System.out.print("-- Amplitud AVL: ");
//        avlTree.recorrerAmplitud();
        
//        System.out.println("-- BSTree");
//        BSTree<Integer> BSTree = new BSTree<>();
//        BSTree.insert(64);
//        BSTree.insert(32);
//        BSTree.insert(16);
//        BSTree.insert(128);
//        BSTree.insert(40);
//        BSTree.insert(8);
//        BSTree.insert(20);
//        BSTree.insert(140);
//        System.out.print("-- Amplitud BSTree: ");
//        BSTree.iterativePreOrder();
//        System.out.println("");
//        try{
//            System.out.println("-- Altura BSTree: " + BSTree.height(64));
//        }catch(ItemNoFound e){
//            System.out.println("No existe el nodo");
//        }
    }
}

