# =========================================================================
# Recipe: startup-service
# Description: Installs a script and a systemd user service to execute
#              background tasks when any user logs into the system.
#
# Unlike system-wide systemd services, this user service runs in the
# context of the logged-in user and terminates when they log out.
# =========================================================================

SUMMARY = "Custom user login startup service"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
    file://myscript.sh \
    file://mystartup.service \
"

S = "${WORKDIR}"

# Note: We do not inherit the 'systemd' class directly here.
# The 'systemd' class is optimized for system-wide services (running as root 
# on boot) rather than user sessions. Because we want this to be a *user* 
# service that automatically starts on login, we manually install it to the 
# global systemd user directories.
do_install() {
    # 1. Install the executable script to /usr/bin
    # This is the actual script that will run in the background.
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/myscript.sh ${D}${bindir}/myscript.sh

    # 2. Install the systemd system service file globally
    # /usr/lib/systemd/system/ is where systemd looks for system unit files.
    install -d ${D}${nonarch_libdir}/systemd/system
    install -m 0644 ${WORKDIR}/mystartup.service ${D}${nonarch_libdir}/systemd/system/mystartup.service

    # 3. Enable the service to start implicitly on boot
    # By creating a symlink in multi-user.target.wants, systemd will automatically
    # start this service during the boot process.
    install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants
    ln -sf ${nonarch_libdir}/systemd/system/mystartup.service ${D}${sysconfdir}/systemd/system/multi-user.target.wants/mystartup.service
}

# Ensure bitbake knows to package these newly created directories and files
# into the final output package.
FILES:${PN} += " \
    ${nonarch_libdir}/systemd/system/mystartup.service \
    ${sysconfdir}/systemd/system/multi-user.target.wants/mystartup.service \
"

# If your script requires bash instead of sh, uncomment the following line:
# RDEPENDS:${PN} += "bash"
