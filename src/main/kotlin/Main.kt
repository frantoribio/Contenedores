import args.ArgsParser
import args.Opcion
import args.OpcionParser
import args.OpcionResumen
import core.exporting.contenedores.CsvExporterContenedores
import core.exporting.contenedores.JsonExporterContenedores
import core.exporting.contenedores.XmlExporterContenedores
import core.exporting.html.HtmlDistritoExporter
import core.exporting.html.HtmlExporter
import core.exporting.residuos.CsvExporterResiduos
import core.exporting.residuos.JsonExporterResiduos
import core.exporting.residuos.XmlExporterResiduos
import core.exporting.xml.BitacoraExporter
import core.importing.contenedores.CsvImporterContenedores
import core.importing.residuos.CsvImporterResiduos
import extensions.loggedWith
import models.Bitacora
import models.Consulta
import models.ConsultaDistrito
import mu.KotlinLogging
import org.apache.commons.lang3.StringUtils
import readers.CsvDirectoryReader
import utils.awaitAll
import writers.DirectoryWriter
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.CompletableFuture.supplyAsync

val logger = KotlinLogging.logger("Main")
fun main(args: Array<String>) {

    when (val opcion = ArgsParser(args).parse()) {
        is OpcionParser -> withBitacora(opcion) { writeParser(opcion) }
        is OpcionResumen -> withBitacora(opcion) { handleResumen(opcion) }
    }
}

fun withBitacora(opcion: Opcion, process: () -> Unit) {
    var ex: Throwable? = null
    var hasExito = true
    val start = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
    val instant = Instant.now()

    runCatching {
        process()
    }.onSuccess {
        hasExito = true
    }.onFailure {
        hasExito = false
        ex = it
    }

    val writerBitacora = DirectoryWriter(opcion.directorioDestino, "bitacora", BitacoraExporter()) loggedWith logger
    writerBitacora.write(
        Bitacora(
            UUID.randomUUID().toString(),
            start,
            opcion.toString(),
            hasExito,
            Duration.between(Instant.now(), instant).toString()
        )
    )

    throw ex ?: return
}

fun handleResumen(opcion: OpcionResumen) {
    if (opcion.distrito == null) writeResumen(opcion)
    else writeResumenDistrito(opcion)
}

fun writeResumen(opcion: OpcionResumen) {
    val residuosFuture =
        supplyAsync { CsvDirectoryReader(opcion.directorioOrigen, CsvImporterResiduos()).loggedWith(logger).read() }
    val contenedoresFuture =
        supplyAsync { CsvDirectoryReader(opcion.directorioOrigen, CsvImporterContenedores()).loggedWith(logger).read() }

    val consulta = Consulta(contenedoresFuture.get(), residuosFuture.get())

    DirectoryWriter(opcion.directorioDestino, "resumen", HtmlExporter())
        .loggedWith(logger)
        .write(consulta)
}

fun writeResumenDistrito(opcion: OpcionResumen) {
    val residuosFuture =
        supplyAsync { CsvDirectoryReader(opcion.directorioOrigen, CsvImporterResiduos()).loggedWith(logger).read() }
    val contenedoresFuture =
        supplyAsync { CsvDirectoryReader(opcion.directorioOrigen, CsvImporterContenedores()).loggedWith(logger).read() }

    val consulta =
        ConsultaDistrito(
            contenedoresFuture.get(),
            residuosFuture.get(),
            StringUtils.stripAccents(opcion.distrito).uppercase()
        )

    DirectoryWriter(opcion.directorioDestino, "resumen${opcion.distrito}", HtmlDistritoExporter())
        .loggedWith(logger)
        .write(consulta)
}

fun writeParser(opcion: Opcion) {
    val residuosFileWriter = DirectoryWriter(
        opcion.directorioDestino,
        "residuos",
        JsonExporterResiduos(),
        XmlExporterResiduos(),
        CsvExporterResiduos()
    ) loggedWith logger


    val contenedoresFileWriter = DirectoryWriter(
        opcion.directorioDestino,
        "contenedores",
        JsonExporterContenedores(),
        XmlExporterContenedores(),
        CsvExporterContenedores()
    ) loggedWith logger


    val residuosFuture =
        supplyAsync { CsvDirectoryReader(opcion.directorioOrigen, CsvImporterResiduos()).loggedWith(logger).read() }
    val contenedoresFuture =
        supplyAsync { CsvDirectoryReader(opcion.directorioOrigen, CsvImporterContenedores()).loggedWith(logger).read() }

    //write async

    awaitAll(
        { residuosFileWriter.write(residuosFuture.get()) },
        { contenedoresFileWriter.write(contenedoresFuture.get()) }
    )
}

