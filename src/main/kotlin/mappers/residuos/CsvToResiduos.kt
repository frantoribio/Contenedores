package mappers.residuos

import dto.Residuos
import mappers.Mapper

class CsvToResiduos : Mapper<Sequence<Residuos>, Sequence<String>> {
    override fun map(input: Sequence<Residuos>): Sequence<String> = sequence {
        yield("Año;Mes;Lote;Residuo;Distrito;Nombre Distrito;Toneladas")
        yieldAll(input.map { residuos ->
            "${residuos.ano};${residuos.mes};${residuos.lote};${residuos.residuo};${residuos.distrito};${residuos.nombreDistrito};${
                residuos.toneladas.toString().replace('.', ',')
            }"
        })
    }
}