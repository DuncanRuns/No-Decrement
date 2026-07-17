package me.duncanruns.nodecrement.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.stream.Stream;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @WrapOperation(method = "consumeItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setStackInHand(Lnet/minecraft/util/Hand;Lnet/minecraft/item/ItemStack;)V"))
    private void wrapSetStack(LivingEntity instance, Hand hand, ItemStack stack, Operation<Void> original) {
        if (!(instance instanceof PlayerEntity) || ((PlayerEntity) instance).abilities.creativeMode) {
            original.call(instance, hand, stack);
            return;
        }
        PlayerInventory inventory = ((PlayerEntity) instance).inventory;
        if (Stream.concat(Stream.concat(inventory.main.stream(), inventory.armor.stream()), inventory.offHand.stream())
                .noneMatch(i -> i == stack))
            inventory.insertStack(stack);
    }
}
