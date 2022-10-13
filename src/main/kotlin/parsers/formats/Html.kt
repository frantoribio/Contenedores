package parsers.formats

import parsers.IExporter

interface IHtmlExporter<T> : IExporter<T> {
    override val extension: String
        get() = ".html"
}