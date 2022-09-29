package readers

import dto.Residuos
import mappers.residuos.ResiduosToCsvMapper
import java.io.File
import java.io.FileNotFoundException

class ResiduosCsvReader : Reader<Residuos> {
    private val mapper = ResiduosToCsvMapper()

    override fun read(path: String): Sequence<Residuos> = sequence {
        File(path)
            .apply { if (!exists()) throw FileNotFoundException() }
            .useLines { yieldAll(mapper.map(it)) }
    }
}

