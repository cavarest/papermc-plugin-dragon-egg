# Dragon Egg Lightning Plugin

A Minecraft Paper plugin that allows players to cast lightning abilities using Dragon Eggs held in their offhand.

---

## For Server Administrators 🚀

### Quick Installation (5 Minutes)

#### Step 1: Download the Plugin
```bash
# Download the latest release from GitHub
# Visit: https://github.com/ronaldtse/papermc-plugin-dragon-egg/releases
# Download: DragonEggLightning-0.1.0.jar
```

#### Step 2: Install on Your Paper Server
```bash
# 1. Stop your Paper server (if running)
# 2. Copy the JAR file to your plugins directory
cp DragonEggLightning-0.1.0.jar /path/to/your/paper-server/plugins/

# 3. Start/restart your Paper server
java -Xms2G -Xmx2G -jar paper-1.21.8-latest.jar nogui
```

#### Step 3: Verify Installation
1. **Check Server Console**: Look for `[INFO] DragonEggLightning enabled`
2. **In-Game Test**: `/plugins` should show "DragonEggLightning"
3. **Command Test**: `/ability 1` should work (after getting Dragon Egg)

### Complete Feature Testing Protocol

Use this comprehensive checklist to verify every feature works correctly:

#### ✅ Basic Installation Tests
- [ ] Server starts without errors
- [ ] Plugin appears in `/plugins` list
- [ ] Server console shows "DragonEggLightning enabled"
- [ ] No critical errors in server logs

#### ✅ Core Functionality Tests

**Test 1: Plugin Loading**
```bash
# In server console or with plugins:
/plugins
# Expected: Shows "DragonEggLightning" in green
```

**Test 2: Command Recognition**
```bash
# In Minecraft chat:
/ability 1
# Expected:
# - With Dragon Egg in offhand: Shows "No valid target found!" or attempts to cast
# - Without Dragon Egg: Shows "You must hold a Dragon Egg in your offhand!"
```

**Test 3: Dragon Egg Requirement**
```bash
# Steps to test:
1. /give @p minecraft:dragon_egg
2. /ability 1  # Should fail - no offhand check yet
3. Press F key to move Dragon Egg to offhand
4. /ability 1  # Should work if target available
# Expected: Ability only works with Dragon Egg in offhand
```

**Test 4: Lightning Ability Execution**
```bash
# Setup:
1. /give @p minecraft:dragon_egg 5
2. Press F to move Dragon Egg to offhand
3. Find a target entity (zombie, cow, villager, etc.)
4. Look at the entity (within 50 blocks)
/ability 1

# Expected Results:
- [ ] Purple lightning strikes appear
- [ ] 3 strikes with 0.5-second intervals between each
- [ ] Target takes 1.5 hearts damage per strike
- [ ] Success message: "Lightning ability activated!"
- [ ] Thunder sound plays for each strike
```

**Test 5: Cooldown System**
```bash
# Test sequence:
1. /ability 1  # First use - should work
2. Immediately /ability 1  # Should fail - cooldown active
3. Wait 60 seconds
4. /ability 1  # Should work again

# Expected Results:
- [ ] First attempt works normally
- [ ] Second attempt fails with cooldown message
- [ ] HUD shows countdown: "59s", "58s", etc.
- [ ] After 60s, shows "⚡ Lightning ready"
- [ ] Ability works again after cooldown
```

**Test 6: Target Detection**
```bash
# Test scenarios:
1. Look at entity within 50 blocks → Should target closest entity
2. Look at empty area → Should show "No valid target found!"
3. Look at entity behind wall → Should show "No valid target found!"
4. Look at entity >50 blocks away → Should show "No valid target found!"

# Expected Results:
- [ ] Accurate targeting within 50-block range
- [ ] Line-of-sight validation (no targeting through walls)
- [ ] Clear error messages for invalid targets
```

**Test 7: Edge Case Handling**
```bash
# Test item switching during casting:
1. Start lightning with /ability 1
2. During strikes, remove Dragon Egg from offhand
# Expected: "Ability cancelled! Dragon Egg removed from offhand."

# Test target death during casting:
1. Start lightning with /ability 1
2. Kill target during strikes
# Expected: Remaining strikes stop gracefully

# Test multiple rapid use:
1. Use /ability 1
2. Try to use repeatedly
# Expected: Cooldown prevents spam
```

#### ✅ Performance Tests

**Test 8: Server Performance**
```bash
# Monitor during lightning use:
/tps  # Should remain ~20
/mem  # Check memory usage

# Expected Results:
- [ ] TPS doesn't drop significantly during lightning
- [ ] Memory usage stays stable
- [ ] No console errors during heavy use
```

