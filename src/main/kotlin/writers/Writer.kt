package writers

interface Writer<T> {
    /**
     * Writes a sequence of objects to a destination
     */
    fun write(content: Sequence<T>)
}