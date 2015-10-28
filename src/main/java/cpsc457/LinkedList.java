package cpsc457;

import cpsc457.doNOTmodify.Pair;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class LinkedList<T> implements Iterable<T> {
	
	private int sizeM;
	protected Node headM;
	protected Node current;

    public LinkedList() {

	sizeM=0;
	headM= null;
	current=headM;
    }

    public LinkedList<T> append(T t) {
        return this;
    }

    public int size() {
        return sizeM;
    }

    public boolean isEmpty() {
        if(headM == null)
		return true;
	else
		return false;
    }

    public void clear() {
	headM = null;
	current = headM;
	sizeM=0;
    }

    public T get(int index) {
	current = headM;
	for(int i = 0; i<index; i++)
		current = current.getNext();
	return null;
	//return current.getItem();
    }

    public void sort(Comparator<T> comp) {
	new MergeSort<T>(comp).sort(this);
    }

    public void par_sort(Comparator<T> comp) {
	new MergeSort<T>(comp).parallel_sort(this);
    }

    public static <T extends Comparable<T>> void par_sort(LinkedList<T> list) {
	
    }

    public static <T extends Comparable<T>> void sort(LinkedList<T> list){
        list.sort(new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return o1.compareTo(o2);
            }
        });
    }

    @Override
    public Iterator<T> iterator() {
	return null;

	//ptr = head
	//hasnext can i keep going is ptr == null
	//next points to the next return value of T (T v =ptr.value;ptr=ptr.next;return v;
	//remove don't need it
    }

    static class MergeSort<T> { // object method pattern;
     final Comparator<T> comp;

     public MergeSort(Comparator<T> comp) {
         this.comp = comp;
     }

     public void sort(LinkedList<T> list) {


     }

     public void parallel_sort(LinkedList<T> list) {


     }
 }

 
}
