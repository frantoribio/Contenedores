package mappers.residuos

import dto.Residuo
import exceptions.CsvException
import extensions.*
import mappers.InMapperCsv
import mappers.OutMapperCsv

/**
 * maps all lines of the csv file to a Residuo lazy sequence
 */

class CsvMapperResiduos : InMapperCsv, OutMapperCsv {
    override fun mapToDto(input: Sequence<String>): Sequence<Residuo> = input.drop(1).map { line ->
        val (ano, mes, lote, residuo, distrito, nombreDistrito, toneladas) = line.split(';')
        Residuo(
            ano?.toIntOrNull()
                ?: throw CsvException("El año no es un número"),

            mes?.ifBlank { throw CsvException("El mes no puede estar vacío") }
                ?: throw CsvException("El mes no puede ser null"),

            lote?.toIntOrNull()
                ?: throw CsvException("El lote no es un número"),

            residuo?.ifBlank { throw CsvException("El residuo no puede estar vacio") }
                ?: throw CsvException("El residuo no puede ser nulo"),

            distrito?.toIntOrNull()
                ?: throw CsvException("El distrito no es un número"),

            nombreDistrito?.ifBlank { throw CsvException("El nombre del distrito no puede estar vacío") }
                ?: throw CsvException("El nombre del distrito no puede ser null"),

            toneladas?.replace(',', '.')?.toDoubleOrNull()
                ?: throw CsvException("Las toneladas no son un número")
        )
    }

    override fun mapFromDto(input: Sequence<Residuo>): Sequence<String> = sequence {
        yield("Año;Mes;Lote;Residuo;Distrito;Nombre Distrito;Toneladas")
        yieldAll(input.map { residuos ->
            "${residuos.ano};${residuos.mes};${residuos.lote};${residuos.residuo};${residuos.distrito};${residuos.nombreDistrito};${
                residuos.toneladas.toString().replace('.', ',')
            }"
        })
    }
}
