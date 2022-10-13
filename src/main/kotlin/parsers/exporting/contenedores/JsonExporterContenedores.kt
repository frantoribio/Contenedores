package parsers.exporting.contenedores


import aliases.Contenedores
import dto.ContenedorDto
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToStream
import parsers.formats.IJsonExporter
import java.io.OutputStream
import java.util.*


class JsonExporterContenedores(private val json: Json = Json { prettyPrint = true }) : IJsonExporter<Contenedores> {

    @OptIn(ExperimentalSerializationApi::class)
    override fun export(input: Sequence<ContenedorDto>, outputStream: OutputStream) =
        json.encodeToStream(input.toList(), outputStream)
}

