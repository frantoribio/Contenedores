package parsers

interface CsvParser<T> : Parser<T> {
    val firstLine: String
}