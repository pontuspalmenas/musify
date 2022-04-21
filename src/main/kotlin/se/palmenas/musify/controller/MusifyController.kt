package se.palmenas.musify.controller

import kotlinx.coroutines.runBlocking
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import se.palmenas.musify.model.Details
import se.palmenas.musify.service.MusifyService

@RestController
@RequestMapping("/musify")
class MusifyController(private val musifyService: MusifyService) {
    @GetMapping("/music-artist/details/{id}")
    fun getDetailsById(@PathVariable id: String): Details? {
        return runBlocking { musifyService.getDetailsById(id) }
    }
}

