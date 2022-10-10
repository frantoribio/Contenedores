import args.ArgsParser
import args.Opcion
import args.OpcionParser
import args.OpcionResumen
import extensions.toContenedor
import parsers.contenedores.CsvParserContenedores
import parsers.contenedores.JsonParserContenedores
import parsers.contenedores.XmlParserContenedores
import parsers.html.HtmlUnParser
import parsers.residuos.CsvParserResiduos
import parsers.residuos.JsonParserResiduos
import parsers.residuos.XmlParserResiduos
import readers.CsvDirectoryReader
import writers.DirectoryWriter

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


    val residuosCsvFileReader = CsvDirectoryReader(opcion.directorioOrigen, csvParserResiduos)
    val contenedoresFileReader = CsvDirectoryReader(opcion.directorioOrigen, csvParserContenedores)

    residuosFileWriter.write(residuosCsvFileReader.read())
    contenedoresFileWriter.write(contenedoresFileReader.read())

    //TESTING HTML
    HtmlUnParser().unParse(contenedoresFileReader.read().toContenedor(), System.out)
}