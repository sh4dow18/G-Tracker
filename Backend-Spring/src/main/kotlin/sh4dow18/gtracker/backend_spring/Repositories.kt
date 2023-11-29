package sh4dow18.gtracker.backend_spring

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, String>

@Repository
interface RoleRepository: JpaRepository<Role, Long>

@Repository
interface GameRepository: JpaRepository<Game, Long> {
    fun findFirst20ByOrderByRatingDesc(): List<Game>
    fun findByNameContainingIgnoreCase(@Param("name") name: String): List<Game>
}

@Repository
interface PlatformRepository: JpaRepository<Platform, Long> {
    fun findAllByNameIn(@Param("name") platformsNamesList: List<String>): List<Platform>
}

@Repository
interface GenreRepository: JpaRepository<Genre, Long> {
    fun findAllByNameIn(@Param("name") genresNamesList: List<String>): List<Genre>
}

@Repository
interface GameLogRepository: JpaRepository<GameLog, Long> {
    fun findAllByUserEmail(@Param("email") email: String): List<GameLog>
}