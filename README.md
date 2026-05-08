# KnowledgeOS (Aarch64)

KnowledgeOS is a custom Linux distribution built using the **Yocto Project (Scarthgap release)**. It is optimized for the **QEMU Aarch64** architecture and comes with an integrated AI stack including OpenCV, NumPy, and Python3.

---

## 📂 Repository Structure

| Directory / Layer | Description |
| :--- | :--- |
| **`build/`** | The main Yocto build directory. Contains your compiled images and environment configurations (`conf/local.conf`, `conf/multiconfig/`). |
| **`poky/`** | The core Yocto Project reference distribution framework. |
| **`meta-kos-core/`** | The primary custom layer. Contains KnowledgeOS-specific recipes, configuration overrides, and package groups. |
| **`meta-openembedded/`**| Collection of community open-source recipes extending poky (networking, python, multimedia, etc.). |
| **`meta-raspberrypi/`** | Hardware BSP layer providing support for building on Raspberry Pi devices. |
| **`meta-arm/`** | Base hardware definitions and support for various ARM architectures. |
| **`meta-tensorflow/`** | Provides recipes for TensorFlow Lite and additional AI processing pipelines. |
| **`meta-onnxruntime/`** | Provides recipes for configuring the ONNX Runtime engine. |
| **`meta-ros/`** | Layer enabling integration with the Robot Operating System (ROS). |
| **`meta-mender*/`** | Meta layers responsible for Mender's OTA update client and A/B rootfs partitions. |
---

##  1. Host System Setup

### Requirements
* **Tested OS:** Ubuntu 24.04 / 25.10 (Modern GCC 14+ hosts)  
* **Storage:** 100GB+ free space (SSD recommended)  
* **RAM:** 16GB+ recommended  

### Install Dependencies
Prepare your Ubuntu host by installing the mandatory Yocto build tools:

```bash
sudo apt-get update
sudo apt-get install -y gawk wget git diffstat unzip texinfo gcc build-essential \
chrpath socat cpio python3 python3-pip python3-pexpect xterm libsdl1.2-dev libyaml-dev \
g++-multilib python3-pyserial
```

## 2. Installation & Initialization

### Clone the Project
This project uses Git submodules to manage metadata layers. Use the --recursive flag to ensure all layers are downloaded automatically.
#### Using HTTPS:
```bash
git clone --recursive https://github.com/UElement/Knowledge-OS.git
cd knowledge_os
```
#### Using SSH:
```bash
git clone --recursive git@github.com:UElement/Knowledge-OS.git
cd knowledge_os
```

### Initialize Build Environment
This command uses our custom template to automatically pre-configure your build environment (setting up the `MerlinOS` distribution, layer paths, and multiconfigs):
```bash
export TEMPLATECONF="$(pwd)/meta-kos-core/conf/templates/default"
source poky/oe-init-build-env build
```

## 3. Configuration & Layer Setup
### Fix Configuration Paths 
If you have cloned this repository and your bblayers.conf contains hardcoded paths from a different system, run the following command to automatically update them to your current directory:
``` bash
sed -i "s|/mnt/knowledge/Knowledge-OS|${PWD}|g" conf/bblayers.conf
```
If you want to see which layers are included in your current build environment
```bash 
bitbake-layers show-layers
```

### Adding a New Layer
1. Clone the layer into your project directory (example: meta-openembedded):
```bash 
git clone -b scarthgap git://git.openembedded.org/meta-openembedded
```

2. Add the layer to your build configuration:
```bash 
bitbake-layers add-layer ../meta-openembedded/meta-oe
bitbake-layers add-layer ../meta-openembedded/meta-python
bitbake-layers add-layer ../meta-openembedded/meta-networking
```
3. Verify that the layer has been added:
```bash 
bitbake-layers show-layers
``` 
Note - Edit build/conf/local.conf to adjust build options such as image type, machine target.

### Installing Packages in Your Image
To maintain a reproducible and clean build environment, it is best practice to avoid adding packages directly to `local.conf`. Instead, use the custom layer structure provided in KnowledgeOS.

1. **Using Multiconfigs (Target-Specific):**<br>
    You can append packages to a specific target machine by editing its multiconfig file located in `meta-kos-core/conf/multiconfig/` (e.g., `raspberrypi.conf` or `qemuarm64.conf`). Be sure to use the modern Yocto override syntax (`:append`):
    ```bash 
    # Example: Add Python3 and AI libraries to the target
    IMAGE_INSTALL:append = " python3-opencv python3-numpy python3-pandas"
    ```

