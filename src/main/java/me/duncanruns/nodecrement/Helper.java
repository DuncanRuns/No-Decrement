package me.duncanruns.nodecrement;

public class Helper {
    public static final ThreadLocal<Boolean> using = ThreadLocal.withInitial(() -> false);
}
