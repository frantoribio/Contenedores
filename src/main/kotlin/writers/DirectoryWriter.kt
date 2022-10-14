package writers

import parsers.IExporter
import utils.awaitAll
import java.io.File
import java.io.File.separator
import java.util.function.Supplier

class DirectoryWriter<T>(
    private var path: String,
    private val fileName: String,
    vararg exporters: IExporter<T>,
) {
    private val fileWriters = mutableListOf<FileWriter<T>>()

    init {
        path = File(path)
            .apply { if (isFile) throw IllegalArgumentException("El directorio destino no puede ser un archivo") }
            .apply { (isDirectory || mkdirs()) || throw IllegalArgumentException("No se pudo crear el directorio destino") }
            .path

        exporters.forEach { parser ->
            val fileWriter = FileWriter("$path$separator${createName(extension = parser.extension)}", parser)
            fileWriters.add(fileWriter)
        }
    }

    private fun createName(i: Int = 0, extension: String): String {
        val incrementator = if (i == 0) "" else "($i)"
        var name = fileName + incrementator + extension
        val file = File(path + separator + name)
        if (file.exists()) name = createName(i + 1, extension)
        return name
    }

    fun write(content: T) = awaitAll(*fileWriters.map { Supplier { it.write(content) } }.toTypedArray())
}