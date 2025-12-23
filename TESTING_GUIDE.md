# Dragon Egg Lightning Plugin - Comprehensive Testing Guide

## Table of Contents
1. [Quick Start for Testing](#quick-start)
2. [Installation Instructions](#installation)
3. [User Acceptance Testing](#user-acceptance-testing)
4. [Integration Testing](#integration-testing)
5. [Performance Testing](#performance-testing)
6. [Troubleshooting](#troubleshooting)

---

## Quick Start for Testing

### Prerequisites Check
```bash
# Verify Java version (must be 21+)
java -version

# Verify Docker is installed
docker --version
docker-compose --version

# Verify Maven is installed
mvn --version
```

### One-Command Test
```bash
# Run the complete test suite
./test-plugin.sh
```

---

## Installation Instructions

### For Server Administrators

#### Method 1: Automated Setup (Recommended)

1. **Clone/Download the Plugin**
   ```bash
   # If you have the source code
   git clone <repository-url>
   cd papermc-plugin-dragon-egg

   # Or download and extract the source code
   ```

2. **Build the Plugin**
   ```bash
   # Linux/Mac
   chmod +x build.sh
   ./build.sh

   # Windows
   build.sh
   ```

3. **Start Paper Server with Plugin**
   ```bash
   # Linux/Mac
   chmod +x start-server.sh
   ./start-server.sh

   # Windows
   start-server.sh
   ```

4. **Connect to Server**
   - Open Minecraft 1.21.8+
   - Add server: `localhost:25565`
   - Join the server

#### Method 2: Manual Setup

1. **Build JAR File**
   ```bash
   mvn clean package
   ```

2. **Setup Paper Server**
   ```bash
   # Create server directory
   mkdir paper-server
   cd paper-server

   # Download Paper server (1.21.8)
   wget https://api.papermc.io/v2/projects/paper/versions/1.21.8/builds/latest/downloads/paper-1.21.8-latest.jar

   # Start server
   java -Xms2G -Xmx2G -jar paper-1.21.8-latest.jar nogui
   ```

3. **Install Plugin**
   ```bash
   # Copy plugin to plugins folder
   cp target/DragonEggLightning-1.0.0.jar paper-server/plugins/

   # Restart server
   ```

#### Method 3: Docker Compose (Advanced)

1. **Using Provided Docker Setup**
   ```bash
   # Build and start
   docker-compose up -d

   # Check logs
   docker logs -f papermc-dragonegg

   # Stop
   docker-compose down
   ```

### For Third-Party Users

#### Server Requirements
- **Minecraft Version**: 1.21.8 or higher
- **Paper Server**: Latest Paper build
- **Java**: Version 21 or higher
- **Memory**: Minimum 2GB RAM allocated to server

#### Installation Steps

1. **Download Paper Server**
   - Visit [papermc.io](https://papermc.io)
   - Download Paper for Minecraft 1.21.8
   - Extract and run server initialization

2. **Install Plugin**
   - Download `DragonEggLightning-1.0.0.jar`
   - Place in server's `plugins/` directory
   - Start/restart the server

3. **Verify Installation**
   - Server console should show: `[INFO] DragonEggLightning enabled`
   - Use `/plugins` command to verify plugin is loaded

---

## User Acceptance Testing

### Pre-Testing Setup

1. **Server Configuration**
   ```bash
   # Ensure these settings in server.properties
   gamemode=survival
   difficulty=normal
   enable-command-block=false
   ```

2. **Test Player Setup**
   ```bash
   # Make player admin for testing
   op <username>

   # Give test items
   give @p minecraft:dragon_egg 5
   ```

### Test Case 1: Basic Functionality

**Objective**: Verify the lightning ability works correctly

**Steps**:
1. Join the server
2. Get a Dragon Egg: `/give @p minecraft:dragon_egg`
3. Move Dragon Egg to offhand: Press `F` key
4. Look at a target entity (zombie, cow, etc.)
5. Execute: `/ability 1`
6. Observe results

**Expected Results**:
- [ ] Purple lightning appears on target
- [ ] Lightning strikes 3 times with 0.5s intervals
- [ ] Target takes damage (1.5 hearts per strike)
- [ ] Success message appears: "Lightning ability activated!"
- [ ] HUD shows cooldown timer

**Pass/Fail Criteria**: All visual effects, damage, and messages work as expected

### Test Case 2: Cooldown System

**Objective**: Verify cooldown enforcement works

**Steps**:
1. Execute `/ability 1` successfully
2. Immediately try `/ability 1` again
3. Wait 60 seconds
4. Try `/ability 1` again

**Expected Results**:
- [ ] Second attempt fails with cooldown message
- [ ] HUD shows countdown (59s, 58s, etc.)
- [ ] After 60s, ability works again
- [ ] HUD shows "⚡ Lightning ready"

**Pass/Fail Criteria**: Cooldown system prevents spam and shows proper feedback

### Test Case 3: Item Requirements

**Objective**: Verify Dragon Egg requirement

**Steps**:
1. Remove Dragon Egg from offhand
2. Try `/ability 1`
3. Put Dragon Egg back in offhand
4. Try `/ability 1` again

**Expected Results**:
- [ ] Without Dragon Egg: Error message "You must hold a Dragon Egg in your offhand!"
- [ ] With Dragon Egg: Ability works normally

**Pass/Fail Criteria**: Ability only works with Dragon Egg in offhand

### Test Case 4: Target Detection

**Objective**: Verify targeting system

**Steps**:
1. Look at nearest entity within 50 blocks
2. Execute `/ability 1`
3. Look away from entities
4. Execute `/ability 1`
5. Look at entity and try again

**Expected Results**:
- [ ] Lightning targets closest entity in line of sight
- [ ] No targets: Error "No valid target found!"
- [ ] Target validation works correctly

**Pass/Fail Criteria**: Target detection works within specified parameters

### Test Case 5: Edge Cases

**Objective**: Verify edge case handling

**Steps**:
1. Start ability with target
2. Remove Dragon Egg during casting
3. Kill target during casting
4. Switch targets during casting

**Expected Results**:
- [ ] Item removal: "Ability cancelled! Dragon Egg removed from offhand."
- [ ] Target death: Remaining strikes stop
- [ ] No crashes or console errors

**Pass/Fail Criteria**: All edge cases handled gracefully

---

## Integration Testing

### Unit Tests

**Run Unit Tests**:
```bash
# Run all unit tests
mvn test

# Run specific test class
mvn test -Dtest=LightningAbilityTest
mvn test -Dtest=AbilityManagerTest

# Run with verbose output
mvn test -X
```

**Expected Results**:
```
[INFO] Tests run: 13, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

### Integration Tests

**Server Integration Test**:
```bash
# Start server with plugin
./start-server.sh

# Run integration tests in-game
# (Manual testing as documented above)

# Check server logs
docker logs papermc-dragonegg
```

**Expected Log Messages**:
```
[INFO] [DragonEggLightning] DragonEggLightning enabled
[INFO] [DragonEggLightning] AbilityManager initialized
[INFO] [DragonEggLightning] Lightning ability loaded
[INFO] [DragonEggLightning] HUD manager started
```

### Performance Tests

**Load Testing**:
1. Start server with plugin
2. Join with multiple test clients
3. Execute abilities simultaneously
4. Monitor server performance

**Metrics to Monitor**:
- [ ] Server tick rate remains at 20 TPS
- [ ] Memory usage stays stable
- [ ] No console errors during heavy use
- [ ] Lightning effects render smoothly

---

## Performance Testing

### Stress Testing

**High-Frequency Testing**:
```bash
# Create test scenario
# 1. Start server
./start-server.sh

# 2. Join with multiple clients
# 3. Execute /ability 1 repeatedly across clients
# 4. Monitor server console for performance issues
```

**Expected Performance**:
- [ ] Server maintains 20 TPS under normal load
- [ ] No memory leaks after extended use
- [ ] Lightning effects perform smoothly
- [ ] Cooldown calculations remain accurate

### Memory Usage Testing

**Monitor Memory**:
```bash
# Check server memory usage
docker stats papermc-dragonegg

# Monitor JVM memory
# In server console:
# /tps
# /mem
```

**Expected Memory Usage**:
- Initial startup: ~500MB
- After 1 hour gameplay: ~600MB
- No significant memory growth over time

---

## Troubleshooting

### Common Issues and Solutions

#### Issue 1: Plugin Not Loading

**Symptoms**:
- Plugin doesn't appear in `/plugins`
- No "DragonEggLightning enabled" message
- Command `/ability 1` not recognized

**Solutions**:
```bash
# Check Java version
java -version  # Must be 21+

# Check server logs
docker logs papermc-dragonegg
# OR
tail -f paper-server/logs/latest.log

# Verify JAR file
ls -la plugins/DragonEggLightning*.jar

# Check plugin.yml
# Ensure correct name and main class
```

#### Issue 2: Command Not Working

**Symptoms**:
- "Unknown command" message
- Permission denied errors

**Solutions**:
```bash
# Check permissions
op <username>  # Make player operator

# Verify plugin is loaded
/plugins

# Check command registration
# Server console should show:
# [INFO] [DragonEggLightning] Registering commands...
```

#### Issue 3: Lightning Not Appearing

**Symptoms**:
- Command works but no visual effects
- Target doesn't take damage

**Solutions**:
```bash
# Check particle settings
# In server.properties:
enable-command-block=true
enable-query=true

# Monitor server performance
# Low FPS can cause particle lag

# Check for conflicts
# Disable other combat/particle plugins temporarily
```

#### Issue 4: HUD Not Displaying

**Symptoms**:
- Ability works but no cooldown display
- Action bar doesn't show messages

**Solutions**:
```bash
# Check Adventure API compatibility
# Server must have Adventure API support

# Verify HUD manager
# Server console should show:
# [INFO] [DragonEggLightning] HUD manager started
```

### Debug Commands

```bash
# Plugin status
/plugins
/dragonreload  # Reload plugin (if implemented)

# Server performance
/tps
/mem

# Debug logging
# Add to server.properties:
# debug=true
```

### Log Analysis

**Important Log Messages**:
```bash
# Successful loading
[INFO] [DragonEggLightning] DragonEggLightning enabled
[INFO] [DragonEggLightning] Loaded Lightning ability
[INFO] [DragonEggLightning] HUD manager initialized

# Command usage
[INFO] <player> issued server command: /ability 1

# Ability execution
[INFO] [DragonEggLightning] Lightning ability executed by <player>
[INFO] [DragonEggLightning] Target found: <entity>
[INFO] [DragonEggLightning] Lightning strike #1/3

# Errors
[ERROR] [DragonEggLightning] Failed to execute ability: <error>
[WARN] [DragonEggLightning] Player <player> missing required item
```

### Performance Issues

**Memory Problems**:
```bash
# Increase JVM memory
java -Xms4G -Xmx4G -jar paper-1.21.8-latest.jar

# Monitor garbage collection
# Add JVM flags:
-XX:+UseG1GC
-XX:+ParallelRefProcEnabled
-XX:MaxGCPauseMillis=200
```

**High CPU Usage**:
```bash
# Optimize plugin settings
# Reduce particle counts in LightningAbility.java

# Check for infinite loops
# Monitor server TPS with /tps command
```

---

## Test Results Documentation

### Test Environment
- **Server**: Paper 1.21.8
- **Java**: OpenJDK 21
- **Plugin Version**: 1.0.0
- **Test Date**: <Date>
- **Tester**: <Name>

### Test Results Summary

| Test Case | Status | Notes |
|-----------|--------|-------|
| Basic Functionality | ✅/❌ | |
| Cooldown System | ✅/❌ | |
| Item Requirements | ✅/❌ | |
| Target Detection | ✅/❌ | |
| Edge Cases | ✅/❌ | |
| Unit Tests | ✅/❌ | |
| Integration Tests | ✅/❌ | |
| Performance Tests | ✅/❌ | |

### Performance Metrics

- **Server TPS**: <Value> (Target: 20)
- **Memory Usage**: <Value> MB (Target: <600MB)
- **Average Response Time**: <Value> ms
- **Particle Render Rate**: <Value> FPS

### Issues Found

1. **Critical Issues**:
   - <Description>
   - <Reproduction Steps>
   - <Expected vs Actual>

2. **Minor Issues**:
   - <Description>
   - <Impact Assessment>

### Recommendations

- <Performance improvements>
- <Feature enhancements>
- <Code optimizations>
- <Documentation updates>

---

## Contact Information

**For Technical Issues**:
- Check server logs first
- Reproduce with minimal plugins
- Document exact error messages
- Include server configuration

**For Feature Requests**:
- Describe desired functionality
- Explain use case
- Consider performance impact
- Review existing architecture

**Support Channels**:
- GitHub Issues
- Server Console Logs
- Discord Community (if available)
- Email Support (if applicable)

---

**Last Updated**: December 24, 2025
**Plugin Version**: 1.0.0
**Tested With**: Paper 1.21.8, Java 21
