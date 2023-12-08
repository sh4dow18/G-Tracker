package sh4dow18.gtracker.backend_spring

import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("\${url.users}")
class UserController(private val userService: UserService) {
    @GetMapping("{email}")
    @ResponseBody
    fun findUserById(@PathVariable email: String) = userService.findUserById(email)

    @PutMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun updateUser(@RequestPart("user") updateUserRequest: UpdateUserRequest,
                   @RequestPart("image") image: MultipartFile?): UserResponse {
        return userService.updateUser(updateUserRequest, image)
    }

    @PutMapping("{email}")
    @ResponseBody
    fun closeAccount(@PathVariable email: String) = userService.closeAccount(email)
}

@RestController
@RequestMapping("\${url.public.users}")
class PublicUserController(private val userService: UserService) {
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun userRegistration(@RequestBody userRegistrationRequest: UserRegistrationRequest): UserResponse {
        return userService.userRegistration(userRegistrationRequest)
    }
}

@RestController
@RequestMapping("\${url.public.image}")
class ImageController(
    private val userService: UserService,
    private val gameService: GameService
) {
    @GetMapping("user/{imageName}")
    @ResponseBody
    fun serveProfileImage(@PathVariable imageName: String?, response: HttpServletResponse) {
        userService.serveProfileImage(imageName, response)
    }
    @GetMapping("game/{imageName}")
    @ResponseBody
    fun serveGameImage(@PathVariable imageName: String?, response: HttpServletResponse) {
        gameService.serveGameImage(imageName, response)
    }
}

@RestController
@RequestMapping("\${url.games}")
class GameController(private val gameService: GameService) {
    @GetMapping
    @ResponseBody
    fun findAllGames() = gameService.findAllGames()

    @GetMapping("{id}")
    @ResponseBody
    fun findGameById(@PathVariable("id") id: Long) = gameService.findGameById(id)

    @GetMapping("first20")
    @ResponseBody
    fun findFirst20ByOrderByRatingDesc() = gameService.findFirst20ByOrderByRatingDesc()

    @GetMapping("search/{name}")
    @ResponseBody
    fun findByNameContainingIgnoreCase(@PathVariable("name") name: String) = gameService.findByNameContainingIgnoreCase(name)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun gamesRegistration(@RequestBody gameRegistrationRequestsList: List<GameRegistrationRequest>): List<GameResponse> {
        return gameService.gamesRegistration(gameRegistrationRequestsList)
    }

    @PostMapping("one", consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun gameRegistration(@RequestBody gameRegistrationRequest: GameRegistrationRequest): GameResponse {
        return gameService.gameRegistration(gameRegistrationRequest)
    }

    @PostMapping("images")
    @ResponseBody
    fun createImageFromGameUrls(): Boolean {
        return gameService.createImagesFromGamesUrls()
    }

    @PostMapping("images/one/{id}")
    @ResponseBody
    fun createImageFromGameUrl(@PathVariable("id") id: Long): Boolean {
        return gameService.createImageFromGameUrl(id)
    }
}

@RestController
@RequestMapping("\${url.platforms}")
class PlatformController(private val platformService: PlatformService) {
    @GetMapping
    @ResponseBody
    fun findAllPlatforms() = platformService.findAllPlatforms()

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun platformsRegistration(@RequestBody platformsNamesList: List<String>): List<PlatformDetails> {
        return platformService.platformsRegistration(platformsNamesList)
    }
}

@RestController
@RequestMapping("\${url.genres}")
class GenreController(private val genreService: AbstractGenreService) {
    @GetMapping
    @ResponseBody
    fun findAllGenres() = genreService.findAllGenres()

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun genresRegistration(@RequestBody genresNamesList: List<String>): List<GenreDetails> {
        return genreService.genresRegistration(genresNamesList)
    }
}

@RestController
@RequestMapping("\${url.gameLogs}")
class GameLogController(private val gameLogService: GameLogService) {
    @GetMapping
    @ResponseBody
    fun findAllGameLogs() = gameLogService.findAllGameLogs()

    @GetMapping("{id}")
    @ResponseBody
    fun findGameLogById(@PathVariable("id") id: Long) = gameLogService.findGameLogById(id)

    @GetMapping("user/{email}")
    @ResponseBody
    fun findAllByUserEmail(@PathVariable("email") email: String) = gameLogService.findAllByUserEmail(email)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun gameLogRegistration(@RequestBody gameLogRegistrationRequest: GameLogRegistrationRequest): GameLogResponse {
        return gameLogService.gameLogRegistration(gameLogRegistrationRequest)
    }

    @PutMapping("finished/{id}")
    @ResponseBody
    fun gameLogUpdateFinished(@PathVariable("id") id: Long): GameLogResponse {
        return gameLogService.gameLogUpdateFinished(id)
    }

    @PutMapping("finishedAtAll/{id}")
    @ResponseBody
    fun gameLogUpdateFinishedAtAll(@PathVariable("id") id: Long): GameLogResponse {
        return gameLogService.gameLogUpdateFinishedAtAll(id)
    }
}