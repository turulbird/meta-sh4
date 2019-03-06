PV = "git${SRCPV}"
SRC_URI = " \
	git://github.com/OpenVisionE2/oscam.git;protocol=git;name=trunk \
	file://oscam.conf \
	file://oscam.server \
	file://oscam.srvid \
	file://oscam.user \
	file://oscam.provid \
	"
S = "${WORKDIR}/git"
SRCREV_pn-enigma2-plugin-softcams-oscam = "${AUTOREV}"
