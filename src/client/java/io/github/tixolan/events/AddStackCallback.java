package io.github.tixolan.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;

public interface AddStackCallback {

    Event<AddStackCallback> EVENT = EventFactory.createArrayBacked(AddStackCallback.class,
        (listeners) -> (inventory, stack, slot) -> {
            for(AddStackCallback listener : listeners) {
                 int result = listener.update(inventory, stack, slot);
                 if(result != -1) {
                     return result;
                 }
            }
            return -1;
        }
    );

    int update(PlayerInventory player, ItemStack stack, int slot);
}
