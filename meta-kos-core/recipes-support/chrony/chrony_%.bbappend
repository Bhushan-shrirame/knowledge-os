# =========================================================================
# Recipe bbappend: chrony
# Description: Customizes the Chrony NTP daemon to forcefully prioritize 
#              Hardware GPS (GNSS) over the internet for time sync. 
#              Strictly required for accurate ROS 2 multi-drone timestamps.
# =========================================================================

# Append our custom GPS and PPS logic dynamically to chrony's default config
do_install:append() {
    # 1. SHM 0 (GPSd NMEA messages)
    # The 'delay' is larger because NMEA serial parsing is slow
    echo "refclock SHM 0 delay 0.5 refid GPS" >> ${D}${sysconfdir}/chrony.conf

    # 2. SHM 1 (GPSd PPS signal - Pulse Per Second)
    # PPS provides microsecond-level accuracy. We prioritize this highly!
    echo "refclock SHM 1 offset 0.0 delay 0.1 refid PPS prefer" >> ${D}${sysconfdir}/chrony.conf

    # 3. Allow chrony to step the clock (jump) abruptly if it is out of sync 
    # upon boot (drones usually don't have internet to catch up slowly).
    # makestep <threshold> <number of updates> (1 sec, first 3 updates)
    echo "makestep 1.0 3" >> ${D}${sysconfdir}/chrony.conf
    
    # 4. Disable internet pools if we are offline
    echo "offline" >> ${D}${sysconfdir}/chrony.conf
}
