

inline fun <P, R> cachedFunction(crossinline f: (P) -> R): (P) -> R {
    val cache = mutableMapOf<P, R>()
    return { input ->
        cache.getOrPut(input) { f(input) }
    }
}
