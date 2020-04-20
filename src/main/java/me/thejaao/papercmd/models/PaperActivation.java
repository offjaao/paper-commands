package me.thejaao.papercmd.models;

import com.google.common.collect.Lists;
import lombok.*;
import me.thejaao.papercmd.builder.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
@Setter
public class PaperActivation {

    private int identifier;
    private ItemStack itemStack;
    private String name;
    public List<CommandWrapper> commands;
    private ItemBuilder itemBuilder = new ItemBuilder(Material.PAPER);

    public PaperActivation(int identifier, String name) {
        this.identifier = identifier;
        this.itemStack = paperItem(name);
        this.name = name;
        this.commands = Lists.newArrayList();
    }

    public void sendCommands(Player player) {
        commands.forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.format(player.getName())));
    }

    private ItemStack paperItem(String displayName) {
        return itemBuilder.name(displayName.replace("&", "§"))
                .lore("§7Clique com botão §fdireito§7 para ativar.")
                .build();
    }

    public void addCommand(CommandWrapper commandWrapper) {
        commands.add(commandWrapper);
    }

}
