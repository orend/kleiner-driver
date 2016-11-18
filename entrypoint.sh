#!/bin/bash
set -e

echo "Starting kleiner-driver"

exec java \
  -XX:-OmitStackTraceInFastThrow \
  -XX:+UseG1GC \
  -jar /app/target/driver-0.1.0-standalone.jar \
  "$@"
