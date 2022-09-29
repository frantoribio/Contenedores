package writers

interface Writer<T> {
    /**
     * Takes a path and a sequence of objects and writes them to the path
     */
    fun write(path: String, content: Sequence<T>)
}