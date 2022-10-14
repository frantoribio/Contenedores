package models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import java.util.*

@Serializable
data class Bitacora(
    val id: String = UUID.randomUUID().toString(),
    @XmlElement(true)
    val instante: String,
    @XmlElement(true)
    val opcion: String,
    @XmlElement(true)
    val hasExito: Boolean,
    @XmlElement(true)
    @Contextual
    val tiempoEjecucion: String
)
