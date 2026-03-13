SUMMARY = "Custom user login startup service"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
    file://myscript.sh \
    file://mystartup.service \
"

S = "${WORKDIR}"

# We don't inherit 'systemd' directly because that class is optimized 
# for system-wide services, not user sessions. Manual installation is safer here.

do_install() {
    # 1. Install the executable script
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/myscript.sh ${D}${bindir}/myscript.sh

    # 2. Install the systemd user service file globally
    install -d ${D}${nonarch_libdir}/systemd/user
    install -m 0644 ${WORKDIR}/mystartup.service ${D}${nonarch_libdir}/systemd/user/mystartup.service

    # 3. Enable the service to start implicitly for all users on login (default.target)
    install -d ${D}${sysconfdir}/systemd/user/default.target.wants
    ln -sf ${nonarch_libdir}/systemd/user/mystartup.service ${D}${sysconfdir}/systemd/user/default.target.wants/mystartup.service
}

# Ensure bitbake knows to package these directories
FILES:${PN} += " \
    ${nonarch_libdir}/systemd/user/mystartup.service \
    ${sysconfdir}/systemd/user/default.target.wants/mystartup.service \
"

# If your script requires bash instead of sh, uncomment the following line:
# RDEPENDS:${PN} += "bash"
