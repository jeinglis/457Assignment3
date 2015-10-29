package cpsc457;

import cpsc457.doNOTmodify.Pair;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.naming.OperationNotSupportedException;

/**
 * 
 * @author Brad & James
 * @param <T> 
 */
public class LinkedList<T extends Comparable> implements Iterable<T> {
    private static int idValue = 0;
    private int id;
    private int sizeM;
    protected Node<T> headM;
    //TODO evaluate whether or not this is necessary
    protected Node<T> current;
    

    public LinkedList() {
        id = idValue;
        idValue++;
        sizeM = 0;
        headM = null;
        current = headM;
    }
    
    public LinkedList(LinkedList<T> list)   {
        Iterator<T> it = list.iterator();
        headM = null;
        sizeM = 0;
        id = idValue;
        idValue++;
        current = headM;
        while(it.hasNext())     {
            append(it.next());
        }
    }
    
    
    public LinkedList(Node<T> head)     {
        id = idValue;
        idValue++;
        headM = head;
        current = headM;
        sizeM = 0;
        if (headM != null)  {
            sizeM++;
            Node<T> cursorM = headM;
            while (cursorM.hasNext()) {
                sizeM++;
            }
        }
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
     * Cut the LinkedList at index
     * @param index Location of node that will now be at end of the list
     */
    public void cut(int index)  {
        if (headM == null)  {
            return;
        }
        if (index < 0 || index >= sizeM)
            throw new IndexOutOfBoundsException();
        sizeM = 1;
        Node<T> cursorM = headM;
        for (int i = 0; i < index; i++)     {
            cursorM = cursorM.getNext();
            sizeM++;
        }
        cursorM.nextM = null;
    }

    /**
     * Get the object at index in the linked list
     * @param index index of object to retrieve
     * @return reference to object at index
     */
    public T get(int index) {
        Node<T> cursorM = headM;
        for (int i = 0; i < index; i++) {
            cursorM = cursorM.getNext();
        }
        return cursorM.getItem();
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
    
    public Node<T> getNode(int index)   {
        Node<T> cursorM = headM;
        for (int i = 0; i < index; i++) {
            cursorM = cursorM.getNext();
        }
        return cursorM;
    }
    
    @Override
    public String toString()    {
        StringBuilder sb = new StringBuilder("Linked List " + id + ": {");
        Iterator i = iterator();
        while (i.hasNext()) {
            sb.append(" ").append(i.next()).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" }");
        return sb.toString();
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
        new MergeSort<>(comp).parallel_sort(this);
    }

    public static <T extends Comparable<T>> void par_sort(LinkedList<T> list) {

    }

    public static <T extends Comparable<T>> void sort(LinkedList<T> list) {
        list.sort(Comparable::compareTo);
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator<>(headM);
        
	//ptr = head
        //hasnext can i keep going is ptr == null
        //next points to the next return value of T (T v =ptr.value;ptr=ptr.next;return v;
        //remove don't need it
    }
    
    private class LinkedListIterator<T extends Comparable> implements Iterator<T>  {
        Node<T> cursor;
        
        private LinkedListIterator(Node<T> head)  {
            cursor = new Node(null, head);
        }
        
        @Override
        public boolean hasNext() {
            return cursor.hasNext();
        }

        @Override
        public T next() {
            if (cursor.hasNext()) {
                cursor = cursor.nextM;
                return cursor.itemM;
            } else
                throw new NoSuchElementException();
        }
    }

    static class MergeSort<T extends Comparable> { // object method pattern;

        final Comparator<T> comp;

        public MergeSort(Comparator<T> comp) {
            this.comp = comp;
        }

        public void sort(LinkedList<T> list) {
            mergeSort(list);
        }
        
        //TODO figure out how this is entering an infinite loop
        
        private LinkedList<T> mergeSort(LinkedList<T> list)  {
            if (list.size() == 1)
                return list;
            LinkedList<T> L1 = new LinkedList<>(list);
            int cutHere = list.size() / 2 - 1;
            LinkedList<T> L2 = new LinkedList(L1.getNode(cutHere + 1));
            L1.cut(cutHere);
            
            L1 = mergeSort(L1);
            L2 = mergeSort(L2);
            
            return merge(L1, L2);
        }
        
        private LinkedList<T> merge(LinkedList<T> A, LinkedList<T> B) {
            LinkedList C = new LinkedList<>();
            Node<T> cursorA = A.headM;
            Node<T> cursorB = B.headM;
            while (cursorA != null && cursorB != null) {
                int compare = cursorA.itemM.compareTo(cursorB.itemM);
                if (compare >= 0) {
                    C.append((Comparable) cursorB);
                    cursorB = cursorB.nextM;
                } else {
                    C.append((Comparable) cursorA);
                    cursorA = cursorA.nextM;
                }
            }
            while (cursorA.hasNext())   {
                C.append((Comparable) cursorA);
                cursorA = cursorA.nextM;
            }
            while (cursorB.hasNext())   {
                C.append((Comparable) cursorB);
                cursorB = cursorB.nextM;
            }
            return C;
        }

        public void parallel_sort(LinkedList<T> list) {

        }
    }

}
