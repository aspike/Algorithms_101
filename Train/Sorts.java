package Train;

import java.util.Random;

/**
 * User: berdarm
 * Date: 26.09.15
 */
public class Sorts {
    private static Random rn = new Random();

    public static void main(String[] args) throws Exception {
        Integer[] nums = generateRandomSequence(43, 60);
        heapSort(nums);
        assert isSorted(nums);
    }

    public static void heapSort(Comparable a[]) {
        Heap heap = new Heap<>(a);
        for (int i = a.length - 1; i >= 0; i--) a[i] = heap.removeMax();
    }

    public static void selectionSort(Comparable a[]) {
        for (int i = 0; i < a.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < a.length; j++) {
                if (less(a[j], a[min])) min = j;
            }
            swap(a, min, i);
            print(a);
        }
    }

    public static void shellSort(Comparable a[]) {
        int[] sequence = generateKnuthSequence(a.length);
        for (int i = sequence.length - 1; i >= 0; i--) {
            int step = sequence[i];
            for (int j = step; j < a.length; j += step) {
                for (int k = j; k >= step; k -= step) {
                    if (less(a[k], a[k - step])) {
                        swap(a, k, k - step);
                    } else {
                        break;
                    }
                }
                print(a);
            }
        }
    }

    public static void insertionSort(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = i; j > 0; j--) {
                if (less(a[j], a[j - 1])) {
                    swap(a, j, j - 1);
                    print(a);
                } else {
                    break;
                }
            }
        }
    }

    /**
     * Standard recursive top-down merge-sort realisation
     *
     * @param a array of comparables to be sorted
     */
    public static void mergeSort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        mergeSort(a, 0, a.length - 1, aux);
    }

    public static void bottomUpMergeSort(Comparable a[]) {
        Comparable[] aux = new Comparable[a.length];
        for (int i = 1; i < a.length; i *= 2) {
            for (int j = 0; j < a.length - i; j += i * 2) {
                int mid = j + i - 1;
                int hi = Math.min(j + i + i - 1, a.length - 1);
                merge(a, aux, j, mid, hi);
                print(a);
            }
        }
    }

    private static void mergeSort(Comparable[] a, int lo, int hi, Comparable[] aux) {
        if (hi <= lo) return;
        int mid = (hi + lo) / 2;
        mergeSort(a, lo, mid, aux);
        mergeSort(a, mid + 1, hi, aux);
        merge(a, aux, lo, mid, hi);
        print(a);
    }

    private static void merge(Comparable a[], Comparable[] aux, int lo, int mid, int hi) {
        System.arraycopy(a, lo, aux, lo, hi + 1 - lo);

        int ai = lo, bi = mid + 1;
        for (int i = lo; i <= hi; i++) {
            if ((ai <= mid) && (bi > hi || less(aux[ai], aux[bi]))) {
                a[i] = aux[ai++];
            } else {
                a[i] = aux[bi++];
            }
        }
    }

    public static void quickSort(Comparable[] a) {
        quickSort(a, 0, a.length);
    }

    public static void quickSort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;

        int k = standardPartitioning(a, lo, hi);
        swap(a, lo, k);
        quickSort(a, lo, k);
        quickSort(a, k + 1, hi);
    }

    private static int standardPartitioning(Comparable a[], int lo, int hi) {
        int k = lo;
        for (int i = lo + 1; i < hi; i++) {
            if (less(a[i], a[lo])) swap(a, ++k, i);
        }
        return k;
    }

    private static void dijkstraPartitioning(Comparable a[], int lo, int hi, int lt, int gt) {
        lt = lo;
        gt = hi;
        for (int i = lo + 1; i < hi; i++) {
            if (less(a[i], a[lo]) && i > lt) swap(a, ++lt, i);
            else if (greater(a[i], a[lo]) && i < gt) swap(a, --gt, i--);
        }
    }

    private static int[] generateKnuthSequence(int length) {
        int sequenceLength = ((length - 1) / 3) - 1;
        int[] sequence = new int[sequenceLength];
        for (int i = 0; i < sequenceLength; i++) sequence[i] = (3 * i) + 1;
        return sequence;
    }

    private static Integer[] generateRandomSequence(int length, int upperBound) {
        Integer[] sequence = new Integer[length];
        for (int i = 0; i < length; i++) sequence[i] = rn.nextInt(upperBound);
        return sequence;
    }

    private static void print(Object[] a) {
        for (Object obj : a) System.out.print(obj + " ");
        System.out.println();
    }

    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static boolean lessEqual(Comparable a, Comparable b) {
        return a.compareTo(b) <= 0;
    }

    private static boolean equal(Comparable a, Comparable b) {
        return a.compareTo(b) == 0;
    }

    private static boolean greater(Comparable a, Comparable b) {
        return a.compareTo(b) > 0;
    }

    private static void swap(Comparable[] a, int i, int j) {
        if (i == j) return;
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static boolean isSorted(Comparable a[]) {
        for (int i = 1; i < a.length; i++) if (!lessEqual(a[i - 1], a[i])) return false;
        return true;
    }
}
