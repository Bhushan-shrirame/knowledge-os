SUMMARY = "MerlinOS Performance Profiling Script"
DESCRIPTION = "A script to capture boot times, disk IO, latency, and temperatures for MerlinOS profiling."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://profile_merlin.sh"

S = "${WORKDIR}"

RDEPENDS:${PN} = "bash rt-tests sysstat iotop procps"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/profile_merlin.sh ${D}${bindir}/profile_merlin
}

FILES:${PN} = "${bindir}/profile_merlin"
