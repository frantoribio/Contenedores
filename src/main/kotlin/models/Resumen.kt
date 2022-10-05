package models

import java.time.LocalDateTime

data class Resumen(
    val titulo: String,
    val fecha: LocalDateTime,
    val autores: Pair<String, String>,
    val `Numero de contenedores de cada tipo que hay en cada distrito`: Any,
    val `Media de contenedores de cada tipo que hay en cada distrito`: Any,
    val `Grafico con el total de contenedores por distrito`: Any,
    val `Media de toneladas anuales de recogidas por cada tipo de basura agrupadas por distrito`: Any,
    val `Grafico de media de toneladas mensuales de recogida de basura por distrito`: Any,
    val `Maximo, minimo , media y desviacion de toneladas anuales de recogidas por cada tipo de basura`: Any,
    val `Suma de todo lo recogido en un ano por distrito`: Any,
    val `Por cada distrito obtener para cada tipo de residuo`: Any,
    val `Tiempo de generacion del mismo en milisegundo`: Long
)