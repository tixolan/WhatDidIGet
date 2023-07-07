package io.github.tixolan;

import io.github.tixolan.events.AddStackCallback;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;

public class WhatDidIGetClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		LinkedBlockingQueue<RenderedItemStack> renderItems = new LinkedBlockingQueue<>();

		AddStackCallback.EVENT.register((inventory, stack, slot) -> {
			int amount = inventory.count(stack.getItem());
			if(renderItems.size() > 5) {
				renderItems.poll();
			}
			// If item is already in list, modify contents and reset delta
			for(RenderedItemStack renderItem : renderItems) {
				if(Objects.equals(renderItem.getStack().getName().getString(), stack.getName().getString())) {
					RenderedItemStack newRender = renderItem.clone();
					renderItems.remove(renderItem);
					newRender.setText(String.valueOf(amount));
					newRender.setDelta(0.0f);
					renderItems.offer(newRender);
					return amount;
				}
			}
			// Create new one if not found
			RenderedItemStack renderItem = new RenderedItemStack(
					Text.of(String.valueOf(amount)),
					stack,
					0.0f
			);
			renderItems.offer(renderItem);
			return amount;
		});

		HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
			int i = 0;
			int size = renderItems.size();
			for(RenderedItemStack renderItem : renderItems) {
				if(renderItem.getDelta() >= RenderedItemStack.MAX_DELTA) {
					if(renderItems.remove(renderItem)) { continue; }
				}

				drawItemAmount(drawContext, renderItem.getStack(), renderItem.getText(), size - i++);
				renderItem.incrementDelta(tickDelta);
			}
		});
	}

	private void drawItemAmount(DrawContext context, ItemStack stack, Text text, int i) {
		int x;
		int y;

		MinecraftClient client = MinecraftClient.getInstance();
		TextRenderer renderer = client.textRenderer;

		int width = context.getScaledWindowWidth();
		int height = context.getScaledWindowHeight();

		x = width - renderer.getWidth(text) / 2 - 20;
		y = height - 16 * i;

		context.drawCenteredTextWithShadow(renderer, text, x, y, 0xffffff);
		context.drawItem(stack, width - 17, y);
	}
}