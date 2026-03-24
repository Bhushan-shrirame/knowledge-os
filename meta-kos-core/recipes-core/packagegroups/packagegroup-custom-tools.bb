# =========================================================================
# Custom Package Groups Definition (packagegroup-custom-tools.bb)
# =========================================================================
#
# A "packagegroup" recipe does not compile any source code itself. Instead,
# it acts as a meta-package that depends on other real packages. This allows
# you to logically group related tools together (e.g., networking, AI, drone)
# and install them all into a final image simply by adding the packagegroup
# name to IMAGE_INSTALL in the distribution or image configuration.
#
# By keeping them organized here, we make the top-layer image manifests much
# cleaner and easier to maintain.
# =========================================================================

SUMMARY = "Custom package groups for AI, System, Networking, and Drone tools"
PR = "r2"

inherit packagegroup

# --- Logical Group Declarations ---
# In Yocto, the PACKAGES variable dictates exactly which sub-packages will
# be output by this recipe. If an RDEPENDS group is not listed here, Yocto
# will not actually create a package for it. 
# 
# (Note: We explicitly list all the specialized groups we are defining below)
PACKAGES = " \
    packagegroup-custom-core \
    packagegroup-custom-net \
    packagegroup-custom-python \
    packagegroup-custom-tools \
    packagegroup-llm-package \
"

# =========================================================================
# 1. Base System & Utilities
# =========================================================================
# Core command-line utilities for system administration, file management,
# archiving, and text editing.
RDEPENDS:packagegroup-custom-core = " \
    bash \
    bzip2 \
    coreutils \
    git \
    glibc-utils \
    gzip \
    htop \
    locale-base-en-us \
    localedef \
    lz4 \
    procps \
    screen \
    shadow \
    sudo \
    tar \
    tmux \
    util-linux \
    vim \
    xz \
    zstd \
    startup-service \
    thermal-throttle \
    robotics-sysctl \
    network-bonding \
"

# =========================================================================
# 2. Networking & Connectivity
# =========================================================================
# Essential tools for configuring IP routing, wireless networks, firewall
# rules, and secure communications.
RDEPENDS:packagegroup-custom-net = " \
    ca-certificates \
    curl \
    iproute2 \
    iptables \
    iw \
    networkmanager \
    openssh \
    wpa-supplicant \
"

# =========================================================================
# 3. Python Development
# =========================================================================
# Core python interpreter and commonly used modules for backend services,
# web frameworks, and scientific computing.
RDEPENDS:packagegroup-custom-python = " \
    python3 \
    python3-dev \
    python3-flask \
    python3-future \
    python3-lxml \
    python3-modules \
    python3-numpy \
    python3-opencv \
    python3-pillow \
    python3-pip \
    python3-requests \
"

# =========================================================================
# 4. C/C++ Build Tools
# =========================================================================
# Compilers, standard libraries, and build automation systems needed for
# on-device compilation.
# Note: Using '=' instead of '+=' for consistency across the file.
RDEPENDS:packagegroup-custom-tools = " \
    cmake \
    libstdc++ \
    libstdc++-dev \
    packagegroup-core-buildessential \
    pkgconfig \
    python3-setuptools \
    python3-wheel \
    merlin-profiler \
"


# =========================================================================
# 5. Artificial Intelligence & Machine Learning
# =========================================================================
# Core frameworks for inference, including TinyML, LLM acceleration, and
# required libraries for local execution.
RDEPENDS:packagegroup-custom-ai = " \
    cmake \
    glibc-utils \
    llama-cpp \
    libstdc++-dev \
    locale-base-en-us \
    make \
    tensorflow-lite \
    v4l-utils \
"

# Hardware acceleration APIs for GPUs/NPUs (OpenCL/ONNX)
RDEPENDS:packagegroup-gpu-npu-acceleration = " \
    clinfo \
    lmsensors-sensors \
    onnxruntime \
    opencl-icd-loader \
    python3-onnxruntime \
"

# Specialized, minimal Large Language Model (LLM) package group
RDEPENDS:packagegroup-llm-package = " \
    llama-cpp \
    opencl-icd-loader \
"


# =========================================================================
# 6. Robotics & Drone Specific
# =========================================================================
# Connectivity and sensor suites for drone operations (MAVLink, GPS,
# Video streaming via GStreamer, and telemetry).
RDEPENDS:packagegroup-drone = " \
    can-utils \
    chrony \
    collectd \
    e2fsprogs \
    ffmpeg \
    gps-utils \
    gpsd \
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    i2c-tools \
    libv4l \
    mavlink \
    mosh \
    nvme-cli \
    pps-tools \
    smartmontools \
    watchdog \
    xfsprogs \
"


# =========================================================================
# 7. Temporary / Testing Groups
# =========================================================================
# Experimental configurations used primarily during testing and prototyping.

RDEPENDS:packagegroup-llm-rpi = " \
    ca-certificates \
    cpufrequtils \
    curl \
    git \
    htop \
    iproute2 \
    iptables \
    iw \
    kernel-modules \
    linux-firmware-rpidistro-bcm43430 \
    llama-cpp \
    networkmanager \
    opencl-icd-loader \
    openssh \
    tmux \
    wpa-supplicant \
    zram \
"