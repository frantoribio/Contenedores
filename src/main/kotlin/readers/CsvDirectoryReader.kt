package readers

import aliases.CsvSequenceImporter
import extensions.firstLine
import java.io.File

class CsvDirectoryReader<T>(val path: String, val parser: CsvSequenceImporter<T>) {

    private val fileReader: FileReader<T>

    init {
        val csvFiles = File(path)
            .apply { if (!isDirectory) throw IllegalArgumentException("$path no es un directorio") }
            .listFiles { _, name -> name.endsWith(parser.extension) }

        val firstLines = csvFiles?.map { file -> file to file.firstLine }
            ?: throw IllegalArgumentException("No se encontraron archivos .csv en el directorio $path")

        val file = firstLines.firstOrNull { it.second == parser.firstLine }
            ?: throw IllegalArgumentException("No se encontró el archivo con el formato correcto")

        fileReader = FileReader(file.first.path, parser)
    }

    fun read(): Sequence<T> = fileReader.read()
}