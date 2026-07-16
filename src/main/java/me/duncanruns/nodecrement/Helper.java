package me.duncanruns.nodecrement;

public class Helper {
    public static final ThreadLocal<Integer> count = ThreadLocal.withInitial(() -> 0);

    public static void enterNoDecrementArea() {
        count.set(count.get() + 1);
    }

    public static void exitNoDecrementArea() {
        count.set(count.get() - 1);
    }

    public static boolean isNotDecrementing() {
        return count.get() > 0;
    }
}
