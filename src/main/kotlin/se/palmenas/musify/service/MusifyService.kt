package se.palmenas.musify.service

import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import se.palmenas.musify.cache.Cache
import se.palmenas.musify.integration.coverart.CoverArtClient
import se.palmenas.musify.integration.musicbrainz.MusicBrainzClient
import se.palmenas.musify.integration.musicbrainz.model.ArtistResponse
import se.palmenas.musify.integration.wikidata.WikidataClient
import se.palmenas.musify.integration.wikipedia.WikipediaClient
import se.palmenas.musify.integration.wikipedia.model.WikipediaSummary
import se.palmenas.musify.model.Album
import se.palmenas.musify.model.Details

@Component
@Service
class MusifyService(
    private val cache: Cache,
    private val musicBrainzClient: MusicBrainzClient,
    private val wikidataClient: WikidataClient,
    private val wikipediaClient: WikipediaClient,
    private val coverArtClient: CoverArtClient
    ) {
    suspend fun getDetailsById(id: String): Details? {
        val cached = cache.getDetails(id)
        if (cached != null) {
            println("DEBUG ${this.javaClass.simpleName}\t: Cache hit: $id")
            return cached
        }
        println("DEBUG ${this.javaClass.simpleName}\t: Cache miss, fetching from sources: $id")

        val artist = musicBrainzClient.getArtistById(id) ?: return null

        val wikidata = artist.relations.find { it.type == "wikidata" } ?: throw RuntimeException("Missing relation: wikidata")
        val wikiEntity = wikidata.url.resource.substringAfterLast("/")

        val wikiSummary = wikipediaSummary(wikiEntity)

        val albums = mutableListOf<Album>()
        // todo: investigate why kotlin coroutines perform worse that java stream parallel here.
        artist.releaseGroups.filter { it.primaryType == "Album" }.stream().parallel().forEach {
            val image = coverArtClient.getFrontImageUrl(it.id)
            albums.add(Album(it.id, it.title, image))
        }

        val details = toDetails(artist, wikiSummary, albums)
        cache.addDetails(details)

        return details
    }

    // Extracts Wikipedia summary given a wikiEntity (Q-id) by calling Wikidata and Wikipedia APIs.
    private fun wikipediaSummary(wikiEntity: String): WikipediaSummary {
        val wikipediaTitle = wikidataClient.getWikipediaTitle(wikiEntity)
        return wikipediaClient.getWikipediaSummary(wikipediaTitle)
    }

    private fun toDetails(artist: ArtistResponse, wikipediaSummary: WikipediaSummary, albums: List<Album>): Details =
        Details(artist.id, artist.name, artist.gender ?: "n/a", artist.country, artist.disambiguation, wikipediaSummary.extract,
            albums)
}