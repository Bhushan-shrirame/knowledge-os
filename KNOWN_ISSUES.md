# Known Issues - Knowledge OS

## 1. TensorFlow Lite do_install Architecture Mismatch
**Date Identified:** 2026-01-20
**Component:** `tensorflow-lite_2.16.1.bb`
**Host OS:** Ubuntu 25.10 (x86_64)
**Target Arch:** AArch64 (Cortex-A57)

### Description
The `do_install` task fails because the build process generates an x86_64 Python wheel instead of an AArch64 wheel, or Pip on the host OS refuses to install the AArch64 wheel into the target sysroot due to a platform compatibility check.

**Error Message:**
`ERROR: tflite_runtime-2.16.1-cp312-cp312-linux_x86_64.whl is not a supported wheel on this platform.`<br>
`ImportError: Python version mismatch: module was compiled for Python 3.11, but the interpreter version is incompatible: 3.12.12 (main, Oct  9 2025, 11:07:00) [GCC 13.4.0].`
