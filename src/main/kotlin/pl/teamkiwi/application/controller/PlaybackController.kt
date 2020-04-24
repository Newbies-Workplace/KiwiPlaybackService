package pl.teamkiwi.application.controller

import io.ktor.application.ApplicationCall
import io.ktor.response.respond
import pl.teamkiwi.domain.service.PlaybackSessionService
import pl.teamkiwi.kiwi_ktor_authentication.util.authPrincipal

class PlaybackController(
    private val playbackSessionService: PlaybackSessionService
) {

    suspend fun getPlaybackSession(call: ApplicationCall) {
        val userId = call.authPrincipal().userId

        val session = playbackSessionService.getPlaybackSession(userId)

        call.respond(session)

        //post test message
        //call.publish(KIWI_EXCHANGE, KIWI_KEY, null, TestObject("test name"))
    }
}