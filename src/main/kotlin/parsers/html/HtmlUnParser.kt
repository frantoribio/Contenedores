package parsers.html

import extensions.exportToHtml
import extensions.toContenedor
import extensions.toResiduo
import extensions.toSpanish
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import models.Consulta
import models.Contenedor
import models.Residuo
import org.jetbrains.letsPlot.geom.geomBar
import org.jetbrains.letsPlot.ggsize
import org.jetbrains.letsPlot.label.ggtitle
import org.jetbrains.letsPlot.letsPlot
import parsers.UnParser
import java.io.OutputStream
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.Month
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME

class HtmlUnParser : UnParser<Consulta> {
    override val extension: String
        get() = ".html"

    override fun unParse(input: Consulta, outputStream: OutputStream) {
        val contenedores = input.contenedores.toContenedor()
        val residuos = input.residuos.toResiduo()
        val start = Instant.now()

        outputStream.bufferedWriter().appendHTML().html {
            head {
                title { +"Resumen de recogidas de basura y reciclaje de Madrid" }
            }
            body {
                h1 { +"Resumen de recogidas de basura y reciclaje de Madrid" }
                h3 { +"Fecha: ${LocalDateTime.now().format(ISO_LOCAL_DATE_TIME)}" }
                h3 { +"Autores: Roberto Blázquez y Fran Toribio" }
                h1 { +"Número de contenedores de cada tipo que hay en cada distrito" }
                consulta1(contenedores)
                h1 { +"Media de contenedores de cada tipo que hay en cada distrito" }
                consulta2(contenedores)
                h1 { +"Gráfico con el total de contenedores por distrito" }
                consulta3(contenedores)
                h1 { +" Media de toneladas anuales de recogidas por cada tipo de basura agrupadas por distrito" }
                consulta4(residuos)
                h1 { +"Gráfico de media de toneladas mensuales de recogida de basura por distrito" }
                consulta5(residuos)
                h1 { +"Máximo, mínimo , media y desviación de toneladas anuales de recogidas por cada tipo de basura agrupadas por distrito" }
                consulta6(residuos)

                p { +"Tiempo de ejecución: ${Duration.between(Instant.now(), start)}" }
            }
        }.flush()
    }

    private fun BODY.consulta1(list: Sequence<Contenedor>) {
        list.groupBy {
            it.distrito
        }.forEach {
            p { +"Distrito ${it.key}" }
            ul {
                it.value.groupBy {
                    it.tipoContenedor
                }.forEach {
                    li { +"${it.key}: ${it.value.size}" }
                }
            }
        }
    }

    //Solo Dios sabe que hay que hacer aqui
    private fun BODY.consulta2(list: Sequence<Contenedor>) {
        list.groupBy {
            it.tipoContenedor
        }.map { distrito ->
            distrito.key to distrito.value.map { it.cantidadContenedores }.average()
        }.forEach {
            p { +"Distrito ${it.first}" }
            p { +"Media contenedores : ${it.second}" }
        }
    }

    private fun BODY.consulta3(list: Sequence<Contenedor>) {
        //Agrupamos por distrito, mapeamos cada distrito a su suma de contenedores y luego los añadimos la cantidad de contenedores que tenga
        val distritos = list.groupBy { it.distrito }
            .map { distrito -> distrito.key to distrito.value.sumOf { it.cantidadContenedores } }
            .flatMap { pair ->
                val count = mutableListOf<String>()
                repeat(pair.second) { count.add(pair.first) }
                count
            }

        val data = mapOf(
            "Distritos" to distritos
        )
        val p = letsPlot(data) +
                geomBar(color = "dark_green", alpha = .3) { x = "Distritos" } +
                ggsize(700, 350) +
                ggtitle("Gráfico con el total de contenedores por distrito")

        unsafe { +p.exportToHtml() }
    }

    private fun BODY.consulta4(residuos: Sequence<Residuo>) {
        val distritos = residuos
            .groupBy { it.nombreDistrito }
            .map { distrito -> distrito.key to distrito.value.groupBy { it.residuo } }

        distritos.forEach { distrito ->
            p { +"Distrito ${distrito.first}" }
            ul {
                distrito.second.forEach { residuo ->
                    li { +"${residuo.key}: ${residuo.value.map { it.toneladas }.average()}" }
                }
            }
        }
    }

    private fun BODY.consulta5(residuos: Sequence<Residuo>) {
        val distritos = residuos
            .groupBy { it.nombreDistrito }


        val centro =
            residuos.filter { it.nombreDistrito.uppercase() == "CENTRO" }.filter { it.fecha.month == Month.JANUARY }
                .map { it.toneladas }.average()


        val medias = distritos.map { distrito ->
            distrito.key to (distrito.value.groupBy { it.fecha.month.toSpanish() }
                .flatMap { month ->
                    val list = mutableListOf<String>()
                    repeat(month.value.map { it.toneladas }.average().toInt()) { list.add(month.key) }
                    list
                })
        }


        medias.forEach { distrito ->
            h2 { +"Distrito ${distrito.first}" }

            val data = mapOf(
                "Meses" to distrito.second.map { it }
            )

            val p = letsPlot(data) +
                    geomBar(color = "dark_green", alpha = .3) { x = "Meses" } +
                    ggsize(700, 350) +
                    ggtitle("Gráfico de media de toneladas mensuales de recogida de basura en ${distrito.first}")

            unsafe { +p.exportToHtml() }
        }
    }

    private fun BODY.consulta6(residuos: Sequence<Residuo>) {

        val distritos = residuos
            .groupBy { it.nombreDistrito }

        distritos.forEach { distrito ->
            h2 { +"Distrito ${distrito.key}" }

            distrito.value.groupBy { it.residuo }.forEach { residuo ->
                p { +residuo.key }
                ul {
                    li { +"Media : ${residuo.value.map { it.toneladas }.average()}" }
                    li { +"Max : ${residuo.value.maxOfOrNull { it.toneladas }}" }
                    li { +"Min : ${residuo.value.minOfOrNull { it.toneladas }}" }
                    //TODO: Desviacion
                }
            }
        }

    }
}






