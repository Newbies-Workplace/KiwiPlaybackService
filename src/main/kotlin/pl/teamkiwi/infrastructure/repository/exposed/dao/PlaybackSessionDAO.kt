package pl.teamkiwi.infrastructure.repository.exposed.dao

import org.jetbrains.exposed.dao.EntityID
import pl.teamkiwi.domain.model.entity.PlaybackSession
import pl.teamkiwi.infrastructure.repository.exposed.table.PlaybackSessions
import pl.teamkiwi.infrastructure.repository.exposed.table.StringIdEntity
import pl.teamkiwi.infrastructure.repository.exposed.table.StringIdEntityClass

class PlaybackSessionDAO(id: EntityID<String>) : StringIdEntity(id) {
    companion object : StringIdEntityClass<PlaybackSessionDAO>(PlaybackSessions)

    var lastPlayedSong by SongDAO optionalReferencedOn PlaybackSessions.lastPlayedSong

    fun toPlaybackSession() =
        PlaybackSession(
            id = id.value,
            lastPlayedSong = lastPlayedSong?.toSong()
        )
}