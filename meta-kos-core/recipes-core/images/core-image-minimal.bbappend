# =========================================================================
# Core Image Minimal Append (core-image-minimal.bbappend)
# =========================================================================
#
# This file is a "bbappend" file. In Yocto, rather than copying and heavily
# modifying an upstream recipe (like the standard `core-image-minimal.bb`
# provided by Poky), you create a `.bbappend` file with the exact same root
# name. BitBake automatically finds this file and applies its instructions
# on top of the original recipe.
#
# This specific bbappend is used to execute custom shell commands at the
# very end of the root filesystem creation process for MerlinOS.
# =========================================================================

# --- Rootfs Post-Processing Function ---
# finalize_kos(): This is a custom shell function we define to perform
# clean-up tasks right before the final image gets packed into its output
# format (like an ext4 file or a tarball).
# 
# In this function, we specifically target Yocto's default branding
# (the "Poky" reference distribution banners) and remove them so that our
# own custom branding (configured elsewhere) takes precedence.
finalize_kos() {
    # 1. Clear the pre-login banner. 
    # The /etc/issue file is displayed by getty before the username prompt.
    # By echoing an empty string here, we ensure default Yocto text is gone.
    # (Note: Our custom-boot script actually overwrites this later during boot).
    echo "" > ${IMAGE_ROOTFS}${sysconfdir}/issue

    # 2. Clear the post-login Message of the Day (MOTD).
    # /etc/motd is displayed immediately after a user successfully logs in.
    # We remove the default "Welcome to Poky" message.
    echo "" > ${IMAGE_ROOTFS}${sysconfdir}/motd
    
    # 3. Prevent future generation of the MOTD.
    # Some initialization scripts might try to regenerate the MOTD from a
    # template file if it exists. Nullifying the template ensures the Poky
    # warning doesn't mysteriously return upon first boot.
    if [ -f ${IMAGE_ROOTFS}${sysconfdir}/motd.template ]; then
        echo "" > ${IMAGE_ROOTFS}${sysconfdir}/motd.template
    fi
}

# --- Hook into the Build Process ---
# ROOTFS_POSTPROCESS_COMMAND is a special Yocto variable containing a list
# of functions to run after the root filesystem has been populated with
# packages, but before the final image file is created.
# We append our custom `finalize_kos` function to this list. The trailing
# semicolon is required syntax when appending shell commands to this list.
ROOTFS_POSTPROCESS_COMMAND += "finalize_kos; "