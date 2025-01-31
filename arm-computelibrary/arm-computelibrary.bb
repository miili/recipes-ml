# 
# ARM Computer Vision and Machine Learning library
# 

SUMMARY = "ARM ComputerLibrary"
DESCRIPTION = "The ARM Computer Vision and Machine Learning library is a set of functions optimised for both ARM CPUs and GPUs using SIMD technologies."

COMPATIBLE_MACHINE = "armv7a|aarch64"

# Version to use
PV = "22.08"
PR = "r0"

S = "${WORKDIR}/git"
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=f3c5879801d3cffc4ac2399f2b8e8ec5"

# for nproc command
DEPENDS = "coreutils-native " 

SRC_URI = "\
    git://github.com/ARM-software/ComputeLibrary.git;protocol=https;tag=v${PV};nobranch=1 \
    file://0001-Fix-compile-error.patch \
"

inherit scons

EXTRA_OESCONS:armv7a += "\
	arch=armv7a \
	extra_cxx_flags="-fPIC -Wno-unused-but-set-variable -Wno-ignored-qualifiers -Wno-noexcept ${TOOLCHAIN_OPTIONS}" \
	benchmark_tests=1 \
	validation_tests=0 \
	neon=1 \
	openmp=1 \
	opencl=0 \
	set_soname=1 \
	toolchain_prefix=' ' \
	install_dir=_install \
"

EXTRA_OESCONS:aarch64 += "\
	arch=arm64-v8a \
    extra_cxx_flags="-fPIC -Wno-unused-but-set-variable -Wno-ignored-qualifiers -Wno-noexcept ${TOOLCHAIN_OPTIONS}" \
    benchmark_tests=1 \
    validation_tests=0 \
    neon=1 \
    openmp=1 \
    opencl=0 \
    set_soname=1 \
    toolchain_prefix=' ' \
    install_dir=_install \
"

do_install(){
    mkdir -p ${D}${bindir}
    mkdir -p ${D}${libdir}
    mkdir -p ${D}${includedir}

    rm -r ${S}/build/_install/include/CL

    cp -R ${S}/build/_install/bin/* ${D}${bindir}
    cp -R ${S}/build/*.so* ${D}${libdir}
    cp -R ${S}/build/_install/include/* ${D}${includedir}
}

INSANE_SKIP:${PN} = "ldflags rdepends "
