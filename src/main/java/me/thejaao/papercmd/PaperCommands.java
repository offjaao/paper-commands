package me.thejaao.papercmd;

import lombok.Getter;
import me.thejaao.papercmd.command.MainCommand;
import me.thejaao.papercmd.listener.PaperListener;
import me.thejaao.papercmd.managers.PaperManager;
import me.thejaao.papercmd.managers.loader.PaperLoader;
import org.bukkit.plugin.java.JavaPlugin;

public final class PaperCommands extends JavaPlugin {

    @Getter
    private static PaperCommands instance;
    @Getter
    private PaperManager paperManager;

    @Override
    public void onEnable() {
        instance = this;
        loadConfiguration();
        initialize();
    }

    private void initialize() {
        paperManager = new PaperManager();
        PaperLoader paperLoader = new PaperLoader(paperManager);
        paperLoader.load();

        getServer().getPluginManager().registerEvents(new PaperListener(), this);
        getCommand("papel").setExecutor(new MainCommand());
        System.out.println("[paper-commands] by jaao has been loaded.");
    }

    private void loadConfiguration() {
        getConfig().options().copyDefaults(false);
        saveDefaultConfig();
    }
}
