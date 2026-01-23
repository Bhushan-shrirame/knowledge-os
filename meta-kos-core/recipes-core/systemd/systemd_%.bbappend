do_install:append() {
    if [ -e ${D}${sysconfdir}/systemd/system.conf ]; then
        sed -i 's/#ShowStatus=yes/ShowStatus=no/' ${D}${sysconfdir}/systemd/system.conf
        sed -i 's/#LogLevel=info/LogLevel=emerg/' ${D}${sysconfdir}/systemd/system.conf
    fi
}