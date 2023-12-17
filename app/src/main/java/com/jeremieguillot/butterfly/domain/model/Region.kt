package com.jeremieguillot.butterfly.domain.model

enum class Region(override val displayName: String) : Displayable {
    HAUTS_DE_FRANCE("Hauts-de-France"),
    GRAND_EST("Grand Est"),
    ILE_DE_FRANCE("Île-de-France"),
    NORMANDIE("Normandie"),
    BRETAGNE("Bretagne"),
    AUVERGNE_RHONE_ALPES("Auvergne-Rhône-Alpes"),
    OCCITANIE("Occitanie"),
    BOURGOGNE_FRANCHE_COMTE("Bourgogne Franche-Comté"),
    NOUVELLE_AQUITAINE("Nouvelle-Aquitaine"),
    PAYS_DE_LA_LOIRE("Pays de la Loire"),
    CENTRE_VAL_DE_LOIRE("Centre-Val de Loire"),
    SUD_PROVENCE_ALPES_COTE_D_AZUR("Sud-Provence-Alpes-Côte d'Azur"),
    CORSE("Corse")
}
