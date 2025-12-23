package com.dragonegg.lightning;

import com.dragonegg.lightning.ability.AbilityManager;
import com.dragonegg.lightning.command.AbilityCommand;
import com.dragonegg.lightning.hud.HudManager;
import org.bukkit.plugin.java.JavaPlugin;

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
}
