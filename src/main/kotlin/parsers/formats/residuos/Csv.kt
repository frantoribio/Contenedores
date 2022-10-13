package parsers.formats.residuos

import aliases.Residuos
import parsers.formats.ICsvExporter
import parsers.formats.ICsvImporter

private const val FIRSTLINE = "Año;Mes;Lote;Residuo;Distrito;Nombre Distrito;Toneladas"

interface ICsvExporterResiduos : ICsvExporter<Residuos> {
    override val firstLine: String
        get() = FIRSTLINE
}

interface ICsvImporterResiduos : ICsvImporter<Residuos> {
    override val firstLine: String
        get() = FIRSTLINE
}