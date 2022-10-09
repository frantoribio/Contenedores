package writers

import parsers.NamedParser
import java.io.File
import java.io.File.separator
import java.util.concurrent.CompletableFuture

class DirectoryWriter<T>(private var path: String, private vararg val parsers: NamedParser<T>) : Writer<T> {
    init {
        path = File(path)
            .apply { if (isFile) throw IllegalArgumentException("El directorio destino no puede ser un archivo") }
            .apply { if (!canWrite()) throw IllegalArgumentException("No se puede escribir en el directorio destino") }
            .apply { isDirectory || mkdirs() }
            .path
    }

    override fun write(content: Sequence<T>) {
        val futures = parsers.map { parser ->
            CompletableFuture.runAsync {
                FileWriter("$path$separator${parser.name}", parser).write(content)
            }
        }.toTypedArray()
        CompletableFuture.allOf(*futures).join()

    }
}