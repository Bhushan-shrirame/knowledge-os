FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
# Use .cfg here tells Yocto to merge this with the main config
SRC_URI += "file://silent_boot.cfg"