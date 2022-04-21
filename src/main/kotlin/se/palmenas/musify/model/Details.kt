package se.palmenas.musify.model

data class Details(
    val mbid: String,
    val name: String,
    val gender: String?,
    val country: String,
    val disambiguation: String,
    val description: String,
    val albums: List<Album>
)