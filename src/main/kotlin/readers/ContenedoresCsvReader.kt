package readers

import dto.ContenedorDto
import mappers.contenedores.CsvMapperContenedores
import java.io.File
import java.io.FileNotFoundException

class ContenedoresCsvReader(private val path: String) : Reader<ContenedorDto> {
    private val mapper = CsvMapperContenedores
    override fun read(): Sequence<ContenedorDto> = sequence {
        File(path)
            .apply { if (!exists()) throw FileNotFoundException() }
            .useLines { yieldAll(mapper.mapTo(it)) }
    }
}