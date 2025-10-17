package me.alexdevs.solstice.modules.kit;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.JsonOps;
import eu.pb4.placeholders.impl.StringArgOps;
import eu.pb4.sgui.api.gui.SimpleGui;
import me.alexdevs.solstice.Solstice;
import net.minecraft.nbt.*;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static String serializeItemStack(ItemStack itemStack) {
        return NbtUtils.prettyPrint(ItemStack.CODEC.encodeStart(NbtOps.INSTANCE, itemStack).result().get());
    }

    public static ItemStack deserializeItemStack(String element) throws CommandSyntaxException {
        var nbt = TagParser.parseCompoundFully(element);
        return ItemStack.CODEC.decode(NbtOps.INSTANCE, nbt).result().get().getFirst();
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
