package org.hitbioinfo.exp1;



//commit on branch B1
//commit on branch C4


//b2


// commit by fake_fan

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            run(args);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    // It is A utility function to run the main process of the program.
    // The purpose of this construction of the Main class is for the separation of the other codes from TRY BLOCK.
    private static void run(String[] args) {
        // Process different parameters.
        if (args.length == 0 || args[0].equals("-h")) {
            // Print the help text.
            System.out.println("Usage: wordsToGraph [-h] <filename>");
        } else if (args.length == 1) {
            /* ----------------- Construction of Words Graph ----------------- */

            // Read in the file.
            File inputFile = new File(args[0]);
            if (inputFile.isDirectory()) {       // Check whether the file is a directory.
                System.out.println("Attention: '" + args[0]
                        + "' is a directory. See help text with argument '-h'.");
                return;
            }

            // Read in the text.
<<<<<<< HEAD
            Scanner in;
            try {
                in = new Scanner(inputFile, "UTF-8");
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Attention: The file is not found!");
            }
            StringBuilder builder = new StringBuilder();
            while (in.hasNextLine()) {
                builder.append(in.nextLine());
=======
            // AND HANDLE THE EXCEPTION.
            Scanner in = null;
            StringBuilder builder;
            try {
                in = new Scanner(inputFile, "UTF-8");
                builder = new StringBuilder();
                while (in.hasNextLine()) {
                    builder.append(in.nextLine());
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Attention: The file is not found!");
            } finally {
                if (in != null) {
                    in.close();
                }
>>>>>>> partner/master
            }
            String rawInput = builder.toString();

            // Generate a new graph.
            WordsGraph wordsGraph = new WordsGraph(rawInput);

            /* ----------------- Interaction Begins ----------------- */

            // Show the generated graph.
            System.out.println("The Generated graph is showed and saved.");
            wordsGraph.showDirectedGraph();
            System.out.println();

            while (true) {
                System.out.println("Choose your operation:");
                System.out.println("1.Query a bridge word:");
                System.out.println("2.Generate new text.");
                System.out.println("3.Get the shortest path(s) from word1 to word2.");
                System.out.println("4.Get all shortest paths from one word to other words.");
                System.out.println("5.Traverse the graph randomly.");
                System.out.println("6.Stop the program.");
                System.out.println("Take one operation by inputting the corresponding number:");

                int opNumber;

                while (true) {

                    String rawOpNumber;
                    in = new Scanner(System.in);
                    rawOpNumber = in.nextLine();
                    boolean breakInLoop = false;
                    for (int i = 0; i < rawOpNumber.length(); i++) {
                        if (! Character.isDigit(rawOpNumber.charAt(i))){
                            breakInLoop = true;
                            break;
                        }
                    }
                    if (rawOpNumber.length() == 0) {
                        breakInLoop = true;
                    }
                    if (breakInLoop) {
                        System.out.println("Invalid input! Please input again:");
                        continue;
                    }
                    int tempNum = Integer.parseInt(rawOpNumber);
                    if (tempNum >= 7 || tempNum <= 0) {
                        System.out.println("Invalid input! Please input again:");
                    } else {
                        opNumber = Integer.parseInt(rawOpNumber);
                        break;
                    }
                }

                boolean breakInLoop = false;

                switch (opNumber) {
                    case 1: {   // Find bridge words from word1 to word2.
                        System.out.println("Please input two words to find their bridge words.");
                        System.out.print("Word 1: ");

                        in = new Scanner(System.in);    // Set "in" as "System.in".
                        String word1 = in.next();
                        System.out.print("Word 2: ");
                        String word2 = in.next();

                        if (!wordsGraph.containsWords(word1) || !wordsGraph.containsWords(word2)) {
                            wordsNotFound(word1, word2, wordsGraph);
                        } else {
                            String[] bridgeWords = wordsGraph.queryBridgeWords(word1, word2);

                            if (bridgeWords.length == 0) {
                                System.out.println("No bridge words from '" + word1 + "' to '" + word2 + " '!");
                            } else if (bridgeWords.length == 1) {
                                System.out.println("The bridge word from '" + word1 + "' to '" + word2 + "' is: "
                                        + bridgeWords[0] + ".");
                            } else {
                                System.out.print("The bridge words from '" + word1 + "' to '" + word2 + "' are: ");

                                // Print words except the last one in the FOR LOOP.
                                for (int i = 0; i < bridgeWords.length - 2; ++i) {
                                    System.out.print(bridgeWords[i] + ", ");
                                }
                                System.out.println(bridgeWords[bridgeWords.length - 2] + " and "
                                        + bridgeWords[bridgeWords.length - 1] + ".");
                            }
                        }
                        System.out.println();

                        in.nextLine();
                        break;
                    } case 2: { // Generate new text in terms of the newly input text and the graph.
                        System.out.println("Please input a text in a single line:");
                        String inputText = in.nextLine();
                        System.out.println("The text is:");
                        System.out.println(wordsGraph.generateNewText(inputText));
                        System.out.println();
                        break;
                    } case 3: { // Get the shortest path(s) from word1 to word2 in the graph.
                        System.out.println("Please input two words to find shortest path(s) from word1 to word2.");
                        System.out.print("Word1: ");
                        String word1 = in.next();
                        System.out.print("Word2: ");
                        String word2 = in.next();

                        if (!wordsGraph.containsWords(word1) || !wordsGraph.containsWords(word2)) {
                            wordsNotFound(word1, word2, wordsGraph);
                        } else {
                            String[] paths = wordsGraph.calcShortestPath(word1, word2);
                            if (paths.length == 0) {
                                System.out.println("There is no path from word1 to word2.");
                            } else if (paths.length == 1) {
                                System.out.println("The shortest path is:");
                                System.out.println(paths[0]);
                            } else {
                                System.out.println("The shortest paths are:");
                                for (String path : paths) {
                                    System.out.println(path);
                                }
                            }
                        }
                        System.out.println();
                        break;
                    } case 4: {
                        // Get all shortest paths from one word to other words.
                        System.out.println("Please input a word to find all shortest path(s) to other words if existed:");
                        String word = in.next();
                        if (!wordsGraph.containsWords(word)) {
                            wordsNotFound(word);
                        } else {
                            String[] paths = wordsGraph.calcShortestPath(word);
                            for (String path : paths) {
                                System.out.println(path);
                            }
                        }
                        System.out.println();
                        break;
                    } case 5: { // Traverse the graph from a random word as a start point.
                                // And write the result into disk.
                        System.out.println("The random traversal path is:");
                        wordsGraph.resetWalk();
                        builder = new StringBuilder();
                        String presentWord;

                        while (true) {
                            presentWord = wordsGraph.randomWalk();

                            // Test whether the walk is over.
                            if (presentWord.equals("")) {
                                System.out.println("The walk is over.");
                                System.out.println();
                                break;
                            }

                            builder.append(presentWord).append(" ");
                            System.out.print("The present word in walk is: "
                                    + presentWord + ". Input 'y' to continue.  ");
                            String ans = in.next();

                            if (!ans.equals("y") && !ans.equals("Y")) {   // Stopped by user's input.
                                System.out.println("The walk is over.");
                                System.out.println();
                                break;
                            }
                        }

                        String walkTrace = builder.toString();

                        // Write down the result into a file.
                        Path outputFile = Paths.get("random-walk-trace.txt");
                        try {
                            Files.write(outputFile, walkTrace.getBytes("UTF-8"));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    } case 6: {
                        breakInLoop = true;
                        break;
                    } default: {
                        throw new RuntimeException("Error: Unexpected switch case.");

                    }
                }
                if (breakInLoop) {
                    break;
                }
            }
        } else {

            System.out.println("Attention: Bad parameter. See 'wordsToGraph -h'.");
        }
    }

    // Two utility functions to print the "WordsNotFound" prompt.
    private static void wordsNotFound(String word) {
        System.out.println("No '" + word + "' in the graph!");
    }

    private static void wordsNotFound(String word1, String word2, WordsGraph graph) {
        if (graph.containsWords(word1) && !graph.containsWords(word2)) {
            System.out.println("No '" + word2 + "' in the graph!");
        } else if (!graph.containsWords(word1) && graph.containsWords(word2)) {
            System.out.println("No '" + word1 + "' in the graph!");
        } else if (!graph.containsWords(word1) && !graph.containsWords(word2)) {
            System.out.println("No '" + word1 + "' and '" + word2 + "' in the graph!");
        } else {
            throw new RuntimeException("Error: Bad parameters in wordsNotFound().");
        }
    }
}
