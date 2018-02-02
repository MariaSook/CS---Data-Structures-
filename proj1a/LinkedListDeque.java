public class LinkedListDeque<T> {
    private class HelperNode{
        public HelperNode next;
        public T item;
        public HelperNode previous;

        public HelperNode(HelperNode p, T i, HelperNode n){
            previous = p;
            item = i;
            next = n;
        }
    }

    private HelperNode sentinel;
    private int size;

    public LinkedListDeque(){
        sentinel = new HelperNode(sentinel, null, sentinel);
        size = 0;
    }

    public void addFirst(T item){
    /*Adds an item of type T to the front of the deque.*/
        sentinel.previous = new HelperNode(sentinel.next, item, sentinel.previous);

        size += 1;
    }

    public void addLast(T item){
    /*Adds an item of type T to the back of the deque. */
        sentinel.next = new HelperNode(sentinel.next, item, sentinel.next);
        size += 1;
    }

    public boolean isEmpty(){
    /*Returns true if deque is empty, false otherwise.*/
        if (size == 0){
            return true;
        } return false;
    }

    public int size(){
    /* Returns the number of items in the deque.*/
        return size;
    }

    public void printDeque(){
    /*Prints the items in the deque from first to last, separated by a space.*/
        if (size == 0){
            return;
        } else {
            HelperNode dummynode = new HelperNode(sentinel.next, null, sentinel.next);
            while (sentinel.next != sentinel) {
                System.out.print(sentinel.next.item + " ");
                sentinel.next = sentinel.next.next;
            }
            sentinel.next = dummynode.next;
        }
    }

    public T removeFirst(){
    /*Removes and returns the item at the front of the deque. If no such item exists, returns null.*/
        if (sentinel == null){
            return null;
        } else {
            if (size != 0) {
                size -= 1;
            }
            HelperNode removed = sentinel.next;
            sentinel = sentinel.next;

            return removed.item;
            }
    }

    public T removeLast(){
    /*Removes and returns the item at the back of the deque. If no such item exists, returns null.*/
        if (sentinel != null){
            return removeLast();
        }

        if (size != 0){
            size -= 1;
        }
        return null;
    }

    public T get(int index){
    /*Gets the item at the given index, where 0 is the
     front, 1 is the next item, and so forth. If no such item
     exists, returns null. Must not alter the deque!*/
        int i = index;

        while (i != 0){
            i -= 1;
            sentinel = sentinel.next;
        }
        return sentinel.next.item;
    }

    public T getRecursive(int index){
        if (index == 0){
            return sentinel.next.item;
        }
        sentinel = sentinel.next;
        return getRecursive(index-1);
    }
}

