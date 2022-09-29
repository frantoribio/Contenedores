package readers

interface Reader<T> {
    fun read(path: String): Sequence<T>
}