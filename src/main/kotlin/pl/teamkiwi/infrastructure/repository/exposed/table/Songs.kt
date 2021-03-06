package pl.teamkiwi.infrastructure.repository.exposed.table

import pl.teamkiwi.infrastructure.repository.exposed.constants.Constants.DEFAULT_CHARSET
import pl.teamkiwi.infrastructure.repository.exposed.constants.IMAGE_PATH_MAX_LENGTH
import pl.teamkiwi.infrastructure.repository.exposed.constants.PATH_MAX_LENGTH
import pl.teamkiwi.infrastructure.repository.exposed.constants.TITLE_MAX_LENGTH

/**
 * Database table
 */
object Songs : StringIdTable() {
    val title = varchar("title", TITLE_MAX_LENGTH, DEFAULT_CHARSET)
    val path = varchar("path", PATH_MAX_LENGTH, DEFAULT_CHARSET)
    val imagePath = varchar("imagePath", IMAGE_PATH_MAX_LENGTH, DEFAULT_CHARSET).nullable()
    val artistId = varchar("artistId", STRING_UUID_LENGTH, DEFAULT_CHARSET)
}