package datastructures.worklists;

import cse332.interfaces.worklists.PriorityWorkList;
import cse332.interfaces.worklists.WorkList;
import cse332.exceptions.NotYetImplementedException;
import java.util.NoSuchElementException;


public class MinFourHeap<E extends Comparable<E>> extends PriorityWorkList<E> {
    private E[] data;
    private int index;
    
    public MinFourHeap() {
    	this.data = (E[])new Comparable[5];
    	this.index = -1;
    }

    @Override
    public boolean hasWork() {
        return super.hasWork();
    }

    @Override
    public void add(E work) {
    	this.index++;
    	checkSize();
        int i = percolateUp(index, work);
        this.data[i] = work;
    }
    
    private void checkSize() {
    	if(index == data.length) {
    		E[] arrayUpdate = (E[])new Comparable[index * 2];
    		for(int i = 0; i < index; i++) 
    			arrayUpdate[i] = this.data[i];    		
    		this.data = arrayUpdate;
    	}
    }
    
    private int percolateUp(int hole, E val) {
    	while(hole > 0 && val.compareTo(this.data[(hole - 1) / 4]) < 0) {
    		data[hole] = data[(hole - 1)/ 4];
    		hole = (hole - 1) / 4;
    	}
    	return hole;
    }

    @Override
    public E peek() {
        if(this.index == -1) 
        	throw new NoSuchElementException();
    	return this.data[0];
    }

    @Override
    public E next() {
    	if(this.index == -1) 
    		throw new NoSuchElementException();
    	E ret = this.data[0];
    	int i = percolateDown(0, this.data[index]); 
    	this.data[i] = this.data[index];
    	index--;
    	return ret;    	
    }
    
    private int percolateDown(int hole, E val) {
    	while(4*hole + 1 <= index) {
    		int child = (hole * 4) + 1;
    		int minInt = child;
	    	for(int i = child; i < child + 3 && i < index; i++) {
	    		if(this.data[minInt].compareTo(this.data[i + 1]) > 0) 
	    			minInt = i + 1;
    		}
	    	
	    	if(this.data[minInt].compareTo(val) < 0) {
	    		this.data[hole] = this.data[minInt];
	    		hole = minInt;
	    	} else {
	    		break;
	    	}
    	}
    	return hole;
    }

    @Override
    public int size() {
        return this.index + 1;
    }

    @Override
    public void clear() {
        index = -1;
    }
}
