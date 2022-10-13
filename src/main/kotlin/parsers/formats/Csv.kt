package parsers.formats

import parsers.IExporter
import parsers.IImporter

interface ICsvImporter<T> : IImporter<T> {
    override val extension: String
        get() = ".csv"

    val firstLine: String
}

interface ICsvExporter<T> : IExporter<T> {
    override val extension: String
        get() = ".csv"

    val firstLine: String
}

