SUMMARY = "MAVLink Router"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=93888867ace35ffec2c845ea90b2e16b"

DEPENDS = "googlebenchmark systemd mavlink"

# Use the latest master branch
SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/mavlink-router/mavlink-router.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

inherit meson pkgconfig systemd

# THE FIX: 
# Instead of patching meson.build, we just create the folders it wants.
# Even if they are empty, Meson will stop complaining.
do_configure:prepend() {
    mkdir -p ${S}/modules/mavlink_c_library_v2/ardupilotmega
}

# Add the sysroot mavlink directory to the search path via CFLAGS
# This ensures the compiler finds the actual headers from your Yocto sysroot
TARGET_CFLAGS += "-I${STAGING_INCDIR}/mavlink"
TARGET_CPPFLAGS += "-I${STAGING_INCDIR}/mavlink"

EXTRA_OEMESON = "-Dsystemdsystemunitdir=${systemd_system_unitdir}"
SYSTEMD_SERVICE:${PN} = "mavlink-router.service"