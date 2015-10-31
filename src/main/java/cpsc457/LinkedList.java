package cpsc457;

import cpsc457.LinkedList;
import cpsc457.LinkedList.MergeSort.ParallelMergeSort;
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
	// TODO evaluate whether or not this is necessary
	protected Node<T> current;

	public LinkedList() {
		id = idValue;
		idValue++;
		sizeM = 0;
		headM = null;
		current = headM;
	}

	public LinkedList(LinkedList<T> list) {
		Iterator<T> it = list.iterator();
		headM = null;
		sizeM = 0;
		id = idValue;
		idValue++;
		current = headM;
		while (it.hasNext()) {
			append(it.next());
			sizeM++;
		}
	}

	public LinkedList(Node<T> head) {
		id = idValue;
		idValue++;
		headM = head;
		current = headM;
		sizeM = 0;
		if (headM != null) {
			sizeM++;
			Node<T> cursorM = headM;
			while (cursorM.hasNext()) {
				sizeM++;
				cursorM = cursorM.getNext();
			}
		}
	}

	/**
	 * Append the object T to the end of the Linked List implementation
	 * 
	 * @param t
	 *            object to be appended
	 * @return The linked list being appended to
	 */
	public synchronized LinkedList<T> append(T t) {
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
	 * 
	 * @param t
	 *            object to be inserted
	 * @param index
	 *            index for object to be inserted at
	 * @return the linked list
	 * @throws IndexOutOfBoundsException
	 *             if you attempt to insert outside of the bounds of LinkedList
	 */
	public synchronized LinkedList<T> insert(T t, int index)
			throws IndexOutOfBoundsException {
		if (index == 0) {
			this.push_front(t);
		} else if (index == sizeM) {
			this.append(t);
		} else if (index > sizeM || index < 0) {
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
	 * 
	 * @return size of linked list
	 */
	public int size() {
		return sizeM;
	}

	/**
	 * Check if linked list is empty or not
	 * 
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
	public synchronized void clear() {
		headM = null;
		current = headM;
		sizeM = 0;
	}

	/**
	 * Cut the LinkedList at index
	 * 
	 * @param index
	 *            Location of node that will now be at end of the list
	 */
	public synchronized void cut(int index) {
		if (headM == null) {
			return;
		}
		if (index < 0 || index >= sizeM)
			throw new IndexOutOfBoundsException();
		sizeM = 1;
		Node<T> cursorM = headM;
		for (int i = 0; i < index; i++) {
			cursorM = cursorM.getNext();
			sizeM++;
		}
		cursorM.nextM = null;
	}

	/**
	 * Get the object at index in the linked list
	 * 
	 * @param index
	 *            index of object to retrieve
	 * @return reference to object at index
	 */
	public synchronized T get(int index) {
		Node<T> cursorM = headM;
		for (int i = 0; i < index; i++) {
			cursorM = cursorM.getNext();
		}
		return cursorM.getItem();
	}

	/**
	 * Add the object to the front of the linked list
	 * 
	 * @param itemA
	 *            object to add
	 */
	public synchronized LinkedList<T> push_front(T itemA) {
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

	public synchronized Node<T> getNode(int index) {
		Node<T> cursorM = headM;
		for (int i = 0; i < index; i++) {
			cursorM = cursorM.getNext();
		}
		return cursorM;
	}

	@Override
	public synchronized String toString() {
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
	 * 
	 * @param comp
	 */
	public  void sort(Comparator<T> comp) {
		new MergeSort<T>(comp).sort(this);
	}

	/**
	 * implement a parallel merge sort to sort the linked list
	 * 
	 * @param comp
	 */
	public synchronized void par_sort(Comparator<T> comp) {
		new MergeSort<>(comp).parallel_sort(this);
	}

	public static <T extends Comparable<T>> void par_sort(LinkedList<T> list) {
		list.par_sort(Comparable::compareTo);
	}

	public static <T extends Comparable<T>> void sort(LinkedList<T> list) {
		list.sort(Comparable::compareTo);
	}

	public void setLinkedList(Node<T> head) {
		headM = head;
		Node<T> cursorM = headM;
		sizeM = 0;
		if (cursorM != null) {
			while (cursorM.hasNext()) {
				cursorM = cursorM.nextM;
				sizeM++;
			}
		}
		current = headM;
	}

	@Override
	public Iterator<T> iterator() {
		return new LinkedListIterator<>(headM);

		// ptr = head
		// hasnext can i keep going is ptr == null
		// next points to the next return value of T (T v
		// =ptr.value;ptr=ptr.next;return v;
		// remove don't need it
	}

	private class LinkedListIterator<T extends Comparable> implements
			Iterator<T> {
		Node<T> cursor;

		private LinkedListIterator(Node<T> head) {
			cursor = new Node(null, head);
		}

		@Override
		public boolean hasNext() {
			return cursor.hasNext();
		}

		@Override
		public synchronized T next() {
			if (cursor.hasNext()) {
				cursor = cursor.nextM;
				return cursor.itemM;
			} else
				throw new NoSuchElementException();
		}
	}

	static class MergeSort<T extends Comparable> { // object method pattern;

		final Comparator<T> comp;
		private ExecutorService pool;
		int maxThreads = 64;

		public MergeSort(Comparator<T> comp) {
			this.comp = comp;
		}

		public MergeSort(Comparator<T> comp, int threads) {
			this.comp = comp;
			maxThreads = threads;
		}

		public void sort(LinkedList<T> list) {
			list.setLinkedList(mergeSort(list).getNode(0));
		}

		// TODO figure out how this is entering an infinite loop

		private LinkedList<T> mergeSort(LinkedList<T> list) {
			if (list.size() == 1)
				return list;
			LinkedList<T> L1 = new LinkedList<>(list);
			int cutHere = (list.size() / 2) - 1;
			LinkedList<T> L2 = new LinkedList(L1.getNode(cutHere + 1));
			L1.cut(cutHere);

			L1 = mergeSort(L1);
			L2 = mergeSort(L2);

			return merge(L1, L2);
		}

		private synchronized LinkedList<T> merge(LinkedList<T> A, LinkedList<T> B) {
			LinkedList<T> C = new LinkedList<>();
			Node<T> cursorA = A.headM;
			Node<T> cursorB = B.headM;
			Node<T> cursorC = null;
			while (cursorA != null && cursorB != null) {
				int compare = cursorA.itemM.compareTo(cursorB.itemM);
				if (compare >= 0) {
					C.append(cursorB.getItem());
					if (cursorC != null)
						cursorC.nextM = cursorB;
					cursorC = cursorB;
					cursorB = cursorB.nextM;
				} else {
					C.append(cursorA.getItem());
					if (cursorC != null)
						cursorC.nextM = cursorA;
					cursorC = cursorA;
					cursorA = cursorA.nextM;
				}
			}

			while (cursorA != null) {
				C.append(cursorA.getItem());
				if (cursorC != null) {
					cursorC.nextM = cursorA;
				}
				cursorC = cursorA;
				cursorA = cursorA.nextM;
			}
			while (cursorB != null) {
				C.append(cursorB.getItem());
				if (cursorC != null) {
					cursorC.nextM = cursorB;
				}
				cursorC = cursorB;
				cursorB = cursorB.nextM;
			}
			return C;
		}

		public LinkedList<T> parallel_sort(LinkedList<T> list) {
			@SuppressWarnings("unused")
			String result;
			// set the thread pool size
			pool = Executors.newFixedThreadPool(maxThreads);
			ParallelMergeSort toBeSorted = new ParallelMergeSort(list);
			// creates new thread and calls call() func in ParallelMergeSort
			Future<LinkedList<T>> sortedList = pool.submit(toBeSorted);

			// try {
			// result = sortedList.get().toString();
			// } catch (ExecutionException | InterruptedException e) {
			// e.printStackTrace();
			// }

			// kills the thread pool
			pool.shutdown();
			// gets the linkedlist returned by the thread
			try {
				return sortedList.get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
		}

		public class ParallelMergeSort implements Callable<LinkedList<T>> {

			private LinkedList<T> sortList;

			public ParallelMergeSort(LinkedList<T> list) {
				sortList = list;
				//decrement the thread count everytime a new thread is about to be made
				maxThreads--;
			}

			@SuppressWarnings("unchecked")
			public LinkedList<T> call() {

				//same as regular merge sort
				if (sortList.size() == 1)
					return sortList;
				LinkedList<T> L1 = new LinkedList<>(sortList);
				int cutHere = (sortList.size() / 2) - 1;
				LinkedList<T> L2 = new LinkedList<T>(L1.getNode(cutHere + 1));
				L1.cut(cutHere);

				//if there are enough threads use them
				if (maxThreads >= 2) {
					System.out.printf("in thread if maxthreads = %d",maxThreads);
					ParallelMergeSort left = new ParallelMergeSort(L1);
					ParallelMergeSort right = new ParallelMergeSort(L2);
					
					Future<LinkedList<T>> listL1 = pool.submit(left);
					Future<LinkedList<T>> listL2 = pool.submit(right);

					try {
						return merge(listL1.get(), listL2.get());
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//other wise use sequential merge sort
				else {
					L1 = mergeSort(L1);
					L2 = mergeSort(L2);

					return merge(L1, L2);

				}
				return sortList;
			}

		}
	}

}
