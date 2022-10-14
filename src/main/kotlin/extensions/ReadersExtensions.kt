package extensions

import loggers.LoggedCsvDirectoryReader
import readers.CsvDirectoryReader

fun <T> CsvDirectoryReader<T>.logged() = LoggedCsvDirectoryReader(this)