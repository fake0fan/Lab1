# SE-Experiment-1
Implement a program which generates a directed graph with a text file.

<<<<<<< HEAD

=======
*This README plays the role of a project documentation.*
>>>>>>> partner/master

## Declaration
*This project contains binary file from [Graphviz](http://www.graphviz.org/) and codes from [this top answer](https://stackoverflow.com/questions/26481910/how-to-call-graphviz-from-java).*

## Architecture
1. Main class (main.java)
    1. It only contains the **logic**(NOT present in the README) of the entire program process.
    2. The valid parameter(s) of the program:
    ```
        -h         Print the help text.
        NONE       Print the help text.
        FILENAME   Run the program normally.
    ```
    3. The main function call the `void run(String[] args)` to perform the main process.
       The purpose of this design is to extract some code out of the `try` block.
2. A simple DirectedGraph class (DirectedGraph.java)

    The methods of this class are:
    ```java
    class DirectedGraph<T> {
        // Get the weight of arc v1 -> v2.
        int getWeight(T, T)
        
        // Set the weight of arc v1 -> v2.
        int setWeight(T, T, int)
    
        // Test whether there is an arc from v1 to v2.
        boolean isArc(T, T);
        
        // Add an arc from v1 to v2 with weight.
        void addArc(T, T, Integer);
        
        // Return an list contain all the adjacent vertices to src.
        List<T> adjacentVertices(T);
        
        // Test whether the vertex is colored.
        boolean isColored(T);
        
        // Test whether the edge is colored.
        boolean isColored(T, T);
        
        // Color a vertex.
        void colorVertex(T); 
        
        // Color an edge.
        void colorEdge(T, T); 
        
        // Wipe all colors on vertices and edges.
        void cleanColors(); 
    }
    ```

3. Words graph class (WordsGraph.java)
    1. Data member:
        ```java
        DirectedGraph<String> mWordsGraph;      // Store the words graph with index.
        Set<String> wordsSet = new HashSet<>(); // Store all the words.
        int mSize;                              // Store the size of words graph.
        String presentWordInWalk;               // The present word in the random walk.
        boolean flagOfWalk = false;             // Flag to indicate whether an arc is just visited twice in the walk.
        ```
    2. Private Methods:
        ```java
        // Get a random word in words set
        String randWord();
        
        /***
        * To display a words graph,
        * first, you should create a StringBuilder object and use createArcFormat() to form a dotFormat string.
        * then call createDotGraph() with dotFormat string you've just created,
        * and finally call the Desktop.getDesktop().open(File).
        *
        * Attention: isEmphasized indicates whether an arc should be displayed in red.
        */
        
        // Add an arc into graph-viz format file.
        String createArcFormat(String src, String tgt, int weight, boolean isEmphasized);
        
        // Create dot graph.
        void createDotGraph(String dotFormat,String fileName);
        ```
    
    3. Public Methods:
        1. [**Constructor**]Read in a file and translate it into a direct graph.
        
            **Interface:**
            
            |     Name    | Parameter Type | Return Value Type |
            |:-----------:|:--------------:|:-----------------:|
            |  WordsGraph |     String     |         -         |

        2. Show the graph and save it as a new picture file.
         
            **Interface:**
            
            |        Name       | Parameter Type | Return Value Type |
            |:-----------------:|:--------------:|:-----------------:|
            | showDirectedGraph |        -       |        void       |
         
        3. Get all **bridge words** from word1 to word2.
         
            **Interface:**
            
            |       Name       | Parameter Type | Return Value Type |
            |:----------------:|:--------------:|:-----------------:|
            | queryBridgeWords | String, String |      String[]     |

        4. Generate new text in terms of the newly input text and the graph.
         
            **Requirements:**
            
            1. If two words in the newly input text have *ONE* bridge word in the graph. Insert it into the text.
            2. If two words in the newly input text have *MORE THAN ONE* bridge word in the graph. Insert one randomly.
            3. If two words in the newly input text have no bridge word in the graph. Insert nothing.

            **Interface:**
            
            |       Name      | Parameter Type | Return Value Type |
            |:---------------:|:--------------:|:-----------------:|
            | generateNewText |     String     |       String      |
            
        5. Get the shortest path(s) from word1 to word2 in the graph.
         
            **Requirements:**
            
            1. If there are several shortest paths between them, return them all.
            2. If there is no shortest path between them, return an empty array.
            3. The shortest path(s) should be showed in the form like "to -> explore -> band".
            
            **Interface:**
            
            |       Name       | Parameter Type | Return Value Type |
            |:----------------:|:--------------:|:-----------------:|
            | calcShortestPath | String, String |      String[]     |
            
        6. Get all shortest paths from one word to other words.
         
            **Interface:**
            
            |       Name       | Parameter Type | Return Value Type |
            |:----------------:|:--------------:|:-----------------:|
            | calcShortestPath |     String     |      String[]     |
            
            **Requirements:**
            
            1. If there are several shortest paths from this word, return them all.
            2. If there is no shortest path from this word to another, mark it as "Unreachable".
            3. The shortest path(s) should be showed in the form like "to -> explore -> band".
            
        7. Traverse the graph from a random words as a start point.
         
            **Requirements:**
            
            1. The traversal path is random.
            2. Only outarcs can be visited during the traversal.
            3. The traversal stops whenever:
                1. An arc is visited twice.
                2. There is no next reachable word.
            4. The traversal path written ino file should be showed in the form like "to explore band".
            5. **This function return a visited word every time it is called. Use resetWalk() to reset the process.**
            
            **Interface:**
            
            |    Name    | Parameter Type | Return Value Type |
            |:----------:|:--------------:|:-----------------:|
            | randomWalk |        -       |       String      |
           
        8. [**EXTRA**]Reset the random walk.
        
            **Interface:**
            
            |    Name   | Parameter Type | Return Value Type |
            |:---------:|:--------------:|:-----------------:|
            | resetWalk |        -       |        void       |
            
        9. [**EXTRA**]Test whether a word is in the words graph.
        
            **Interface:**
            
            |      Name     | Parameter Type | Return Value Type |
            |:-------------:|:--------------:|:-----------------:|
            | containsWords |     String     |      boolean      |
            
## Collaborators
[@ANDI_Mckee](https://github.com/ANDI-Mckee)

[@fake0fan](https://github.com/fake0fan)
