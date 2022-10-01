package mappers

interface ResiduosInMapper<in R, out T> {
    /**
     * Takes an input and returns a parsed output
     */
    fun mapTo(input: R): Sequence<T>

}