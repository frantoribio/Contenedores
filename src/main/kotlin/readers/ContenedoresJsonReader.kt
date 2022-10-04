package readers

import dto.ContenedorDto
import mappers.residuos.JsonMapper
import java.io.File
import java.io.FileNotFoundException

class ContenedoresJsonReader(private val path: String) : Reader<ContenedorDto> {
    private val maper = JsonMapper

    override fun read(): Sequence<ContenedorDto> = sequence {
        File(path)
            .apply { if (!exists()) throw FileNotFoundException() }
            .run { inputStream().use { yieldAll(maper.mapTo(it)) } }
    }
}