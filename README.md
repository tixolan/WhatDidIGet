# What Did I Get

I thought of this mod first when I was building big structures planned in creative
and copied with [Litematica](https://github.com/maruohon/litematica). I kept running
out of resources and going to collect because I had no idea if I had enough and didn't
feel like counting stacks. This mod does that for me!

## How it works

The mod will keep a list of the last items you picked up and show how much of that item
you have in your inventory, it does this with a custom event that's triggered every time
an `ItemStack` is added to the player's inventory. This, however, means it may be triggered
twice if the player collects an `ItemStack` which covers an already-existing stack and an
empty one. This issue will be addressed on the new version of the mod.

## Dependencies

This mod has no dependencies as of version `1.0.0`.