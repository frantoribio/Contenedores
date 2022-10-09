package services

import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import models.Contenedor
import readers.Reader
import java.io.OutputStream
import java.time.LocalDateTime

class HtmlService(private val reader: Reader<Contenedor>) {
    fun html(outputStream: OutputStream) = outputStream.bufferedWriter().appendHTML().html {
        head {
            title { +"Resumen de recogidas de basura y reciclaje de Madrid" }
        }
        body {
            h1 { +"Resumen de recogidas de basura y reciclaje de Madrid" }
            h3 { "Fecha: ${LocalDateTime.now()}" }
            h3 { +"Autores: Roberto Blázquez y Fran Toribio" }
            h1 { +"Número de contenedores de cada tipo que hay en cada distrito" }
            consulta1()
            h1 { +"Media de contenedores de cada tipo que " }
        }
    }

    private fun BODY.consulta1() {
        reader.read().groupBy {
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
}