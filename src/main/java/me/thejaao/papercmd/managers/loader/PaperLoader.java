package me.thejaao.papercmd.managers.loader;

import me.thejaao.papercmd.builder.ItemBuilder;
import me.thejaao.papercmd.managers.PaperManager;
import me.thejaao.papercmd.models.CommandWrapper;
import me.thejaao.papercmd.models.PaperActivation;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static me.thejaao.papercmd.PaperCommands.*;

public class PaperLoader {

    private PaperManager paperManager;

    public PaperLoader(PaperManager paperManager) {
        this.paperManager = paperManager;
    }

    public void load() {
        FileConfiguration config = getInstance().getConfig();
        ConfigurationSection paper = config.getConfigurationSection("Items");
        if (paper == null) return;
        for (String key : paper.getKeys(false)) {

            int id = paper.getInt(key + ".id", 0);
            String name = paper.getString(key + ".nome", "");
            PaperActivation paperActivation = new PaperActivation(id, name);

            paper.getStringList(key + ".commands").forEach(s -> paperActivation.addCommand(new CommandWrapper(s)));

            paperManager.getElements().add(paperActivation);
        }
        System.out.println("[paper-commands] " + paperManager.getElements().size() + " items were loaded.");
    }

}
