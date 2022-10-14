package core.formats.residuos

import aliases.Residuos
import core.formats.ICsvExporter
import core.formats.ICsvImporter

private const val FIRSTLINE = "Año;Mes;Lote;Residuo;Distrito;Nombre Distrito;Toneladas"

interface ICsvExporterResiduos : ICsvExporter<Residuos> {
    override val firstLine: String
        get() = FIRSTLINE
}

interface ICsvImporterResiduos : ICsvImporter<Residuos> {
    override val firstLine: String
        get() = FIRSTLINE
}