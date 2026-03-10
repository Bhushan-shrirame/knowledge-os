# =========================================================================
# Custom Boot Splash Recipe (custom-boot.bb)
# =========================================================================
#
# This recipe is responsible for installing and enabling a custom boot splash
# screen for KnowledgeOS. It copies a local bash script containing a loading
# animation and sets it up to run automatically during boot via a dedicated
# systemd service unit.
#
# Recipes (.bb files) are the fundamental building blocks of Yocto. They
# instruct the build system where to find source code or local files, how to
# patch them if necessary, how to compile them, and finally, how to install
# the resulting artifacts into the root filesystem.
# =========================================================================

# --- Recipe Metadata ---
# SUMMARY: A short description of what this recipe provides.
SUMMARY = "Knowledge OS Splash screen"

# LICENSE: The license under which this software is distributed.
# LIC_FILES_CHKSUM: Yocto requires a checksum of the license text to ensure
# that if the upstream license changes, the build system halts and warns the
# developer. Here we use the standard MIT license text provided by Yocto.
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"


# --- Source Files ---
# SRC_URI: Defines where the source files come from. Using "file://" tells
# BitBake to look for these files in the local recipe directory (usually in
# a folder named 'files' or right alongside the .bb file).
# We are grabbing our animation script and our custom systemd service file.
SRC_URI = "file://loading-anim.sh \
           file://knowledge-splash.service"


# --- Systemd Integration ---
# inherit systemd: This tells the recipe to pull in the systemd class, which
# provides automatic handling for packaging systemd service units and enabling
# them to run on boot.
inherit systemd

# SYSTEMD_PACKAGES: Specifies which package within this recipe contains the
# systemd service. ${PN} refers to the primary Package Name (custom-boot).
SYSTEMD_PACKAGES = "${PN}"

# SYSTEMD_SERVICE: Tells the systemd class exactly which .service file to
# manage for the specified package.
SYSTEMD_SERVICE:${PN} = "knowledge-splash.service"

# SYSTEMD_AUTO_ENABLE: Ensures the service is automatically 'enabled' by
# default on the target rootfs (meaning it starts at boot time).
SYSTEMD_AUTO_ENABLE:${PN} = "enable"


# --- Installation Phase ---
# do_install(): This function dictates exactly how files should be placed
# into the temporary destination folder (${D}) before they get bundled into
# a package.
do_install() {
    # Install the script:
    # 1. Create the target binary directory (/usr/bin usually).
    # 2. Copy the script from the working directory to the target directory
    #    with executable permissions (0755).
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/loading-anim.sh ${D}${bindir}/

    # Install the systemd service unit:
    # 1. Create the systemd unit directory (/lib/systemd/system usually).
    # 2. Copy the service file with standard read/write permissions (0644).
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/knowledge-splash.service ${D}${systemd_system_unitdir}/
}


# --- Packaging Rules ---
# FILES: This variable tells BitBake which installed files should actually
# end up in the final ".ipk" or ".rpm" package. If files are installed during
# `do_install` but not listed in `FILES`, Yocto will throw a QA error about
# unpackaged files. We explicitly include our script and service file.
FILES:${PN} += "${bindir}/loading-anim.sh \
                ${systemd_system_unitdir}/knowledge-splash.service"