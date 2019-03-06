FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

CXXFLAGS_append_sh4 += " -std=c++11 -fPIC -fno-strict-aliasing "

DEPENDS_append_sh4 += "\
	libmme-image libmme-host \
	"

RDEPENDS_${PN}_append_sh4 += "\
	libmme-host \
	"

EXTRA_OECONF_append_sh4 += "\
	--enable-${MACHINE} --with-lcd \
	--with-libsdl=no --with-boxtype=${MACHINE} \
	--enable-dependency-tracking \
	ac_cv_prog_c_openmp=-fopenmp \
	${@get_crashaddr(d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "textlcd", "--with-textlcd" , "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "7segment", "--with-7segment" , "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "7seg", "--with-7segment" , "", d)} \
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
	"

E2_REV = "3e63f640c6b31c5c15c150f67c6cbac6b89bcb20"
PV_sh4 = "sh4+git${E2_REV}"
PKGV_sh4 = "sh4+git${E2_REV}"
SRCREV_enigma2 = "${E2_REV}"
SRC_URI_sh4 = " \
	git://github.com/OpenPLi/enigma2.git;branch=develop;name=enigma2;rev=${E2_REV} \ 
	"

SRC_URI_append_sh4 += "\
	${@bb.utils.contains("MACHINE_FEATURES", "debug4", "file://set-default-debug-level-at-4.patch", "", d)} \
	file://enigma2-pli-nightly.${E2_REV}.patch \
	file://vfd-drivers.patch \
	file://rc-models.patch \
	${@bb.utils.contains("MACHINE", "tf7700", "file://tf_frontpanel.h", "", d)} \
	${@bb.utils.contains("MACHINE", "tf7700", "file://tftypes.h", "", d)} \
	"
#	file://09-undefine-macro-HAVE_CONFIG_H.patch

do_configure_prepend() {
	if [ "${TARGET_ARCH}" == "sh4" ]
	then
#		# Restore the files first in case we run configure twice between checking out the source
#		git --git-dir="${S}/.git" --work-tree="${S}" checkout "${S}/data/rc_models/Makefile.am"
#		git --git-dir="${S}/.git" --work-tree="${S}" checkout "${S}/data/rc_models/rc_models.cfg"
#		git --git-dir="${WORKDIR}/extra_rc_models/.git" --work-tree="${WORKDIR}/extra_rc_models" pull
		for i in $(find "${S}/data/rc_models" -maxdepth 1 -type f -name "*.xml" -o -name "*.png")
		do
#	#		if [ "${i}" -ne "dmm.png" -a "${i}" -ne "dmm.xml" -a "${i}" -ne "dmmadv.png" -a "${i}" -ne "dmmadv.xml" ]; then
			if ${i} -ne dmm.png; then
				rm ${i}
			fi
		done
		for i in $(find "${THISDIR}/${PN}" -maxdepth 1 -type f -name "*.xml" -o -name "*.png")
		do
			file="$(echo "${i}" | sed 's:.*/::')"
#			sed -i '${s/$/'" $file"'/}' "${S}/data/rc_models/Makefile.am"
			cp -f "${i}" "${S}/data/rc_models/"
		done
#		cat "${WORKDIR}/extra_rc_models/rc_models.cfg" >> "${S}/data/rc_models/rc_models.cfg"
	fi
}
