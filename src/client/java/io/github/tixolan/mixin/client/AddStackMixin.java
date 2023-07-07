package io.github.tixolan.mixin.client;

import io.github.tixolan.events.AddStackCallback;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInventory.class)
public class AddStackMixin {
    @Inject(
            at = @At(value = "TAIL"),
            method = "addStack(ILnet/minecraft/item/ItemStack;)I",
            cancellable = true
    )
    private void onPickup(final int slot, final ItemStack stack, CallbackInfoReturnable<Boolean> info) {
        int result = AddStackCallback.EVENT.invoker().update((PlayerInventory) (Object) this, stack, slot);

        if(result == -1) {
            info.cancel();
        }
    }
}
