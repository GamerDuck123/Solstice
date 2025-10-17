package me.alexdevs.solstice.modules.kit;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import eu.pb4.placeholders.impl.StringArgOps;
import eu.pb4.sgui.api.gui.SimpleGui;
import me.alexdevs.solstice.Solstice;
import net.minecraft.nbt.TagParser;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static String serializeItemStack(ItemStack itemStack) {
//        var registry = Solstice.server.registryAccess();
//        var nbt = itemStack.save(registry);
//        return nbt.getAsString();
        return ItemStack.CODEC.encodeStart(StringArgOps.INSTANCE, itemStack).resultOrPartial().get().left().get();
    }

    public static ItemStack deserializeItemStack(String string) throws CommandSyntaxException {
//        var registry = Solstice.server.registryAccess();
//        var nbt = TagParser.parseCompoundFully(string);
//        return ItemStack.parseOptional(registry, nbt);
        return ItemStack.CODEC.decode(StringArgOps.INSTANCE, StringArgOps.INSTANCE.createString(string)).resultOrPartial().get().getFirst();
    }

    public static KitInventory createInventory(List<ItemStack> items) {
        var inventory = new KitInventory();
        for (var i = 0; i < items.size(); i++) {
            inventory.setItem(i, items.get(i));
        }
        return inventory;
    }

    public static List<ItemStack> getItemStacks(KitInventory inventory) {
        var items = new ArrayList<ItemStack>();
        for (var i = 0; i < inventory.getContainerSize(); i++) {
            var stack = inventory.getItem(i);
            if(!stack.isEmpty()) {
                items.add(stack);
            }
        }
        return items;
    }

    public static void redirect(SimpleGui container, KitInventory inventory) {
        for(var i = 0; i < container.getSize(); i++) {
            container.setSlotRedirect(i, new Slot(inventory, i, 0, 0));
        }
    }
}
