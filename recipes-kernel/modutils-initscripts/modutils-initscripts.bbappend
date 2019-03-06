FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append += "\
	${@bb.utils.contains("MACHINE_FEATURES", "modcuberevo", "file://cuberevo_modutils.patch", "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "modoctagon1008", "file://octagon1008_modutils.patch", "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "modsh4", "file://sh4_modutils.patch", "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "modspark", "file://spark_modutils.patch", "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "modufs910", "file://ufs910_modutils.patch", "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "modviphl", "file://viphl_modutils.patch", "", d)} \
	"
