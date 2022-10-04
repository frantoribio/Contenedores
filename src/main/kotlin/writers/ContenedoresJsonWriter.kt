package writers

import dto.ContenedorDto
import mappers.residuos.JsonMapper
import java.io.File
import java.nio.file.Files

class ContenedoresJsonWriter(private val path: String) : Writer<ContenedorDto> {
    private val mapper = JsonMapper

    override fun write(content: Sequence<ContenedorDto>) {
        File(path)
            .apply { if (exists()) Files.delete(toPath()) }
            .apply { createNewFile() }
            .apply {
                outputStream().use { output -> mapper.mapFrom(content).use { input -> input.copyTo(output) } }
            }
    }
}