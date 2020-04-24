package pl.teamkiwi.domain.model.entity

data class PlaybackSession(
    val id: String,
    val lastPlayedSong: Song? = null
)