# 1. Strip conflicting flags to allow compilation for Cortex-A57
TUNE_CCARGS:remove = "-mcpu=cortex-a57+crc"

# 2. Disable failing ARMv8.2 kernels
EXTRA_OECMAKE += "-Dort_ENABLE_ARM64_MLAS_GEMS=OFF \
                  -Donnxruntime_ENABLE_ARM64_MLAS_GEMS=OFF \
                  -Donnxruntime_BUILD_FOR_NATIVE_MACHINE=OFF"

# 3. Build Configuration
PACKAGECONFIG:remove = "armv82"
PACKAGECONFIG:append = " python"

# 4. Packaging Logic (CRITICAL: This creates the python3-onnxruntime package)
PACKAGES =+ "python3-onnxruntime"

# Map the installed files to the new package
# Note: Scarthgap uses Python 3.12
FILES:python3-onnxruntime += " \
    ${libdir}/python3.12/site-packages/onnxruntime \
    ${libdir}/python3.12/site-packages/onnxruntime_pybind11_state* \
"

# 5. Runtime Dependencies
RDEPENDS:python3-onnxruntime += "onnxruntime python3-numpy python3-protobuf"

# 6. Build Optimization for IdeaPad (2-4 cores recommended)
PARALLEL_MAKE = "-j 4"