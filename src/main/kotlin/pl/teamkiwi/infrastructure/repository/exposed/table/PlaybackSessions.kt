package pl.teamkiwi.infrastructure.repository.exposed.table

import org.jetbrains.exposed.sql.ReferenceOption

object PlaybackSessions : StringIdTable() {
    val lastPlayedSong = optReference("lastPlayedSong", Songs, ReferenceOption.SET_NULL)
}