package sh4dow18.gtracker.backend_spring

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${url.users}")
class UserController(private val userService: UserService) {
    @GetMapping("{email}")
    @ResponseBody
    fun findUserById(@PathVariable email: String) = userService.findUserById(email)
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