package sh4dow18.gtracker.backend_spring

import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun getDateAsString(date: ZonedDateTime?, hour: Boolean): String {
    return if (date != null) {
        if (hour) {
            date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("America/Costa_Rica")))
        }
        else {
            date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.of("America/Costa_Rica")))
        }
    }
    else ""
}

fun getDateStringAsDate(date: String, time: String): ZonedDateTime {
    val localDateTime = LocalDateTime.of(
        LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
        LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"))
    )
    return ZonedDateTime.of(localDateTime, ZoneId.of("America/Costa_Rica"))
}

fun restartAutoIncrement(entityManagerFactory: EntityManagerFactory, sequence: String, startAt: Int) {
    val entityManager: EntityManager = entityManagerFactory.createEntityManager()
    entityManager.transaction.begin()
    entityManager.createNativeQuery("ALTER SEQUENCE $sequence RESTART WITH $startAt").executeUpdate()
    entityManager.transaction.commit()
    entityManager.close()
}