
public class Main {
	
	public static void main(String[] args) {
		String text = "Lorem ipsum dolor sit amet";
		String pattern = "ip";
		BoyerMoore bm = new BoyerMoore(pattern);
		
		int first_occur_position = bm.search(text);
		System.out.println("The text '" + pattern + "' is first found after the " 
                                    + first_occur_position + " position.");
	}
}

class BoyerMoore {
    private final int BASE;
    private int[] occurrence;
    private String pattern;

    public BoyerMoore(String pattern) {
        this.BASE = 256;
        this.pattern = pattern;

        occurrence = new int[BASE];
        for (int c = 0; c < BASE; c++)
            occurrence[c] = -1;
        for (int j = 0; j < pattern.length(); j++)
            occurrence[pattern.charAt(j)] = j;
    }

    public int search(String text) {
    	int n = text.length();
    	int m = pattern.length();
        int skip;
        for (int i = 0; i <= n - m; i += skip) {
            skip = 0;
            for (int j = m-1; j >= 0; j--) {
                if (pattern.charAt(j) != text.charAt(i+j)) {
                    skip = Math.max(1, j - occurrence[text.charAt(i+j)]);
                    break;
                }
            }
            if (skip == 0) return i;
        }
        return n;
    }
}