package args

import args.OpcionConfig.OpcionParser
import args.OpcionConfig.OpcionResumen
import java.util.*

class ArgsParser(private val params: Array<String>) {
    init {
        if (params.isEmpty())
            throw IllegalArgumentException("No se han introducido parámetros")

        if (params.size > 4)
            throw IllegalArgumentException("Demasiados parámetros")
    }

    fun parse(): OpcionConfig = when (params.first().lowercase(Locale.getDefault())) {
        "parser" -> OpcionParser(params)
        "resumen" -> OpcionResumen(params)
        else -> throw IllegalArgumentException("La opción no es válida")
    }
}