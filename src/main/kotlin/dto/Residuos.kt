package dto

data class Residuos(
    val age : Int,
    val month : String,
    val batch : Int,
    val distrit : Int,
    val distritName : String,
    val tons : Int
)

enum class Residuo {
    RESTO,
    ENVASES,
    VIDRIO,
    ORGANICA,
    PAPEL_Y_CARTON,
    PUNTOS_LIMPIOS,
    CARTON_COMERCIAL,
    VIDRIO_COMERCIAL,
    PILAS,
    ANIMALES_MUERTOS,
    RCD,
    CONTENEDORES_DE_ROPA_USADA,
    RESIDUOS_DEPOSITADOS

}
