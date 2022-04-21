package se.palmenas.musify.integration.coverart

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Component
import se.palmenas.musify.integration.coverart.model.CoverArt
import java.io.IOException

class CoverArtClient {
    private val baseUrl = "http://coverartarchive.org/release-group/"
    private val client = OkHttpClient()
    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    private val jsonAdapter: JsonAdapter<CoverArt> = moshi.adapter(CoverArt::class.java)

    fun getFrontImageUrl(id: String): String {
        val request = Request.Builder().url("$baseUrl/$id")
            .addHeader("user-agent", "musify/0.0.1 (pontus@palmenas.se)").build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val entityData = jsonAdapter.fromJson(response.body!!.source()) ?:
            throw RuntimeException("Failed parsing json of request ${request.url}")

            // Prefer front image if one exists, else take the first
            return entityData.images.find { it.front }?.image ?: entityData.images.first().image
        }
    }
}