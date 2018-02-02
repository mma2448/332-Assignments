package datastructures.dictionaries;

import java.lang.reflect.Array;

import cse332.datastructures.containers.Item;
import cse332.datastructures.trees.BinarySearchTree;
import cse332.datastructures.trees.BinarySearchTree.BSTNode;


public class AVLTree<K extends Comparable<K>, V> extends BinarySearchTree<K, V>  {
    public AVLTree() {
        super();
    }
    
    public V insert(K key, V val) {
        V ret = find(key);        
        if(ret == null) {
            root = insert(AVLCast(root), key, val);
            super.size++;
        } else {
            replace(AVLCast(root), key, val);
        }        
        return ret;
    }
    
    private void replace(AVLNode node, K key, V value) {
        if(node.key.compareTo(key) == 0) {
            node.value = value;
            return;
        }        
        if(node.key.compareTo(key) > 0)
            replace(AVLCast(node.children[0]), key, value);
        else
            replace(AVLCast(node.children[1]), key, value);        
    }
    
    private AVLNode insert(AVLNode node, K key, V val) {
        if(node == null) 
            return new AVLNode(key, val);
            
        int i = 0;   
        if(node.key.compareTo(key) < 0) 
            i = 1;
            
        node.children[i] = insert(AVLCast(node.children[i]), key, val);        
        if(Math.abs(getHeight(AVLCast(node.children[i])) - getHeight(AVLCast(node.children[(i + 1) % 2]))) > 1)
            node = fix(node, key, i);        
        node.height = Math.max(getHeight(AVLCast(node.children[i])), getHeight(AVLCast(node.children[(i + 1) % 2]))) + 1;  
        
        return node;
    }
    
    
    private AVLNode fix(AVLNode node, K key, int i) { // i = 0 for left, i = 1 for right
        int j = 1;
        if(AVLCast(node.children[i]).key.compareTo(key) > 0) 
            j = 0;
            
        if(i != j)
            node.children[i] = AVLCast(rotate(AVLCast(node.children[i]), j));
        node = rotate(node, i);
        return node;
    }
    
    private AVLNode rotate(AVLNode node, int i) { // i = 0 for clockwise, i = 1 counterclockwise
        AVLNode temp = AVLCast(node.children[i].children[(i + 1) % 2]);
        AVLNode ret = AVLCast(node.children[i]);
        node.children[i].children[(i + 1) % 2] = node;
        node.children[i] = temp;
        node.height = Math.max(getHeight(AVLCast(node.children[0])), getHeight(AVLCast(node.children[1]))) + 1;
        ret.height = Math.max(getHeight(AVLCast(ret.children[0])), getHeight(AVLCast(ret.children[1]))) + 1;         
        return ret;
    }
    
    public class AVLNode extends BSTNode {
        public int height;
        
        public AVLNode(K key, V value) {
            super(key, value);
            this.height = 0;
        }        
    }
    
    @SuppressWarnings("unchecked")
    private AVLNode AVLCast(BSTNode node) {
        return (AVLNode) node;
    }
    
    private int getHeight(AVLNode node) {
        if(node == null)  return -1;        
        return node.height;            
    }
}