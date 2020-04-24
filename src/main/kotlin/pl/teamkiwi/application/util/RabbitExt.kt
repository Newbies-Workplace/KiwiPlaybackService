package pl.teamkiwi.application.util

import org.koin.core.Koin
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import pl.jutupe.ktor_rabbitmq.RabbitMQ

fun RabbitMQ.getKoin(): Koin = GlobalContext.get().koin

inline fun <reified T : Any> RabbitMQ.inject(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
) =
    lazy { get<T>(qualifier, parameters) }

inline fun <reified T : Any> RabbitMQ.get(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
) =
    getKoin().get<T>(qualifier, parameters)

inline fun <reified T> RabbitMQ.getProperty(key: String) =
    getKoin().getProperty<T>(key)

inline fun <reified T> RabbitMQ.getProperty(key: String, defaultValue: T) =
    getKoin().getProperty(key) ?: defaultValue
