DESCRIPTION = "STM ST-231 Coprocessor firmware"
LICENSE = "CLOSED"
SECTION = "base"
inherit allarch

COMPATIBLE_MACHINE = "adb_box|albis8000|arivalink200|atemio520|atemio530|atevio7500|cuberevo|cuberevo_250hd|cuberevo_2000hd|cuberevo_3000hd|cuberevo_mini|cuberevo_mini2|cuberevo_9500hd|fortis_hdbox|hl101|hs7110|hs7119|hs7420|hs7429|hs7810a|hs7819|ipbox55|ipbox99|ipbox9900|pace7241|sagemcom88|octagon1008|spark|spark7162|tf7700|ufc960|ufs910|ufs912|ufs913|ufs922|vitamin_hd5000|vip1_v2|vip2_v1"

# fix architecture mismatch QA error
INSANE_SKIP_${PN} = "arch"

SRC_URI = "file://${AUDIOELF} \
	file://${VIDEOELF} \
"

FILES_${PN} += "/boot"

do_install () {
    # Remove stuff from old packages if present
    if [ -e /etc/init.d/stslave.sh ] ; then
        rm /etc/init.d/stslave.sh
    fi
    if [ -e /etc/rcS.d/S03stslave ] ; then
        rm /etc/rcS.d/S03stslave
    fi
    install -d ${D}/boot
    install -m 644 ${WORKDIR}/${AUDIOELF}  ${D}/boot/audio.elf
    install -m 644 ${WORKDIR}/${VIDEOELF}  ${D}/boot/video.elf
}
