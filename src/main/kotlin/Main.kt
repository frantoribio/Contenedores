import args.ArgsParser
import args.Opcion
import args.Opcion.OpcionParser
import args.Opcion.OpcionResumen
import extensions.named
import parsers.contenedores.CsvParserContenedores
import parsers.contenedores.JsonParserContenedores
import parsers.contenedores.XmlParserContenedores
import parsers.residuos.CsvParserResiduos
import parsers.residuos.JsonParserResiduos
import parsers.residuos.XmlParserResiduos
import readers.CsvFileReader
import writers.NamedFileWriter

fun main(args: Array<String>) {
    val opcion = ArgsParser(args).parse()

    when (opcion) {
        is OpcionParser -> writeParser(opcion)

        is OpcionResumen -> {
            //val controller = ResumenController().getResumen(contenedoresFileReader.read().toContenedor())
        }
    }
}

private fun writeParser(opcion: Opcion) {
    val residuosFileWriter = NamedFileWriter(
        opcion.directorioOrigen,
        JsonParserResiduos() named "residuos.json",
        XmlParserResiduos() named "residuos.xml"
    )

    val contenedoresFileWriter = NamedFileWriter(
        opcion.directorioOrigen,
        JsonParserContenedores() named "contenedores.json",
        XmlParserContenedores() named "contenedores.xml"
    )


    val residuosCsvFileReader = CsvFileReader(opcion.directorioOrigen, CsvParserResiduos())
    val contenedoresFileReader = CsvFileReader(opcion.directorioOrigen, CsvParserContenedores())

    residuosFileWriter.write(residuosCsvFileReader.read())
    contenedoresFileWriter.write(contenedoresFileReader.read())
}