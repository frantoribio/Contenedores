package writers

import parsers.Parser
import java.io.File
import java.nio.file.Files

class FileWriter<T>(path: String, private val parser: Parser<T>) : Writer<T> {
    private val file = File(path)
    override fun write(content: Sequence<T>) = file
        .apply { if (isDirectory) throw IllegalArgumentException("El archivo destino no puede ser un directorio") }
        .apply { if (exists()) Files.delete(toPath()) }
        .apply { createNewFile() }
        .outputStream()
        .use { parser.unParse(content, it) }
}