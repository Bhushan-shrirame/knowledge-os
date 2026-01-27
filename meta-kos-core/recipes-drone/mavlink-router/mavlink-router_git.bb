SUMMARY = "MAVLink Router"
DESCRIPTION = "Bridge MAVLink packets between different interfaces"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=93888867ace35ffec2c845ea90b2e16b"

# Dependencies for build-time
DEPENDS = "googlebenchmark systemd mavlink"

SRCREV = "master"
SRC_URI = "git://github.com/mavlink-router/mavlink-router.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

inherit meson pkgconfig systemd

# Patch the build system to use Yocto's sysroot instead of internal git submodules
do_configure:prepend() {
    sed -i "s|include_directories('modules/mavlink_c_library_v2')|include_directories('${STAGING_INCDIR}/mavlink')|g" ${S}/meson.build
    sed -i "s|'modules/mavlink_c_library_v2/ardupilotmega'|'${STAGING_INCDIR}/mavlink/ardupilotmega'|g" ${S}/meson.build
}

EXTRA_OEMESON = "-Dsystemdsystemunitdir=${systemd_system_unitdir}"

SYSTEMD_SERVICE:${PN} = "mavlink-router.service"