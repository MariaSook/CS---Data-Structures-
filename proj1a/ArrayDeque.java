public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    public static void main(String[] args) {
        ArrayDeque A = new ArrayDeque();
        A.addFirst(1);
        A.addFirst(2);
        A.addFirst(3);
        A.addFirst(4);
        A.addFirst(1);
        A.addFirst(2);
        A.addFirst(3);
        A.addFirst(4);
        A.addFirst(1);
        A.addFirst(2);
        A.addFirst(3);
        A.addFirst(4);

    }

    //@Gabby Shvartsman helped me visualize what resize was supposed to do
    private void resize(int resizeval) {
        T[] newArray = (T[]) new Object[resizeval];
        int firstvalindex = nextFirst + 1;
        for (int i = 0; i < items.length; i++) {
            while (firstvalindex >= items.length) {
                firstvalindex -= items.length;
            }
            if (items[firstvalindex] == null) {
                break;
            }
            newArray[i] = items[firstvalindex];
            firstvalindex += 1;
        }
        items = newArray;
        nextFirst = items.length - 1;
        nextLast = size;
    }

    private void nflc(){
        /*next first length change*/
        nextFirst = Math.floorMod(nextFirst - 1, items.length);
    }

    private void nllc(){
        /*next last length change*/
        nextLast = Math.floorMod(nextLast - 1, items.length);
    }

    public void addFirst(T item) {
        /*Adds an item of type T to the front of the array.*/
        int sizehold = size();
        if (size == items.length) {
            resize(sizehold*2);
        }
        items[nextFirst] = item;
        size += 1 ;
        nflc();
    }

    public void addLast(T item) {
        /*Adds an item of type T to the back of the array. */
        int sizehold = size();
        if (size == items.length) {
            resize(sizehold*2);
        }
        items[nextLast] = item;
        size += 1;
        nllc();
    }

    public T removeFirst() {
        /*Removes and returns the item at the front of the deque.
        If no such
        item exists, returns null.*/
        if (size == 0){
            T[] temp = (T[]) new Object[8];
            items = temp;
            return null;
        } else if (size * 4 < items.length && items.length >= 16) {
            resize(items.length / 2);
        }
        nflc();
        T holdval = items[nextFirst];
        items[nextFirst] = null;
        size -= 1;
        return holdval;
    }

    public T removeLast() {
        /*Removes and returns the item at the back of the deque.
        If no such
        item exists, returns null.*/
        if (size == 0){
            T[] temp = (T[]) new Object[8];
            items = temp;
            return null;
        } else if (size * 4 < items.length && items.length >= 16) {
            resize(items.length / 2);
        }
        nllc();
        T holdval = items[nextLast];
        items[nextLast] = null;
        size -= 1;
        return holdval;
    }

    public boolean isEmpty() {
        /*Returns true if deque is empty, false otherwise.*/
        if (size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void printDeque(){
        /*Prints the items in the array from first to last,
        separated by a space.*/
        if (size == 0){
            return;
        } else {
            for (int i = 0; i < items.length; i++){
                if (items[i] == null){
                    continue;
                }
                System.out.print(items[i] + " ");
            }
        }
    }

    public T get(int index) {
        return items[(nextFirst + 1 + index )%items.length];
    }
}