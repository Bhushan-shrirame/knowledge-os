SUMMARY = "Llama.cpp for KnowledgeOS"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1539dadbedb60aa18519febfeab70632"

SRC_URI = "git://github.com/ggerganov/llama.cpp;protocol=https;branch=master"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

inherit cmake

DEPENDS += "curl"

# 1. Force shared libraries and disable native optimizations for QEMU
EXTRA_OECMAKE += "-DBUILD_SHARED_LIBS=ON -DGGML_NATIVE=OFF -DGGML_OPENMP=OFF"

do_install() {
    # Install binaries
    install -d ${D}${bindir}
    install -m 0755 ${B}/bin/llama-cli ${D}${bindir}/
    install -m 0755 ${B}/bin/llama-server ${D}${bindir}/

    # Install libraries
    install -d ${D}${libdir}
    
    # 2. Use 'find' to locate all .so files in the build directory 
    # This avoids the "No such file or directory" error by finding them wherever CMake put them
    find ${B} -name "*.so*" -exec cp -P {} ${D}${libdir}/ \;
}

FILES:${PN} += "${libdir}/*.so*"
INSANE_SKIP:${PN} += "dev-so"