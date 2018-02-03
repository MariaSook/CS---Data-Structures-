public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;
        size = 0;
    }

    public void addFirst(T item) {
        if (size == items.length) {
            resize(2 * size());
        }
        items[nextFirst] = item;
        nextFirst = Math.floorMod(nextFirst - 1, items.length);
        size++;
    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(2 * size());
        }
        items[nextLast] = item;
        nextLast = Math.floorMod(nextLast + 1, items.length);
        size++;
    }

    private void resize(int capacity) {
        T[] placeholder = (T[]) new Object[capacity];
        int header = nextFirst + 1;
        for (int i = 0; i < items.length; i++) {
            while (header >= items.length) {
                header -= items.length;
            }
            if (items[header] == null) {
                break;
            }
            placeholder[i] = items[header];
            header++;
        }
        items = placeholder;
        nextFirst = items.length - 1;
        nextLast = size;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        String s = "";
        for (int i = 0; i < items.length; i++) {
            s += items[i] + " ";
        }
        System.out.println(s.substring(0, s.length() - 1));
    }

    public T removeFirst() {
        if (size == 0){
            T[] temp = (T[]) new Object[8];
            items = temp;
            return null;
        }else if (size * 4 < items.length && items.length >= 16) {
            resize(items.length / 2);
        }
        nextFirst = Math.floorMod(nextFirst + 1, items.length);
        T value = items[nextFirst];
        items[nextFirst] = null;
        size--;
        return value;
    }

    public T removeLast() {
        if (size == 0){
            T[] temp = (T[]) new Object[8];
            items = temp;
            return null;
        } else if (size * 4 < items.length && items.length >= 16) {
            resize(items.length / 2);
        }
        nextLast = Math.floorMod(nextLast - 1, items.length);
        T value = items[nextLast];
        items[nextLast] = null;
        size--;
        return value;
    }

    public T get(int index) {
        return items[Math.floorMod(nextFirst + 1 + index, items.length)];
    }
}