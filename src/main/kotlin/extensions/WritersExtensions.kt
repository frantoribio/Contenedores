package extensions

import loggers.LoggedDirectoryWritter
import mu.KLogger
import writers.DirectoryWriter

infix fun <T> DirectoryWriter<T>.loggedWith(logger: KLogger) = LoggedDirectoryWritter(this, logger)