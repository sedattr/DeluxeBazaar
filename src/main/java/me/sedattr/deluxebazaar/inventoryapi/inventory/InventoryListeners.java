package me.sedattr.deluxebazaar.inventoryapi.inventory;

import me.sedattr.deluxebazaar.inventoryapi.item.ClickInterface;
import me.sedattr.deluxebazaar.inventoryapi.item.ClickableItem;
import me.sedattr.deluxebazaar.inventoryapi.HInventory;
import me.sedattr.deluxebazaar.others.TaskUtils;
import me.sedattr.deluxebazaar.others.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.time.ZonedDateTime;

public class InventoryListeners implements Listener {
    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        if (inventory == null)
            return;
        if (!(inventory.getHolder() instanceof HInventory))
            return;
        event.setCancelled(true);

        if (!(event.getWhoClicked() instanceof Player player))
            return;
        if (event.getSlot() < 0)
            return;

        HInventory gui = InventoryAPI.getInventory(player);
        if (gui == null)
            return;

        if (!inventory.equals(gui.getInventory()))
            return;

        ClickableItem clickableItem = gui.getItem(event.getSlot());
        if (clickableItem == null)
            return;

        ClickInterface click = clickableItem.getClick();
        if (click == null)
            return;

        long cooldown = InventoryVariables.getCooldown(player);
        if (cooldown > 0) {
            long time = ZonedDateTime.now().toInstant().toEpochMilli() - cooldown;
            if (time < 300) {
                Utils.sendMessage(player, "click_cooldown");
                return;
            }
        }

        click.click(event);
        InventoryVariables.addCooldown(player, ZonedDateTime.now().toInstant().toEpochMilli());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player))
            return;

        HInventory gui = InventoryAPI.getInventory(player);
        if (gui == null)
            return;

        if (gui.isCloseable()) {
            InventoryVariables.removePlayerInventory(player);
            return;
        }
        if (InventoryAPI.getInstance() == null)
            return;

        TaskUtils.runLater(() -> gui.open(player), 1L);
    }
}