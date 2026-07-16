package me.duncanruns.nodecrement.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.block.AbstractBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import static me.duncanruns.nodecrement.Helper.enterNoDecrementArea;
import static me.duncanruns.nodecrement.Helper.exitNoDecrementArea;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockMixin {
    @WrapMethod(method = "onUse")
    private ActionResult wrapOnUse(World world, PlayerEntity player, Hand hand, BlockHitResult hit, Operation<ActionResult> original) {
        enterNoDecrementArea();
        try {
            return original.call(world, player, hand, hit);
        } finally {
            exitNoDecrementArea();
        }
    }
}
