#!/bin/bash

# Start Paper Minecraft server with Dragon Egg Lightning plugin

set -e

echo "================================"
echo "Starting Paper MC Server"
echo "================================"

# Find the generated JAR file dynamically (handle different Java versions)
JAR_FILE=$(find target/ -name "DragonEggLightning-*.jar" | head -1)

if [ -z "$JAR_FILE" ]; then
    echo "✗ Plugin JAR not found!"
    echo "  Please run ./build.sh first"
    echo "Checking for JAR files..."
    ls -la target/ 2>/dev/null || echo "No target directory found"
    exit 1
fi

echo "✓ Plugin JAR found: $JAR_FILE"

# Start Docker container
echo "Starting Docker container..."
docker-compose up -d

echo ""
echo "================================"
echo "Server Starting!"
echo "================================"
echo ""
echo "Server will be available on port 25565"
echo ""
echo "Useful commands:"
echo "  View logs:      docker logs -f papermc-dragonegg"
echo "  Server console: docker attach papermc-dragonegg"
echo "  Stop server:    ./stop-server.sh"
echo ""
echo "Waiting for server to start (this may take a minute)..."
sleep 5
docker logs papermc-dragonegg
