package pl.teamkiwi.domain.service

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import pl.teamkiwi.domain.`interface`.PlaybackSessionRepository
import pl.teamkiwi.domain.`interface`.SongRepository
import pl.teamkiwi.domain.model.entity.PlaybackSession
import pl.teamkiwi.domain.model.entity.Song
import pl.teamkiwi.domain.model.exception.SongDoesNotExistsException

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class PlaybackSessionServiceTest {

    private val playbackSessionRepository = mockk<PlaybackSessionRepository>()
    private val songRepository = mockk<SongRepository>()
    private val playbackSessionService = PlaybackSessionService(playbackSessionRepository, songRepository)

    @Nested
    inner class UpdateLastPlayedSong {

        @Test
        fun `should throw SongDoesNotExist given invalid song id`() {
            //given
            val userId = "userId"
            val invalidSongId = "invalidSongId"

            every { playbackSessionService.getPlaybackSession(userId) } returns mockk()
            every { songRepository.findById(invalidSongId) } returns null

            //when
            assertThrows<SongDoesNotExistsException> {
                playbackSessionService.updateLastPlayedSong(userId, invalidSongId)
            }
        }

        @Test
        fun `should save updated session song`() {
            //given
            val userId = "userId"
            val songId = "songId"
            val fetchedSong = createTestSong(id = songId)
            val fetchedSession = createTestPlaybackSession(userId = userId)

            every { playbackSessionRepository.findById(userId) } returns fetchedSession
            every { songRepository.findById(songId) } returns fetchedSong
            every { playbackSessionRepository.save(any()) } returnsArgument 0

            //when
            val updatedSession = playbackSessionService.updateLastPlayedSong(userId, songId)

            //then
            assertEquals(fetchedSong, updatedSession.lastPlayedSong)
        }
    }

    @Nested
    inner class GetPlaybackSession {

        @Test
        fun `should return fetched session when exists`() {
            //given
            val userId = "userId"
            val fetchedSession = mockk<PlaybackSession>()
            every { playbackSessionRepository.findById(userId) } returns fetchedSession

            //when
            val session = playbackSessionService.getPlaybackSession(userId)

            //then
            assertEquals(fetchedSession, session)
        }

        @Test
        fun `should create new session if none exists with given user id`() {
            //given
            val userId = "userId"
            val createdSession = mockk<PlaybackSession>()

            every { playbackSessionRepository.save(any()) } returns createdSession
            every { playbackSessionRepository.findById(userId) } returns null

            //when
            val session = playbackSessionService.getPlaybackSession(userId)

            //then
            assertEquals(createdSession, session)
        }
    }
}

fun createTestPlaybackSession(
    userId: String = "userId",
    lastPlayedSong: Song? = null
) =
    PlaybackSession(
        id = userId,
        lastPlayedSong = lastPlayedSong
    )
fun createTestSong(
    id: String = "songId",
    title: String = "SongTitle",
    imagePath: String? = null,
    artistId: String = "artistRandomId",
    path: String = "songPath"
) =
    Song(
        id = id,
        title = title,
        imagePath = imagePath,
        artistId = artistId,
        path = path
    )
