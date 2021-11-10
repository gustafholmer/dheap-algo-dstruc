// gustaf holmer guho0000


//BinaryHeap class
//
//CONSTRUCTION: with optional capacity (that defaults to 100)
//            or an array containing initial items
//
//******************PUBLIC OPERATIONS*********************
//void insert( x )       --> Insert x
//Comparable deleteMin( )--> Return and remove smallest item
//Comparable deleteMin( )--> Return and remove smallest item
//Comparable findMin( )  --> Return smallest item
//boolean isEmpty( )     --> Return true if empty; else false
//boolean isEmpty( )     --> Return true if empty; else false
//void makeEmpty( )      --> Remove all items
//******************ERRORS********************************
//Throws UnderflowException as appropriate

/**
 * Implements a binary heap.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class DHeap<A extends Comparable<? super A>> {

    private static final int DEFAULT_CAPACITY = 10;
    private int currentSize;      // Number of elements in heap
    private A[] array; // The heap array
    private int howManyChildren; // How many children


    /**
     * Construct the binary heap.
     */
    public DHeap() {
        this.howManyChildren = 2;
        currentSize = 0;
        array = (A[]) new Comparable[DEFAULT_CAPACITY + 1];
    }

    /**
     * Construct the d-binary heap.
     */
    public DHeap(int d) {
        if (d < 2) {
            throw new IllegalArgumentException();
        }

        this.howManyChildren = d;
        currentSize = 0;
        array = (A[]) new Comparable[DEFAULT_CAPACITY + 1];
    }


    public int parentIndex(int i) {
        int parentInd = (i + howManyChildren - 2) / howManyChildren; // 1 + 4-2 / 4

        if (i < 2 && currentSize == 0) {
            throw new IllegalArgumentException();
        }
        return parentInd;
    }

    public int firstChildIndex(int i) {
        int firstChildInd = i * howManyChildren - (howManyChildren - 2);

        if (firstChildInd < 2) {
            throw new IllegalArgumentException();
        }
        return firstChildInd;
    }

    public int size() {
        return currentSize;
    }

    A get(int index) {
        return array[index];
    }

    /**
     * Insert into the priority queue, maintaining heap order.
     * Duplicates are allowed.
     *
     * @param x the item to insert.
     */
    public void insert(A x) {
        if (currentSize == array.length - 1)
            enlargeArray(array.length * 2 + 1);

        System.out.println("Insert x: " + x);


        // Percolate up

        int hole = ++currentSize;

        array[0] = x;

        for (; x.compareTo(array[parentIndex(hole)]) < 0; hole = parentIndex(hole))
            array[hole] = array[parentIndex(hole)];
        array[hole] = x;

        String outPut = "";
        for(int i = 0; i < 10; i++) {
            outPut += array[i] + ", ";
        }
        System.out.println("[" + outPut +"]");

    }


    private void enlargeArray(int newSize) {
        A[] old = array;
        array = (A[]) new Comparable[newSize];
        for (int i = 0; i < old.length; i++)
            array[i] = old[i];
    }

    /**
     * Find the smallest item in the priority queue.
     *
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public A findMin() {
        if (isEmpty())
            throw new UnderflowException();
        return array[1];
    }

    /**
     * Remove the smallest item from the priority queue.
     *
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public A deleteMin() {
        if (isEmpty())
            throw new UnderflowException();

        A minItem = findMin();
        array[1] = array[currentSize];

        currentSize--;

        percolateDown(1);

        return minItem;
    }

    /**
     * Establish heap order property from an arbitrary
     * arrangement of items. Runs in linear time.
     */
    private void buildHeap() {
        for (int i = currentSize / 2; i > 0; i--)
            percolateDown(i);
    }

    /**
     * Test if the priority queue is logically empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * Make the priority queue logically empty.
     */
    public void makeEmpty() {
        currentSize = 0;
    }

    private void percolateDown(int hole) {
        A tmp = array[hole];
        for (; smallestChildIndex(hole) <= currentSize && array[smallestChildIndex(hole)].compareTo(tmp) <= 0; hole = smallestChildIndex(hole))
            array[hole] = array[smallestChildIndex(hole)];

        array[hole] = tmp;

    }

    private int smallestChildIndex(int indexParent){
        int leftChildIndx = firstChildIndex(indexParent);
        int index = 1;

        if (currentSize < leftChildIndx)
            return currentSize + 1;

        A smallestChild = array[leftChildIndx];

        int minChildIndex = leftChildIndx;

        while (index < howManyChildren) {
            if (leftChildIndx + index <= currentSize) {
                A tmp = array[leftChildIndx + index];

                if (tmp != null && smallestChild.compareTo(tmp) > 0) {
                    minChildIndex = leftChildIndx + index;
                    smallestChild = tmp;
                }
            }
            index++;
        }

        return minChildIndex;
    }


    public static void main( String [ ] args )
    {
        int numItems = 10000;
        DHeap<Integer> h = new DHeap<>( );
        int i = 37;

        for( i = 37; i != 0; i = ( i + 37 ) % numItems )
            h.insert( i );
        for( i = 1; i < numItems; i++ )
            if( h.deleteMin( ) != i )
                System.out.println( "Oops! " + i );
    }


}