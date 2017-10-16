package org.hitbioinfo.exp1;



//commit on branch B1
//commit on branch C4


//b2


//commit by fake_fan

import java.util.*;

/***
 * The class of directed graph is now implemented in adjacent matrix.
 * So, unless allowing enlarging the mGraph dynamically, the mSize
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

    private int mSize;

    /* ----------------- Utility Function ----------------- */

    // Convert Integer to boolean
    private boolean toBoolean(Integer x) {
        return (x != 0);
    }

    // Test whether a vertex is in the graph.
    private boolean containsVertex(T vertex) {
        return dataToIndex.containsKey(vertex);
    }

    /* ----------------- Constructors ----------------- */

    public DirectedGraph(Set<T> wordsSet) {
        int size = wordsSet.size();
        mSize = size;

        // Initialize mGraph
        Integer[] temp1 = new Integer[size * size];
        Arrays.fill(temp1, 0);
        mGraph = Arrays.asList(temp1);

        // Initialize mVertexColor
        Boolean[] temp2 = new Boolean[size];
        Arrays.fill(temp2, false);
        mVertexColor = Arrays.asList(temp2);

        // Initialize mEdgeColor
        temp2 = new Boolean[size * size];
        Arrays.fill(temp2, false);
        mEdgeColor = Arrays.asList(temp2);

        // Initialize the two maps
        int index = 0;
        for (T tempStr : wordsSet) {
            dataToIndex.put(tempStr, index);
            indexToData.put(index, tempStr);
            ++index;
        }
    }

    /* ----------------- Primary Operations ----------------- */

    // Get the weight of arc v1 -> v2.
    public int getWeight(T v1, T v2) {
        // Throw exception when receiving bad parameters.
        if (! containsVertex(v1) || ! containsVertex(v2)) {
            throw new RuntimeException("Error in getWeight(T, T): The v1 (or v2) " +
                    "is not found in the DirectedGraph object!");
        }

        int index1 = dataToIndex.get(v1);
        int index2 = dataToIndex.get(v2);
        return mGraph.get(index1 * mSize + index2);
    }

    // Set the weight of arc v1 -> v2.
    public void setWeight(T v1, T v2, int weight) {
        // Throw exception when receiving bad parameters.
        if (! containsVertex(v1) || ! containsVertex(v2)) {
            throw new RuntimeException("Error in setWeight(T, T, int): The v1 (or v2) " +
                    "is not found in the DirectedGraph object!");
        }

        int index1 = dataToIndex.get(v1);
        int index2 = dataToIndex.get(v2);
        mGraph.set(index1 * mSize + index2, weight);
    }

    // Test whether there is an arc from v1 to v2.
    public boolean isArc(T v1, T v2) {
        // Throw exception when receiving bad parameters.
        if (! containsVertex(v1) || ! containsVertex(v2)) {
            throw new RuntimeException("Error in isArc(T, T): The v1 (or v2) is" +
                    " not found in the DirectedGraph object!");
        }

        int index1 = dataToIndex.get(v1);
        int index2 = dataToIndex.get(v2);
        return toBoolean(mGraph.get(index1 * mSize + index2));
    }

    // Add an arc from v1 to v2.
    public void addArc(T v1, T v2, Integer weight) {
        // Throw exception when receiving bad parameters OR there is already an arc from v1 to v2.
        if (! containsVertex(v1) || ! containsVertex(v2)) {
            throw new RuntimeException("Error in addArc(T, T, int): The v1 (or v2) " +
                    "is not found in the DirectedGraph object!");
        } else if (isArc(v1, v2)) {
            throw new RuntimeException("Error in addArc(T, T, int): There is already an arc from v1 to v2!");
        }

        // Add vertex into Map.
        int index1 = dataToIndex.get(v1);
        int index2 = dataToIndex.get(v2);
        mGraph.set(index1 * mSize + index2, weight);
    }

    // Return an set contain all the adjacent vertices to src.
    public List<T> adjacentVertices(T src) {
        // Throw exception when receiving bad parameters.
        if (! containsVertex(src)) {
            throw new RuntimeException("Error in adjacentVertices(T): The src " +
                    "is not found in the DirectedGraph object!");
        }

        List<T> adjVertices = new ArrayList<>();
        for (int i = 0; i < mSize; ++i) {
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
        // Throw exception when receiving bad parameters.
        if (! containsVertex(vertex)) {
            throw new RuntimeException("Error in isColored(T): The vertex " +
                    "is not found in the DirectedGraph object!");
        }

        int index = dataToIndex.get(vertex);
        return mVertexColor.get(index);
    }

    // Test whether the edge is colored.
    public boolean isColored(T v1, T v2) {
        // Throw exception when receiving bad parameters.
        if (! containsVertex(v1) || ! containsVertex(v2)) {
            throw new RuntimeException("Error in isColored(T, T): The v1 (or v2) is not " +
                    "found in the DirectedGraph object!");
        }

        int index1 = dataToIndex.get(v1);
        int index2 = dataToIndex.get(v2);
        return mEdgeColor.get(index1 * mSize + index2);
    }

    // Color a vertex. Return false if it is already colored.
    public void color(T vertex) {
        // Throw exception when receiving bad parameters.
        if (! containsVertex(vertex)) {
            throw new RuntimeException("Error in color(T): The vertex " +
                    "is not found in the DirectedGraph object!");
        } else if (isColored(vertex)) {
            throw new RuntimeException("Error in color(T, T): The vertex is already colored!");
        }

        int index = dataToIndex.get(vertex);
        mVertexColor.set(index, true);
    }

    // Color an edge. Return false if it is already colored.
    public void color(T v1, T v2) {
        // Throw exception when receiving bad parameters OR the edge is already colored.
        if (! containsVertex(v1) || ! containsVertex(v2)) {
            throw new RuntimeException("Error in color(T, T): The v1 (or v2) is not " +
                    "found in the DirectedGraph object!");
        } else if (isColored(v1, v2)) {
            throw new RuntimeException("Error in color(T, T): The arc from v1 to v2 is already colored!");
        }

        int index1 = dataToIndex.get(v1);
        int index2 = dataToIndex.get(v2);
        mEdgeColor.set(index1 * mSize + index2, true);
    }

    // Wipe all colors on vertices and edges.
    public void cleanColors() {
        for (int i = 0; i < mSize; ++i) {
            mVertexColor.set(i, false);
        }
        for (int i = 0; i < mSize * mSize; ++i) {
            mEdgeColor.set(i, false);
        }
    }
}
