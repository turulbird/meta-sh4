DESCRIPTION = "MME image library"

require audioniek-apps.inc

DEPENDS += " sh4-driver-modules"

FILES_${PN} += "${libdir}/libmme_host.so"
FILES_${PN}-dev = "${libdir}/libmme_host.la"

INSANE_SKIP_${PN} += "dev-so"
