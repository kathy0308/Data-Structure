import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

public class MyHashMap<K, V> {

    private class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private ArrayList<Node>[] buckets;
    private int size;
    private double loadFactor;
    private HashSet<K> keys;

    private static final int DEFAULT_INT_SIZE = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    @Override
    public void clear() {
        buckets = (ArrayList<Node>[]) new ArrayList[DEFAULT_INT_SIZE];
        size = 0;
        keys = new HashSet<>();
    }

    @Override
    public boolean containsKey(K key) {
        return keys.contains(key);
    }


    @Override
    public Set<K> keySet() {
        return keys;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<K> iterator() {
        return keys.iterator();
    }

    public MyHashMap() {
        this(DEFAULT_INT_SIZE);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, DEFAULT_LOAD_FACTOR);
    }


    public MyHashMap(int initialSize, double maxLoad) {
        buckets = (ArrayList<Node>[]) new ArrayList[initialSize];
        size = 0;
        loadFactor = maxLoad;
        keys = new HashSet<K>();
    }

    @Override
    public V get(K key) {
        Node node = getNode(key);
        if (node == null) {
            return null;
        }
        return node.value;
    }

    private Node getNode(K key) {
        int idx = findBucket(key);
        ArrayList<Node> bucketList = buckets[idx];
        if (bucketList != null) {
            for (Node node : bucketList) {
                if (node.key.equals(key)) {
                    return node;
                }
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        Node node = getNode(key);
        if (node != null) {
            node.value = value;
            return;
        }

        if (((double)size/buckets.length) > loadFactor) {
            reBucket(buckets.length*2);
        }
        size++;
        keys.add(key);
        int index = findBucket(key);
        ArrayList<Node> bucketList = buckets[index];
        if (bucketList == null) {
            bucketList = new ArrayList<Node>();
            buckets[index] = bucketList;
        }
        bucketList.add(new Node(key, value));
    }

    private int findBucket(K key, int numBuckets) {
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    private int findBucket(K key) {
        return findBucket(key, buckets.length);
    }

    private void reBucket(int targetSize) {
        ArrayList<Node>[] newBuckets = (ArrayList<Node>[]) new ArrayList[targetSize];
        for (K key : keys) {
            int idx = findBucket(key, newBuckets.length);
            if (newBuckets[idx] == null) {
                newBuckets[idx] = new ArrayList<>();
            }
            newBuckets[idx].add(getNode(key));
        }
        buckets = newBuckets;
    }

}
