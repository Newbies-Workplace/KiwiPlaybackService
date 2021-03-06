package pl.teamkiwi.infrastructure.repository.exposed.dao

import org.jetbrains.exposed.dao.EntityID
import pl.teamkiwi.domain.model.entity.Song
import pl.teamkiwi.infrastructure.repository.exposed.table.Songs
import pl.teamkiwi.infrastructure.repository.exposed.table.StringIdEntity
import pl.teamkiwi.infrastructure.repository.exposed.table.StringIdEntityClass

class SongDAO(id: EntityID<String>) : StringIdEntity(id) {
    companion object : StringIdEntityClass<SongDAO>(Songs)

    var title by Songs.title
    var path by Songs.path
    var imagePath by Songs.imagePath
    var artistId by Songs.artistId

    fun toSong() =
        Song(
            id = id.value,
            title = title,
            path = path,
            imagePath = imagePath,
            artistId = artistId
        )
}