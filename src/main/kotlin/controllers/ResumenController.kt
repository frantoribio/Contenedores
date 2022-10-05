package controllers

import models.Contenedor
import models.Resumen

class ResumenController {
    fun getResumen(contenedores: Sequence<Contenedor>): Resumen {
        val distritos = contenedores.groupBy { it.distrito }.map {
            it.key to it.value.map { contenedores -> contenedores.tipoContenedor }.count()
        }
        TODO()
    }
}