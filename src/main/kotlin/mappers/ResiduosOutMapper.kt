package mappers

interface ResiduosOutMapper<in T, out R> {
    fun mapFrom(input: Sequence<T>): R
}