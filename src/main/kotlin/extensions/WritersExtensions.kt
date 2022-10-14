package extensions

import loggers.LoggedDirectoryWritter
import writers.DirectoryWriter

internal fun <T> DirectoryWriter<T>.logged() = LoggedDirectoryWritter(this)