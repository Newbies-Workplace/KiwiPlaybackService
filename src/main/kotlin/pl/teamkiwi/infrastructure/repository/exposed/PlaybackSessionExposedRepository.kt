package pl.teamkiwi.infrastructure.repository.exposed

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import pl.teamkiwi.domain.`interface`.PlaybackSessionRepository
import pl.teamkiwi.domain.model.entity.PlaybackSession
import pl.teamkiwi.infrastructure.repository.exposed.dao.PlaybackSessionDAO
import pl.teamkiwi.infrastructure.repository.exposed.dao.SongDAO
import pl.teamkiwi.infrastructure.repository.exposed.table.Songs

class PlaybackSessionExposedRepository : PlaybackSessionRepository {

    override fun save(session: PlaybackSession): PlaybackSession =
        transaction {
            PlaybackSessionDAO.new(session.id) {
                lastPlayedSong = SongDAO(EntityID(session.lastPlayedSong?.id, Songs))
            }.toPlaybackSession()
        }

    override fun findById(id: String): PlaybackSession? =
        transaction {
            PlaybackSessionDAO.findById(id)?.toPlaybackSession()
        }
}