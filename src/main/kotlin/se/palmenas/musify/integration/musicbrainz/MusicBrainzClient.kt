package se.palmenas.musify.integration.musicbrainz

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import se.palmenas.musify.integration.musicbrainz.model.ArtistResponse
import java.io.IOException

class MusicBrainzClient {
    private val baseUrl = "http://musicbrainz.org/ws/2/artist/"
    private val client = OkHttpClient()
    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    private val jsonAdapter: JsonAdapter<ArtistResponse> = moshi.adapter(ArtistResponse::class.java)

    fun getArtistById(id: String): ArtistResponse? {
        val request = Request.Builder().url("$baseUrl$id/?fmt=json&inc=url-rels+release-groups")
            .addHeader("user-agent", "musify/0.0.1 (pontus@palmenas.se)").build()

        client.newCall(request).execute().use { response ->
            if (response.code == 404) return null
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            return jsonAdapter.fromJson(response.body!!.source()) ?:
                throw RuntimeException("Failed parsing json of request ${request.url}")
        }
    }
}
