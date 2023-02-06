import java.util.Arrays;

class IntList {
    private int[] array;
    private int lastIndex = 0;

    public IntList() {
        array = new int[1];
    }

    public IntList(int val) {
        array = new int[2];
        array[0] = val;
        lastIndex = 1;
    }

    public int size() {
        return lastIndex;
    }

    public void change(int idx, int val) {
        if (idx >= array.length) {
            array = Arrays.copyOf(array, Integer.max(idx + 1, array.length * 2));
        }
        lastIndex = Integer.max(lastIndex, idx + 1);
        array[idx] = val;
    }

    public void add(int val) {
        change(lastIndex, val);
    }

    public int get(int idx) {
        return array[idx];
    }

    public int pop() {
        lastIndex--;
        return array[lastIndex];
    }
}