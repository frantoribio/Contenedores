package readers

import dto.Residuos
import mappers.ResiduosCsvMapper
import java.io.File
import java.io.FileNotFoundException

class ResiduosCsvReader : Reader<Residuos> {
    private val parser = ResiduosCsvMapper()

    override fun read(path: String): Sequence<Residuos> = sequence {
        File(path)
            .apply { if (!exists()) throw FileNotFoundException() }
            .useLines { yieldAll(parser.map(it)) }
    }
}

