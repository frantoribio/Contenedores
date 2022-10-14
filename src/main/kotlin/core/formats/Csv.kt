package core.formats

import core.IExporter
import core.IImporter

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

