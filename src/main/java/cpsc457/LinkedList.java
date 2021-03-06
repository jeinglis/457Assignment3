package cpsc457;

import cpsc457.LinkedList;
import cpsc457.LinkedList.MergeSort.ParallelMergeSort;
import cpsc457.doNOTmodify.Pair;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.Semaphore;

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

        public static void main(String[] args) throws InterruptedException  {
            System.out.println("Starting the comparisons:");
            double[] counts = new double[5];
            counts[0] = 1e5;
            counts[1] = 2e5;
            counts[2] = 4e5;
            counts[3] = 8e5;
            counts[4] = 16e5;
            
            for (int i = 0; i < counts.length; i++)     {
                System.out.println("Testing both sorts with N = " + counts[i]);
                LinkedList<Integer> merge_list = new LinkedList<Integer>();
                LinkedList<Integer> parallel_list = new LinkedList<Integer>();
                Random r = new Random();
                for (int j = 0; j < counts[i]; j++)     {
                    merge_list.append(r.nextInt());
                    parallel_list.append(r.nextInt());
                }
                System.out.println("Beginning merge sort...");
                long startTime = System.currentTimeMillis();
                LinkedList.sort(merge_list);
                long endTime = System.currentTimeMillis();
                System.out.println("Time to complete merge sort with N = " + counts[i] 
                        + ": " + (endTime - startTime));
                System.out.println("Beginning parallel sort...");
                startTime = System.currentTimeMillis();
                LinkedList.par_sort(parallel_list, 4);
                endTime = System.currentTimeMillis();
                System.out.println("Time to complete parallel sort with N = " + counts[i] 
                        + ": " + (endTime - startTime));
            }
        }
        
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
	public  LinkedList<T> append(T t) {
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
	public  LinkedList<T> insert(T t, int index)
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
	public  void clear() {
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
	public  void cut(int index) {
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
	public  T get(int index) {
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
	public  LinkedList<T> push_front(T itemA) {
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

	public  Node<T> getNode(int index) {
		Node<T> cursorM = headM;
		for (int i = 0; i < index; i++) {
			cursorM = cursorM.getNext();
		}
		return cursorM;
	}

	@Override
	public  String toString() {
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
	 * @throws InterruptedException 
	 */
	public  void sort(Comparator<T> comp) throws InterruptedException {
		new MergeSort<T>(comp).sort(this);
	}

	/**
	 * implement a parallel merge sort to sort the linked list
	 * 
	 * @param comp
	 */
	public  void par_sort(Comparator<T> comp,int availableThreads) {
		new MergeSort<>(comp,availableThreads).parallel_sort(this);
	}

	public static <T extends Comparable<T>> void par_sort(LinkedList<T> list, int availableThreads) {
		list.par_sort(Comparable::compareTo,availableThreads);
	}

	public static <T extends Comparable<T>> void sort(LinkedList<T> list) throws InterruptedException {
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
		public  T next() {
			if (cursor.hasNext()) {
				cursor = cursor.nextM;
				return cursor.itemM;
			} else
				throw new NoSuchElementException();
		}
	}
	
	
	
/**********************************************************
 * This is the class we used semaphores in
 * Due to the way our code was written the only time
 * a lock may be needed is during the merge to prevent concurrent writes
 * a semaphore (mergeAppend)has been used in the merge method 
 * this locks the call to append so only one thread may enter at once
 *************************************************************/
	
	
	static class MergeSort<T extends Comparable> { // object method pattern;
		private Semaphore mergeAppend  = new Semaphore(1);
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

		public void sort(LinkedList<T> list) throws InterruptedException {
			list.setLinkedList(mergeSort(list).getNode(0));
		}

		// TODO figure out how this is entering an infinite loop

		private LinkedList<T> mergeSort(LinkedList<T> list) throws InterruptedException {
			if (list.size() <= 1)
				return list;
			LinkedList<T> L1 = new LinkedList<>(list);
			int cutHere = (list.size() / 2) - 1;
			LinkedList<T> L2 = new LinkedList(L1.getNode(cutHere + 1));
			L1.cut(cutHere);

			L1 = mergeSort(L1);
			L2 = mergeSort(L2);

			return merge(L1, L2);
		}
/*
 * merge method (using sempahores)
 * receives two LinkedLists combines the two into a final sorted LinkedList
 * returns a sorted Linked list of size A.size()+B.size()
 * 
 */
		private LinkedList<T> merge(LinkedList<T> A, LinkedList<T> B) throws InterruptedException {
			LinkedList<T> C = new LinkedList<>();
			Node<T> cursorA = A.headM;
			Node<T> cursorB = B.headM;
			Node<T> cursorC = null;
			while (cursorA != null && cursorB != null) {
				int compare = cursorA.itemM.compareTo(cursorB.itemM);
				if (compare >= 0) {
					mergeAppend.acquire();
					C.append(cursorB.getItem());
					mergeAppend.release();
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
				mergeAppend.acquire();
				C.append(cursorB.getItem());
				mergeAppend.release();
				if (cursorC != null) {
					cursorC.nextM = cursorA;
				}
				cursorC = cursorA;
				cursorA = cursorA.nextM;
			}
			while (cursorB != null) {
				mergeAppend.acquire();
				C.append(cursorB.getItem());
				mergeAppend.release();
				if (cursorC != null) {
					cursorC.nextM = cursorB;
				}
				cursorC = cursorB;
				cursorB = cursorB.nextM;
			}
			return C;
		}


		public void parallel_sort(LinkedList<T> list) {
			// set the thread pool size
			pool = Executors.newFixedThreadPool(maxThreads);
			ParallelMergeSort toBeSorted = new ParallelMergeSort(list);
			// creates new thread and calls call() func in ParallelMergeSort
			Future<LinkedList<T>> sortedList = pool.submit(toBeSorted);

			//pass sorted list by reference 
			try {
				list.setLinkedList(sortedList.get().getNode(0));
			} catch (ExecutionException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			// kills the thread pool
			pool.shutdown();
			try {
				pool.awaitTermination(20, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		/*
		 * This class implements the callable interface and is used by 
		 * the parallel_sort method above.
		 */

		
		public class ParallelMergeSort implements Callable<LinkedList<T>> {

			private LinkedList<T> sortList;
			public ParallelMergeSort(LinkedList<T> list) {
				 sortList = list;
				//decrement the thread count everytime a new thread is about to be made
				maxThreads--;
			}


			public  LinkedList<T> call() throws InterruptedException {

				//same as regular merge sort
				if (sortList.size() == 1)
					return sortList;
				LinkedList<T> L1 = new LinkedList<>(sortList);
				int cutHere = (sortList.size() / 2) - 1;
				LinkedList<T> L2 = new LinkedList<T>(L1.getNode(cutHere + 1));
				L1.cut(cutHere);

				//if there are enough threads use them
				if (maxThreads >= 2) {
					//System.out.printf("in  call(): maxthreads = %d\n",maxThreads);
					ParallelMergeSort left = new ParallelMergeSort(L1);
					ParallelMergeSort right = new ParallelMergeSort(L2);
					
					Future<LinkedList<T>> listL1 = pool.submit(left);
					Future<LinkedList<T>> listL2 = pool.submit(right);

					try {
						return merge(listL1.get(), listL2.get());
					} catch (ExecutionException | InterruptedException e) {
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
