# KnowledgeOS (Aarch64)

KnowledgeOS is a custom Linux distribution built using the **Yocto Project (Scarthgap release)**. It is optimized for the **QEMU Aarch64** architecture and comes with an integrated AI stack including OpenCV, NumPy, and Python3.

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

### 2. Installation & Initialization

#### Clone the Project
This project uses Git submodules to manage metadata layers. Use the --recursive flag to ensure all layers are downloaded automatically.
```bash
git clone --recursive https://github.com/UElement/c.git
cd knowledge_os
```

#### Initialize Build Environment
This command sets up the environment and creates the build directory:
```bash
source poky/oe-init-build-env build
```

### 3. Configuration & Layer Setup
If you want to see which layers are included in your current build environment
```bash 
bitbake-layers show-layers
```


### 4.Building & Testing
#### Build the Image

To build the base system with the AI stack included:

```bash 
bitbake core-image-minimal
```

#### Run in QEMU (Emulator)

Once the build completes, launch the OS using the built-in emulator:

```bash 
runqemu qemuarm64
```
OR 
for no graphic version
```bash 
runqemu nographic
```

Login: root (no password required)

Verify Architecture: uname -a (should show aarch64)

Exit QEMU: Press Ctrl+A, then X or poweroff