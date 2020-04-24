package pl.teamkiwi.domain.service

import pl.teamkiwi.domain.`interface`.PlaybackSessionRepository
import pl.teamkiwi.domain.`interface`.SongRepository
import pl.teamkiwi.domain.model.entity.PlaybackSession
import pl.teamkiwi.domain.model.exception.SongDoesNotExistsException

class PlaybackSessionService(
    private val playbackSessionRepository: PlaybackSessionRepository,
    private val songRepository: SongRepository
) {

    fun updateLastPlayedSong(userId: String, songId: String): PlaybackSession {
        val session = getPlaybackSession(userId)

        val song = songRepository.findById(songId)
            ?: throw SongDoesNotExistsException("Song with given id: $songId does not exists.")

        val modifiedSession = session.copy(lastPlayedSong = song)

        return playbackSessionRepository.save(modifiedSession)
    }

    fun getPlaybackSession(userId: String): PlaybackSession =
        playbackSessionRepository.findById(userId) ?: createPlaybackSession(userId)

    private fun createPlaybackSession(userId: String): PlaybackSession {
        val session = PlaybackSession(
            id = userId
        )

        return playbackSessionRepository.save(session)
    }
}