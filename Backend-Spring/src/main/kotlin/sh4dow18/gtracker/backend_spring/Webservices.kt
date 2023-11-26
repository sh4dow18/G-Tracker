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
class ImageController(private val userService: UserService) {
    @GetMapping("user/{imageName}")
    @ResponseBody
    fun serveProfileImage(@PathVariable imageName: String?, response: HttpServletResponse) {
        userService.serveProfileImage(imageName, response)
    }
}