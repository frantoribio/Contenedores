package parsers.formats

import parsers.IExporter
import parsers.IImporter

interface IJsonImporter<T> : IImporter<T> {
    override val extension: String
        get() = ".json"
}

interface IJsonExporter<T> : IExporter<T> {
    override val extension: String
        get() = ".json"
}