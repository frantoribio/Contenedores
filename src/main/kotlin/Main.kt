import readers.ResiduosCsvReader

fun main(args: Array<String>) {
    val reader = ResiduosCsvReader()
    reader.read("src/main/resources/residuos.csv").forEach { println(it) }
}