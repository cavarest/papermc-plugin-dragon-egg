package com.dragonegg.lightning.ability;

import com.dragonegg.lightning.DragonEggLightningPlugin;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Manages player abilities and cooldowns.
 */
public class AbilityManager {

  private final DragonEggLightningPlugin plugin;
  private final Map<UUID, Ability> abilities;
  private final Map<UUID, Long> cooldowns;

  public AbilityManager(DragonEggLightningPlugin plugin) {
    this.plugin = plugin;
    this.abilities = new HashMap<>();
    this.cooldowns = new HashMap<>();

    registerAbilities();
  }

  /**
   * Register available abilities.
   */
  private void registerAbilities() {
    abilities.put(
      UUID.fromString("00000000-0000-0000-0000-000000000001"),
      new LightningAbility(plugin)
    );
  }

  /**
   * Get ability by ID.
   *
   * @param abilityId The ability ID (1, 2, etc.)
   * @return The ability or null if not found
   */
  public Ability getAbility(int abilityId) {
    UUID abilityUuid = UUID.fromString(
      String.format("00000000-0000-0000-0000-%012d", abilityId)
    );
    return abilities.get(abilityUuid);
  }

  /**
   * Check if player can use an ability.
   *
   * @param player The player
   * @param ability The ability
   * @return true if the ability can be used
   */
  public boolean canUseAbility(Player player, Ability ability) {
    if (player == null || ability == null) {
      return false;
    }
    if (!ability.hasRequiredItem(player)) {
      return false;
    }

    Long cooldownEnd = cooldowns.get(player.getUniqueId());
    if (cooldownEnd == null) {
      return true;
    }

    return System.currentTimeMillis() >= cooldownEnd;
  }

  /**
   * Use an ability.
   *
   * @param player The player
   * @param ability The ability
   * @return true if the ability was successfully used
   */
  public boolean useAbility(Player player, Ability ability) {
    if (!canUseAbility(player, ability)) {
      return false;
    }

    boolean success = ability.execute(player);
    if (success) {
      long cooldownEnd = System.currentTimeMillis() +
        ability.getCooldownMillis();
      cooldowns.put(player.getUniqueId(), cooldownEnd);
    }

    return success;
  }

  /**
   * Get remaining cooldown in seconds.
   *
   * @param player The player
   * @return Remaining cooldown in seconds, or 0 if no cooldown
   */
  public int getRemainingCooldown(Player player) {
    if (player == null) {
      return 0;
    }
    Long cooldownEnd = cooldowns.get(player.getUniqueId());
    if (cooldownEnd == null) {
      return 0;
    }

    long remaining = cooldownEnd - System.currentTimeMillis();
    if (remaining <= 0) {
      cooldowns.remove(player.getUniqueId());
      return 0;
    }

    return (int) Math.ceil(remaining / 1000.0);
  }

  /**
   * Check if player is on cooldown.
   *
   * @param player The player
   * @return true if on cooldown
   */
  public boolean isOnCooldown(Player player) {
    if (player == null) {
      return false;
    }
    return getRemainingCooldown(player) > 0;
  }

  /**
   * Clear cooldown for a player (for testing).
   *
   * @param player The player
   */
  public void clearCooldown(Player player) {
    if (player != null) {
      cooldowns.remove(player.getUniqueId());
    }
  }
}
