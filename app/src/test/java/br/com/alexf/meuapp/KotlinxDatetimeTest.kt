package br.com.alexf.meuapp

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import org.junit.Test

class KotlinxDatetimeTest {

    @OptIn(FormatStringsInDatetimeFormats::class)
    @Test
    fun dateTimeTest(){
        val clock = Clock.System.now()
        val millis = clock.toEpochMilliseconds()
        println("millis: $millis")
        val localDateTime = clock.toLocalDateTime(TimeZone.currentSystemDefault())
        println(localDateTime.date)
        val localDateTimeFormatter = LocalDateTime.Format {
            byUnicodePattern("dd/MM/yyyy - HH:mm:ss")
        }
        val formattedLocalDate = localDateTimeFormatter.format(localDateTime)
        println(formattedLocalDate)
        val resultLocalDateTime = localDateTimeFormatter.parse("14/07/2024 - 15:57:49")
        println(resultLocalDateTime)
    }

}