package mappers

interface Mapper<R, T> {
    /**
     * Takes an input and returns a parsed output
     */
    fun map(input: R): T
}