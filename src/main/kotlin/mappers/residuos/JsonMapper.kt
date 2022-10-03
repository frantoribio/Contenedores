package mappers.residuos


import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeToSequence
import kotlinx.serialization.json.encodeToStream
import models.Residuo
import java.io.InputStream
import java.io.OutputStream
import java.util.*


class JsonMapper(private val json: Json = Json { prettyPrint = true }) : Mapper<Residuo> {

    @OptIn(ExperimentalSerializationApi::class)
    override fun map(input: InputStream): Sequence<Residuo> = json.decodeToSequence(input)

    @OptIn(ExperimentalSerializationApi::class)
    override fun map(input: Sequence<Residuo>, outputStream: OutputStream) =
        json.encodeToStream(input.toList(), outputStream)
}

