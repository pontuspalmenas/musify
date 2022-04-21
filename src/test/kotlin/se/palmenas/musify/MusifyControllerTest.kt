package se.palmenas.musify

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import se.palmenas.musify.model.Details
import se.palmenas.musify.service.MusifyService

@WebMvcTest
class MusifyControllerTest(@Autowired val mockMvc: MockMvc) {
    @MockBean
    lateinit var musifyService: MusifyService

    val details = Details("daeec599-bf5d-4428-994a-e76ba5f86e2f","Passion Pit", null, "US", "", "Some band", listOf())

    @Test
    fun whenGetDetails_shouldMarshalCorrectly() {
        runBlocking { given(musifyService.getDetailsById("daeec599-bf5d-4428-994a-e76ba5f86e2f")).willReturn(details) }

        mockMvc.perform(get("/musify/music-artist/details/daeec599-bf5d-4428-994a-e76ba5f86e2f"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.mbid").value("daeec599-bf5d-4428-994a-e76ba5f86e2f"))
    }
}