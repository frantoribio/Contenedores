import args.ArgsParser
import args.Opcion
import controllers.ResumenController
import extensions.toContenedor
import parsers.contenedores.CsvParserContenedores
import parsers.residuos.CsvParserResiduos
import readers.FileReader
import writers.ResiduosFileWriter

fun main(args: Array<String>) {
    val opcion = ArgsParser(args).parse()
    val residuosFileWriter = ResiduosFileWriter(opcion.directorioDestino)
    val residuosCsvFileReader = FileReader(opcion.directorioOrigen, CsvParserResiduos())
    val contenedoresFileReader = FileReader(opcion.directorioOrigen, CsvParserContenedores())


    when (opcion) {
        is Opcion.OpcionParser -> {
            val residuos = residuosCsvFileReader.read()
            residuosFileWriter.write(residuos)
        }

        is Opcion.OpcionResumen -> {
            val controller = ResumenController().getResumen(contenedoresFileReader.read().toContenedor())
        }
    }
}