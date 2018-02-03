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

    private static void main(String[] args) {
        ArrayDeque A = new ArrayDeque();
        A.addFirst(1);
        A.addFirst(2);
        A.addFirst(3);
        A.addFirst(4);
        A.addLast(99999);
        A.addFirst(5);
        A.addFirst(6);
        A.addFirst(7);
        A.removeFirst();


//        A.addFirst(8);
//        A.addFirst(9);
//        A.addLast(10);
//        A.printDeque();
    }

    private void resize(int arraysize, int index){
        T[] newarray = (T []) new Object[arraysize];
        System.arraycopy(items, 0, newarray, 0, index);
        System.arraycopy(items, index, newarray, index+1, items.length-index);
        items = newarray;
    }

    public void addFirst(T item){
        int nextFirsthold = nextFirst;
        if (size == items.length){ /*size == items.length*/
            resize(++size, nextFirst);
        } else {
            nextFirst = (nextFirst - 1 + items.length) % items.length;
            size += 1;
        }
        items[nextFirsthold] = item;

    }

    public void addLast(T item){
        int nextlasthold = nextLast;
        if (size == items.length){
            resize(++size, nextLast);
        } else {
            nextLast = (nextLast + 1) % items.length;
            size += 1;
        }
        items[nextlasthold] = item;
    }

    public boolean isEmpty(){
       if (size == 0){
           return false;
       }
       return true;
    }

    public int size(){
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

//    private void resizedown(int arraysize, int index){
//        T[] newarray = (T []) new Object[arraysize];
//        System.arraycopy(items, 0, newarray, 0, index);
//        System.arraycopy(items, index, newarray, index, items.length-index+1);
//        items = newarray;
//    }

    public T removeFirst(){
        T nextLasthold = items[nextLast];
        if (size == 1){
            size -= 1;
            return null;
        } else {
            resize(--size, nextLast+1);
            nextLast = (nextLast + 1) % items.length;
        }
        return nextLasthold;
    }

    public T removeLast(){
        size -= 1;
        return items[nextFirst];

    }

    public T get(int index){
        return items[nextFirst];
    }

    
}
