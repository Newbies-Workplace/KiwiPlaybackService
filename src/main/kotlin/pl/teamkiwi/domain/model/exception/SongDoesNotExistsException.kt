package pl.teamkiwi.domain.model.exception

class SongDoesNotExistsException : Exception {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}