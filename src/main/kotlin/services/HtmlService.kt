package services

import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import java.io.OutputStream

class HtmlService {
    fun html(outputStream: OutputStream) = outputStream.bufferedWriter().appendHTML().html {
        head {
            title { +"Residuos" }
        }
        body {
            h1 { +"Hello World!" }
        }
    }
}