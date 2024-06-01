package me.duncanruns.nodecrement.mixin;

import net.minecraft.item.EnderEyeItem;
import net.minecraft.item.FireChargeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = {EnderEyeItem.class, FireChargeItem.class})
public abstract class BlockInteractorItemMixin {
    @ModifyArg(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V"), index = 0)
    private int noDecrementMixin(int amount) {
        return 0;
    }
}
