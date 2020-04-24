package pl.teamkiwi.domain.`interface`

import pl.teamkiwi.domain.model.entity.Song

interface SongRepository {

    fun save(song: Song): Song

    fun deleteById(id: String)

    fun findById(id: String): Song?
}