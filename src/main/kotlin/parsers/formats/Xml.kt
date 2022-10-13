package parsers.formats

import parsers.IExporter
import parsers.IImporter

interface IXmlImporter<T> : IImporter<T> {
    override val extension: String
        get() = ".xml"
}

interface IXmlExporter<T> : IExporter<T> {
    override val extension: String
        get() = ".xml"
}