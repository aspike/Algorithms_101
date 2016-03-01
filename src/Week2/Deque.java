package Week2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * User: berdarm
 * Date: 13.07.15
 */
public class Deque<Item> implements Iterable<Item> {
    private int head = 0;
    private int tail = 1;
    private int size = 0;
    private Item[] items = (Item[]) new Object[2];

    /**
     * Check if queue is empty
     *
     * @return true if empty false if not
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Return the number of items on the deque
     *
     * @return int - size of Week2.Deque
     */
    public int size() {
        return size;
    }

    /**
     * Add the item to the front
     *
     * @param item add item to beginning of the queue
     */
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
        checkResize();

        if (items[head] == null) {
            items[head] = item;
        } else {
            int newHead = head - 1;
            if (newHead < 0) newHead = items.length - 1;
            items[newHead] = item;
            head = newHead;
        }
        ++size;
    }

    /**
     * Add the item to the end
     *
     * @param item add item to the end of deque
     */
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();
        checkResize();

        items[tail] = item;
        if (items[head] == null) head = tail;
        tail = (tail + 1) % items.length;
        ++size;
    }

    /**
     * Remove and return the item from the front
     *
     * @return Item removed
     */
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = items[head];
        items[head] = null;
        int newHead = (head + 1) % items.length;
        if (newHead != tail) head = newHead;
        --size;
        checkResize();

        return item;
    }

    /**
     * Remove and return the item from the end
     *
     * @return item removed
     */
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();

        int newTail = tail - 1;
        if (newTail < 0) newTail = items.length - 1;
        Item item = items[newTail];
        items[newTail] = null;
        if (newTail != head) tail = newTail;
        --size;
        checkResize();

        return item;
    }

    /**
     * Return an iterator over items in order from front to end
     *
     * @return iterator
     */
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private int current = head - 1;

            @Override
            public boolean hasNext() {
                return (current + 1) % items.length != tail && !isEmpty();
            }

            @Override
            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                current = (current + 1) % items.length;
                return items[current];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Remove is not supported");
            }
        };
    }

    /**
     * Double array size
     */
    private void extend() {
        Item[] newItems = (Item[]) new Object[items.length * 2];

        int index = newItems.length / 4;
        for (Item item : this) newItems[index++] = item;
        head = newItems.length / 4;
        tail = index;
        items = newItems;

    }

    /**
     * Reduce array size by 2
     */
    private void reduce() {
        Item[] newItems = (Item[]) new Object[items.length / 2];

        int index = newItems.length / 4;
        for (Item item : this) newItems[index++] = item;
        head = newItems.length / 4;
        tail = index;
        if (head == tail) tail++;
        items = newItems;

    }

    private void checkResize() {
        if (size == 1 && items.length == 4) reduce();
        else if (size == items.length - 1) extend();
        else if (size < 3) return;
        else if (size == items.length / 4) reduce();
    }

    public static void main(String[] args) {
    }
}
