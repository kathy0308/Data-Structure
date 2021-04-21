package bearmaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private ArrayList<Node> heap;
    private HashMap<T, Integer> itemsBox;

    private class Node {
        T item;
        double priority;

        Node(T i, double p) {
            this.item = i;
            this.priority = p;
        }
        public double getPriority() {
            return priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }
    }


    public ArrayHeapMinPQ() {
        heap = new ArrayList<>();
        itemsBox = new HashMap<>();
    }

    @Override
    public boolean contains(T item) {
        return itemsBox.containsKey(item);
    }

    @Override
    // Adds an item of type T with the given priority.
    // If the item already exists, throw an IllegalArgumentException.
    // You may assume that item is never null.
    public void add(T item, double priority) {
        if (!contains(item)) {
            heap.add(new Node(item, priority));
            itemsBox.put(item, size() - 1);
            siwmUp(size() - 1);
        } else {
            throw new IllegalArgumentException();
        }

    }

    @Override
    // Returns the item with smallest priority.
    // If no items exist, throw a NoSuchElementException.
    public T getSmallest() {
        if (!heap.isEmpty()) {
            return heap.get(0).item;
        } else {
            throw new NoSuchElementException();
        }

    }

    @Override
    // Removes and returns the item with smallest priority.
    // If no items exist, throw a NoSuchElementException.
    public T removeSmallest() {
        if (!heap.isEmpty()) {
            T currItem = getSmallest();
            swap(0, size() - 1);
            itemsBox.remove(currItem);
            heap.remove(size() - 1);
            sinkDown(0);
            return currItem;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    // Returns the number of items.
    public int size() {
        return heap.size();
    }

    @Override
    // Sets the priority of the given item to the given value.
    // If the item does not exist, throw a NoSuchElementException
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        int i = itemsBox.get(item);
        if (priority < heap.get(i).getPriority()) {
            heap.get(i).setPriority(priority);
            siwmUp(i);
        } else {
            heap.get(i).setPriority(priority);
            sinkDown(i);
        }
    }

    private static int parent(int i) {
        return (i - 1) / 2;
    }

    private static int leftChild(int i) {
        return (i * 2 + 1);
    }

    private static int rightChild(int i) {
        return (i * 2 + 2);
    }

    private void swap(int a, int b) {
        Node temp = heap.get(a);
        heap.set(a, heap.get(b));
        heap.set(b, temp);
        itemsBox.put(heap.get(a).item, a);
        itemsBox.put(heap.get(b).item, b);
    }

    private void siwmUp(int i) {
        Node node = heap.get(i);
        Node parent = heap.get(parent(i));
        if (node.getPriority() < parent.getPriority()) {
            swap(i, parent(i));
            siwmUp(parent(i));
        }
    }

    private void sinkDown(int i) {
        int minChlid = leftChild(i);
        if (leftChild(i) < size()) {
            if (rightChild(i) < size() && smaller(rightChild(i), leftChild(i))) {
                minChlid++;
            }
            if (smaller(minChlid, i)) {
                swap(i, minChlid);
                i = minChlid;
                sinkDown(i);
            }
        }
    }
    private boolean smaller(int n1, int n2) {
        return heap.get(n1).priority < heap.get(n2).priority;
    }
    private boolean isEmpty() {
        return size() == 0;
    }


}
