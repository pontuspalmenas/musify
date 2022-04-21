package se.palmenas.musify.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import se.palmenas.musify.cache.Cache
import se.palmenas.musify.cache.InMemoryCache
import se.palmenas.musify.integration.coverart.CoverArtClient
import se.palmenas.musify.integration.musicbrainz.MusicBrainzClient
import se.palmenas.musify.integration.wikidata.WikidataClient
import se.palmenas.musify.integration.wikipedia.WikipediaClient
import se.palmenas.musify.service.MusifyService

@Configuration
class MusifyConfiguration {
    @Bean
    fun cache(): Cache = InMemoryCache()

    @Bean
    fun musicBrainzClient(): MusicBrainzClient = MusicBrainzClient()

    @Bean
    fun wikidataClient(): WikidataClient = WikidataClient()

    @Bean
    fun wikipediaClient(): WikipediaClient = WikipediaClient()

    @Bean
    fun coverArtClient(): CoverArtClient = CoverArtClient()
}