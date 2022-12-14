package controllers

import aliases.Contenedores
import aliases.Residuos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import models.Consulta
import readers.IReader
import writers.IWriter

class ResumenController(
    private val writer: IWriter<Consulta>,
    private val residuosReader: IReader<Residuos>,
    private val contenedoresReader: IReader<Contenedores>,
) : IController {
    override suspend fun process() = withContext(Dispatchers.IO) {
        val residuosFuture =
            async { residuosReader.read() }
        val contenedoresFuture =
            async { contenedoresReader.read() }

        val consulta = Consulta(contenedoresFuture.await(), residuosFuture.await())

        writer.write(consulta)
    }

}