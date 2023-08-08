package pl.akai.fillist

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties
class FillistApplication

fun   main(args: Array<String>) {
    runApplication<FillistApplication>(*args)
}
