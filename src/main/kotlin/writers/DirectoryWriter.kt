package writers

import parsers.NamedParser
import java.io.File
import java.io.File.separator
import java.util.concurrent.CompletableFuture

class DirectoryWriter<T>(private var path: String, vararg parsers: NamedParser<T>) : Writer<T> {
    private val fileWriters = mutableListOf<FileWriter<T>>()

    init {
        path = File(path)
            .apply { if (isFile) throw IllegalArgumentException("El directorio destino no puede ser un archivo") }
            .apply { if (!canWrite()) throw IllegalArgumentException("No se puede escribir en el directorio destino") }
            .apply { isDirectory || mkdirs() }
            .path

        parsers.forEach { parser -> fileWriters.add(FileWriter("$path$separator${parser.name}", parser)) }
    }

    override fun write(content: Sequence<T>) {
        val futures = fileWriters.map {
            CompletableFuture.runAsync { it.write(content) }
        }.toTypedArray()
        CompletableFuture.allOf(*futures).join()
    }
}