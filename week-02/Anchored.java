import java.util.Scanner;
import java.io.*;

public class Anchored {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        String filename = scanner.nextLine();
        scanner.close();

        File infile = new File(filename);
        String s1, s2;
        try (Scanner input = new Scanner(infile)) {
            s1 = input.nextLine();
            s2 = input.nextLine();
        }

        Anchored anchored = new Anchored();
        int longestSubsequence = anchored.lcs(s1, s2);
        System.out.println(longestSubsequence);

    }
    
    public int lcs(String s1, String s2) {

        int[][] L = new int[s1.length() + 1][s2.length() + 1];

        int maxAnchoredLength = 0;

        for (int i = 1; i < L.length; i++) {
            for (int j = 1; j < L[0].length; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    L[i][j] = 1 + L[i - 1][j - 1];
                } else if (s1.charAt(i - 1) == '*') {
                    L[i][j] = L[i][j - 1];
                } else if (s2.charAt(j - 1) == '*') {
                    L[i][j] = L[i - 1][j];
                } else {
                    L[i][j] = Math.max(L[i - 1][j], L[i][j - 1]);
                }
                if (L[i][j] > maxAnchoredLength) {
                    maxAnchoredLength = L[i][j];
                }
            }
        }
        return maxAnchoredLength;
    }
}
