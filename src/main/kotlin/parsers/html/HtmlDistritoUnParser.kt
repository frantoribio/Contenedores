package parsers.html

import dto.*
import extensions.exportToHtml
import extensions.toHtmlFormatted
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import models.ConsultaDistrito
import org.apache.commons.lang3.StringUtils
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.letsPlot.geom.geomBar
import org.jetbrains.letsPlot.label.ggtitle
import org.jetbrains.letsPlot.letsPlot
import parsers.UnParser
import java.io.OutputStream
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME

class HtmlDistritoUnParser : UnParser<ConsultaDistrito> {
    override val extension: String
        get() = ".html"

    override fun unParse(input: ConsultaDistrito, outputStream: OutputStream) {
        val residuosDf = input.residuos.toList().toDataFrame()
        val contenedoresDf = input.contenedores.toList().toDataFrame()
        val start = Instant.now()

        outputStream.bufferedWriter().appendHTML().html {
            head {
                title { +"Resumen de recogidas de basura y reciclaje de Madrid" }
                link {
                    href = " \thttps://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css"; rel =
                    "stylesheet"; attributes["crossorigin"] = "anonymous"
                }
            }
            body {

                nav("navbar navbar-dark bg-primary") {
                    div("container-fluid") {
                        span("navbar-brand mb-0 h1") { +"Resumen de recogidas de basura y reciclaje de Madrid" }
                        span("navbar-brand mb-0 h1") {
                            +"Fecha de consulta: ${
                                LocalDateTime.now().format(ISO_LOCAL_DATE_TIME)
                            }"
                        }
                        span("navbar-brand mb-0 h1") { +"Autores: Roberto Blázquez y Fran Toribio" }
                    }
                }

                div("container-fluid d-flex flex-column align-items-center text-center justify-content-center") {
                    titleInfo("Número de contenedores de cada tipo que hay en este distrito")
                    consulta1(contenedoresDf, input.distrito)
                    titleInfo("Total de toneladas recogidas en ese distrito por residuo en este distrito")
                    consulta2(residuosDf, input.distrito)
                    titleInfo("Gráfico con el total de contenedores por residuo en este distrito")
                    consulta3(residuosDf, input.distrito)
                    titleInfo("Máximo, mínimo , media y desviación por mes por residuo en dicho distrito")
                    consulta4(residuosDf, input.distrito)
                    titleInfo("Gráfica del máximo, mínimo y media por meses en dicho distrito")
                    //consulta5(residuosDf, input.distrito)
                }


                p { +"Tiempo de ejecución: ${Duration.between(Instant.now(), start)}" }

                script {
                    src = " https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js"
                    attributes["crossorigin"] = "anonymous"
                }
            }
        }.flush()
    }

    private fun DIV.consulta1(list: DataFrame<ContenedorDto>, distrito: String) {
        val distritos = list
            .filter { distrito.clear == distrito }

        val html = distritos.groupBy { tipoContenedor }.aggregate { tipoContenedorGroup ->
            tipoContenedorGroup.sumOf { cantidadContenedores } into "cantidad contenedores"
        }.toHtmlFormatted()

        div("card card border-info m-5 w-50") {
            div("card-header") { +distrito }
            div("card-body mx-auto") { unsafe { +html } }
        }
    }

    //Solo Dios sabe que hay que hacer aqui
    private fun DIV.consulta2(list: DataFrame<ResiduoDto>, distrito: String) {
        val distritos = list
            .filter { nombreDistrito.clear == distrito }

        val html = distritos.groupBy { residuo }.aggregate { tipoContenedorGroup ->
            tipoContenedorGroup.sumOf { toneladas } into "total toneladas"
        }.toHtmlFormatted()

        div("card card border-info m-5 w-50") {
            div("card-header") { +distrito }
            div("card-body mx-auto") { unsafe { +html } }
        }
    }

    private fun DIV.consulta3(contenedores: DataFrame<ResiduoDto>, distrito: String) {
        //Agrupamos por distrito, mapeamos cada distrito a su suma de contenedores y luego los añadimos la cantidad de contenedores que tenga
        val distritos = contenedores
            .filter { nombreDistrito.clear == distrito }

        val distritosToToneladas = distritos.groupBy { residuo }.map { fechaGroup ->
            val list = mutableListOf<String>()
            repeat(fechaGroup.group.sumOf { toneladas }.toInt()) {
                list.add(fechaGroup.key.mes)
            }
            list
        }.flatten()

        val data = mapOf(
            "Toneladas" to distritosToToneladas
        )

        val p = letsPlot(data) +
                geomBar(color = "dark_green", alpha = .3) { x = "Meses" } +
                ggtitle("Gráfico de suma de toneladas por residuo en ${distrito}")

        div("card card border-info m-5 w-50") {
            div("card-header") { +distrito }
            div("card-body mx-auto") { unsafe { +p.exportToHtml() } }
        }
    }

    private fun DIV.consulta4(residuos: DataFrame<ResiduoDto>, distrito: String) {
        val distritos = residuos
            .filter { nombreDistrito.clear == distrito }

        distritos.groupBy { mes }.forEach {
            p { it.key.mes }
            val html = it.group.groupBy { residuo }.aggregate {
                sumOf { toneladas } into "suma"
                minOf { toneladas } into "minimo"
                maxOf { toneladas } into "maximo"
                stdOf { toneladas } into "desviacion"
            }.toHtmlFormatted()

            div("card card border-info m-5 w-50") {
                div("card-header") { +distrito }
                div("card-body mx-auto") { unsafe { +html } }
            }

        }
    }

    private fun DIV.consulta5(residuos: DataFrame<ResiduoDto>, distrito: String) {
        val distritos = residuos
            .filter { nombreDistrito.clear == distrito }

        val distritosToToneladas = distritos.groupBy { mes }.map { fechaGroup ->
            val list = mutableListOf<String>()
            repeat(fechaGroup.group.meanOf { toneladas }.toInt()) {
                list.add(fechaGroup.key.mes)
            }
            list
        }.flatten()

        val data = mapOf(
            "Meses" to distritosToToneladas
        )

        val p = letsPlot(data) +
                geomBar(color = "dark_green", alpha = .3) { x = "Meses" } +
                ggtitle("Gráfico de media de toneladas mensuales de recogida de basura en $distrito")

        div("card card border-info m-5 w-50") {
            div("card-header") { +distrito }
            div("card-body mx-auto") { unsafe { +p.exportToHtml() } }
        }
    }

    private fun DIV.titleInfo(title: String) {
        div("alert alert-primary mt-5") {
            attributes["role"] = "alert"
            h4("alert-heading") { +title }
        }
    }

    private val String.clear get() = StringUtils.stripAccents(this).uppercase()
}










