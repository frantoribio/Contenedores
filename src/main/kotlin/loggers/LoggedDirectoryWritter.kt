package loggers

import writers.DirectoryWriter

internal class LoggedDirectoryWritter<T>(private val writer: DirectoryWriter<T>) {
    fun write(content: T) {
        println("Writing ${writer.fileName} to ${writer.path}")
        println("Formats ${writer.exporters.joinToString(separator = " ") { it.extension }}")
        writer.write(content)
    }
}