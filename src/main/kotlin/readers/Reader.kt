package readers

interface Reader<T> {
    /**
     * Reads from an origin and returns a sequence of objects
     */
    fun read(): Sequence<T>
}