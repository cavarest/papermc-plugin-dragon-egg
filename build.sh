#!/bin/bash

# Build script for Dragon Egg Lightning plugin

set -e

echo "================================"
echo "Building Dragon Egg Lightning Plugin"
echo "================================"

# Clean and build
echo "Running Maven clean and package..."
mvn clean package

# Find the generated JAR file dynamically (handle different Java versions)
JAR_FILE=$(find target/ -name "DragonEggLightning-*.jar" | head -1)

if [ -n "$JAR_FILE" ]; then
    echo "✓ Plugin JAR created successfully!"
    echo "  Location: $JAR_FILE"
else
    echo "✗ Failed to create plugin JAR"
    echo "Checking for alternative JAR files..."
    ls -la target/
    exit 1
fi

echo ""
echo "================================"
echo "Build Complete!"
echo "================================"
echo ""
echo "Next steps:"
echo "  1. Start server: ./start-server.sh"
echo "  2. Stop server: ./stop-server.sh"
echo "  3. View logs: docker logs -f papermc-dragonegg"
