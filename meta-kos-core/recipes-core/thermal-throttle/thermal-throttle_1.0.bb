SUMMARY = "MerlinOS Thermal Throttling Service"
DESCRIPTION = "Background daemon to monitor /sys/class/thermal and pause heavy robotics processes."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
    file://thermal-throttle.sh \
    file://thermal-throttle.service \
"
S = "${WORKDIR}"

RDEPENDS:${PN} = "bash"

inherit systemd

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE:${PN} = "thermal-throttle.service"

do_install() {
    # Install the script natively
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/thermal-throttle.sh ${D}${bindir}/thermal-throttle.sh

    # Install the systemd service globally
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/thermal-throttle.service ${D}${systemd_system_unitdir}
}

FILES:${PN} += "${systemd_system_unitdir}/thermal-throttle.service"
