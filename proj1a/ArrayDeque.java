public class ArrayDeque<T> {
    private class HelperArray{

    }
    private T[] items;
    private int size;

    private void resize(int arraysize){
        T[] a = (T []) new Object[arraysize];
        System.arraycopy(items, 0,
                a, 0, size);
        items = a;

    }

    public ArrayDeque(){
        items = (T []) new Object[8];
        size = 0;
    }

    public void addFirst(T item){
        size += 1;
    }

    public void addLast(T item){
        size += 1;
    }

    public boolean isEmpty(){

    }

    public int size(){
        return size;
    }

    public void printDeque(){

    }

    public T removeFirst(){
        size -= 1;
    }

    public T removeLast(){
        size -= 1;
    }

    public T get(int index){

    }

}
