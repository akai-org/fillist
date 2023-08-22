package pl.akai.fillist

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class TestController {
    @GetMapping("/test")
    fun test(): Mono<String> = Mono.just("test")
}
