package core.formats

import core.IExporter
import core.IImporter

interface IXmlImporter<T> : IImporter<T> {
    override val extension: String
        get() = ".xml"
}

interface IXmlExporter<T> : IExporter<T> {
    override val extension: String
        get() = ".xml"
}