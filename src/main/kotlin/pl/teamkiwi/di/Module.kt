package pl.teamkiwi.di

import com.typesafe.config.ConfigFactory
import io.ktor.config.ApplicationConfig
import io.ktor.config.HoconApplicationConfig
import org.koin.dsl.module
import pl.jutupe.DatabaseConfiguration
import pl.teamkiwi.application.controller.PlaybackController
import pl.teamkiwi.application.converter.SongConverter
import pl.teamkiwi.application.util.getProp
import pl.teamkiwi.domain.`interface`.PlaybackSessionRepository
import pl.teamkiwi.domain.`interface`.SongRepository
import pl.teamkiwi.domain.service.PlaybackSessionService
import pl.teamkiwi.infrastructure.repository.exposed.PlaybackSessionExposedRepository
import pl.teamkiwi.infrastructure.repository.exposed.SongExposedRepository

val module = module {
    @Suppress("EXPERIMENTAL_API_USAGE")
    single { HoconApplicationConfig(ConfigFactory.load()) as ApplicationConfig }

    single { PlaybackController(get()) }
    single { PlaybackSessionService(get(), get()) }
    single { PlaybackSessionExposedRepository() as PlaybackSessionRepository }

    single { SongConverter() }
    single { SongExposedRepository() as SongRepository }

    single { DatabaseConfiguration(
        url = getProp("kiwi.database.url"),
        driver = getProp("kiwi.database.driver"),
        user = getProp("kiwi.database.user"),
        password = getProp("kiwi.database.password"))
    }
}