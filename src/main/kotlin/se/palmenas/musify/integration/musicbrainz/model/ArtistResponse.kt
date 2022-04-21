package se.palmenas.musify.integration.musicbrainz.model

import com.squareup.moshi.Json

data class ArtistResponse(
    val id: String,
    val gender: String?,
    val name: String,
    val country: String,
    val disambiguation: String,
    @Json(name="release-groups") val releaseGroups: List<ReleaseGroup>,
    val relations: List<Relation>
)

data class ReleaseGroup(
    val id: String,
    val title: String,
    @Json(name="primary-type") val primaryType: String
    )
data class Relation(val type: String, val url: RelationUrl)
data class RelationUrl(val id: String, val resource: String)