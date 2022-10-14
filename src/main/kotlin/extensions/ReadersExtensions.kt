package extensions

import loggers.LoggedCsvDirectoryReader
import mu.KLogger
import readers.CsvDirectoryReader

infix fun <T> CsvDirectoryReader<T>.loggedWith(logger: KLogger) = LoggedCsvDirectoryReader(this, logger)