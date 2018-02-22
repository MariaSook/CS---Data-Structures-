
package synthesizer;

import java.util.Iterator;


public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        first = 0;
        last = 0;
        this.fillCount = 0;
        this.capacity = capacity;
        this.rb = (T[]) new Object[capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */

    @Override
    public void enqueue(T x) {
        if (this.fillCount == capacity) {
            throw new RuntimeException("Ring Buffer Overflow");
        } else {
            this.fillCount += 1;
            rb[last] = x;
            if (last == capacity - 1) {
                last = 0;
            } else {
                last += 1;
            }
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */

    @Override
    public T dequeue() {
        if (this.fillCount == 0) {
            throw new RuntimeException("Ring Buffer Underflow");
        } else {
            this.fillCount -= 1;
            T firsthold = rb[first];
            rb[first] = null;
            if (first == capacity - 1) {
                first = 0;
            } else {
                first += 1;
            }
            return firsthold;
        }
    }

    /**
     * Return oldest item, but don't remove it.
     */

    @Override
    public T peek() {
        return rb[first];
    }

    public java.util.Iterator<T> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<T> {
        private int current;

        public MyIterator() {
            current = first;
        }

        public boolean hasNext() {
            if (current == capacity - 1) {
                current = 0;
            }
            return (current == first);
        }

        public T next() {
            T returnval = rb[current];
            current += 1;
            return returnval;
        }

    }

}


