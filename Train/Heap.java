package Train;

/**
 * User: berdarm
 * Date: 18.10.15
 */
public class Heap<T extends Comparable> {
    private T[] arr = (T[]) new Comparable[2];
    private int current = 0;

    public Heap() {
    }

    public Heap(T[] a) {
        this.arr = (T[]) new Comparable[arr.length * 2];
        for (T item : a) this.insert(item);
    }

    public void insert(T item) {
        if (item == null) throw new NullPointerException();
        if (current == arr.length) extend();
        arr[current] = item;
        swim(current++);
        assert isHeap();
    }

    public T removeMax() {
        swap(arr, 0, --current);
        T max = arr[current];
        arr[current] = null;
        sink(0);
        if (current == arr.length / 4) reduce();
        assert isHeap();
        return max;
    }

    public void printArr() {
        for (int i = 0; i < current; i++) System.out.print(arr[i] + " ");
    }

    public int size() {
        return current;
    }

    private void extend() {
        T[] newArr = ((T[]) new Comparable[arr.length * 2]);
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        arr = newArr;
    }

    private void reduce() {
        T[] newArr = ((T[]) new Comparable[arr.length / 2]);
        System.arraycopy(arr, 0, newArr, 0, current);
        arr = newArr;
    }

    private void swim(int index) {
        if (index == 0) return;
        if (less(arr[parent(index)], arr[index])) {
            swap(arr, parent(index), index);
            swim(parent(index));
        }
    }

    private void sink(int i) {
        T left = null, right = null;
        if (left(i) < arr.length) left = arr[left(i)];
        if (right(i) < arr.length) right = arr[right(i)];

        if (left == null && right == null) return;
        if (left != null && right != null) {
            if (less(left, right) && less(arr[i], right)) {
                swap(arr, i, right(i));
                sink(right(i));
            } else if (less(arr[i], left)) {
                swap(arr, i, left(i));
                sink(left(i));
            }
        } else if (left == null) {
            if (less(arr[i], right)) {
                swap(arr, i, right(i));
                sink(right(i));
            }
        } else {
            if (less(arr[i], left)) {
                swap(arr, i, left(i));
                sink(left(i));
            }
        }
    }

    private boolean isHeap() {
        for (int i = 0; i < current / 2; i++) {
            if (arr[i] == null) return true;
            int left = left(i);
            int right = right(i);
            if (left < arr.length && arr[left] != null && !lessEqual(arr[left], arr[i])
                    || (right < arr.length && arr[right] != null && !lessEqual(arr[right], arr[i]))) {
                return false;
            }

        }
        return true;
    }

    private static int parent(int index) {
        return (index - 1) / 2;
    }

    private static int left(int index) {
        return ++index * 2 - 1;
    }

    private static int right(int index) {
        return ++index * 2;
    }

    private T max(T a, T b) {
        if (a.compareTo(b) < 0) return b;
        else return a;
    }

    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static boolean lessEqual(Comparable a, Comparable b) {
        return a.compareTo(b) <= 0;
    }

    private static boolean greater(Comparable a, Comparable b) {
        return a.compareTo(b) > 0;
    }

    private static void swap(Object[] arr, int i, int j) {
        if (i == j) return;
        Object t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }
}
