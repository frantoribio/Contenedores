package readers

import mappers.InMapperCsv
import mappers.residuos.CsvMapperResiduos
import models.Residuo
import java.io.File
import java.io.FileNotFoundException

class ResiduosCsvReader(private val path: String) : Reader<Residuo> {
    private val mapper: InMapperCsv = CsvMapperResiduos()

    override fun read(): Sequence<Residuo> = sequence {
        File(path)
            .apply { if (!exists()) throw FileNotFoundException() }
            .useLines { yieldAll(mapper.mapToDto(it)) }
    }
}

