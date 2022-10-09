package readers

import extensions.firstLine
import parsers.CsvParser
import java.io.File

class CsvFileReader<T>(path: String, private val parser: CsvParser<T>) :
    Reader<T> {

    private var fileReader: FileReader<T>

    init {
        val csvFiles = File(path).listFiles { _, name -> name.endsWith(".csv") }
        val firstLines =
            csvFiles?.map { file -> file to file.firstLine }
                ?: throw IllegalArgumentException("No se encontraron archivos .csv en el directorio $path")

        val file = firstLines.firstOrNull { it.second == parser.firstLine }
            ?: throw IllegalArgumentException("No se encontró el archivo con el formato correcto")

        fileReader = FileReader(file.first.path, parser)
    }

    override fun read(): Sequence<T> = fileReader.read()
}