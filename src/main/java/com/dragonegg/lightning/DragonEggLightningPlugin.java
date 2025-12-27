package com.dragonegg.lightning;

import com.dragonegg.lightning.ability.AbilityManager;
import com.dragonegg.lightning.command.AbilityCommand;
import com.dragonegg.lightning.hud.HudManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Date;

/**
 * Main plugin class for Dragon Egg Lightning ability.
 */
public class DragonEggLightningPlugin extends JavaPlugin {

  private AbilityManager abilityManager;
  private HudManager hudManager;

  @Override
  public void onEnable() {
    this.abilityManager = new AbilityManager(this);
    this.hudManager = new HudManager(this, abilityManager);

    registerCommands();
    registerListeners();

    getLogger().info("DragonEggLightning plugin enabled!");
    getLogger().info("Plugin version: " + getDescription().getVersion());
  }

  @Override
  public void onDisable() {
    if (hudManager != null) {
      hudManager.shutdown();
    }
    getLogger().info("DragonEggLightning plugin disabled!");
  }

  /**
   * Register plugin commands.
   */
  private void registerCommands() {
    AbilityCommand abilityCommand = new AbilityCommand(this, abilityManager);
    getCommand("ability").setExecutor(abilityCommand);
    getCommand("ability").setTabCompleter(abilityCommand);
  }

  /**
   * Register event listeners.
   */
  private void registerListeners() {
    // Event listeners will be registered here if needed
  }

  public AbilityManager getAbilityManager() {
    return abilityManager;
  }

  public HudManager getHudManager() {
    return hudManager;
  }

  /**
   * Send plugin info to player
   */
  public void sendPluginInfo(Player player) {
    player.sendMessage(
      Component.text("=== DragonEggLightning Plugin Info ===", NamedTextColor.GOLD)
    );
    player.sendMessage(
      Component.text("Version: " + getDescription().getVersion(), NamedTextColor.WHITE)
    );
    player.sendMessage(
      Component.text("Plugin File: " + getFile().getName(), NamedTextColor.WHITE)
    );
    player.sendMessage(
      Component.text("Last Modified: " + new Date(getFile().lastModified()), NamedTextColor.WHITE)
    );
    player.sendMessage(
      Component.text("Author: " + String.join(", ", getDescription().getAuthors()), NamedTextColor.BLUE)
    );
    player.sendMessage(
      Component.text("Description: " + getDescription().getDescription(), NamedTextColor.GRAY)
    );
  }
}
