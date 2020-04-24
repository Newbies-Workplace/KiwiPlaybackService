package pl.teamkiwi.application.consumer

import pl.jutupe.ktor_rabbitmq.RabbitMQ
import pl.jutupe.ktor_rabbitmq.consume
import pl.teamkiwi.application.converter.SongConverter
import pl.teamkiwi.application.model.event.SongCreatedEvent
import pl.teamkiwi.application.model.event.SongDeletedEvent
import pl.teamkiwi.application.util.KIWI_SONG_CREATED_QUEUE
import pl.teamkiwi.application.util.KIWI_SONG_DELETED_QUEUE
import pl.teamkiwi.application.util.inject
import pl.teamkiwi.domain.`interface`.SongRepository

fun RabbitMQ.songConsumer() {
    val songRepository by inject<SongRepository>()
    val songConverter by inject<SongConverter>()

    consume<SongCreatedEvent>(KIWI_SONG_CREATED_QUEUE) { consumerTag, body ->
        println("Consumed message $body, consumer tag: $consumerTag")

        val song = with(songConverter) { body.song.toSong() }
        songRepository.save(song)
    }

    consume<SongDeletedEvent>(KIWI_SONG_DELETED_QUEUE) { consumerTag, body ->
        println("Consumed message $body, consumer tag: $consumerTag")

        songRepository.deleteById(body.id)
    }
}