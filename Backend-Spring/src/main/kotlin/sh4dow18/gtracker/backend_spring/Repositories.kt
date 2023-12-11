package sh4dow18.gtracker.backend_spring

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository: JpaRepository<User, String> {
    fun findByUserName(@Param("userName") userName: String): Optional<User>
}

@Repository
interface RoleRepository: JpaRepository<Role, Long>

@Repository
interface GameRepository: JpaRepository<Game, Long> {
    fun findFirst10ByOrderByMetacriticDesc(): List<Game>
    fun findTop10ByNameContainingIgnoreCase(@Param("name") name: String): List<Game>
    fun findBySlug(@Param("slug") slug: String): Optional<Game>
}

@Repository
interface PlatformRepository: JpaRepository<Platform, Long> {
    fun findAllByNameIn(@Param("name") platformsNamesList: List<String>): List<Platform>
    fun findByName(@Param("name") name: String): Optional<Platform>
}

@Repository
interface GenreRepository: JpaRepository<Genre, Long> {
    fun findAllByNameIn(@Param("name") genresNamesList: List<String>): List<Genre>
    fun findByName(@Param("name") name: String): Optional<Genre>
}

@Repository
interface GameLogRepository: JpaRepository<GameLog, Long> {
    fun findFirst5ByUserEmailOrderByCreatedDateDesc(@Param("email") email: String): List<GameLog>
    fun findByGameAndUser(@Param("game") game: Game, @Param("user") user: User): Optional<GameLog>
    fun findTop5ByUserEmailAndGameNameContainingIgnoreCase(@Param("email") email: String, @Param("name") name: String): List<GameLog>
}