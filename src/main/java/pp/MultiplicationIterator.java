package pp;

import java.util.Iterator;

import java.util.Iterator;

public class MultiplicationIterator implements Iterator<int[]> {
    private int [][] source;
    private int [] state;

    public MultiplicationIterator(int[][] source) {
        this.source = source;
        init();
    }

    private void init() {
        state = new int[source.length];
        state[0] = -1;
        for (int i = 1; i < source.length; ++i) {
            state[i] = 0;
        }
    }

    @Override
    public boolean hasNext() {
        for (int i = 0; i < state.length; ++i) {
            if (state[i] < source[i].length - 1) return true;
        }
        return false;
    }

    @Override
    public int[] next() {
        iterate();
        int [] result = new int[state.length];
        for (int i = 0; i < state.length; ++i) {
            result[i] = source[i][state[i]];
        }
        return result;
    }

    private void iterate() {
        for (int i = 0; i < state.length; ++i) {
            if (state[i] == source[i].length - 1) state[i] = 0;
            else {
                state[i]++;
                break;
            }
        }
    }

    @Override
    public void remove() {
        throw new RuntimeException();
    }
}

