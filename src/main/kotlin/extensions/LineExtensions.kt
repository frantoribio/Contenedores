package extensions

//Add more components to deconstruct
operator fun <T> List<T>.component1(): T? = getOrNull(0)
operator fun <T> List<T>.component2(): T? = getOrNull(1)
operator fun <T> List<T>.component3(): T? = getOrNull(2)
operator fun <T> List<T>.component4(): T? = getOrNull(3)
operator fun <T> List<T>.component5(): T? = getOrNull(4)
operator fun <T> List<T>.component6(): T? = getOrNull(5)
operator fun <T> List<T>.component7(): T? = getOrNull(6)