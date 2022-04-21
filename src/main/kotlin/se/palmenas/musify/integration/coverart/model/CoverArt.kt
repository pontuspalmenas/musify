package se.palmenas.musify.integration.coverart.model

data class CoverArt(val images: List<Image>)
data class Image(val front: Boolean, val image: String)