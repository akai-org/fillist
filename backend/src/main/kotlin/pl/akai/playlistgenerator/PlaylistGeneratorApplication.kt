package pl.akai.playlistgenerator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PlaylistGeneratorApplication

fun main(args: Array<String>) {
    runApplication<PlaylistGeneratorApplication>(*args)
}
