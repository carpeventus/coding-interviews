package Chap5;

public class AppearOnceInStream {
    private int[] count;
    private int index;
    public AppearOnceInStream() {
        count =  new int[256];
        for (int i = 0; i < count.length; i++) {
            count[i] = -1;
        }
    }

    public void insert(char c) {
        if (count[c] == -1) count[c] = index;
        else if (count[c] >= 0) count[c] = -2;
        index++;
    }

    public char firstAppearOnceChar() {
        int minIndex = Integer.MAX_VALUE;
        char c = '\0';
        for (int i = 0; i < count.length; i++) {
            if (count[i] >= 0 && count[i] < minIndex) {
                minIndex = count[i];
                c = (char)i;
            }
        }
        return c;
    }

    public static void main(String[] args) {
        AppearOnceInStream a = new AppearOnceInStream();
        a.insert('g');
        a.insert('o');
        System.out.println(a.firstAppearOnceChar());
        a.insert('o');
        a.insert('g');
        a.insert('l');
        a.insert('e');
        System.out.println(a.firstAppearOnceChar());
    }
}
