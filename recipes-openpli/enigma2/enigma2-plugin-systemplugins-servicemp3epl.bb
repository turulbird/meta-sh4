DESCRIPTION = "servicemp3 and libeplayer backend for enigma2"
AUTHOR = "OpenPLi team <info@openpli.org>"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

PACKAGE_ARCH = "${MACHINE_ARCH}"

PROVIDES += "virtual/enigma2-mediaservice"
RPROVIDES_${PN} += "virtual/enigma2-mediaservice"

CXXFLAGS_append_sh4 += " -std=c++11 -fPIC -fno-strict-aliasing "

GST_BASE_RDEPS = "\
	gstreamer${GST_VERSION}-plugins-base-alsa \
	gstreamer${GST_VERSION}-plugins-base-app \
	gstreamer${GST_VERSION}-plugins-base-audioconvert \
	gstreamer${GST_VERSION}-plugins-base-audioresample \
	gstreamer${GST_VERSION}-plugins-base-audiorate \
	gstreamer${GST_VERSION}-plugins-base-videoconvert \
	gstreamer${GST_VERSION}-plugins-base-ivorbisdec \
	gstreamer${GST_VERSION}-plugins-base-ogg \
	gstreamer${GST_VERSION}-plugins-base-opus \
	gstreamer${GST_VERSION}-plugins-base-playback \
	gstreamer${GST_VERSION}-plugins-base-subparse \
	gstreamer${GST_VERSION}-plugins-base-typefindfunctions \
	gstreamer${GST_VERSION}-plugins-base-vorbis \
	gstreamer${GST_VERSION}-plugins-base-rawparse \
	"

GST_GOOD_RDEPS = "\
	gstreamer${GST_VERSION}-plugins-good-apetag \
	gstreamer${GST_VERSION}-plugins-good-audioparsers \
	gstreamer${GST_VERSION}-plugins-good-autodetect \
	gstreamer${GST_VERSION}-plugins-good-avi \
	gstreamer${GST_VERSION}-plugins-good-flac \
	gstreamer${GST_VERSION}-plugins-good-flv \
	gstreamer${GST_VERSION}-plugins-good-icydemux \
	gstreamer${GST_VERSION}-plugins-good-id3demux \
	gstreamer${GST_VERSION}-plugins-good-isomp4 \
	gstreamer${GST_VERSION}-plugins-good-matroska \
	gstreamer${GST_VERSION}-plugins-good-mpg123 \
	gstreamer${GST_VERSION}-plugins-good-rtp \
	gstreamer${GST_VERSION}-plugins-good-rtpmanager \
	gstreamer${GST_VERSION}-plugins-good-rtsp \
	gstreamer${GST_VERSION}-plugins-good-soup \
	gstreamer${GST_VERSION}-plugins-good-udp \
	gstreamer${GST_VERSION}-plugins-good-wavparse \
	gstreamer${GST_VERSION}-plugins-good-wavpack \
	"

GST_BAD_RDEPS = "\
	gstreamer${GST_VERSION}-plugins-bad-autoconvert \
	gstreamer${GST_VERSION}-plugins-bad-dashdemux \
	gstreamer${GST_VERSION}-plugins-bad-mms \
	gstreamer${GST_VERSION}-plugins-bad-mpegpsdemux \
	gstreamer${GST_VERSION}-plugins-bad-mpegtsdemux \
	gstreamer${GST_VERSION}-plugins-bad-rtmp \
	gstreamer${GST_VERSION}-plugins-bad-smoothstreaming \
	gstreamer${GST_VERSION}-plugins-bad-faad \
	gstreamer${GST_VERSION}-plugins-bad-hls \
	gstreamer${GST_VERSION}-plugins-bad-opusparse \
	gstreamer${GST_VERSION}-plugins-bad-videoparsersbad \
	"

GST_UGLY_RDEPS = "\
	gstreamer${GST_VERSION}-plugins-ugly-amrnb \
	gstreamer${GST_VERSION}-plugins-ugly-amrwbdec \
	gstreamer${GST_VERSION}-plugins-ugly-asf \
	gstreamer${GST_VERSION}-plugins-ugly-cdio \
	gstreamer${GST_VERSION}-plugins-ugly-dvdsub \
	"

PACKAGECONFIG ??= "gstreamer libeplayer"
PACKAGECONFIG[gstreamer]       = "--enable-gstreamer --with-gstversion=${GST_VERSION},--disable-gstreamer,gstreamer${GST_VERSION}-plugins-base gstreamer${GST_VERSION}"
PACKAGECONFIG[libeplayer]      = "--enable-libeplayer3,--disable-libeplayer3,libeplayer3"

DEPENDS = "\
	enigma2 \
	"

RDEPENDS_${PN} = "\
	enigma2 \
	${@bb.utils.contains("PACKAGECONFIG", "libeplayer", "libeplayer3", "", d)} \
	"

RRECOMMENDS_${PN} = "\
	${@bb.utils.contains("PACKAGECONFIG", "gstreamer", "\
	glib-networking \
	gstreamer${GST_VERSION}-plugin-subsink \
	virtual/gstreamer${GST_VERSION}-dvbmediasink \
	${GST_BASE_RDEPS} \
	${GST_GOOD_RDEPS} \
	${GST_BAD_RDEPS} \
	${GST_UGLY_RDEPS} \
	", "", d)} \
	${@bb.utils.contains("PACKAGECONFIG", "libeplayer", "libeplayer3", "", d)} \
	"

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/OpenVisionE2/servicemp3epl.git;branch=master"
#SRC_URI = "git://github.com/OpenPLi/servicemp3.git;branch=master"

SRC_URI_append += "\
	file://enigma2-servicemp3epl-0.1.patch \
	"
	
S = "${WORKDIR}/git"

inherit autotools gitpkgv pythonnative pkgconfig

PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"

EXTRA_OECONF = "\
	BUILD_SYS=${BUILD_SYS} \
	HOST_SYS=${HOST_SYS} \
	STAGING_INCDIR=${STAGING_INCDIR} \
	STAGING_LIBDIR=${STAGING_LIBDIR} \
	"

do_install_append() {
	rm ${D}${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceMP3/*.pyc
}

FILES_${PN} = "\
	${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceMP3/*.pyo \
	${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceMP3/servicemp3.so"

FILES_${PN}-dev = "\
	${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceMP3/*.py \
	${libdir}/enigma2/python/Plugins/SystemPlugins/ServiceMP3/servicemp3.la"
