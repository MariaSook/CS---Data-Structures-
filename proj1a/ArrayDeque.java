public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }

    private void resize(int capacity) {
        T[] array = (T[]) new Object[capacity];
        int firstvalindex = nextFirst + 1;
        for (int i = 0; i < items.length; i++) {
            T firstval = items[firstvalindex];
            while (firstvalindex >= items.length) {
                firstvalindex -= items.length;
            }
            if (firstval == null) {
                break;
            }
            array[i] = items[firstvalindex];
            firstvalindex += 1;
        }
        items = array;
        nextFirst = items.length - 1;
        nextLast = size;
    }

    public void addFirst(T item) {
        /*Adds an item of type T to the front of the array.*/
        int sizehold = size;
        if (size == items.length) {
            resize(2*sizehold);
        }
        items[nextFirst] = item;
        nextFirst = (nextFirst -1) % items.length;
        size += 1 ;
    }

    public void addLast(T item) {
        /*Adds an item of type T to the back of the array. */
        int sizehold = size;
        if (size == items.length) {
            resize(2*sizehold);
        }
        items[nextLast] = item;
        nextLast = (nextLast +1) %items.length;
        size += 1;
    }

    public T removeFirst() {
        if (size ==0){
            return null;
        } else if (size * 4 < items.length && items.length >= 16) {
            resize(items.length / 2);
        }
        nextFirst = Math.floorMod(nextFirst + 1, items.length);
        T holdval = items[nextFirst];
        items[nextFirst] = null;
        size -= 1;
        return holdval;
    }

    public T removeLast() {
        if (size == 0){
            return null;
        } else if (size * 4 < items.length && items.length >= 16) {
            resize(items.length / 2);
        }
        nextLast = Math.floorMod(nextLast - 1, items.length);
        T holdval = items[nextLast];
        items[nextLast] = null;
        size -= ;
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
        if (size == 0){
            return;
        } else if (size < items.length) {
            for (int i = 0; i < items.length; i++){
                if (items[i] == null){
                    continue;
                }
                System.out.print(items[i] + " ");
            }
        } else {
            for (int i = 0; i < size; i++){
                System.out.print(items[i] + " ");
            }
        }
    }

    public T get(int index) {
        return items[index];
    }
}