**Test 9: Visual Performance**
```bash
# Test with different graphics settings:
- Low: Particles → Minimal
- Medium: Particles → All
- High: Particles → All (maximum effects)

# Expected Results:
- [ ] Lightning visible at all particle levels
- [ ] No client-side lag from effects
- [ ] Smooth 60 FPS gameplay
```

### Troubleshooting Common Issues

#### Issue: Plugin Not Loading
**Symptoms**: `/plugins` doesn't show DragonEggLightning
```bash
# Solutions:
1. Check Java version: java -version  # Must be 21+
2. Verify JAR location: ls plugins/DragonEggLightning*.jar
3. Check server logs: tail -f logs/latest.log | grep -i dragon
4. Restart server after plugin installation
```

#### Issue: Command Not Working
**Symptoms**: "Unknown command" or permission denied
```bash
# Solutions:
1. Make player operator: op <username>
2. Verify plugin loaded: /plugins
3. Check server logs for command registration errors
```

#### Issue: Lightning Not Appearing
**Symptoms**: Command works but no visual effects
```bash
# Solutions:
1. Check graphics settings: Video Settings → Particles → All
2. Monitor server performance: /tps
3. Check for plugin conflicts
4. Reduce render distance if needed
```

#### Issue: Server Lag
**Symptoms**: TPS drops during lightning use
```bash
# Solutions:
1. Increase JVM memory: java -Xms4G -Xmx4G -jar paper-*.jar
2. Monitor with: /tps
3. Check memory with: /mem
4. Consider reducing max players
```

### Server Configuration Recommendations

#### Optimal server.properties settings:
```properties
gamemode=survival
difficulty=normal
view-distance=10
simulation-distance=10
max-players=20
max-tick-time=60000
entity-broadcast-range-percentage=100
```

#### JVM Arguments for Better Performance:
```bash
java -Xms4G -Xmx4G -XX:+UseG1GC -XX:+ParallelRefProcEnabled -XX:MaxGCPauseMillis=200 -jar paper-1.21.8-latest.jar
```

### Monitoring Commands
```bash
# Plugin status
/plugins

# Server performance
/tps  # Target: 20
/mem  # Check memory usage

# Plugin debugging
/dragonreload  # Reload plugin (if implemented)
```

---

## Features

### Lightning Ability (`/ability 1`)
- **Requirement**: Dragon Egg must be held in offhand
- **Effect**: Summons 3 purple lightning strikes targeting the closest entity in player's facing direction
- **Damage**: 1.5 hearts (3 HP) per lightning strike (total 4.5 hearts if all three connect)
- **Timing**: Lightning strikes occur sequentially with 0.5-second intervals between each strike
- **Cooldown**: 60 seconds after ability use
- **Visual**: Purple/magenta colored lightning with particle effects

### HUD Display
- **Location**: Middle-left of the screen (action bar)
- **During Cooldown**: Displays remaining time in seconds (e.g., "59s", "58s", etc.)
- **When Ready**: Displays "⚡ Lightning ready"

### Edge Case Handling
- No valid targets: Shows error message
- Player switches items during casting: Cancels ability
- Dead targets: Cancels remaining strikes
- Cooldown enforcement: Prevents ability spam
- Required item validation: Fails if Dragon Egg not in offhand

## Installation (Development)

### Prerequisites
- Java 21+
- Docker and Docker Compose
- Maven 3.6+

### Quick Start

```bash
# 1. Build the plugin
./build.sh

# 2. Start the server
./start-server.sh

# 3. Connect to server (localhost:25565)

# 4. Test the plugin
./test-plugin.sh

# 5. Stop the server
./stop-server.sh
```

### Manual Setup

```bash
# Build plugin
mvn clean package

# Start Docker server
docker-compose up -d

# Check logs
docker logs -f papermc-dragonegg

# Stop server
docker-compose down
```

## Usage

### In-Game Commands

```bash
# Get a Dragon Egg
/give @p minecraft:dragon_egg

# Activate lightning ability (must have Dragon Egg in offhand)
/ability 1
```

### Player Instructions

1. **Obtain Dragon Egg**: Use `/give @p minecraft:dragon_egg`
2. **Equip Offhand**: Press `F` key to move Dragon Egg to offhand
3. **Use Ability**: Execute `/ability 1`
4. **Watch HUD**: Monitor cooldown status in action bar
5. **Target**: Look at nearest entity (within 50 blocks, in line of sight)

