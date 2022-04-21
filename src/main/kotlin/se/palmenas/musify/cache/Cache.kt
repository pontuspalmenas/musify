package se.palmenas.musify.cache

import se.palmenas.musify.model.Details

interface Cache {
    fun addDetails(details: Details)
    fun getDetails(id: String): Details?
}