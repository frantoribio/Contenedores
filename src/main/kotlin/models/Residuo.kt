package models

data class Residuo(
    val ano: Int,
    val mes: String,
    val lote: Int,
    val residuo: String, //Some values are not specified in the specification
    val distrito: Int,
    val nombreDistrito: String,
    val toneladas: Double
)