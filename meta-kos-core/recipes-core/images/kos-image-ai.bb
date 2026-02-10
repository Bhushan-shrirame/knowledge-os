SUMMARY = "KOS AI Node"
LICENSE = "MIT"

IMAGE_INSTALL = " \
    packagegroup-core-boot \
    llama-cpp \
    onnxruntime \
    git \
"

inherit core-image

# 1. Force SquashFS with XZ compression (Best for fixed AI images)
IMAGE_FSTYPES = "squashfs-xz"

# 2. Strip all package management overhead (Saves ~20MB)
IMAGE_FEATURES:remove = "package-management x11-base debug-tweaks"

# 3. Use BusyBox instead of Bash to save ~2MB
VIRTUAL-RUNTIME_bash = "busybox"

# 4. Remove documentation and locales (Saves ~5-10MB)
IMAGE_LINGUAS = ""
FORCE_RO_REMOVE_ROOTFS = "1"

# 5. Ensure the kernel is not included in the rootfs (saves ~8MB)
# (The kernel should live in the boot partition, not the AI partition)
PACKAGE_EXCLUDE = "kernel-base kernel-image"