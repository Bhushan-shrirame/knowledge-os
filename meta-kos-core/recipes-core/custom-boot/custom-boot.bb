SUMMARY = "Knowledge OS Splash screen"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://loading-anim.sh \
           file://knowledge-splash.service"

inherit systemd

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "knowledge-splash.service"
# This ensures the service is 'enabled' by default
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

do_install() {
    # Install the script
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/loading-anim.sh ${D}${bindir}/

    # Install the service unit
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/knowledge-splash.service ${D}${systemd_system_unitdir}/
}

# Ensure the files are actually included in the package
FILES:${PN} += "${bindir}/loading-anim.sh ${systemd_system_unitdir}/knowledge-splash.service"