## Architecture

### Core Components

1. **DragonEggLightningPlugin**: Main plugin class
2. **AbilityManager**: Manages abilities and cooldowns
3. **LightningAbility**: Implements the lightning strike mechanic
4. **AbilityCommand**: Handles `/ability 1` command
5. **HudManager**: Displays cooldown status to players

### Class Structure

```
DragonEggLightningPlugin
├── AbilityManager
│   ├── Ability (interface)
│   └── LightningAbility (implements Ability)
├── AbilityCommand
└── HudManager
```

### Data Flow

1. Player executes `/ability 1` command
2. `AbilityCommand` validates input and checks prerequisites
3. `AbilityManager` checks cooldown and item requirements
4. `LightningAbility` executes the ability:
   - Finds target entity using ray tracing
   - Creates sequential lightning strikes with timing
   - Applies damage and visual effects
5. `HudManager` updates cooldown display

## Development

### Project Structure

```
├── src/
│   ├── main/
│   │   ├── java/com/dragonegg/lightning/
│   │   │   ├── DragonEggLightningPlugin.java
│   │   │   ├── ability/
│   │   │   │   ├── Ability.java
│   │   │   │   ├── AbilityManager.java
│   │   │   │   └── LightningAbility.java
│   │   │   ├── command/
│   │   │   │   └── AbilityCommand.java
│   │   │   └── hud/
│   │   │       └── HudManager.java
│   │   └── resources/
│   │       └── plugin.yml
│   └── test/
│       └── java/com/dragonegg/lightning/ability/
│           ├── AbilityManagerTest.java
│           └── LightningAbilityTest.java
├── target/                    # Build output
├── server-data/               # Minecraft server data
├── docker-compose.yml         # Docker configuration
├── build.sh                   # Build script
├── start-server.sh            # Server startup script
├── stop-server.sh             # Server shutdown script
└── test-plugin.sh             # Testing script
```

### Key Technologies

- **Paper API 1.21.8**: Latest Minecraft server API
- **Java 21**: Modern Java features
- **Maven**: Build automation and dependency management
- **JUnit 5**: Unit testing framework
- **Mockito**: Mocking framework for testing
- **Docker**: Containerized server environment
- **Adventure API**: Rich text formatting

### Design Principles

1. **Modular Architecture**: Separate concerns for abilities, commands, and UI
2. **Interface-Based Design**: Pluggable ability system
3. **Event-Driven**: Uses Bukkit events for integration
4. **Test-Driven**: Comprehensive unit tests
5. **Error Handling**: Graceful handling of edge cases
6. **Performance**: Efficient ray tracing and particle effects

### Adding New Abilities

1. Create new class implementing `Ability` interface
2. Register ability in `AbilityManager.registerAbilities()`
3. Add command parsing logic in `AbilityCommand`
4. Add tests for the new ability
5. Update HUD if needed

## Configuration

### Plugin Settings

Modify constants in `LightningAbility.java`:

```java
private static final int STRIKE_COUNT = 3;           // Number of lightning strikes
private static final long STRIKE_INTERVAL_TICKS = 10L; // Interval between strikes (ticks)
private static final double DAMAGE_PER_STRIKE = 3.0;   // Damage per strike (HP)
private static final long COOLDOWN_MILLIS = 60000L;    // Cooldown duration (milliseconds)
private static final double MAX_RANGE = 50.0;          // Maximum targeting range
```

### Docker Configuration

Edit `docker-compose.yml` to adjust:
- Memory allocation (`MEMORYSIZE`)
- Server ports
- World data persistence

## Performance Notes

- Ray tracing optimized for entities within 50 blocks
- Particle effects limited to prevent lag
- Cooldown system prevents spam
- HUD updates efficiently at 20 FPS

## Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request

### Code Style

- Follow Java naming conventions
- Add Javadoc for public methods
- Include unit tests for new features
- Use meaningful variable names
- Keep methods focused and concise

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For issues and questions:
1. Check existing issues on GitHub
2. Create detailed bug report with logs
3. Include server configuration and plugin version
4. Describe steps to reproduce the issue

**For Server Administrators**: Check the comprehensive testing guide above first. Most issues can be resolved by following the troubleshooting steps.

---

**Author**: Augustus
**Version**: 0.1.0
**Paper API**: 1.21.8-R0.1-SNAPSHOT
**Minecraft Version**: 1.21.8+
**Release**: https://github.com/ronaldtse/papermc-plugin-dragon-egg/releases/tag/v0.1.0
