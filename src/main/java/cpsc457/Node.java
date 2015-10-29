package cpsc457;

class Node<T extends Comparable> {

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

    public boolean hasNext() {
        if (nextM == null) {
            return false;
        }
        return true;
    }

    public void setNext(Node<T> node) {
        this.nextM = node;
    }

    //constructor given an item and next Node
    public Node(T item, Node next) {
        itemM = item;
        nextM = next;

    }

    public T getItem() {

        return itemM;

    }

    public void setItem(T item) {

        itemM = item;

    }

    public Node getNext() {

        return nextM;

    }
    
    @Override
    public String toString()    {
        return itemM.toString();
    }
    

//synchronize vs mutex lock unlock
//with functions sync will force that only one func gets executed at a time with same instance
//but not always best if they are doing different things 
//can use sync statement inside function you can use sync(a) which locks just that variable
//log _a b = bloga , log _a a = 1, 1 2 4 8 16 number of threads number at that level + level -1
//ln2^n = n, (log _b a) / (log _b c) = log _c a

}
