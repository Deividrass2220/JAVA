package org.example;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.LinkedList;

// What is a set???
// * A collection that does not allow duplicates. Could be used for:
//   * Deduplication
//   * Set Logic: Union, Intersection, Difference
// * Common operations?
//   * add to the set
//   * contains an item?
//   * Iteration of the items
public class HashSet<K> {
    protected LinkedList<K>[] buckets;
    protected int count = 0;

    public HashSet(int initialSize) {
        this.buckets = new LinkedList[initialSize];
    }

    Iterator<K> iterator() {
        return new Iterator<K>() {
            // this is accessible as a Closure
            int currentBucket = -1; // so I can know I need to initialize during hasNext
            Iterator<K> currentBucketIterator;

            private void moveToNextPopulatedBucket(int startingFrom) {
                int bi;
                for (bi = startingFrom; bi < buckets.length; bi++) {
                    if (buckets[bi] != null && buckets[bi].iterator().hasNext()) {
                        break;
                    }
                }
                // IF bi is == length, then I walked off then END!
                currentBucket = bi;
                if (bi != buckets.length)
                    currentBucketIterator = buckets[bi].iterator();
            }

            @Override
            public boolean hasNext() {
                if (currentBucket == -1) {
                    moveToNextPopulatedBucket(0);
                }
                return (currentBucket < buckets.length) && currentBucketIterator.hasNext();
            }

            @Override
            public K next() {
                if (currentBucket == -1) {
                    moveToNextPopulatedBucket(0);
                }
                if (currentBucketIterator == null)
                    return null;
                if (currentBucketIterator.hasNext()) {
                    K result = currentBucketIterator.next();
                    if (!currentBucketIterator.hasNext())
                        moveToNextPopulatedBucket(currentBucket + 1);
                    return result;
                }
                return null;
            }
        };
    }

    /**
     * Puts the given value in the table at key. Overwrites prior values for that key.
     *
     * @param value The thing you want to put in the table.
     */
    public void add(K key) {
        int code = Math.abs(key.hashCode());
        int bucketIndex = code % this.buckets.length;
        if (this.buckets[bucketIndex] == null)
            this.buckets[bucketIndex] = new LinkedList<>();
        // TASK: Dedupe step...YOU HAVE THIS, I DON'T...
        this.buckets[bucketIndex].add(key);
        count++;
        if (needsResized())
            resize();
    }

    private void resize() {
        int newSize = buckets.length * 3;
        // Make a new table
        LinkedList<K>[] newBuckets = new LinkedList[newSize];
        // Copy the items from the old to the new, but at the correct location
        for (LinkedList<K> b : buckets) {
            if (b != null)
                for (K item : b) {
                    int newBucketIndex = Math.abs(item.hashCode()) % newSize;
                    if (newBuckets[newBucketIndex] == null)
                        newBuckets[newBucketIndex] = new LinkedList<>();
                    newBuckets[newBucketIndex].add(item);
                }
        }
        this.buckets = newBuckets;
    }

    private boolean needsResized() {
        return (buckets.length * 2 < count);
    }

    public boolean contains(K key) {
        int code = key.hashCode();
        int bucketIndex = code % this.buckets.length;
        LinkedList<K> list = buckets[bucketIndex];
        if (list != null)
            for (K e : list) {
                if (e == key)
                    return true;
            }
        return false;
    }

    public void delete(K key) {
        int code = key.hashCode();
        int bucketIndex = code % this.buckets.length;
        K me = null;
        LinkedList<K> list = buckets[bucketIndex];
        for (K e : list) {
            if (e == key) {
                me = e;
                break;
            }
        }
        if (me != null) {
            list.remove(me);
            count--;
        }
    }

    // this U b
    public HashSet<K> union(HashSet<K> b) {
        // TODO:
        return null;
    }

    // this I b
    public HashSet<K> intersect(HashSet<K> b) {
        // TODO:
        return null;
    }

    // this - b
    public HashSet<K> difference(HashSet<K> b) {
        // TODO:
        return null;
    }

    public int size() {
        return count;
    }

    public String toString() {
        StringWriter result = new StringWriter();
        result.append("Table has " + count + " entries\n");
        for (int i = 0; i < buckets.length; i++) {
            result.append(i + " -> ");
            if (buckets[i] != null)
                for (K e : buckets[i]) {
                    result.append("[" + e + "] -> ");
                }
            result.append("ø\n");
        }
        return result.toString();
    }

    public static void main(String[] args) {
        HashSet<String> data = new HashSet<>(2);
        data.add("A");
        data.add("B");
        data.add("C");
        data.add("D");
        System.out.println(data);

        Iterator<String> i = data.iterator();
        while (i.hasNext())
            System.out.print(i.next() + " ");
    }
}

