package loggers

import mu.KLogger
import readers.CsvDirectoryReader

class LoggedCsvDirectoryReader<T>(private val reader: CsvDirectoryReader<T>, private val logger: KLogger) {
    fun read(): Sequence<T> {
        logger.info("Reading ${reader.path}")
        logger.info("Formats ${reader.parser.extension}")
        logger.info("First line ${reader.parser.firstLine}")
        return reader.read()
    }
}