package mbraun.unsplasy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UnsplasyApplication

fun main(args: Array<String>) {
    runApplication<UnsplasyApplication>(*args)
}
