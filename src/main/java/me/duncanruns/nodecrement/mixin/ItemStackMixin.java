package me.duncanruns.nodecrement.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

import static me.duncanruns.nodecrement.Helper.*;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    public abstract Item getItem();

    /**
     * @author DuncanRuns
     * @reason lol no damage!!!
     */
    @Overwrite
    public boolean damage(int amount, Random random, @Nullable ServerPlayerEntity player) {
        return false;
    }

    @WrapMethod(method = "use")
    private TypedActionResult<ItemStack> wrapUse(World world, PlayerEntity user, Hand hand, Operation<TypedActionResult<ItemStack>> original) {
        enterNoDecrementArea();
        try {
            return original.call(world, user, hand);
        } finally {
            exitNoDecrementArea();
        }
    }

    @WrapMethod(method = "useOnBlock")
    private ActionResult wrapUseOnBlock(ItemUsageContext context, Operation<ActionResult> original) {
        enterNoDecrementArea();
        try {
            return original.call(context);
        } finally {
            exitNoDecrementArea();
        }
    }

    @WrapMethod(method = "useOnEntity")
    private ActionResult wrapUseOnEntity(PlayerEntity user, LivingEntity entity, Hand hand, Operation<ActionResult> original) {
        enterNoDecrementArea();
        try {
            return original.call(user, entity, hand);
        } finally {
            exitNoDecrementArea();
        }
    }

    @WrapMethod(method = "finishUsing")
    private ItemStack wrapFinishUsing(World world, LivingEntity user, Operation<ItemStack> original) {
        enterNoDecrementArea();
        try {
            return original.call(world, user);
        } finally {
            exitNoDecrementArea();
        }
    }

    @WrapMethod(method = "onStoppedUsing")
    private void wrapOnStoppedUsing(World world, LivingEntity user, int remainingUseTicks, Operation<Void> original) {
        enterNoDecrementArea();
        try {
            original.call(world, user, remainingUseTicks);
        } finally {
            exitNoDecrementArea();
        }
    }

    @Inject(method = "decrement", at = @At("HEAD"), cancellable = true)
    private void noDecrement(int amount, CallbackInfo ci) {
        if (getItem() instanceof BucketItem) return;
        if (isNotDecrementing()) ci.cancel();
    }
}
