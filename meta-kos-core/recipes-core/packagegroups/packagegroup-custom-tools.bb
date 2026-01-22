SUMMARY = "Custom package groups for AI and System tools"
PR = "r1"

inherit packagegroup

# Define the logical groups (Packages)
PACKAGES = " \
    packagegroup-custom-core \
    packagegroup-custom-net \
    packagegroup-custom-python \
    packagegroup-custom-ai \
"

RDEPENDS:packagegroup-custom-core = " \
    bash coreutils util-linux procps shadow sudo \
    tar gzip bzip2 xz lz4 zstd vim htop \
"

RDEPENDS:packagegroup-custom-net = " \
    iproute2 curl openssh networkmanager iptables \
    iw wpa-supplicant ca-certificates \
"

RDEPENDS:packagegroup-custom-python = " \
    python3 python3-modules python3-pip python3-opencv \
    python3-requests python3-flask python3-numpy \
"

RDEPENDS:packagegroup-custom-ai = " \
    tensorflow-lite llama-cpp  \
    cmake make libstdc++-dev \
"