package sh4dow18.gtracker.backend_spring

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "users")
data class User(
    @Id
    var email: String,
    var userName: String,
    var password: String?,
    var createdDate: String,
    var enabled: Boolean,
    var imagePath: String?,
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false, referencedColumnName = "id")
    var role: Role,
    @OneToMany(mappedBy = "user")
    var gamesLogsList: List<GameLog>,
    @OneToMany(mappedBy = "user")
    var logsList: List<Log>
)

@Entity
@Table(name = "roles")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var name: String,
    @OneToMany(mappedBy = "role")
    var usersList: List<User>,
    @ManyToMany(mappedBy = "rolesList")
    var pagesList: List<Page>
)

@Entity
@Table(name = "pages")
data class Page(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var name: String,
    @ManyToMany
    @JoinTable(
        name = "role_page",
        joinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "page_id", referencedColumnName = "id")]
    )
    var rolesList: List<Role>
)

@Entity
@Table(name = "game_log")
data class GameLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var createdDate: String,
    var finished: Boolean,
    var finishedAtAll: Boolean,
    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false, referencedColumnName = "id")
    var game: Game,
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "email")
    var user: User
)

@Entity
@Table(name = "game")
data class Game(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var name: String,
    var slug: String,
    var rating: Int,
    var releaseDate: String,
    @OneToMany(mappedBy = "game")
    var gamesLogsList: List<GameLog>,
    @ElementCollection
    var platformGame: Set<Long>,
    @ElementCollection
    var genderGame: Set<Long>
)

@Entity
@Table(name = "platforms")
data class Platform(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var name: String,
)

@Entity
@Table(name = "logs")
data class Log(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var action: String,
    @ManyToOne
    @JoinColumn(name = "action_type_id", nullable = false, referencedColumnName = "id")
    var actionType: ActionType,
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "email")
    var user: User
)

@Entity
@Table(name = "action_types")
data class ActionType(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var name: String,
    @OneToMany(mappedBy = "actionType")
    var logsList: List<Log>
)

@Entity
@Table(name = "genders")
data class Genre(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var name: String,
)