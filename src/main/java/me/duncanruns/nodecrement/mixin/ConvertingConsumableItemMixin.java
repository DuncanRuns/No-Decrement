package me.duncanruns.nodecrement.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MushroomStewItem;
import net.minecraft.item.PotionItem;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = {MushroomStewItem.class, SuspiciousStewItem.class, PotionItem.class})
public class ConvertingConsumableItemMixin {
    @Inject(method = "finishUsing", at = @At("RETURN"), cancellable = true)
    private void dontConvertMixin(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> info) {
        info.setReturnValue(stack);
    }
}
