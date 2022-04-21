package se.palmenas.musify.integration.wikipedia

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import se.palmenas.musify.integration.wikipedia.model.WikipediaSummary
import java.io.IOException

class WikipediaClient {
    private val baseUrl = "https://en.wikipedia.org/api/rest_v1/page/summary/"
    private val client = OkHttpClient()
    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    private val jsonAdapter: JsonAdapter<WikipediaSummary> = moshi.adapter(WikipediaSummary::class.java)

    fun getWikipediaSummary(title: String): WikipediaSummary {
        val request = Request.Builder().url("$baseUrl$title")
            .addHeader("user-agent", "musify/0.0.1 (pontus@palmenas.se)").build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            return jsonAdapter.fromJson(response.body!!.source())
                ?: throw RuntimeException("Failed parsing json of request ${request.url}")
        }
    }
}