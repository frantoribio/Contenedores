package writers

interface Writer<T> {
    fun write(path: String, content: Sequence<T>)
}