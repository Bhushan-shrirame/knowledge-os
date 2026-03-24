SUMMARY = "MerlinOS Network Bonding Failover Configuration"
DESCRIPTION = "Installs systemd-networkd rules to bond multiple network interfaces for seamless drone telemetry failover."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
    file://10-bond0.netdev \
    file://20-eth0.network \
    file://20-wlan0.network \
    file://30-bond0.network \
"
S = "${WORKDIR}"

do_install() {
    install -d ${D}${sysconfdir}/systemd/network
    install -m 0644 ${WORKDIR}/10-bond0.netdev ${D}${sysconfdir}/systemd/network/
    install -m 0644 ${WORKDIR}/20-eth0.network ${D}${sysconfdir}/systemd/network/
    install -m 0644 ${WORKDIR}/20-wlan0.network ${D}${sysconfdir}/systemd/network/
    install -m 0644 ${WORKDIR}/30-bond0.network ${D}${sysconfdir}/systemd/network/
}

FILES:${PN} += "${sysconfdir}/systemd/network/*"
