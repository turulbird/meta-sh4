FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

COMPATIBLE_MACHINE = "adb_box|arivalink200|atemio520|atemio530|atevio7500|cuberevo|cuberevo_250hd|cuberevo_2000hd|cuberevo_3000hd|cuberevo_mini|cuberevo_mini2|cuberevo_9500hd|fortis_hdbox|hl101|hs7110|hs7119|hs7420|hs7429|hs7810a|hs7819|ipbox55|ipbox99|ipbox9900|sagemcom88|octagon1008|pace7241|spark|spark7162|tf7700|ufc960|ufs910|ufs912|ufs913|ufs922|vip1_v2|vip2_v2|vitamin_hd5000"

DEPENDS += " ${@bb.utils.contains("MACHINE_FEATURES", "sdl", "libsdl", "", d)} ${@bb.utils.contains("TARGET_ARCH", "sh4", "libmme-host sh4-driver-modules", "", d)}"

SRC_URI_prepend_sh4 += "\
    file://directfb-1.4.12+STM2011.09.27-3.patch;patch=1 \
    file://directfb-1.4.11+STM2010.12.15-4.libpng.patch;patch=1 \
    file://directfb-1.4.11+STM2010.12.15-4.no-vt.patch;patch=1 \
"

EXTRA_OECONF = "\
  --enable-freetype=yes \
  ${@bb.utils.contains("MACHINE_FEATURES", "sdl", "--enable-sdl --disable-imlib2 --disable-mesa", "--disable-sdl", d)} \
  --enable-zlib \
  ${@bb.utils.contains("TARGET_ARCH", "sh4", "--with-gfxdrivers=stgfx", "--with-gfxdrivers=none", d)} \
  --disable-vnc \
  --disable-x11 \
  ${@bb.utils.contains("TARGET_ARCH", "sh4", "--disable-fbdev --disable-devmem --enable-mme --enable-stmfbdev --enable-hwjpeg --enable-rle --enable-hwpng", "", d)} \
"

FILES_${PN}_append_sh4 += "\
  ${libdir}/directfb-${RV}/gfxdrivers/*.so \
"

FILES_${PN}-dev_append_sh4 += "\
  ${libdir}/directfb-${RV}/gfxdrivers/*.la \
"

