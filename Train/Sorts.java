package Train;

import java.util.Random;

/**
 * User: berdarm
 * Date: 26.09.15
 */
public class Sorts {

    public static void main(String[] args) throws Exception {
        Integer[] nums = generateRandomSequence(40, 60);
        nums = new Integer[]{80, 21, 89, 99, 44, 34, 52, 82, 41, 33};
        selectionSort(nums);
        if (!isSorted(nums)) throw new Exception("Fuck up");
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

    private static int[] generateKnuthSequence(int length) {
        int sequenceLength = ((length - 1) / 3) - 1;
        int[] sequence = new int[sequenceLength];
        for (int i = 0; i < sequenceLength; i++) sequence[i] = (3 * i) + 1;
        return sequence;
    }

    private static Integer[] generateRandomSequence(int length, int upperBound) {
        Integer[] sequence = new Integer[length];
        Random rn = new Random(4);
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

    private static void swap(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static boolean isSorted(Comparable a[]) {
        for (int i = 1; i < a.length; i++) if (!lessEqual(a[i - 1], a[i])) return false;
        return true;
    }
}
