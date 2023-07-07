package io.github.tixolan;

import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class RenderedItemStack implements Cloneable {
    public static final float MAX_DELTA = 100.0f;

    private Text text;
    private ItemStack stack;
    private float delta;

    public RenderedItemStack(Text text, ItemStack stack, float delta) {
        this.text = text;
        this.stack = stack;
        this.delta = delta;
    }

    public void setText(String string) { this.text = Text.of(string); }

    public Text getText() { return this.text; }

    public void setStack(ItemStack stack) { this.stack = stack; }

    public ItemStack getStack() { return this.stack; }

    public void incrementDelta(float inc) { this.delta += inc; }

    public void setDelta(float delta) { this.delta = delta; }

    public float getDelta() { return this.delta; }

    @Override
    public RenderedItemStack clone() {
        try {
            return (RenderedItemStack) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
