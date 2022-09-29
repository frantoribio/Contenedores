import args.ArgsParser
import readers.ResiduosCsvReader
import writers.ResiduosCsvWriter

fun main(args: Array<String>) {
    val argsConfig = ArgsParser(args).parse()

    val reader = ResiduosCsvReader()
    val writer = ResiduosCsvWriter()
    writer.write("src/main/resources/residuos2.csv", reader.read("src/main/resources/residuos.csv"))
}