# Dragon Egg Lightning Plugin

A Minecraft Paper plugin that allows players to cast lightning abilities using Dragon Eggs held in their offhand.

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

## Installation

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

### Testing Checklist

- [ ] Plugin loads without errors
- [ ] `/ability 1` command works with Dragon Egg in offhand
- [ ] Purple lightning strikes appear visually
- [ ] Lightning deals correct damage (1.5 hearts per strike)
- [ ] Three strikes occur with 0.5-second intervals
- [ ] HUD shows cooldown timer during cooldown
- [ ] HUD shows "Lightning ready" when available
- [ ] Ability fails without Dragon Egg in offhand
- [ ] Ability fails when on cooldown
- [ ] Error message appears when no targets found
- [ ] Ability cancels if items switched during casting

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

- **Paper API 1.21.4**: Latest Minecraft server API
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

## Troubleshooting

### Common Issues

1. **Plugin not loading**: Check Java version (requires Java 21+)
2. **Command not working**: Verify permissions and Dragon Egg in offhand
3. **No lightning effects**: Check server console for errors
4. **HUD not showing**: Ensure Adventure API compatibility

### Debug Commands

```bash
# Check plugin status
/plugins

# Reload plugin
/dragonreload

# View server logs
docker logs papermc-dragonegg

# Check Minecraft logs
# In server console: tail -f logs/latest.log
```

### Performance Notes

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

---

**Author**: Augustus
**Version**: 1.0.0
**Paper API**: 1.21.4-R0.1-SNAPSHOT
**Minecraft Version**: 1.21.8+
