SUMMARY = "MerlinOS Kernel Hardening & Sysctl Tuning"
DESCRIPTION = "Injects custom sysctl rules for RTOS hardware, telemetry buffering, and memory strictness."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://10-robotics-sysctl.conf"
S = "${WORKDIR}"

do_install() {
    # sysctl.d allows us to override kernel params cleanly without touching the base image
    install -d ${D}${sysconfdir}/sysctl.d
    install -m 0644 ${WORKDIR}/10-robotics-sysctl.conf ${D}${sysconfdir}/sysctl.d/10-robotics-sysctl.conf
}

FILES:${PN} += "${sysconfdir}/sysctl.d/10-robotics-sysctl.conf"
