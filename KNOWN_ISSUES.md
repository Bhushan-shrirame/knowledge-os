# Known Issues - Knowledge OS

## 1. TensorFlow Lite do_install Architecture Mismatch
**Date Identified:** 2026-01-20
**Component:** `tensorflow-lite_2.16.1.bb`
**Host OS:** Ubuntu 25.10 (x86_64)
**Target Arch:** AArch64 (Cortex-A57)

### Description
The `do_install` task fails because the build process generates an x86_64 Python wheel instead of an AArch64 wheel, or Pip on the host OS refuses to install the AArch64 wheel into the target sysroot due to a platform compatibility check.

**Error Message:**<br>
`ERROR: tflite_runtime-2.16.1-cp312-cp312-linux_x86_64.whl is not a supported wheel on this platform.`<br>
`ImportError: Python version mismatch: module was compiled for Python 3.11, but the interpreter version is incompatible: 3.12.12 (main, Oct  9 2025, 11:07:00) [GCC 13.4.0].`


## 1. Camera not working 
**Date Identified:** 2026-01-21
**Component:** `tensorflow-lite_2.16.1.bb`
**Host OS:** Ubuntu 25.10 (x86_64)
**Target Arch:** AArch64 (Cortex-A57)

### Description
The camera peripheral (CSI/USB) fails to initialize. While the `v4l-utils` packages are present in the image, the `/dev/video0` node is either missing or inaccessible to the application layer. This prevents python3-opencv from opening the video stream.

**Error Message:**<br>
`[ERROR:0@0.012] global cap_v4l.cpp:1119 tryIoctl VIDEOIO(V4L2:/dev/video0): select timeout`<br>
`cv2.error: OpenCV(4.x.x) Can't open camera by index 0`