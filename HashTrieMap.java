package datastructures.dictionaries;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.BString;
import cse332.interfaces.trie.TrieMap;
import cse332.interfaces.worklists.WorkList;
import datastructures.worklists.ListFIFOQueue;

public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {	
	public class HashTrieNode extends TrieNode<Map<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new HashMap<A, HashTrieNode>();
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            return pointers.entrySet().iterator();
        }

        public boolean isLeaf() {
        	return this.pointers.isEmpty();
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        super.root = new HashTrieNode();
        super.size = 0;
    }

    @Override
    public V insert(K key, V value) {
    	if(key == null || value == null) 
    		throw new IllegalArgumentException();
    	
    	
    	Iterator<A> keyIt = key.iterator();
    	HashTrieNode curNode = (HashTrieNode) super.root;
    	
    	while(keyIt.hasNext()) {
    		A curA = keyIt.next();
    		if(!curNode.pointers.containsKey(curA)) 
    			curNode.pointers.put(curA, new HashTrieNode());
    		curNode = curNode.pointers.get(curA);    		
    	}

		V ret = curNode.value;
		curNode.value = value;
		if(ret == null) 
			super.size++;
		return ret;    	
    }

    @Override
    public V find(K key) {
        if(key == null) 
        	throw new IllegalArgumentException();
        
        Iterator<A> keyIt = key.iterator();
        HashTrieNode curNode = (HashTrieNode) super.root;
        
        while(keyIt.hasNext()) {
        	A curA = keyIt.next();
        	if(!curNode.pointers.containsKey(curA)) {
        		return null;
        	} else {
        		curNode = curNode.pointers.get(curA);
        	}
        }
        return curNode.value;
    }

    @Override
    public boolean findPrefix(K key) {
    	if(key == null) 
        	throw new IllegalArgumentException();
        
    	
    	Iterator<A> keyIt = key.iterator();
        HashTrieNode curNode = (HashTrieNode) super.root;
        
        while(keyIt.hasNext()) {
        	A curA = keyIt.next();
        	if(!curNode.pointers.containsKey(curA)) {
        		return false;
        	} else {
        		curNode = curNode.pointers.get(curA);
        	}
        }
        return true;
    }
    
    @Override
    public void delete(K key) {
    	if(key == null) 
    		throw new IllegalArgumentException();
    	
    	
    	Iterator<A> keyIt = key.iterator();
        HashTrieNode curNode = (HashTrieNode) super.root;
        HashTrieNode keepNode = (HashTrieNode) super.root;
        A removeChar = null;
        
        while(keyIt.hasNext()) {
        	A curA = keyIt.next();
        	if(!curNode.pointers.containsKey(curA)) 
        		return;
        	if(curNode.pointers.size() > 1 || curNode.value != null) {
        		keepNode = curNode;
        		removeChar = curA;
        	}     	
        	curNode = curNode.pointers.get(curA);
        }
        
        V oldVal = curNode.value;
        curNode.value = null;
        
        if(curNode.isLeaf() && removeChar != null) 
        	keepNode.pointers.remove(removeChar);
        if(oldVal != null) 
        	super.size--;
        if(super.size == 0) 
        	clear();
    }

    @Override
    public void clear() {
        this.root = new HashTrieNode();
        super.size = 0;
    }
}
