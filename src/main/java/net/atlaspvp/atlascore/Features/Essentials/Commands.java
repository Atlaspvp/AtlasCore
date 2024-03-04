package net.atlaspvp.atlascore.Features.Essentials;

import net.atlaspvp.atlascore.Struct.Configs.Essentials;
import net.atlaspvp.atlascore.Utils.Chat;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import revxrsal.commands.annotation.*;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.util.ArrayList;
import java.util.List;

public class Commands {

    @Command({"gmc", "gamemode creative"})
    @CommandPermission("atlascore.gamemode")
    public void Creative(@Default("me") Player player) {
        setGamemode(player, GameMode.CREATIVE);
    }

    @Command({"gms", "gamemode survival"})
    @CommandPermission("atlascore.gamemode")
    public void Survival(@Default("me") Player player) {
        setGamemode(player, GameMode.SURVIVAL);
    }

    @Command({"gmsp", "gamemode spectator"})
    @CommandPermission("atlascore.gamemode")
    public void Spectator(@Default("me")  Player player) {
        setGamemode(player, GameMode.SPECTATOR);
    }

    @Command({"gma", "gamemode adventure"})
    @CommandPermission("atlascore.gamemode")
    public void Adventure(@Default("me") Player player) {
        setGamemode(player, GameMode.ADVENTURE);
    }

    @Command({"workbench", "craft", "wb", "make"})
    @CommandPermission("atlascore.workbench")
    public void Workbench(Player player) {
        player.openWorkbench(null, true);
    }

    @Command({"fix", "repair"})
    @CommandPermission("atlascore.fix")
    public void Fix(final Player player) {
        final ItemStack i = player.getInventory().getItemInMainHand();
        if (i.getType() != Material.AIR) {
            if (Fixable(i)) {
                String messageA = Essentials.getMessage("fix-success", null);
                String messageB = messageA.replace("{contents}", i.displayName().toString());

                player.sendMessage(Chat.AltFormat(messageB));

                i.setDurability((short) 0);
            } else {
                String messageA = Essentials.getMessage("fix-failed", null);
                String messageB = messageA.replace("{contents}", i.displayName().toString());

                player.sendMessage(Chat.AltFormat(messageB));
            }
        }
    }

    @Command({"fixall", "repairall"})
    @CommandPermission("andromeda.fixall")
    public void Fixall(final Player player) {
        List<ItemStack> toFix = toFix(player.getInventory());
        if (!toFix.isEmpty()) {
            String messageA = Essentials.getMessage("fix-success", null);
            if (messageA.contains("{contents}")) {
                String message = messageA.replace("{contents}", "");
                player.sendMessage(Chat.AltFormat(message));
            }
            for (ItemStack i : toFix) {
                i.setDurability((short) 0);
                if (messageA.contains("{contents}")) {
                    String cp = Essentials.getMessage("contents-prefix", null);
                    player.sendMessage(cp + i.displayName());
                }
            }
        } else {
            player.sendMessage(Chat.AltFormat(Essentials.getMessage("fixall-failed", null)));
        }
    }

    private void setGamemode(Player player, GameMode gameMode) {
        player.setGameMode(gameMode);

        String messageA = Essentials.getMessage("gamemode", player);
        String messageB = messageA.replace("{state}", gameMode.name());
        String messageC = messageB.replace("{state2}", player.getGameMode().name());

        player.sendMessage(Chat.AltFormat(messageC));
    }

    private boolean Fixable(final ItemStack item) {
        return EnchantmentTarget.ARMOR.includes(item) || EnchantmentTarget.WEAPON.includes(item) || EnchantmentTarget.TOOL.includes(item) || item.getType() == Material.BOW || item.getType() == Material.CROSSBOW || item.getType() == Material.FLINT_AND_STEEL || item.getType() == Material.FISHING_ROD;
    }

    public List<ItemStack> toFix(Inventory inv) {
        ArrayList<ItemStack> items = new ArrayList<>();
        for (ItemStack i : inv) {
            if (Fixable(i)) {
                items.add(i);
            }
        }
        return items;
    }
}
