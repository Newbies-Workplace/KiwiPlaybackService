package pl.teamkiwi.domain.model.entity

data class Song(
    val id: String,
    val title: String,
    val path: String,
    val imagePath: String?,
    val artistId: String
)