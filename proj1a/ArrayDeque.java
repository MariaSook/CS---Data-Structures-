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
        A.addFirst('a');
    }
    /*
    private void resize(int arraysize){
        T[] a = (T []) new Object[arraysize];
        System.arraycopy(items, 0,
                a, 0, size);
        items = a;

    }*/

    public void addFirst(T item){
        items[nextFirst] = item;
        if (nextFirst == 0) {
            nextFirst = items.length-1;
        } else {
            nextFirst = nextFirst-1;
        }
        size +=1;
        }

        /*
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
*/
}
