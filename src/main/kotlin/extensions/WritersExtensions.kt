package extensions

import loggers.LoggedDirectoryWritter
import writers.DirectoryWriter

fun <T> DirectoryWriter<T>.logged() = LoggedDirectoryWritter(this)