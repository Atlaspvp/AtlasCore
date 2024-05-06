package net.atlaspvp.atlascore.Features.Essentials;

import net.atlaspvp.atlascore.Features.PlayerVaults.GUI;
import net.atlaspvp.atlascore.Features.PlayerVaults.PlayerVaults;
import net.atlaspvp.atlascore.Struct.Configs.Essentials;
import net.atlaspvp.atlascore.Utils.Chat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
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
import java.util.Locale;

public class Commands {

    /*@Command({"senddata"})
    public void test(Player player) {
        Data.sendData("This is a test", "factiontest");
    }*/

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
                Component messageA = Essentials.getMessage("fix-success", null);
                Component messageB = messageA.replaceText(TextReplacementConfig.builder().match("<contents>").replacement(i.displayName()).build());


                player.sendMessage(messageB);

                i.setDurability((short) 0);
            } else {
                Component messageA = Essentials.getMessage("fix-failed", null);
                Component messageB = messageA.replaceText(TextReplacementConfig.builder().match("<contents>").replacement(i.displayName()).build());

                player.sendMessage(messageB);
            }
        }
    }

    @Command({"fixall", "repairall"})
    @CommandPermission("andromeda.fixall")
    public void Fixall(final Player player) {
        List<ItemStack> toFix = toFix(player.getInventory());
        if (!toFix.isEmpty()) {
            Component messageA = Essentials.getMessage("fix-success", null);
            Component messageB = messageA.replaceText(TextReplacementConfig.builder().match("<contents>").replacement("").build());
            Component cp = Essentials.getMessage("contents-prefix", null);
            player.sendMessage(messageB);
            for (ItemStack i : toFix) {
                i.setDurability((short) 0);
                if (Essentials.getBoolean("print-contents")) {
                    player.sendMessage(cp.append(i.displayName()));
                } else {
                    System.out.println(Essentials.getBoolean("print-contents"));
                }
            }
        } else {
            player.sendMessage(Essentials.getMessage("fixall-failed", null));
        }
    }

    @Command("speed")
    @CommandPermission("andromeda.speed")
    public void onSpeed(final Player player, final float f) {
        if (player.isFlying()) {
            final float speed = player.getFlySpeed();

            player.setFlySpeed(f);
            player.sendMessage(Chat.format("<green><b>(!)</b> your fly speed has been changed from " + speed + " to " + f + "."));
        } else {
            final float speed = player.getWalkSpeed();

            player.setWalkSpeed(f);
            player.sendMessage(Chat.format("<green><b>(!)</b> ʏᴏᴜʀ ᴡᴀʟᴋ sᴘᴇᴇᴅ ʜᴀs ʙᴇᴇɴ ᴄʜᴀɴɢᴇᴅ ғʀᴏᴍ " + speed + " ᴛᴏ " + f + "."));
        }
    }

    @Command({"pv", "playervaults", "playervault"})
    public void onVault(final Player player, String vault) {
        if (vault.toLowerCase(Locale.ROOT).equals("menu")) {
            new GUI().openGUI(player);

        } else if (vault.toLowerCase(Locale.ROOT).equals("search")){
            PlayerVaults.searchTOC(player, vault);

        } else {
            PlayerVaults.open(player, Integer.parseInt(vault));
        }
    }

    private void setGamemode(Player player, GameMode gameMode) {
        Component messageA = Essentials.getMessage("gamemode", player);
        Component messageB = messageA.replaceText(TextReplacementConfig.builder().match("<state>").replacement(player.getGameMode().name()).build());
        Component messageC = messageB.replaceText(TextReplacementConfig.builder().match("<state2>").replacement(gameMode.name()).build());

        player.setGameMode(gameMode);

        player.sendMessage(messageC);
    }

    private boolean Fixable(final ItemStack item) {
        return EnchantmentTarget.ARMOR.includes(item) || EnchantmentTarget.WEAPON.includes(item) || EnchantmentTarget.TOOL.includes(item) || item.getType() == Material.BOW || item.getType() == Material.CROSSBOW || item.getType() == Material.FLINT_AND_STEEL || item.getType() == Material.FISHING_ROD;
    }

    public List<ItemStack> toFix(Inventory inv) {
        ArrayList<ItemStack> items = new ArrayList<>();
        for (ItemStack i : inv) {
            if (i != null) {
                if (Fixable(i)) {
                    items.add(i);
                }
            }
        }
        return items;
    }
}
