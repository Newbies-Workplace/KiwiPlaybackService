package pl.teamkiwi

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.features.CORS
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.routing.routing
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.get
import org.slf4j.event.Level
import pl.jutupe.Exposed
import pl.jutupe.ktor_rabbitmq.RabbitMQ
import pl.jutupe.ktor_rabbitmq.rabbitConsumer
import pl.teamkiwi.application.consumer.songConsumer
import pl.teamkiwi.application.router.playbackRoutes
import pl.teamkiwi.application.util.*
import pl.teamkiwi.di.module
import pl.teamkiwi.infrastructure.repository.exposed.table.Songs
import pl.teamkiwi.kiwi_ktor_authentication.exception.KiwiUnauthorizedException
import pl.teamkiwi.kiwi_ktor_authentication.kiwiServer

fun main(args: Array<String>) {
    embeddedServer(
        Netty,
        commandLineEnvironment(args)
    ).start()
}

@Suppress("unused") // Referenced in application.conf
fun Application.mainModule() {
    install(Koin) {
        modules(
            listOf(module)
        )
    }

    install(CallLogging) {
        level = Level.DEBUG
    }

    install(Exposed) {
        connectWithConfig(get())

        createSchemas(
            Songs
        )
    }

    install(CORS) {
        anyHost()

        method(HttpMethod.Delete)
        method(HttpMethod.Put)
    }

    install(RabbitMQ) {
        uri = getProp("kiwi.rabbit.url")
        connectionName = "Kiwi Playback Service Connection"

        serialize { jacksonObjectMapper().writeValueAsBytes(it) }
        deserialize { bytes, type -> jacksonObjectMapper().readValue(bytes, type.javaObjectType) }

        initialize {
            exchangeDeclare(KIWI_LIBRARY_EXCHANGE,"direct", true)
            queueDeclare(KIWI_SONG_CREATED_QUEUE, true, false, false, emptyMap())
            queueDeclare(KIWI_SONG_DELETED_QUEUE, true, false, false, emptyMap())
            queueBind(KIWI_SONG_CREATED_QUEUE, KIWI_LIBRARY_EXCHANGE, KIWI_SONG_CREATED_KEY)
            queueBind(KIWI_SONG_DELETED_QUEUE, KIWI_LIBRARY_EXCHANGE, KIWI_SONG_DELETED_KEY)
        }
    }

    install(Authentication) {
        kiwiServer {
            url = getProp("kiwi.auth.url")

            deserialize { string, type -> jacksonObjectMapper().readValue(string, type.javaObjectType) }
        }
    }

    install(ContentNegotiation) {
        jackson {}
    }

    install(StatusPages) {
        exception<KiwiUnauthorizedException>(HttpStatusCode.Unauthorized)
    }

    routing {
        playbackRoutes()
    }

    rabbitConsumer {
        songConsumer()
    }
}
