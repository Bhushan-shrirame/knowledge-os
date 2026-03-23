#!/bin/bash
# MerlinOS Performance Profiler
# Usage: ./profile_merlin.sh [test_name]

NAME=${1:-"baseline"}
OUT_DIR="/tmp/benchmarks/$NAME"
mkdir -p $OUT_DIR

echo "--- Initializing MerlinOS Profiling Sequence: [$NAME] ---"

# 1. Boot Time Analysis
echo "[1/4] Analyzing Boot Sequence..."
systemd-analyze time > $OUT_DIR/boot_time.txt
systemd-analyze blame | head -n 20 > $OUT_DIR/boot_blame.txt

# 2. Disk I/O Baseline (Are we wearing out the SSD?)
echo "[2/4] Measuring Disk I/O (60 seconds)..."
iostat -xz 1 60 > $OUT_DIR/disk_io.txt &

# 3. CPU Latency/Jitter (Crucial for AI/Drone stability)
# Runs cyclictest on all cores for 1 minute
echo "[3/4] Measuring Interrupt Latency (Cyclictest)..."
cyclictest --loops=60000 --priority=99 --smp --quiet > $OUT_DIR/latency.txt &

# 4. Thermal & Load Snapshot
echo "[4/4] Capturing Thermal & Load State..."
uptime > $OUT_DIR/load.txt
if [ -f /sys/class/thermal/thermal_zone0/temp ]; then
    cat /sys/class/thermal/thermal_zone0/temp > $OUT_DIR/cpu_temp.txt
fi

wait
echo "--- Profiling Complete. Results saved to $OUT_DIR ---"
