package core.formats

import core.IExporter

interface IHtmlExporter<T> : IExporter<T> {
    override val extension: String
        get() = ".html"
}