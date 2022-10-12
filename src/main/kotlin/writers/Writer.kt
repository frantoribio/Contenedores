package writers

interface Writer<in T> {
    /**
     * Writes content of objects to a destination
     */
    fun write(content: T)
}