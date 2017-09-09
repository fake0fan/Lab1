package org.hitbioinfo.exp1;

import java.util.Arrays;
import java.util.List;

public class DirectedGraph {
    private List<Integer> mGraph;
    private List<Boolean> mColor;
    private int mSize;

    // Utility function to convert Integer to boolean
    private boolean toBoolean(Integer x) {
        return (x != 0);
    }

    // Constructor
    public DirectedGraph(int size) {
        mSize = size;

        // Initialize mGraph
        Integer[] temp1 = new Integer[size * size];
        Arrays.fill(temp1, 0);
        mGraph = Arrays.asList(temp1);

        // Initialize mColor
        Boolean[] temp2 = new Boolean[size];
        Arrays.fill(temp2, false);
        mColor = Arrays.asList(temp2);
    }

    // Test whether there is an arc from v1 to v2.
    public boolean isArc(int v1, int v2) {
        return toBoolean(mGraph.get(v1 * mSize + v2));
    }

    // Add an arc from v1 to v2. Return false if there is already an arc.
    public boolean addArc(int v1, int v2, Integer weight) {
        if (isArc(v1, v2)) {
            return false;
        }
        mGraph.set(v1 * mSize + v2, weight);
        return true;
    }

    // Test whether the vertex is colored.
    public boolean isColored(int vertex) {
        return mColor.get(vertex);
    }

    // Color a vertex. Return false if it is already colored.
    public boolean colorVertex(int vertex) {
        if (isColored(vertex)) {
            return false;
        }
        mColor.set(vertex, true);
        return true;
    }

    // Wipe all colors on vertices.
    public void cleanColors() {
        for (int i = 0; i < mSize; ++i) {
            mColor.set(i, false);
        }
    }
}
