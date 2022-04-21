package se.palmenas.musify.cache

import se.palmenas.musify.model.Details

// Primitive in-memory cache for testing. No eviction policy, just fills up the memory.
// Use a shared cache (redis, memcached) for production.
class InMemoryCache : Cache {
    private val cache = mutableMapOf<String, Details>()

    override fun addDetails(details: Details) {
        cache[details.mbid] = details
    }

    override fun getDetails(id: String): Details? = cache[id]
}