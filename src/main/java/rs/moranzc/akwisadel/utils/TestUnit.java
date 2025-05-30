package rs.moranzc.akwisadel.utils;

import java.util.ArrayList;
import java.util.List;

public class TestUnit {
    private static final List<Queueable> q = new ArrayList<>();

    public static void main(String[] args) {
        addToBot(new Queueable("Head", 1));
        addToBot(new Queueable("Second", queueIndex(2)));
        addToBot(new Queueable("Third", 3));
        System.out.println(q);
    }
    
    private static int queueIndex(int i) {
        addToBot(new Queueable("Nested", i));
        return i;
    }
    
    private static void addToBot(Queueable qi) {
        q.add(qi);
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