2. **Using Custom Package Groups (Recommended):**<br>
    KnowledgeOS uses modular package groups to keep the main image recipes clean. You can add your desired packages by editing the existing package group recipes in `meta-kos-core/recipes-core/` (such as `packagegroup-custom-core.bb` or `packagegroup-llm-package.bb`):
    ```bash
    RDEPENDS:${PN} += " \
        python3 \
        python3-opencv \
        python3-numpy \
    "
    ```

### Creating a New Layer
Use the create-layer subcommand. By convention, layer names start with meta-.

It is best practice to keep your custom layers outside the poky directory (e.g., in a layers/ or sources/ directory) so they aren't accidentally deleted if you update the core metadata.
```bash
# Example: Creating 'meta-custom' one level above your build directory
bitbake-layers create-layer ../meta-custom
```

What gets created?

The tool generates a standard skeleton:

   * conf/layer.conf: The configuration file that tells BitBake how to handle your layer.

   * recipes-example/: A boilerplate recipe to help you get started.

   * COPYING.MIT & README: Standard documentation files.

#### Add the Layer to Your Build
Creating the directory doesn't mean BitBake knows it exists. You must add it to your conf/bblayers.conf file. The tool can do this automatically:
```bash
bitbake-layers add-layer ../meta-custom
```
Verify the addition: Run the following command to see if your layer appears in the active list:
```bash
bitbake-layers show-layers
```
#### Key Configurations in layer.conf
If you open ../meta-custom/conf/layer.conf, you will see several variables. One of the most important to understand is priority:

   * BBFILE_PRIORITY_meta-custom = "6": This number determines which recipe BitBake chooses if two layers provide the same recipe name. Higher numbers have higher priority.
#### Test Your New Layer
The create-layer command includes a dummy recipe called example. You can verify everything is wired up correctly by attempting to "bake" it:
```bash
bitbake example
```
If the build completes without error, your layer is fully functional and ready for your custom recipes.

Best Practices
   * Naming: Always prefix with meta-.

   * Logical Separation: Create separate layers for different purposes (e.g., meta-bsp for hardware support, meta-apps for custom software).

   * Version Control: Initialize a Git repository inside your new layer immediately to track your changes.

## 4. Building & Testing
### Build the Image
Yocto provides multiple pre-configured images:
| Image | Description |
|-------|-------------|
| `core-image-minimal` | Minimal CLI image, fastest build, no GUI |
| `core-image-sato` | Lightweight GUI environment with X11 |
| `core-image-full-cmdline` | Full command-line system with networking and package manager |
| `core-image-x11` | X11 GUI environment for desktop-like experience |
| `core-image-qt5` | GUI with Qt5 applications pre-installed |

To build the base system with the AI stack included:

```bash 
bitbake mc:raspberrypi:core-image-minimal mc:qemuarm64:core-image-minimal mc:qemux86-64:core-image-minimal
```

To build for a single specific target (e.g., the physical drone hardware):
```bash
bitbake mc:raspberrypi:core-image-minimal
```

### Run in QEMU (Emulator)
Once the build completes, you can test your partitioned `.wic` disk image using the built-in emulator. 

Because the ARM64 emulator requires explicit paths to boot custom `.wic` partitions, use the following command to boot the `qemuarm64` image with no graphics:

```bash 
runqemu qemuarm64 nographic tmp-qemuarm64-glibc/deploy/images/qemuarm64/core-image-minimal-qemuarm64.rootfs.wic
```

**Testing the System:**
1. Login as `root` (no password required).
2. Verify the OS architecture:
   ```bash
   uname -a
   ```
3. Verify your custom partition layout (Boot, Root, Data):
   ```bash
   lsblk
   ```

Exit QEMU: Press `Ctrl+A`, then `X`, or type `poweroff`.


## 📦 Installed Packages and System Features

The operating system includes a curated set of packages to provide a balance between high-performance AI capabilities and production-grade reliability.

### 🧠 AI & Computer Vision Stack
| Package | Description |
| :--- | :--- |
| **tensorflow-lite** | Core engine for running optimized AI models (inference) on hardware. |
| **python3-opencv** | The industry standard for image processing and real-time video capture. |
| **python3-numpy** | High-performance mathematical library for array and matrix operations. |
| **libv4l / v4l-utils** | Drivers and testing tools for USB/CSI cameras (Video4Linux). |

