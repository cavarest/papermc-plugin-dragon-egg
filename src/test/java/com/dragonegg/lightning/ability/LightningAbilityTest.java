package com.dragonegg.lightning.ability;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for LightningAbility.
 */
class LightningAbilityTest {

  @Test
  void testAbilityCreation() {
    // Test that we can create a LightningAbility without errors
    LightningAbility ability = new LightningAbility(null);
    assertNotNull(ability, "LightningAbility should be created successfully");
  }

  @Test
  void testGetName() {
    LightningAbility ability = new LightningAbility(null);
    String name = ability.getName();
    assertNotNull(name, "Ability name should not be null");
    assertFalse(name.isEmpty(), "Ability name should not be empty");
    assertEquals("Lightning Strike", name, "Ability should have correct name");
  }

  @Test
  void testGetCooldownMillis() {
    LightningAbility ability = new LightningAbility(null);
    long cooldown = ability.getCooldownMillis();
    assertTrue(cooldown > 0, "Cooldown should be positive");
    assertEquals(60000L, cooldown, "Cooldown should be 60000ms (60 seconds)");
  }

  @Test
  void testAbilityProperties() {
    LightningAbility ability = new LightningAbility(null);

    // Test that the ability has the expected properties
    assertEquals("Lightning Strike", ability.getName(), "Ability should have correct name");
    assertTrue(ability.getCooldownMillis() > 0, "Cooldown should be positive");
  }

  @Test
  void testHasRequiredItem() {
    LightningAbility ability = new LightningAbility(null);
    // Test with null player - should return false
    boolean result = ability.hasRequiredItem(null);
    assertFalse(result, "Should return false for null player");
  }

  @Test
  void testExecuteWithNullPlayer() {
    LightningAbility ability = new LightningAbility(null);
    // Test that execute handles null player gracefully
    boolean result = ability.execute(null);
    assertFalse(result, "execute should return false for null player");
  }
}
