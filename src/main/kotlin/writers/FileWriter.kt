package writers

import parsers.IExporter
import java.io.File
import java.nio.file.Files

internal class FileWriter<T>(path: String, private val parser: IExporter<T>) {
    private val file = File(path)
    fun write(content: T) = file
        .apply { if (isDirectory) throw IllegalArgumentException("El archivo destino no puede ser un directorio") }
        .apply { if (exists()) Files.delete(toPath()) }
        .apply { createNewFile() }
        .outputStream()
        .use { parser.export(content, it) }
}