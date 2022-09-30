package mappers

interface ResiduosOutMapper<in T, out R> {
    fun mapFromDto(input: Sequence<T>): R
}