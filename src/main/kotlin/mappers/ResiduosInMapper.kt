package mappers

interface ResiduosInMapper<in R, out T> {
    /**
     * Takes an input and returns a parsed output
     */
    fun mapToDto(input: R): Sequence<T>

}