public class LCS {

    public static void main(String[] args) {
        String s1 = "abcedasf";
        String s2 = "acsf";
        LCS lcs = new LCS(s1, s2);
        int[][] t = new int[s1.length()][s2.length()];
        System.out.println(lcs.lcsMemozation(0, 0, t));
    }

    private String s1;
    private String s2;

    public LCS(String s1, String s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    public int lcsRecursion(int i, int j) {
        if (i == s1.length() || j == s2.length()) {
            return 0;
        } else if (s1.charAt(i) == s2.charAt(j)) {
            return 1 + lcsRecursion(i + 1, j + 1);
        } else {
            return Math.max(lcsRecursion(i + 1, j), lcsRecursion(i, j + 1));
        }
    }

    public int lcsMemozation(int i, int j, int[][] t) {
        int result;
        if (i == s1.length() || j == s2.length()) {
            return 0;
        } else if (s1.charAt(i) == s2.charAt(j)) {
            result =  1 + lcsMemozation(i + 1, j + 1, t);
        } else {
            result = Math.max(lcsMemozation(i + 1, j, t), lcsMemozation(i, j + 1, t));
        }
        t[i][j] = result;
        return result;
    }
    
}
