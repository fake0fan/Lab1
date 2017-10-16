package org.hitbioinfo.exp1;



//commit on branch B1

//commit on branch C4


//b2


//commit by fake_fan

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class WordsGraph {
    /* ----------------- Instance Filed ----------------- */

    private DirectedGraph<String> mWordsGraph;
    private int mSize;  // Store the number of vertices.
    private Set<String> wordsSet = new HashSet<>(); // Store all the words present.
    private String presentWordInWalk;   // The present word in the random walk.
    private boolean flagOfWalk = false; // Flag to indicate whether an arc is just visited twice in the walk.
    private static final int INF = 1000000007; // Init the distance;
    private HashMap<String, Integer> ShortDistance = new HashMap<>(); // Record the distance from a special word
    private HashMap<String, Boolean> VerticesInQueue = new HashMap<>(); // Record the distance from a special word
    private HashMap<String, Vector<String>> Front = new HashMap<>(); // Record the distance from a special word
    private List<String> RecordPath = new ArrayList<>(); // Record the distance from a special word
    private List<String> WordPath = new ArrayList<>();

    /* ----------------- Utility Function ----------------- */

    // Get a random word in words set
    private String randWord() {
        int randomIndex = new Random().nextInt(mSize);
        int i = 0;
        String aRandWord = "";
        for (String tempStr : wordsSet) {
            if (i == randomIndex) {
                aRandWord = tempStr;
            }
            ++i;
        }

        if (aRandWord.equals("")) {
            throw new RuntimeException("Error: Failed to generate random word in randWord()!");
        }
        return aRandWord;
    }

    // Add an arc into graph-viz format file.
    private String createArcFormat(String src, String tgt, int weight, boolean isEmphasized) {
        StringBuilder builder = new StringBuilder();
        builder.append(src).append("->").append(tgt).append("[label=").append(weight);
        if (isEmphasized) {
            builder.append(" color=red];");
        } else {
            builder.append("];");
        }

        return builder.toString();
    }

    // Create dot graph.
    private void createDotGraph(String dotFormat, String fileName) {
        GraphViz gv = new GraphViz();
        gv.addln(gv.start_graph());
        gv.add(dotFormat);
        gv.addln(gv.end_graph());
        String type = "gif";
        gv.increaseDpi();
        gv.increaseDpi();
        gv.increaseDpi();
        File out = new File(fileName + "." + type);
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
    }

    private void ShortPath(String word) {
        for (String i : wordsSet) {
            ShortDistance.put(i, INF);
            VerticesInQueue.put(i, Boolean.FALSE);
            Front.put(i, new Vector<>());
            Front.get(i).add("");
        }
        ShortDistance.put(word, 0);
        Queue<String> q = new ArrayDeque<>();
        q.add(word);
        while (!q.isEmpty()) {
            String now = q.remove();
            List<String> adjVertices = mWordsGraph.adjacentVertices(now);
            for (String i : adjVertices) {
                if (ShortDistance.get(i) > ShortDistance.get(now) + mWordsGraph.getWeight(now, i)) {
                    ShortDistance.put(i, ShortDistance.get(now) + mWordsGraph.getWeight(now, i));
                    Front.put(i, new Vector<>());
                    Front.get(i).add(now);
                    if (!VerticesInQueue.get(i)) {
                        q.add(i);
                        VerticesInQueue.put(i, Boolean.TRUE);
                    }
                } else if (ShortDistance.get(i) == ShortDistance.get(now) + mWordsGraph.getWeight(now, i)) {
                    Front.get(i).add(now);
                }
            }
            VerticesInQueue.put(now, Boolean.TRUE);
        }

    }

    private void GetPath(String word) {

        if(word.equals("")){
            StringBuilder tempPath = new StringBuilder(WordPath.get(WordPath.size() - 1));
            for(int i = WordPath.size() - 2; i >= 0; i --){
                tempPath.append(" -> ").append(WordPath.get(i));
            }
            RecordPath.add(tempPath.toString());
            return;
        }
        WordPath.add(word);
        for(int i = 0; i < Front.get(word).size(); i ++) {
            GetPath(Front.get(word).elementAt(i));
        }
        WordPath.remove(WordPath.size() - 1);
    }



    /* ----------------- Constructors ----------------- */

    public WordsGraph(String str) {
        /* Modify specific character. */

        StringBuilder builder = new StringBuilder(str);
        for (int i = 0; i < builder.length(); ++i) {
            char temp = builder.charAt(i);
            if (Character.isLetter(temp)) {
                builder.setCharAt(i, Character.toLowerCase(temp));
            } else {
                builder.setCharAt(i, ' ');
            }
        }

        Scanner strScanner = new Scanner(builder.toString());

        /* Construct the words graph. */

        // Count how many words present and use the size to initialize the graph.
        // This operation is needed because of the bad design of graph with adjacent matrix.
        // It should be solved.
        while (strScanner.hasNext()) {
            wordsSet.add(strScanner.next());
        }
        mSize = wordsSet.size();
        mWordsGraph = new DirectedGraph<>(wordsSet);

        if (mSize == 0 || mSize == 1) { // If there is no word...
            return;
        }

        strScanner = new Scanner(builder.toString());
        String srcWord = strScanner.next();

        // The main part of the construction.
        while (strScanner.hasNext()) {
            // Read the next word.
            String tgtWord = strScanner.next();

            // Add an arc.
            if (mWordsGraph.isArc(srcWord, tgtWord)) {  // If the arc already exists...
                int tempWeight = mWordsGraph.getWeight(srcWord, tgtWord);
                mWordsGraph.setWeight(srcWord, tgtWord, tempWeight + 1);
            } else {
                mWordsGraph.addArc(srcWord, tgtWord, 1);
            }

            srcWord = tgtWord;
        }
<<<<<<< HEAD
=======
        strScanner.close();
>>>>>>> partner/master
    }

    /* ----------------- Primary Methods ----------------- */

    public void showDirectedGraph() {
        StringBuilder builder = new StringBuilder();

        // Create dot format string.
        for (String src : wordsSet) {
            List<String> adjVertices = mWordsGraph.adjacentVertices(src);
            if (adjVertices.size() == 0) {
                continue;
            }
            for (String tgt : adjVertices) {
                String temp;
                int weight = mWordsGraph.getWeight(src, tgt);
                temp = createArcFormat(src, tgt, weight, false);
                builder.append(temp);
            }
        }

        createDotGraph(builder.toString(), "wordsGraph");
        try {
            Desktop.getDesktop().open(new File("wordsGraph.gif"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] queryBridgeWords(String word1, String word2) {
        List<String> tempBridgeWords = new ArrayList<>();
        if (!wordsSet.contains(word1))
            return new String[]{};
        List<String> adjVertices = mWordsGraph.adjacentVertices(word1);
        for (String tempWords : adjVertices) {
            List<String> tempAdjVertices = mWordsGraph.adjacentVertices(tempWords);
            for (String nextTempWords : tempAdjVertices) {
                if (nextTempWords.equals(word2)) {
                    tempBridgeWords.add(tempWords);
                    break;
                }
            }
        }
        String[] BridgeWords = new String[tempBridgeWords.size()];
        for (int i = 0; i < tempBridgeWords.size(); i++) {
            BridgeWords[i] = tempBridgeWords.get(i);
        }
        return BridgeWords;
    }

    public String generateNewText(String aString){
        List<String> WordList = new ArrayList<>();
        StringBuilder builder = new StringBuilder(aString.replaceAll("[^a-zA-Z]+[^a-zA-Z]*", " "));
        for(int i = 0; i < builder.length(); i ++){
            if( (builder.charAt(i) >= 'a' && builder.charAt(i) <= 'z')
              ||(builder.charAt(i) >= 'A' && builder.charAt(i) <= 'Z')){
                StringBuilder temp = new StringBuilder("");
                int j;
                for(j = i; j < builder.length() && !Character.isWhitespace(builder.charAt(j)); j ++){
                    temp.append(builder.charAt(j));
                }
                WordList.add(temp.toString());
                i = j;
            }
        }
        if (WordList.size() < 1) return aString;
        StringBuilder newText = new StringBuilder("");
        newText.append(WordList.get(0));
        for (int i = 1; i < WordList.size(); i++) {
            String[] BridgeWords = queryBridgeWords(WordList.get(i - 1).toLowerCase(), WordList.get(i).toLowerCase());
            if (BridgeWords.length > 0) {
                int p = new Random().nextInt(BridgeWords.length);
                newText.append(" ").append(BridgeWords[p]);
            }
            newText.append(" ").append(WordList.get(i));
        }
        return newText.toString();
    }

    public String[] calcShortestPath(String word) {
        ShortPath(word);
        String[] Path = new String[mSize - 1];
        int cnt = 0;
        for (String i : wordsSet) {
            if (i.equals(word)) continue;
            if (ShortDistance.get(i).equals(INF)) {
                Path[cnt++] = word + " -> " + i + ": Unreachable";
                continue;
            }
            StringBuilder P = new StringBuilder("");
            Stack<String> Q = new Stack<>();
            String temp = i;
            while (!Front.get(temp).elementAt(0).equals("")) {
                Q.push(temp);
                temp = Front.get(temp).elementAt(0);
            }
            P.append(word);
            while (!Q.empty()) {
                P.append(" -> ").append(Q.pop());
            }
            Path[cnt++] = P.toString();
        }
        return Path;
    }

    public String[] calcShortestPath(String word1, String word2) {
        ShortPath(word1);
        if (ShortDistance.get(word2).equals(INF)) {
            return new String[]{};
        }
        RecordPath = new ArrayList<>();
        GetPath(word2);
        String[] ShortestPath = new String[RecordPath.size()];
        for (int i = 0; i < RecordPath.size(); i++) {
            ShortestPath[i] = RecordPath.get(i);
        }
        return ShortestPath;

    }

    public String randomWalk() {
        // Entry and Exit.
        if (presentWordInWalk == null) {    // Test whether it is the first word of walk.
            presentWordInWalk = randWord();
        } else if (presentWordInWalk.equals("")) { // Test whether it is the last word of walk.
            return "";
        }

        // Get all the adjacent words to the present word.
        List<String> adjVertices = mWordsGraph.adjacentVertices(presentWordInWalk);

        // Test whether there is no out-arcs from presentWordInWalk OR an arc is just visited twice.
        if (adjVertices.size() == 0 || flagOfWalk) {
            String tempStr = presentWordInWalk;
            presentWordInWalk = "";
            return tempStr;
        }

        // Select a random target and add it.
        int adjSize = adjVertices.size();
        Random rand = new Random();
        String tgtWord = adjVertices.get(rand.nextInt(adjSize));

        // Test whether the edge is already visited.
        if (mWordsGraph.isColored(presentWordInWalk, tgtWord)) {
            flagOfWalk = true;
        } else {
            mWordsGraph.color(presentWordInWalk, tgtWord);
        }

        // Update presentWordInWalk.
        String tempStr = presentWordInWalk;
        presentWordInWalk = tgtWord;
        return tempStr;
    }

    /* ----------------- Extra Methods ----------------- */

    // Reset the random walk.
    public void resetWalk() {
        presentWordInWalk = null;
        flagOfWalk = false;
    }

    // Test whether a word is in the words graph.
    public boolean containsWords(String word) {
        return wordsSet.contains(word);
    }
}
