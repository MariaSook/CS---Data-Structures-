public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque(){
        items = (T []) new Object[8];
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
        A.addFirst(5);
        A.addFirst(6);
        A.addFirst(7);
        A.addFirst(8);
        A.addFirst(9);
        A.addLast(10);
    }

    private void resize(int arraysize, int index){
        T[] newarray = (T []) new Object[arraysize];
        System.arraycopy(items, 0, newarray, 0, index);
        System.arraycopy(items, index, newarray, index+1, items.length-index);
        items = newarray;
    }

    public void addFirst(T item){
        if (size == items.length){
            resize(++size, nextFirst);
        } else {
            nextFirst = (nextFirst - 1 + items.length) % items.length; //revisit math
        }
        items[nextFirst] = item;
        size += 1;
    }

    public void addLast(T item){
        if (size == items.length){
            resize(++size, nextLast);
        } else {
            nextLast = (nextLast + 1) % items.length; //revisit math
        }
        items[nextLast] = item;
        size += 1;
    }

    public boolean isEmpty(){
       return true;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        return;
    }

    public T removeFirst(){
        size -= 1;
        return items[nextFirst];

    }

    public T removeLast(){
        size -= 1;
        return items[nextFirst];

    }

    public T get(int index){
        return items[nextFirst];
    }


}
