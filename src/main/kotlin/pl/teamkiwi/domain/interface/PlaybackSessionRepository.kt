package pl.teamkiwi.domain.`interface`

import pl.teamkiwi.domain.model.entity.PlaybackSession

interface PlaybackSessionRepository {

    fun save(session: PlaybackSession): PlaybackSession

    fun findById(id: String): PlaybackSession?
}