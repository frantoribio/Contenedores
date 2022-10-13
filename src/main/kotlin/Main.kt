import args.ArgsParser
import args.Opcion
import args.OpcionParser
import args.OpcionResumen
import models.Consulta
import models.ConsultaDistrito
import org.apache.commons.lang3.StringUtils
import parsers.contenedores.CsvParserContenedores
import parsers.contenedores.JsonParserContenedores
import parsers.contenedores.XmlParserContenedores
import parsers.html.HtmlDistritoUnParser
import parsers.html.HtmlUnParser
import parsers.residuos.CsvParserResiduos
import parsers.residuos.JsonParserResiduos
import parsers.residuos.XmlParserResiduos
import readers.CsvDirectoryReader
import utils.awaitAll
import writers.DirectoryWriter
import java.util.concurrent.CompletableFuture.supplyAsync

fun main(args: Array<String>) {

    when (val opcion = ArgsParser(args).parse()) {
        is OpcionParser -> writeParser(opcion)
        is OpcionResumen -> handleResumen(opcion)
    }
}

fun handleResumen(opcion: OpcionResumen) {
    if (opcion.distrito == null) writeResumen(opcion)
    else writeResumenDistrito(opcion)
}

fun writeResumen(opcion: OpcionResumen) {
    val residuosFuture = supplyAsync { CsvDirectoryReader(opcion.directorioOrigen, CsvParserResiduos()).read() }
    val contenedoresFuture = supplyAsync { CsvDirectoryReader(opcion.directorioOrigen, CsvParserContenedores()).read() }

    val consulta = Consulta(contenedoresFuture.get(), residuosFuture.get())

    DirectoryWriter(opcion.directorioOrigen, "resumen", HtmlUnParser()).write(consulta)
}

fun writeResumenDistrito(opcion: OpcionResumen) {
    val residuosFuture = supplyAsync { CsvDirectoryReader(opcion.directorioOrigen, CsvParserResiduos()).read() }
    val contenedoresFuture = supplyAsync { CsvDirectoryReader(opcion.directorioOrigen, CsvParserContenedores()).read() }

    val consulta =
        ConsultaDistrito(
            contenedoresFuture.get(),
            residuosFuture.get(),
            StringUtils.stripAccents(opcion.distrito).uppercase()
        )

    DirectoryWriter(opcion.directorioOrigen, "resumen${opcion.distrito}", HtmlDistritoUnParser()).write(consulta)
}

fun writeParser(opcion: Opcion) {
    val csvParserContenedores = CsvParserContenedores()
    val csvParserResiduos = CsvParserResiduos()

    val residuosFileWriter = DirectoryWriter(
        opcion.directorioDestino,
        "residuos",
        JsonParserResiduos(),
        XmlParserResiduos(),
        csvParserResiduos
    )

    val contenedoresFileWriter = DirectoryWriter(
        opcion.directorioDestino,
        "contenedores",
        JsonParserContenedores(),
        XmlParserContenedores(),
        csvParserContenedores
    )


    val residuosFuture = supplyAsync { CsvDirectoryReader(opcion.directorioOrigen, csvParserResiduos).read() }
    val contenedoresFuture = supplyAsync { CsvDirectoryReader(opcion.directorioOrigen, csvParserContenedores).read() }

    //write async

    awaitAll(
        { residuosFileWriter.write(residuosFuture.get()) },
        { contenedoresFileWriter.write(contenedoresFuture.get()) }
    )

}

