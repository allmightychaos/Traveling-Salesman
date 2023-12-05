import java.util.*;

public class PermutationsIterator<E> implements Iterator<List<E>> {
    private E[] arr;
    private boolean isFirstIteration = true;

    public PermutationsIterator(List<E> original) {
        this.arr = (E[]) original.toArray();
    }

    @Override
    public boolean hasNext() {
        if (isFirstIteration) {
            isFirstIteration = false;
            return true;
        }
        return findNextPermutation();
    }

    private boolean findNextPermutation() {
        // Find longest non-increasing suffix
        int i = arr.length - 1;
        while (i > 0 && ((Comparable<E>) arr[i - 1]).compareTo(arr[i]) >= 0) {
            i--;
        }
        // If the entire array is non-increasing, then this is the last permutation
        if (i <= 0) return false;

        // Find successor to pivot
        int j = arr.length - 1;
        while (((Comparable<E>) arr[j]).compareTo(arr[i - 1]) <= 0) {
            j--;
        }

        swap(i - 1, j);

        // Reverse the suffix
        j = arr.length - 1;
        while (i < j) {
            swap(i, j);
            i++;
            j--;
        }

        return true;
    }

    private void swap(int i, int j) {
        E temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    @Override
    public List<E> next() {
        return new ArrayList<>(Arrays.asList(arr));
    }
}
