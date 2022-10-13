package models

import dto.ContenedorDto
import dto.ResiduoDto

data class ConsultaDistrito(
    val contenedores: Sequence<ContenedorDto>,
    val residuos: Sequence<ResiduoDto>,
    val distrito: String
)