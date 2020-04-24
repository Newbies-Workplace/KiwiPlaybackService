package pl.teamkiwi.application.converter

import pl.teamkiwi.application.model.event.payload.SongPayload
import pl.teamkiwi.domain.model.entity.Song

class SongConverter {

    fun SongPayload.toSong(): Song =
        Song(
            id = id,
            title = title,
            path = path,
            imagePath = imagePath,
            artistId = artistId
        )
}