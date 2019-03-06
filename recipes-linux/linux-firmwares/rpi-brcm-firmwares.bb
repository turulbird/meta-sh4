SUMMARY = "Firmware files for RPi broadcom"
LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGE_ARCH = "all"

SRC_URI = "https://raw.githubusercontent.com/OpenVisionE2/linux-firmwares/master/rpi-brcm-firmwares.zip"

SRC_URI[md5sum] = "51047957b22c402fa6dd018b3f85f4ad"
SRC_URI[sha256sum] = "3ddceafa4a979eff19d6a051862f919ffb9b8e6489fd4acdbd515f8ac7dba021"

S = "${WORKDIR}"

PACKAGES = "${PN}"
FILES_${PN} += "${base_libdir}/firmware/brcm"

do_install() {
    install -d ${D}${base_libdir}/firmware/brcm
    install -m 0644 *.bin ${D}${base_libdir}/firmware/brcm/
    install -m 0644 *.hcd ${D}${base_libdir}/firmware/brcm/
    install -m 0644 *.txt ${D}${base_libdir}/firmware/brcm/
}
