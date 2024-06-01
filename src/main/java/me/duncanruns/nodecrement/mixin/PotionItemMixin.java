package me.duncanruns.nodecrement.mixin;

import net.minecraft.item.PotionItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PotionItem.class)
public abstract class PotionItemMixin {
    @ModifyArg(method = "finishUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V"), index = 0)
    private int noDecrementMixin(int amount) {
        return 0;
    }
}
