package org.example.cache

class SimpleCache<T, D>(
    private val initialCapacity: Int = 16,
    private val loadFactor: Float = .75f,
    private val accessOrder: Boolean = true,
    private val cacheSize: Int = 20,
) : LinkedHashMap<T, D>(initialCapacity, loadFactor, accessOrder) {
    override fun removeEldestEntry(eldest: MutableMap.MutableEntry<T, D>?): Boolean {
        return size > cacheSize
    }
}