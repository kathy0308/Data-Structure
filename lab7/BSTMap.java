import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        private K key;
        private V value;


        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;
    private int size;

    public BSTMap() {
        this.clear();
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }



    @Override
    public int size() {
        return size;
    }


    @Override
    public boolean containsKey(K key){
        return get(key)!= null;
    }


    @Override
    public V get(K key) {

        return getHelper(key, root);

    }

    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            return getHelper(key, p.left);
        }
        else if (cmp > 0) {
            return getHelper(key, p.right);
        }
        else {
            return p.value;
        }
    }


    @Override
    public void put(K key, V value) {

        root = putHelper(key, value, root);

    }

    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size++;
            return new Node(key, value);
        }

        int cmp = key.compareTo(p.key);

        if (cmp < 0) {
            p.left = putHelper(key, value, p.left);
        } else if (cmp > 0) {
            p.right = putHelper(key, value, p.right);
        } else {
            p.value = value;
        }
        return p;
    }

    private void printInOrder(Node p) {
        if (p == null) {
            return;
        }

        printInOrder(p.left);
        System.out.print("(" + p.key.toString() + ", " + p.value.toString() + ") ");
        printInOrder(p.right);
    }

    public void printSet() {
        printInOrder(root);
        System.out.println();
    }


    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }


    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }


    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

}
