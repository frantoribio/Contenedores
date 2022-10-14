package extensions

import loggers.LoggedCsvDirectoryReader
import readers.CsvDirectoryReader

internal fun <T> CsvDirectoryReader<T>.logged() = LoggedCsvDirectoryReader(this)