package org.hitbioinfo.exp1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/***
 * The class of directed graph is now implemented in adjacent matrix.
 * So, unless allowing enlarging the mGraph dynamically, the mCapacity
 * should be denoted when initialize an object of the class.
 * I think it is stupid and less effective in implementation. And I will
 * reconstruct the class soon.
 *
 * @param <T>
 * @author ANDI_Mckee
 */
public class DirectedGraph<T> {
    /* ----------------- Instance Fields ----------------- */

    // Two maps
    private HashMap<Integer, T> indexToData = new HashMap<>();
    private HashMap<T, Integer> dataToIndex = new HashMap<>();

    private List<Integer> mGraph;
    private List<Boolean> mVertexColor;
    private List<Boolean> mEdgeColor;

    private int mSize = 0; // Store the number of the vertex present.
    private int mCapacity;

    // Utility function to convert Integer to boolean
    private boolean toBoolean(Integer x) {
        return (x != 0);
    }

    // Utility function to test whether arc v1 -> v2 can be added.
    private boolean isAbleToAdd(T v1, T v2) {
        if ((! dataToIndex.containsKey(v1) && ! dataToIndex.containsKey(v1)) && mSize == mCapacity - 1) {
            return false;
        } else if ((! dataToIndex.containsKey(v1) && dataToIndex.containsKey(v2)) && mSize == mCapacity) {
            return false;
        } else if ((dataToIndex.containsKey(v1) && ! dataToIndex.containsKey(v2)) && mSize == mCapacity) {
            return false;
        } else {
            return true;
        }
    }

    /* ----------------- Constructors ----------------- */

    public DirectedGraph(int capacity) {
        mCapacity = capacity;

        // Initialize mGraph
        Integer[] temp1 = new Integer[capacity * capacity];
        Arrays.fill(temp1, 0);
        mGraph = Arrays.asList(temp1);

        // Initialize mVertexColor
        Boolean[] temp2 = new Boolean[capacity];
        Arrays.fill(temp2, false);
        mVertexColor = Arrays.asList(temp2);

        // Initialize mEdgeColor
        temp2 = new Boolean[capacity * capacity];
        Arrays.fill(temp2, false);
        mEdgeColor = Arrays.asList(temp2);
    }

    /* ----------------- Primary Operations ----------------- */

    // Test whether a vertex is in the graph.
    public boolean containsVertex(T vertex) {
        return dataToIndex.containsKey(vertex);
    }

    // Return the number of vertices already added.
    public int size() {
        return mSize;
    }

    // Get the weight of arc v1 -> v2.
    public int getWeight(T v1, T v2) {
        // Throw exception when receiving bad parameters.
        if (! containsVertex(v1) || ! containsVertex(v2)) {
            throw new RuntimeException("Error: The v1 (or v2) in getWeight()" +
                    "is not found in the DirectedGraph object!");
        }

        int index1 = dataToIndex.get(v1);
        int index2 = dataToIndex.get(v2);
        return mGraph.get(index1 * mCapacity + index2);
    }

    // Set the weight of arc v1 -> v2.
    public void setWeight(T v1, T v2, int weight) {
        // Throw exception when receiving bad parameters.
        if (! containsVertex(v1) || ! containsVertex(v2)) {
            throw new RuntimeException("Error: The v1 (or v2) in setWeight()" +
                    "is not found in the DirectedGraph object!");
        }

        int index1 = dataToIndex.get(v1);
        int index2 = dataToIndex.get(v2);
        mGraph.set(index1 * mCapacity + index2, weight);
    }

    // Test whether there is an arc from v1 to v2.
    public boolean isArc(T v1, T v2) {
        // Throw exception when receiving bad parameters.
        if (! containsVertex(v1) || ! containsVertex(v2)) {
            throw new RuntimeException("Error: The v1 (or v2) in isArc() is not found in the DirectedGraph object!");
        }

        int index1 = dataToIndex.get(v1);
        int index2 = dataToIndex.get(v2);
        return toBoolean(mGraph.get(index1 * mCapacity + index2));
    }

    // Add an arc from v1 to v2. Return false if there is already an arc.
    public void addArc(T v1, T v2, Integer weight) {
        // Throw exception if the graph cannot be added more vertices.
        if (! isAbleToAdd(v1, v2)) {
            throw new RuntimeException("Error: DirectedGraph object is already full and cannot be added more!");
        }

        // Add vertex into Map.
        if (! dataToIndex.containsKey(v1)) {
            dataToIndex.put(v1, mSize);
            indexToData.put(mSize, v1);
            ++mSize;
        }

        if (! dataToIndex.containsKey(v2)) {
            dataToIndex.put(v2, mSize);
            indexToData.put(mSize, v2);
            ++mSize;
        }

        int index1 = dataToIndex.get(v1);
        int index2 = dataToIndex.get(v2);
        mGraph.set(index1 * mCapacity + index2, weight);
    }

    // Return an set contain all the adjacent vertices to src.
    public List<T> adjacentVertices(T src) {
        List<T> adjVertices = new ArrayList<T>();
        for (int i = 0; i < mCapacity; ++i) {
            T temp = indexToData.get(i);
            if (isArc(src, temp)) {
                adjVertices.add(temp);
            }
        }
        return adjVertices;
    }

    /* ----------------- Color ----------------- */

    // Test whether the vertex is colored.
    public boolean isColored(T vertex) {
        int index = dataToIndex.get(vertex);
        return mVertexColor.get(index);
    }

    // Test whether the edge is colored.
    public boolean isColored(T v1, T v2) {
        int index1 = dataToIndex.get(v1);
        int index2 = dataToIndex.get(v2);
        return mEdgeColor.get(index1 * mCapacity + index2);
    }

    // Color a vertex. Return false if it is already colored.
    public boolean color(T vertex) {
        if (isColored(vertex)) {
            return false;
        }

        int index = dataToIndex.get(vertex);
        mVertexColor.set(index, true);
        return true;
    }

    // Color an edge. Return false if it is already colored.
    public boolean color(T v1, T v2) {
        if (isColored(v1, v2)) {
            return false;
        }

        int index1 = dataToIndex.get(v1);
        int index2 = dataToIndex.get(v2);
        mEdgeColor.set(index1 * mCapacity + index2, true);
        return true;
    }

    // Wipe all colors on vertices and edges.
    public void cleanColors() {
        for (int i = 0; i < mCapacity; ++i) {
            mVertexColor.set(i, false);
        }
        for (int i = 0; i < mCapacity * mCapacity; ++i) {
            mEdgeColor.set(i, false);
        }
    }
}
