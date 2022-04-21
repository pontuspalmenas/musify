package se.palmenas.musify.integration.wikidata

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import se.palmenas.musify.integration.wikidata.model.EntityData
import java.io.IOException

class WikidataClient {
    private val baseUrl = "https://www.wikidata.org/wiki/Special:EntityData/"
    private val client = OkHttpClient()
    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    private val jsonAdapter: JsonAdapter<EntityData> = moshi.adapter(EntityData::class.java)

    fun getWikipediaTitle(id: String): String {
        val request = Request.Builder().url("$baseUrl$id.json")
            .addHeader("user-agent", "musify/0.0.1 (pontus@palmenas.se)").build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val entityData = jsonAdapter.fromJson(response.body!!.source()) ?:
                throw RuntimeException("Failed parsing json of request ${request.url}")

            return entityData.entities[id]!!.sitelinks["enwiki"]!!.title
        }
    }
}