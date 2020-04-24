package pl.teamkiwi.application.router

import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.routing.Routing
import io.ktor.routing.get
import org.koin.ktor.ext.inject
import pl.teamkiwi.application.controller.PlaybackController

fun Routing.playbackRoutes() {
    val playbackController: PlaybackController by inject()

    authenticate {
        get("v1/session") {
            playbackController.getPlaybackSession(call)
        }
    }
}