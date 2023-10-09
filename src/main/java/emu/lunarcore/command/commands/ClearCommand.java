package emu.lunarcore.command.commands;

import java.util.LinkedList;
import java.util.List;

import emu.lunarcore.command.Command;
import emu.lunarcore.command.CommandArgs;
import emu.lunarcore.command.CommandHandler;
import emu.lunarcore.game.inventory.GameItem;
import emu.lunarcore.game.inventory.ItemMainType;
import emu.lunarcore.game.player.Player;

@Command(label = "clear")
public class ClearCommand implements CommandHandler {

    @Override
    public void execute(Player sender, CommandArgs args) {
        // Check target
        if (args.getTarget() == null) {
            this.sendMessage(sender, "Error: Targeted player not found or offline");
            return;
        }

        List<GameItem> toRemove = new LinkedList<>();
        String type = args.get(0).toLowerCase();
        
        switch (type) {
            case "relics", "r" -> {
                for (GameItem item : args.getTarget().getInventory().getItems().values()) {
                    if (item.getItemMainType() == ItemMainType.Relic && item.getLevel() <= 1 && item.getExp() == 0 && !item.isLocked() && !item.isEquipped()) {
                        toRemove.add(item);
                    }
                }
            }
            case "equipment", "lightcones", "lc" -> {
                for (GameItem item : args.getTarget().getInventory().getItems().values()) {
                    if (item.getItemMainType() == ItemMainType.Equipment && item.getLevel() <= 1 && item.getExp() == 0 && !item.isLocked() && !item.isEquipped()) {
                        toRemove.add(item);
                    }
                }
            }
            case "materials", "mats", "m" -> {
                for (GameItem item : args.getTarget().getInventory().getItems().values()) {
                    if (item.getItemMainType() == ItemMainType.Material) {
                        toRemove.add(item);
                    }
                }
            }
            case "items" -> {
                for (GameItem item : args.getTarget().getInventory().getItems().values()) {
                    if (!item.isLocked() && !item.isEquipped()) {
                        toRemove.add(item);
                    }
                }
            }
        }
        
        args.getTarget().getInventory().removeItems(toRemove);
        this.sendMessage(sender, "Removed " + toRemove.size() + " items");
    }

}