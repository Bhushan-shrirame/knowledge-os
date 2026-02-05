SUMMARY = "Custom package groups for AI and System tools"
PR = "r1"

inherit packagegroup

# Define the logical groups (Packages)
PACKAGES = " \
    packagegroup-custom-core \
    packagegroup-custom-net \
    packagegroup-custom-python \
    packagegroup-custom-ai \
    packagegroup-drone \
    packagegroup-gpu-npu-acceleration \
"

RDEPENDS:packagegroup-custom-core = " \
    bash coreutils util-linux procps shadow sudo \
    tar gzip bzip2 xz lz4 zstd vim htop git \
"

RDEPENDS:packagegroup-custom-net = " \
    iproute2 curl openssh networkmanager iptables \
    iw wpa-supplicant ca-certificates \
"

RDEPENDS:packagegroup-custom-python = " \
    python3 python3-modules python3-pip python3-opencv \
    python3-requests python3-flask python3-numpy python3-dev \
    python3-future python3-lxml python3-pillow \
"

RDEPENDS:packagegroup-custom-ai = " \
    tensorflow-lite llama-cpp  \
    cmake make libstdc++-dev v4l-utils \
"
RDEPENDS:packagegroup-drone = " \
    gpsd gps-utils pps-tools nvme-cli e2fsprogs \
    xfsprogs smartmontools ffmpeg \
    mavlink watchdog \
    i2c-tools can-utils \
    gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-good \
    chrony libv4l mosh collectd \
"
RDEPENDS:packagegroup-gpu-npu-acceleration = " \
    clinfo \
    opencl-icd-loader \
    lmsensors-sensors \
    onnxruntime python3-onnxruntime \
"