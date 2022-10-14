import args.ArgsParser
import args.Opcion
import args.OpcionParser
import args.OpcionResumen
import models.Bitacora
import models.Consulta
import models.ConsultaDistrito
import org.apache.commons.lang3.StringUtils
import parsers.exporting.contenedores.CsvExporterContenedores
import parsers.exporting.contenedores.JsonExporterContenedores
import parsers.exporting.contenedores.XmlExporterContenedores
import parsers.exporting.html.HtmlDistritoExporter
import parsers.exporting.html.HtmlExporter
import parsers.exporting.residuos.CsvExporterResiduos
import parsers.exporting.residuos.JsonExporterResiduos
import parsers.exporting.residuos.XmlExporterResiduos
import parsers.exporting.xml.BitacoraExporter
import parsers.importing.contenedores.CsvImporterContenedores
import parsers.importing.residuos.CsvImporterResiduos
import readers.CsvDirectoryReader
import utils.awaitAll
import writers.DirectoryWriter
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.CompletableFuture.supplyAsync

fun main(args: Array<String>) {

    when (val opcion = ArgsParser(args).parse()) {
        is OpcionParser -> withBitacora(opcion) { writeParser(opcion) }
        is OpcionResumen -> withBitacora(opcion) { handleResumen(opcion) }
    }
}

fun withBitacora(opcion: Opcion, process: () -> Unit) {
    var hasExito = true
    val start = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
    val instant = Instant.now()

    runCatching {
        process()
    }.onSuccess {
        hasExito = true
    }.onFailure {
        hasExito = false
    }

    DirectoryWriter(
        opcion.directorioDestino,
        "bitacora",
        BitacoraExporter()
    ).write(
        Bitacora(
            UUID.randomUUID().toString(),
            start,
            opcion.toString(),
            hasExito,
            Duration.between(Instant.now(), instant).toString()
        )
    )
}

fun handleResumen(opcion: OpcionResumen) {
    if (opcion.distrito == null) writeResumen(opcion)
    else writeResumenDistrito(opcion)
}

fun writeResumen(opcion: OpcionResumen) {
    val residuosFuture = supplyAsync { CsvDirectoryReader(opcion.directorioOrigen, CsvImporterResiduos()).read() }
    val contenedoresFuture =
        supplyAsync { CsvDirectoryReader(opcion.directorioOrigen, CsvImporterContenedores()).read() }

    val consulta = Consulta(contenedoresFuture.get(), residuosFuture.get())

    DirectoryWriter(opcion.directorioDestino, "resumen", HtmlExporter()).write(consulta)
}

fun writeResumenDistrito(opcion: OpcionResumen) {
    val residuosFuture = supplyAsync { CsvDirectoryReader(opcion.directorioOrigen, CsvImporterResiduos()).read() }
    val contenedoresFuture =
        supplyAsync { CsvDirectoryReader(opcion.directorioOrigen, CsvImporterContenedores()).read() }

    val consulta =
        ConsultaDistrito(
            contenedoresFuture.get(),
            residuosFuture.get(),
            StringUtils.stripAccents(opcion.distrito).uppercase()
        )

    DirectoryWriter(opcion.directorioDestino, "resumen${opcion.distrito}", HtmlDistritoExporter()).write(consulta)
}

fun writeParser(opcion: Opcion) {

    val residuosFileWriter = DirectoryWriter(
        opcion.directorioDestino,
        "residuos",
        JsonExporterResiduos(),
        XmlExporterResiduos(),
        CsvExporterResiduos()
    )

    val contenedoresFileWriter = DirectoryWriter(
        opcion.directorioDestino,
        "contenedores",
        JsonExporterContenedores(),
        XmlExporterContenedores(),
        CsvExporterContenedores()
    )


    val residuosFuture = supplyAsync { CsvDirectoryReader(opcion.directorioOrigen, CsvImporterResiduos()).read() }
    val contenedoresFuture =
        supplyAsync { CsvDirectoryReader(opcion.directorioOrigen, CsvImporterContenedores()).read() }

    //write async

    awaitAll(
        { residuosFileWriter.write(residuosFuture.get()) },
        { contenedoresFileWriter.write(contenedoresFuture.get()) }
    )

}

