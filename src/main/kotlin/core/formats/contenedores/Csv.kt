package core.formats.contenedores

import aliases.Contenedores
import core.formats.ICsvExporter
import core.formats.ICsvImporter

private const val FIRSTLINE =
    "Código Interno del Situad;Tipo Contenedor;Modelo;Descripcion Modelo;Cantidad;Lote;Distrito;Barrio;Tipo Vía;Nombre;Número;COORDENADA X;COORDENADA Y;LONGITUD;LATITUD;DIRECCION"

interface ICsvExporterContenedores : ICsvExporter<Contenedores> {
    override val firstLine: String
        get() = FIRSTLINE
}

interface ICsvImporterContenedores : ICsvImporter<Contenedores> {
    override val firstLine: String
        get() = FIRSTLINE
}