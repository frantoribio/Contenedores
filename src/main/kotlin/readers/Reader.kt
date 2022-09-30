package readers

interface Reader<T> {
    /**
     * Takes a path and returns a sequence of parsed objects
     */
    fun read(): Sequence<T>
}