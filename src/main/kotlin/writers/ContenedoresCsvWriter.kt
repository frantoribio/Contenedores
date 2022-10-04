package writers

import dto.ContenedorDto
import mappers.contenedores.CsvMapperContenedores
import java.io.File
import java.nio.file.Files

class ContenedoresCsvWriter(private val path: String) : Writer<ContenedorDto> {
    private val mapper = CsvMapperContenedores

    override fun write(content: Sequence<ContenedorDto>) = File(path)
        .apply { if (exists()) Files.delete(toPath()) }
        .apply { createNewFile() }
        .printWriter()
        .use { out -> mapper.mapFrom(content).forEach { out.println(it) } }
}