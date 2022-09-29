package writers

import dto.Residuos
import mappers.residuos.CsvToResiduos
import java.io.File
import java.nio.file.Files

class ResiduosCsvWriter : Writer<Residuos> {
    private val mapper = CsvToResiduos()

    override fun write(path: String, content: Sequence<Residuos>) = File(path)
        .apply { if (exists()) Files.delete(toPath()) }
        .apply { createNewFile() }
        .printWriter()
        .use { out -> mapper.map(content).forEach { out.println(it) } }
}