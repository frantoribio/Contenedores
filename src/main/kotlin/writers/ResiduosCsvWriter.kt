package writers

import dto.Residuo
import mappers.OutMapperCsv
import mappers.residuos.CsvMapperResiduos
import java.io.File
import java.nio.file.Files

class ResiduosCsvWriter(private val path: String) : Writer<Residuo> {
    private val mapper: OutMapperCsv = CsvMapperResiduos()

    override fun write(content: Sequence<Residuo>) = File(path)
        .apply { if (exists()) Files.delete(toPath()) }
        .apply { createNewFile() }
        .printWriter()
        .use { out -> mapper.mapFromDto(content).forEach { out.println(it) } }
}