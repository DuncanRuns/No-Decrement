package me.duncanruns.nodecrement.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Random;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    /**
     * @author DuncanRuns
     * @reason lol no damage!!!
     */
    @Overwrite
    public boolean damage(int amount, Random random, @Nullable ServerPlayerEntity player) {
        return false;
    }
}
