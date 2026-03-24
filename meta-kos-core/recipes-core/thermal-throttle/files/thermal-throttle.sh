#!/bin/bash
# MerlinOS Thermal Throttling Daemon
# Designed for drone AI workload management

# Critical Temperature Limit (80C)
TEMP_LIMIT=80000
# Recovery Temperature Limit (70C)
RECOVER_LIMIT=70000

# Track state to avoid spamming logs
THROTTLED=0

while true; do
    if [ -f /sys/class/thermal/thermal_zone0/temp ]; then
        TEMP=$(cat /sys/class/thermal/thermal_zone0/temp)
        
        if [ "$TEMP" -ge "$TEMP_LIMIT" ] && [ "$THROTTLED" -eq 0 ]; then
            echo "CRITICAL: Thermal Limit Reached (${TEMP}mC). Stopping AI Workloads..."
            
            # Pause the heavy background processing model
            # Note: SIGSTOP aggressively pauses a process without killing it,
            # saving its state in RAM while instantly freezing CPU usage!
            killall -STOP myscript.sh 2>/dev/null
            
            THROTTLED=1
            
        elif [ "$TEMP" -le "$RECOVER_LIMIT" ] && [ "$THROTTLED" -eq 1 ]; then
            echo "RECOVERY: Temperature normalized (${TEMP}mC). Resuming operations..."
            
            # Send SIGCONT to smoothly unpause the process
            killall -CONT myscript.sh 2>/dev/null
            
            THROTTLED=0
        fi
    fi
    
    # Check thermals every 5 seconds
    sleep 5
done
