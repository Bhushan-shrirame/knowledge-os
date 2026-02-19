SUMMARY = "Custom package groups for AI and System tools"
PR = "r1"

inherit packagegroup

# Define the logical groups (Packages)
PACKAGES = " \
    packagegroup-llm-rpi \
    packagegroup-custom-net \
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

RDEPENDS:packagegroup-llm-package = " \
    llama-cpp \
    onnxruntime \
    opencl-icd-loader \
    git \
    curl \
"

# Below are the temporary package list which are use for the testing purpose only  (Packages)

RDEPENDS:packagegroup-llm-rpi = " \
    iproute2 curl openssh networkmanager iptables \
    iw wpa-supplicant ca-certificates \
    llama-cpp opencl-icd-loader \
    git htop zram cpufrequtils \
    linux-firmware-rpidistro-bcm43430 \
    kernel-modules \
"

RDEPENDS:packagegroup-interceptor-minimal = " \
    python3 python3-modules python3-pip python3-opencv \
    python3-requests python3-flask python3-numpy python3-dev \
    python3-future python3-lxml python3-pillow \
    opencl-icd-loader git v4l-utils libv4l ca-certificates \
    onnxruntime python3-onnxruntime \
    python3-io python3-netserver \
    curl ca-certificates\
"