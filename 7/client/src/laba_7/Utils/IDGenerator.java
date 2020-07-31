package laba_7.Utils;

import java.util.HashSet;
import java.util.Random;

public class IDGenerator {
    private static HashSet<Integer> IDs = new HashSet<>();

    public static int generateID() {
        Integer id = new Random().nextInt(Integer.MAX_VALUE);
        if (IDs.contains(id)) {
            while (IDs.contains(id)) {
                id = new Random().nextInt(Integer.MAX_VALUE);
            }
        }
        IDs.add(id);
        return id;
    }
}
