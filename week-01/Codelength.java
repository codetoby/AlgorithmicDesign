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
        String input = Files.readString(infile.toPath(), Charset.defaultCharset());

        // remove spacing and non alphabetical characters
        input = input.replaceAll("[^A-Za-z]+", "").toLowerCase();
        input = addMissingCharacters(input);

        int monographs = computeLength(input, 1);
        int digraphs = computeLength(input, 2);
        int trigraphs = computeLength(input, 3);

        System.out.println(monographs + " " + digraphs + " " + trigraphs);

    }

    private static int computeLength(String input, int c) {

        HashMap<Substring, Integer> frequencyTable = createFrequencyTable(input, c);
        PriorityQueue<HuffmanNode> priorityQueue = createPriorityQueue(frequencyTable);

        HuffmanNode tree = createHuffmanTree(priorityQueue);

        HashMap<Substring, String> huffmanCodes = new HashMap<>();
        computeHuffmanCodes(tree, huffmanCodes, "");

        String encodedText = encodeText(huffmanCodes, input, c);
        return encodedText.length();

    }

    private static String encodeText(HashMap<Substring, String> codes, String input, int c) {
        StringBuilder builder = new StringBuilder();

        int n = input.length();

        for (int i = 0; i < n; i += c) {
            Substring substring = new Substring(input.substring(i, i + c));
            String code = codes.get(substring);
            builder.append(code);
        }
        return builder.toString();

    }

    private static void computeHuffmanCodes(HuffmanNode root, HashMap<Substring, String> huffmanCodes,
            String currentCode) {
        if (root == null) {
            return;
        }
        if (root.getLeft() == null && root.getRight() == null) {
            huffmanCodes.put(root.getLetter(), currentCode);
            return;
        }
        computeHuffmanCodes(root.getLeft(), huffmanCodes, currentCode + "0");
        computeHuffmanCodes(root.getRight(), huffmanCodes, currentCode + "1");
    }

    private static HuffmanNode createHuffmanTree(PriorityQueue<HuffmanNode> queue) {

        if (queue.size() == 1) {
            HuffmanNode node = queue.poll();
            HuffmanNode newNode = new HuffmanNode(node.getValue());
            newNode.setLeft(node);
            return newNode;
        }

        while (queue.size() > 1) {
            HuffmanNode left = queue.poll();
            HuffmanNode right = queue.poll();
            HuffmanNode newNode = new HuffmanNode(left.getValue() + right.getValue());
            newNode.setLeft(left);
            newNode.setRight(right);
            queue.add(newNode);
        }

        return queue.peek();
    }

    private static PriorityQueue<HuffmanNode> createPriorityQueue(HashMap<Substring, Integer> frequencyTable) {
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();
        for (Substring substring : frequencyTable.keySet()) {
            queue.add(new HuffmanNode(substring, frequencyTable.get(substring)));
        }
        return queue;
    }

    private static String addMissingCharacters(String input) {
        int missing = input.length() % 6;
        if (missing != 0) {
            missing = 6 - missing;
        }
        for (int i = 0; i < missing; i++) {
            input += 'z';
        }
        return input;
    }

    private static HashMap<Substring, Integer> createFrequencyTable(String input, int c) {
        HashMap<Substring, Integer> frequencyTable = new HashMap<>();

        int n = input.length();

        for (int i = 0; i < n; i += c) {
            Substring subString = new Substring(input.substring(i, i + c));
            if (frequencyTable.containsKey(subString)) {
                frequencyTable.put(subString, frequencyTable.get(subString) + 1);
            } else {
                frequencyTable.put(subString, 1);
            }
        }
        return frequencyTable;
    }
}

class Substring {
    
    private String subString;

    public Substring(String subString) {
        this.subString = subString;
    }

    public String getSubString() {
        return subString;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Substring) {
            Substring other = (Substring) obj;
            return subString.equals(other.getSubString());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return subString.hashCode();
    }
}

class HuffmanNode implements Comparable<HuffmanNode> {
    private int value;
    private Substring letter;
    private HuffmanNode right, left = null;

    public HuffmanNode(Substring letter, int value) {
        this.value = value;
        this.letter = letter;
    }

    public HuffmanNode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Substring getLetter() {
        return letter;
    }

    public void setRight(HuffmanNode node) {
        right = node;
    }

    public void setLeft(HuffmanNode node) {
        left = node;
    }

    public HuffmanNode getLeft() {
        return left;
    }

    public HuffmanNode getRight() {
        return right;
    }

    @Override
    public int compareTo(HuffmanNode o) {
        return value - o.getValue();
    }

    @Override
    public String toString() {
        return "[Value: " + value + ", Symbol: " + letter + "]";
    }
}