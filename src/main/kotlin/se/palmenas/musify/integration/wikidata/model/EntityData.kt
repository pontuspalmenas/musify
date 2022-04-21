package se.palmenas.musify.integration.wikidata.model

data class EntityData(val entities: Map<String, Entity>)
data class Entity(val sitelinks: Map<String, SiteLink>)
data class SiteLink(val title: String)