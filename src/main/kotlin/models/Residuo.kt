package models

import java.time.LocalDate

data class Residuo(
    val fecha: LocalDate,
    val lote: Int,
    val residuo: String, //Some values are not specified in the specification
    val distrito: Int,
    val nombreDistrito: String,
    val toneladas: Double
)