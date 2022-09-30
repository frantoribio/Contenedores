package extensions

import dto.Residuo
import java.time.LocalDate
import java.time.Month
import java.util.*

/**
 * Returns date in LocalDate format
 * @throws IllegalArgumentException if date not in specified format
 */
val Residuo.fecha: LocalDate
    get() = LocalDate.of(ano, mes.parse(), 1)

private fun String.parse(): Int {
    return when (this.lowercase(Locale.getDefault())) {
        "enero" -> Month.JANUARY.value
        "febrero" -> Month.FEBRUARY.value
        "marzo" -> Month.MARCH.value
        "abril" -> Month.APRIL.value
        "mayo" -> Month.MAY.value
        "junio" -> Month.JUNE.value
        "julio" -> Month.JULY.value
        "agosto" -> Month.AUGUST.value
        "septiembre" -> Month.SEPTEMBER.value
        "octubre" -> Month.OCTOBER.value
        "noviembre" -> Month.NOVEMBER.value
        "diciembre" -> Month.DECEMBER.value
        else -> throw IllegalArgumentException("El mes no es válido")
    }
}
