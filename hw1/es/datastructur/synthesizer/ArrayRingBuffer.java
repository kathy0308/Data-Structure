package es.datastructur.synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    @Override
    public int capacity() {
        return rb.length;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {

        first = 0;
        last = 0;
        fillCount = 0;
        rb = (T[]) new Object[capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {

        if (isFull()) {
            throw new RuntimeException(("Ring buffer overflow"));
        } else {
            rb[last] = x;
            last++;
            fillCount++;

            if (last >= capacity()) {
                last = 0;
            }
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {

        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        } else {
            T temp = rb[first];
            rb[first] = null;
            first++;
            fillCount--;

            if (first >= capacity()) {
                first = 0;
            }
            return temp;
        }
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {

        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }


    private class ArrayRingBufferIterator implements Iterator<T> {
        private int count = 0;
        private int position = first;

        public boolean hasNext() {
            return count < fillCount();
        }

        public T next() {
            T item = rb[position];
            position++;
            if (position == capacity()) {
                position = 0;
            }
            count++;
            return item;
        }
    }
    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        Iterator iter1 = ((ArrayRingBuffer) o).iterator();
        Iterator iter2 = this.iterator();
        for (int i = 0; i < ((ArrayRingBuffer) o).fillCount; i++) {
            if (iter1.next() != iter2.next()) {
                return false;
            }
        }
        return false;

    }

}
