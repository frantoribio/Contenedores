package mappers

import dto.Residuos
import extensions.component6
import extensions.component7

/**
 * Parses all lines of the csv file to a Residuos lazy sequence
 */

class ResiduosCsvMapper : Mapper<Sequence<String>, Sequence<Residuos>> {
    override fun map(input: Sequence<String>): Sequence<Residuos> = input.drop(1).map { line ->
        val (ano, mes, lote, residuo, distrito, nombreDistrito, toneladas) = line.split(';')
        Residuos(
            ano.toIntOrNull() ?: throw NumberFormatException("El año no es un número"),
            mes.apply { if (isBlank()) throw IllegalArgumentException("El mes no puede estar vacío") },
            lote.toIntOrNull() ?: throw NumberFormatException("El lote no es un número"),
            residuo.trim(),
            distrito.toIntOrNull() ?: throw NumberFormatException("El distrito no es un número"),
            nombreDistrito.apply { if (isBlank()) throw IllegalArgumentException("El nombre del distrito no puede estar vacío") },
            toneladas.replace(',', '.').toDoubleOrNull()
                ?: throw NumberFormatException("Las toneladas no son un número")
        )
    }
}
