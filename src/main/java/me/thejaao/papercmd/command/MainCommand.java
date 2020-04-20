package me.thejaao.papercmd.command;

import me.thejaao.papercmd.managers.PaperManager;
import me.thejaao.papercmd.models.PaperActivation;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.thejaao.papercmd.PaperCommands.*;

public class MainCommand implements CommandExecutor {

    private PaperManager paperManager = getInstance().getPaperManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("papel.enviar")) {
            sender.sendMessage("§cVocê não possui permissão para executar este comando.");
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage("§cUtilize: /papel enviar <id-do-papel> <jogador>.");
            return false;
        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("enviar")) {
                String stringId = args[1];

                if (!isInteger(stringId)) {
                    sender.sendMessage("§cO número inserido não é válido.");
                    return false;

                }
                PaperActivation paperId = paperManager.getById(Integer.parseInt(stringId));
                if (paperId == null) {
                    sender.sendMessage("§cO papel com o id '" + stringId + "' não existe.");
                    return false;
                }

                Player target = Bukkit.getPlayerExact(args[2]);
                if (target == null || !target.isOnline()) {
                    sender.sendMessage("§cEste jogador não foi encontrado.");
                    return false;
                }

                if (target.getInventory().firstEmpty() > -1) {
                    target.getInventory().addItem(paperId.getItemStack());
                } else {
                    target.sendMessage("§cVocê recebeu um papel '" + paperId.getName().replace("&", "§") + "§c' e seu inventário estava cheio, o item foi dropado.");
                    target.getWorld().dropItem(target.getLocation(), paperId.getItemStack());
                }
                sender.sendMessage("§aOba! Papel '" + paperId.getName().replace("&", "§") + "§a' enviado para " + target.getName() + "§a.");
            }
        } else sender.sendMessage("§cUtilize: /papel enviar <id-do-papel> <jogador>.");

        return false;
    }

    private boolean isInteger(String valor) {
        if (valor.contains("NaN") || valor.contains("-")) {
            return false;
        }
        try {
            int i = Integer.parseInt(valor);
            if (i < 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
