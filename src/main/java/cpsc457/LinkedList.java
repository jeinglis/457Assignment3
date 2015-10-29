package cpsc457;

import cpsc457.doNOTmodify.Pair;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedList<T> implements Iterable<T> {

    private int sizeM;
    protected Node<T> headM;
    protected Node<T> current;

    public LinkedList() {

        sizeM = 0;
        headM = null;
        current = headM;
    }

    /**
     * Append the object T to the end of the Linked List implementation
     * @param t object to be appended
     * @return The linked list being appended to
     */
    public LinkedList<T> append(T t) {
        if (headM == null) {
            headM = new Node(t);
        } else {
            Node end = headM;
            while (end.hasNext()) {
                end = end.getNext();
            }
            end.setNext(new Node(t));

        }
        sizeM++;
        return this;
    }
    
    /**
     * Inserts an object at the specified index in the linked list
     * @param t object to be inserted 
     * @param index index for object to be inserted at
     * @return the linked list
     * @throws IndexOutOfBoundsException if you attempt to insert outside of the bounds of LinkedList 
     */
    public LinkedList<T> insert(T t, int index) throws IndexOutOfBoundsException    {
        if (index == 0) {
            this.push_front(t);
        } else if (index == sizeM) {
            this.append(t);
        } else if (index > sizeM || index < 0)    { 
            throw new IndexOutOfBoundsException();
        } else {
            Node<T> cursorM = headM;
            for (int i = 0; i < index - 1; i++) {
                cursorM = cursorM.getNext();
            }
            Node<T> cursorNext = cursorM.nextM;
            cursorM.nextM = new Node(t, cursorNext);
        }
        sizeM++;
        return this;
    }

    /**
     * Get the size of the linked list
     * @return size of linked list
     */
    public int size() {
        return sizeM;
    }

    /**
     * Check if linked list is empty or not
     * @return true if empty, false if not
     */
    public boolean isEmpty() {
        if (headM == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Clear the linked list
     */
    public void clear() {
        headM = null;
        current = headM;
        sizeM = 0;
    }

    /**
     * Get the object at index in the linked list
     * @param index index of object to retrieve
     * @return reference to object at index
     */
    public T get(int index) {
        current = headM;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getItem();
    }

    /**
     * Add the object to the front of the linked list
     * @param itemA object to add
     */
    public LinkedList<T> push_front(T itemA) {
        Node<T> cursorM = null;
        Node<T> new_node = new Node<T>(itemA);
        if (headM == null) {
            headM = new_node;
        } else {
            cursorM = headM.nextM;
            Node<T> p = headM;
            while (cursorM != null) {
                cursorM = cursorM.nextM;
                p = p.nextM;
            }
            p.nextM = new_node;
        }
        sizeM++;
        return this;
    }

    /**
     * Implement a typical merge sort to sort the values inside the linked list
     * @param comp 
     */
    public void sort(Comparator<T> comp) {
        new MergeSort<T>(comp).sort(this);
    }

    
    /**
     * implement a parallel merge sort to sort the linked list
     * @param comp 
     */
    public void par_sort(Comparator<T> comp) {
        new MergeSort<T>(comp).parallel_sort(this);
    }

    public static <T extends Comparable<T>> void par_sort(LinkedList<T> list) {

    }

    public static <T extends Comparable<T>> void sort(LinkedList<T> list) {
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
        
        public void merge(LinkedList<T> A, LinkedList<T> B) {
            LinkedList C = new LinkedList<>();
            
        }

        public void parallel_sort(LinkedList<T> list) {

        }
    }

}
