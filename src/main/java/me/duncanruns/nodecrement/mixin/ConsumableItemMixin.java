package me.duncanruns.nodecrement.mixin;

import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = {EnderPearlItem.class, SnowballItem.class, EggItem.class, EnderEyeItem.class, ThrowablePotionItem.class, BoatItem.class})
public abstract class ConsumableItemMixin extends Item {
    public ConsumableItemMixin(Settings settings) {
        super(settings);
    }

    @ModifyArg(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V"), index = 0)
    private int noDecrementMixin(int amount) {
        return 0;
    }
}
