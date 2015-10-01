package Week2;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * User: berdarm
 * Date: 25.07.15
 */

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int tail = 0;
    private Item[] items = (Item[]) new Object[2];

    /**
     * Construct an empty randomized queue
     */
    public RandomizedQueue() {
    }

    /**
     * @return is the queue empty ?
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * @return The number of items on the queue
     */
    public int size() {
        return tail;
    }

    /**
     * Add the item
     *
     * @param item - item to be added
     */
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();

        checkResize();
        items[tail++] = item;
    }

    /**
     * Remove and return a random item
     *
     * @return random item from queue
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();

        int index = StdRandom.uniform(size());
        Item item = items[index];

        items[index] = items[--tail];
        items[tail] = null;

        checkResize();
        return item;
    }

    /**
     * Return (but do not remove) a random item
     *
     * @return Random item
     */
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        return items[StdRandom.uniform(size())];
    }

    /**
     * @return Iterator for collection
     */
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Item[] iterItems = (Item[]) new Object[size()];
            private int current = -1;

            {
                for (int i = 0; i < tail; i++) {
                    iterItems[i] = items[i];
                    swap(iterItems, i, StdRandom.uniform(i + 1));
                }
            }

            public boolean hasNext() {
                return current + 1 != iterItems.length;
            }

            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                return iterItems[++current];
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private static void swap(Object[] arr, int i, int j) {
        Object tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private void checkResize() {
        if (size() == 1 && items.length == 4) reduce();
        else if (tail == items.length) extend();
        else if (tail < 3) return;
        else if (tail == items.length / 4) reduce();
    }

    private void extend() {
        Item[] newItems = (Item[]) new Object[items.length * 2];
        int index = 0;

        for (Item item : this) newItems[index++] = item;

        items = newItems;
        tail = index;
    }

    private void reduce() {
        Item[] newItems = (Item[]) new Object[items.length / 2];
        int index = 0;

        for (Item item : this) newItems[index++] = item;

        items = newItems;
        tail = index;
    }

    public static void main(String[] args) {
//        Week2.RandomizedQueue<Integer> deq = new Week2.RandomizedQueue<Integer>();
//        for (int i = 0; i < 1000; i++) {
//            Random rn = new Random();
//            try {
//                System.out.print(i + " : ");
//                float val = rn.nextFloat();
//                if (val < 0.5f) {
//                    System.out.print("en  ");
//                    deq.enqueue(i);
//                } else if (val < 0.9f) {
//                    System.out.print("de  ");
//                    deq.dequeue();
//                } else {
//                    System.out.print("sm   ");
//                    deq.sample();
//                }
//            } catch (Exception e) {
//                if (e instanceof ArrayIndexOutOfBoundsException) {
//                    e.printStackTrace();
////                    System.out.println("Error " + deq.index);
//
//                }
//            }
//            deq.print();
//        }

    }

//    public void print() {
//        for (Item item : items) {
//            System.out.print((item == null ? "_" : item) + " ");
//        }
//        System.out.print("(" + tail + ")");
//        System.out.println();
//    }
}
