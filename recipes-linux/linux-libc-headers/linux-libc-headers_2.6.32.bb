require linux-libc-headers.inc

SRC_URI[md5sum] = "260551284ac224c3a43c4adac7df4879"
SRC_URI[sha256sum] = "5099786d80b8407d98a619df00209c2353517f22d804fdd9533b362adcb4504e"

SRC_URI += "file://linuxdvb.patch \
    file://ppp.patch \
    file://types.patch \
"

# sh4 boxes require some headers from the kernel modules (for the framebuffer
# and ioctls) which are not shipped by the kernel headers.
# In order to avoid adding explicit sh4-conditional dependancies to the
# driver package in several packages (just for a couple of generic headers)
# we add them here.
# In this way, we also avoid unnecessary rebuilds of several stuff when
# drivers are updated.

SRC_URI_append_sh4 = "\
    file://stmfb.h \
    file://stm_ioctls.h \
    file://bpamem.h \
    file://extra_ipv6_headers.patch \
"

do_install_append_sh4() {
    install -d ${D}/${includedir}/linux/dvb
    install -m 644 ${WORKDIR}/stm_ioctls.h ${D}/${includedir}/linux/dvb
    install -m 644 ${WORKDIR}/stmfb.h ${D}/${includedir}/linux
    install -m 644 ${WORKDIR}/bpamem.h ${D}/${includedir}
    install -m 644 ${S}/include/linux/usb/video.h ${D}${includedir}/linux/usb/video.h
}
