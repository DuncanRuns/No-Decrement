package me.duncanruns.nodecrement.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static me.duncanruns.nodecrement.Helper.enterNoDecrementArea;
import static me.duncanruns.nodecrement.Helper.exitNoDecrementArea;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow
    @Final
    public PlayerInventory inventory;

    @Shadow
    @Nullable
    public abstract ItemEntity dropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(method = "dropSelectedItem", at = @At("HEAD"), cancellable = true)
    public void dropDupeMixin(boolean dropEntireStack, CallbackInfoReturnable<Boolean> info) {
        if (!dropEntireStack) {
            ItemStack copy = this.inventory.getStack(this.inventory.selectedSlot).copy();
            copy.setCount(1);
            info.setReturnValue(this.dropItem(copy, false, true) != null);
        }
    }

    @WrapMethod(method = "interact")
    private ActionResult wrapInteract(Entity entity, Hand hand, Operation<ActionResult> original) {
        enterNoDecrementArea();
        try {
            return original.call(entity, hand);
        } finally {
            exitNoDecrementArea();
        }
    }
}
