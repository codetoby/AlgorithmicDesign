import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

import java.nio.charset.Charset;
import java.io.*;
import java.nio.file.Files;
import java.io.File;

public class Codelength {

    public static void main(String[] args) throws IOException {
        // Get Filename
        Scanner scanner = new Scanner(System.in);
        String filename = scanner.nextLine();
        scanner.close();
        // Read in File Content
        File infile = new File(filename);
        String text = Files.readString(infile.toPath(), Charset.defaultCharset());

        // remove spacing and non alphabetical characters
        text = text.replaceAll("[^A-Za-z]+", "").toLowerCase();

        text = addCharacters(text);
        
        int one = computeLength(text, 1);
        int two = computeLength(text, 2);
        int three = computeLength(text, 3);

        System.out.println(one + " " + two + " " + three);

    }

    private static int computeLength(String text, int c) {

        HashMap<String, Integer> frequencyTable = getFrequencyTable(text, c);
        PriorityQueue<Node> queue = getPriorityQueue(frequencyTable);

        Node root = getRoot(queue);

        HashMap<String, String> codes = new HashMap<>();
        computeCodes(root, codes, "");
        // System.out.println(codes);

        String encoded = getEncodedString(codes, text, c);
        int length = encoded.length();
        return length;
    }

    private static String getEncodedString(HashMap<String, String> codes, String text, int c) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < text.length(); i += c) {
            String character = text.substring(i, i + c);
            String code = codes.get(character);
            builder.append(code);
        }
        return builder.toString();

    }

    private static void computeCodes(Node root, HashMap<String, String> codes, String string) {
        if (root == null) {
            return;
        }
        if (root.getLeft() == null && root.getRight() == null) {
            codes.put(root.getLetter(), string);
            return;
        }
        computeCodes(root.getLeft(), codes, string + "0");
        computeCodes(root.getRight(), codes, string + "1");
    }

    private static Node getRoot(PriorityQueue<Node> queue) {

        if (queue.size() == 1) {
            Node node = queue.peek();
            Node node1 = new Node(node.getValue());
            node1.setLeft(node);
            return node1;
        }

        while (queue.size() > 1) {
            Node node1 = queue.poll();
            Node node2 = queue.poll();
            Node newNode = new Node(node1.getValue() + node2.getValue());
            newNode.setLeft(node1);
            newNode.setRight(node2);
            queue.add(newNode);
        }
        return queue.peek();
    }

    private static PriorityQueue<Node> getPriorityQueue(HashMap<String, Integer> frequencyTable) {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        for (String entry : frequencyTable.keySet()) {
            Node node = new Node(frequencyTable.get(entry), entry);
            queue.add(node);
        }
        return queue;
    }

    private static String addCharacters(String text) {
        int remainLength = text.length() % 6;
        if (remainLength != 0) {
            remainLength = 6 - remainLength;
        }
        for (int i = 0; i < remainLength; i++) {
            text += 'z';
        }
        return text;
    }

    private static HashMap<String, Integer> getFrequencyTable(String text, int c) {
        HashMap<String, Integer> frequency = new HashMap<>();
        int n = text.length();
        for (int i = 0; i < n; i += c) {
            String subString = text.substring(i, i + c);
            if (frequency.containsKey(subString)) {
                frequency.put(subString, frequency.get(subString) + 1);
            } else {
                frequency.put(subString, 1);
            }
        }
        return frequency;
    }
}

class Node implements Comparable<Node> {
    private int value;
    private String letter;
    private Node right, left = null;

    public Node(int value, String letter) {
        this.value = value;
        this.letter = letter;
    }

    public Node(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getLetter() {
        return letter;
    }

    public void setRight(Node node) {
        right = node;
    }

    public void setLeft(Node node) {
        left = node;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    @Override
    public int compareTo(Node o) {
        return value - o.getValue();
    }

    @Override
    public String toString() {
        return "[Value: " + value + ", Symbol: " + letter + "]";
    }
}