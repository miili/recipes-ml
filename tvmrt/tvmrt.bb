#
# TVM runtime
#

SUMMARY = "TVM runtime"
DESCRIPTION = "Tensors and Dynamic neural networks in Python with strong GPU acceleration"

# Version to use
PV = "0.10.0"
PR = "r0"

S = "${WORKDIR}/git"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=f30f7a1bcf7728eb568504d96cee7b09"

SRC_URI = " \
    gitsm://github.com/apache/tvm;protocol=https;nobranch=1;tag=v${PV} \
    file://0001-Add-support-for-Yocto-build.patch \
"

RDEPENDS:${PN} += "\
    pyxir \
"
DEPENDS += " \
    python3-native \
    pyxir \
"

inherit cmake 

EXTRA_OECMAKE = "\
    -DUSE_LIBBACKTRACE=OFF \
    -DCMAKE_BUILD_TYPE=Release \
"

do_configure:prepend(){
    echo set\(USE_LLVM OFF\) >> ${S}/config.cmake
    echo set\(USE_VITIS_AI ON\) >> ${S}/config.cmake
}

SOLIBS = ".so"
FILES_SOLIBSDEV = ""
