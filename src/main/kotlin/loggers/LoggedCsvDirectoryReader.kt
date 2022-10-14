package loggers

import readers.CsvDirectoryReader

class LoggedCsvDirectoryReader<T>(private val reader: CsvDirectoryReader<T>) {
    fun read(): Sequence<T> {
        println("Reading ${reader.path}")
        println("Formats ${reader.parser.extension}")
        println("First line ${reader.parser.firstLine}")
        return reader.read()
    }
}