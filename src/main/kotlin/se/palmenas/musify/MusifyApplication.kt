package se.palmenas.musify

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MusifyApplication

fun main() {
	runApplication<MusifyApplication> {
		setBannerMode(Banner.Mode.OFF)
	}
}
