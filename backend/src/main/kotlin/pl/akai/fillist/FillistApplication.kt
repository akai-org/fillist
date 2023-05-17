package pl.akai.fillist

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FillistApplication

fun main(args: Array<String>) {
	runApplication<FillistApplication>(*args)
}
