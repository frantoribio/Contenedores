import args.ArgsParser
import args.Opcion
import args.OpcionParser
import args.OpcionResumen
import extensions.named
import parsers.contenedores.CsvParserContenedores
import parsers.contenedores.JsonParserContenedores
import parsers.contenedores.XmlParserContenedores
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
    val residuosFileWriter = DirectoryWriter(
        opcion.directorioOrigen,
        JsonParserResiduos() named "residuos.json",
        XmlParserResiduos() named "residuos.xml"
    )

    val contenedoresFileWriter = DirectoryWriter(
        opcion.directorioOrigen,
        JsonParserContenedores() named "contenedores.json",
        XmlParserContenedores() named "contenedores.xml"
    )


    val residuosCsvFileReader = CsvDirectoryReader(opcion.directorioOrigen, CsvParserResiduos())
    val contenedoresFileReader = CsvDirectoryReader(opcion.directorioOrigen, CsvParserContenedores())

    residuosFileWriter.write(residuosCsvFileReader.read())
    contenedoresFileWriter.write(contenedoresFileReader.read())
}