### 🌐 Web & API Services
| Package | Description |
| :--- | :--- |
| **python3-flask** | Micro-framework for building web dashboards and AI model interfaces. |
| **python3-djangorestframework** | Advanced toolkit for building robust, scalable Web APIs. |
| **python3-gunicorn** | Production HTTP server used to run Flask/Django apps reliably. |
| **python3-requests** | Standard library for making HTTP requests to external services. |

### 🛡️ Production & Security
| Package | Description |
| :--- | :--- |
| **sudo** | Essential for managing secure administrative access. |
| **iptables** | Built-in firewall to protect the device from unauthorized network access. |
| **haveged** | Entropy daemon to ensure fast SSH logins and secure SSL/TLS encryption. |
| **ca-certificates** | Required for the system to verify and trust secure HTTPS websites. |
| **watchdog** | Hardware monitor that reboots the device automatically if it hangs. |

### 📊 System Management & Logging
| Package | Description |
| :--- | :--- |
| **rsyslog** | High-performance system for collecting and managing system logs. |
| **logrotate** | Prevents disk failure by automatically compressing and purging old logs. |
| **htop** | Real-time interactive process viewer and system resource monitor. |
| **networkmanager** | Complete stack for managing Ethernet, Wi-Fi, and mobile data. |
| **opkg** | Lightweight package manager for on-device software updates. |

### 🛠️ Base System Utilities
- **Shell & Core:** `bash`, `coreutils`, `util-linux`, `procps`, `shadow`.
- **Text Processing:** `vim`, `sed`, `grep`, `gawk`, `findutils`, `diffutils`.
- **Networking:** `openssh`, `curl`, `net-tools`, `iproute2`, `iputils`, `iw`, `wpa-supplicant`.
- **Compression:** `tar`, `gzip`, `bzip2`, `xz`, `lz4`, `zstd`.
- **Python Environment:** `python3-modules`, `python3-pip`, `python3-setuptools`.

---


## 🧠 KnowledgeOS: Embedded AI (llama.cpp)

This guide explains how to deploy and run the **Qwen-0.5B** model using `llama.cpp` on a `qemuarm64` target.

---

### 🚀 1. Launching KnowledgeOS (Host Side)


Because AI models are memory-intensive, you must launch the QEMU emulator with at least **4GB of RAM** and multiple CPU cores to prevent system freezes.

Run the following command from your Yocto build directory:

```bash
runqemu qemuarm64 nographic qemuparams="-m 4G -smp 4"
```

### 📥 2. Downloading the Model
KnowledgeOS uses GGUF-formatted models. For the best balance of speed and intelligence, we use the Qwen2.5-0.5B-Instruct (Quantized to 4-bit).
```bash
curl -L [https://huggingface.co/bartowski/Qwen2.5-0.5B-Instruct-GGUF/resolve/main/Qwen2.5-0.5B-Instruct-Q4_K_M.gguf](https://huggingface.co/bartowski/Qwen2.5-0.5B-Instruct-GGUF/resolve/main/Qwen2.5-0.5B-Instruct-Q4_K_M.gguf) -o /root/qwen.gguf
```

### 🤖 3. Running the AI
#### A. Simple Inference (CLI Mode)
To ask a single question, use the llama-cli. In an emulated environment, it is highly recommended to use 1 thread (-t 1) to ensure the system remains responsive.

```bash
llama-cli -m /root/qwen.gguf \
          -p "Greetings from KnowledgeOS! Tell me one cool fact about Linux." \
          -n 128 -t 1 --mmap
```

#### B. Interactive Chat Mode
To enter an ongoing conversation with the AI:
```bash
llama-cli -m /root/qwen.gguf -cnv -t 1 --mmap
```

#### 🛠 4. Troubleshooting
##### The terminal freezes during inference:

This is "CPU Starvation." Kill the process from your Host PC using killall -9 qemu-system-aarch64.

Restart QEMU and ensure you use -t 1 to leave CPU overhead for the OS.

##### "Insufficient memory" error:

Check your local.conf on the host. Ensure QB_MEM = "-m 4096" is set and that no mem=4M boot arguments are overriding your RAM.