# =========================================================================
# Systemd Configuration Append (systemd_%.bbappend)
# =========================================================================
#
# This file is a wildcard version bbappend for the "systemd" recipe.
# The `%` acts as a wildcard, meaning this append file will apply to ANY
# version of systemd that the Yocto build system attempts to compile
# (e.g., systemd_255.bb, systemd_254.bb).
#
# Its purpose is to tweak the default systemd runtime configuration after
# systemd has been installed into the temporary image staging area.
# =========================================================================


# --- Installation Overrides ---
# do_install:append(): In Yocto, you can append custom shell commands to
# the end of an existing task. `do_install` is the task where a recipe
# copies its compiled binaries and configuration files into the temporary
# destination folder (${D}) before they are packaged up.
#
# Here, we hook into the end of systemd's installation phase to modify its
# primary configuration file (/etc/systemd/system.conf).
do_install:append() {
    # Check if the configuration file actually exists in the target directory
    if [ -e ${D}${sysconfdir}/systemd/system.conf ]; then
        
        # 1. Disable Boot Status Messages
        # By default, systemd prints "[  OK  ]" or "[FAILED]" messages to the
        # console as services start up. We use 'sed' to uncomment and change
        # 'ShowStatus=yes' to 'ShowStatus=no'. This keeps the boot screen
        # completely clean so our custom ASCII art boot splash isn't garbled.
        sed -i 's/#ShowStatus=yes/ShowStatus=no/' ${D}${sysconfdir}/systemd/system.conf
        
        # 2. Reduce Systemd Log Level
        # By default, systemd logs at the 'info' level, which can still print
        # some noise to the console. We change 'LogLevel=info' to 'LogLevel=emerg'
        # so systemd only outputs absolute emergency critical errors during boot.
        sed -i 's/#LogLevel=info/LogLevel=emerg/' ${D}${sysconfdir}/systemd/system.conf
    fi
}