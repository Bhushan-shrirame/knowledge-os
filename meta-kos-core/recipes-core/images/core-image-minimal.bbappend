finalize_kos() {
    # 1. Clear the login banner (before login)
    echo "" > ${IMAGE_ROOTFS}${sysconfdir}/issue

    # 2. Clear the Poky warning (after login)
    echo "" > ${IMAGE_ROOTFS}${sysconfdir}/motd
    
    # 3. Prevent the 'Welcome to Poky' message from being generated
    if [ -f ${IMAGE_ROOTFS}${sysconfdir}/motd.template ]; then
        echo "" > ${IMAGE_ROOTFS}${sysconfdir}/motd.template
    fi
}

ROOTFS_POSTPROCESS_COMMAND += "finalize_kos; "