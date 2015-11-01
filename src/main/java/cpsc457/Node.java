package cpsc457;

import cpsc457.Node;

public class Node<T extends Comparable> {

    protected T itemM;
    protected Node<T> nextM;

    //default constructor
    public Node()    {
        itemM = null;
        nextM = null;
    }package cpsc457;

public class Node<T extends Comparable> {

    protected T itemM;
    protected Node<T> nextM;

    //default constructor
    public Node()    {
        itemM = null;
        nextM = null;
    }

    public Node(T t) {
        itemM = t;
        nextM = null;
    }
    
    //constructor given an item and next Node
    public Node(T item, Node next) {
        itemM = item;
        nextM = next;

    }

    public boolean hasNext() {
        if (nextM == null) {
            return false;
        }
        return true;
    }

    public  void  setNext(Node<T> node) {
        this.nextM = node;
    }



    public T getItem() {

        return itemM;

    }

    public  void setItem(T item) {

        itemM = item;

    }

    public  Node<T> getNext() {

        return nextM;

    }
    
    @Override
    public String toString()    {
        return itemM.toString();
    }
    

}


    public Node(T t) {
        itemM = t;
        nextM = null;
    }
    
    //constructor given an item and next Node
    public Node(T item, Node next) {
        itemM = item;
        nextM = next;

    }

    public boolean hasNext() {
        if (nextM == null) {
            return false;
        }
        return true;
    }

    public synchronized void  setNext(Node<T> node) {
        this.nextM = node;
    }



    public T getItem() {

        return itemM;

    }

    public synchronized void setItem(T item) {

        itemM = item;

    }

    public synchronized Node<T> getNext() {

        return nextM;

    }
    
    @Override
    public String toString()    {
        return itemM.toString();
    }
    


}
