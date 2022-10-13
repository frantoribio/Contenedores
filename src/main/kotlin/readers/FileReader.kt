package readers

import aliases.SequenceImporter
import java.io.File
import java.io.FileNotFoundException

class FileReader<T>(path: String, private val parser: SequenceImporter<T>) {
    private val file = File(path)
    fun read(): Sequence<T> = sequence {
        file
            .apply { if (isDirectory) throw IllegalArgumentException("El archivo origen no puede ser un directorio") }
            .apply { if (!exists()) throw FileNotFoundException("File not found") }
            .inputStream()
            .use { yieldAll(parser.import(it)) }
    }
}

