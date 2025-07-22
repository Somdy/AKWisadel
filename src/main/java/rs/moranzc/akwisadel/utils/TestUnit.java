package rs.moranzc.akwisadel.utils;

import java.util.ArrayList;
import java.util.List;

public class TestUnit {
    private static final List<Queueable> q = new ArrayList<>();

    public static void main(String[] args) {
        addToBot(new Queueable("Head", 1));
        addToBot(new Queueable("Second", queueIndex(2)));
        addToBot(new Queueable("T3rd", 3));
        addToBot(new Queueable("F4", 4));
        addToBot(new Queueable("5fth", 5));
        addToBot(new Queueable("Sixth", 6));
        addToBot(new Queueable("7ven", 7));
        addToBot(new Queueable("Eighth", 8));
        addToBot(new Queueable("Nin9th", 9));
        System.out.println(q);
        int times = 5;
        int size = q.size();
        int attempts = 0;
        for (int i = q.size() - 1; i >= 0; i--) {
            if (times <= 0) break;
            attempts++;
            Queueable qi = q.get(i);
            if (qi.id.length() == 4) {
                times--;
                continue;
            }
            for (int j = i - 1; j >= 0; j--) {
                if (times <= 0) break;
                Queueable qn = q.get(j);
                if (qn.id.length() != 4)
                    continue;
                q.remove(j);
                q.add(i--, qn);
                System.out.println(q);
                times--;
            }
            break;
        }
        System.out.println(q);
        System.out.println(attempts);
    }
    
    private static int queueIndex(int i) {
        addToBot(new Queueable("Nested", i));
        return i;
    }
    
    private static void addToBot(Queueable qi) {
        q.add(0, qi);
    }
    
    private static class Queueable {
        private final String id;
        private final int index;

        private Queueable(String id, int index) {
            this.id = id;
            this.index = index;
        }

        @Override
        public String toString() {
            return String.format("%s:%d", id, index);
        }
    }
}
