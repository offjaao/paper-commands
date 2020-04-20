package me.thejaao.papercmd.listener;

import me.thejaao.papercmd.managers.PaperManager;
import me.thejaao.papercmd.models.PaperActivation;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static me.thejaao.papercmd.PaperCommands.*;

public class PaperListener implements Listener {

    private PaperManager paperManager = getInstance().getPaperManager();

    @EventHandler
    public void onUsePaper(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack itemInHand = player.getItemInHand();
        ItemStack item = event.getItem();
        if (item == null) return;
        if (!item.hasItemMeta()) return;

        if (action.name().contains("RIGHT") && itemInHand.getType() == Material.PAPER) {
            for (PaperActivation paper : paperManager.getElements()) {
                if (itemInHand.isSimilar(paper.getItemStack())) {
                    if (itemInHand.getAmount() >= 2) {
                        itemInHand.setAmount(itemInHand.getAmount() - 1);
                    } else if (itemInHand.getAmount() == 1) {
                        player.setItemInHand(new ItemStack(Material.AIR));
                        paper.sendCommands(player);
                        player.sendMessage("§aAtivado '" + paper.getName().replace("&", "§") + "§a' em sua conta.");
                        return;
                    }
                }
            }
        }
    }
}
