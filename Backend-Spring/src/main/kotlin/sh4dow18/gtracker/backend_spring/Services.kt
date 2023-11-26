package sh4dow18.gtracker.backend_spring

import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.FileCopyUtils
import org.springframework.web.client.HttpClientErrorException.BadRequest
import org.springframework.web.multipart.MultipartFile
import java.awt.geom.Ellipse2D
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.nio.file.Paths
import javax.imageio.ImageIO

@Bean
fun passwordEncoder(): BCryptPasswordEncoder {
    return BCryptPasswordEncoder()
}

interface UserService {
    fun findUserById(email: String): UserResponse
    fun userRegistration(userRegistrationRequest: UserRegistrationRequest): UserResponse
    fun updateUser(updateUserRequest: UpdateUserRequest, image: MultipartFile?): UserResponse
    fun closeAccount(email: String): UserResponse
    fun serveProfileImage(imageName: String?, response: HttpServletResponse)

}
@Service
class AbstractUserService(
    @Autowired
    val userRepository: UserRepository,
    @Autowired
    val userMapper: UserMapper,
    @Autowired
    val mappingService: MappingService,
    @Value("\${upload.path}")
    val uploadPath: String
): UserService {
    @Throws(NoSuchElementException::class)
    override fun findUserById(email: String): UserResponse {
        val user: User = userRepository.findById(email).orElse(null)
            ?: throw NoSuchElementException(String.format("The User with the email %s does not exists", email))
        return userMapper.userToUserResponse(user)
    }
    @Throws(ElementAlreadyExists::class)
    override fun userRegistration(userRegistrationRequest: UserRegistrationRequest): UserResponse {
        val user: User? = userRepository.findById(userRegistrationRequest.email).orElse(null)
        if (user != null) {
            throw ElementAlreadyExists(String.format("The User with the email %s already exists", userRegistrationRequest.email))
        }
        val newUser = userMapper.userRegistrationRequestToUser(userRegistrationRequest, mappingService, passwordEncoder())
        return userMapper.userToUserResponse(userRepository.save(newUser))
    }
    @Throws(NoSuchElementException::class, BadRequestException::class)
    override fun updateUser(updateUserRequest: UpdateUserRequest, image: MultipartFile?): UserResponse {
        val user: User = userRepository.findById(updateUserRequest.email).orElse(null)
            ?: throw NoSuchElementException(String.format("The User with the email %s does not exists", updateUserRequest.email))
        userMapper.updateUser(updateUserRequest, user)
        if (image != null) {
            // Verifies if the image file is really an Image
            if (ImageIO.read(image.inputStream) == null) {
                throw BadRequestException("Image file type not supported")
            }
            // Create a New Image 200 x 200 from the original image
            val originalImage = ImageIO.read(image.inputStream)
            val targetWidth = 150
            val targetHeight = 150
            val xScale = targetWidth.toDouble() / originalImage.width
            val yScale = targetHeight.toDouble() / originalImage.height
            val scale = if (xScale > yScale) xScale else yScale
            val newWidth = (originalImage.width * scale).toInt()
            val newHeight = (originalImage.height * scale).toInt()
            val resizedImage = BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB)
            val graphics = resizedImage.createGraphics()
            graphics.clip = Ellipse2D.Float(0f, 0f, targetWidth.toFloat(), targetHeight.toFloat())
            graphics.drawImage(originalImage, (targetWidth - newWidth) / 2, (targetHeight - newHeight) / 2, newWidth, newHeight, null)
            graphics.dispose()
            // Add the "User" email plus ".jpg" has the image path
            val uniqueFileName = updateUserRequest.email + ".png"
            // Renders the image as "jpg" and save it in the path "uploadPath"
            ImageIO.write(resizedImage, "png", File("$uploadPath/users/$uniqueFileName"))
            // Update the imagePath
            user.imagePath = uniqueFileName
        }
        return userMapper.userToUserResponse(userRepository.save(user))
    }
    @Throws(NoSuchElementException::class)
    override fun closeAccount(email: String): UserResponse {
        val user: User = userRepository.findById(email).orElse(null)
            ?: throw NoSuchElementException(String.format("The User with the email %s does not exists", email))
        user.password = null
        user.enabled = false
        return userMapper.userToUserResponse(userRepository.save(user))
    }
    @Throws(IOException::class, NoSuchElementException::class)
    override fun serveProfileImage(imageName: String?, response: HttpServletResponse) {
        val imagePath = Paths.get("$uploadPath/users/", imageName).toAbsolutePath().toString()
        response.contentType = "image/png"
        FileInputStream(imagePath).use { imageStream -> FileCopyUtils.copy(imageStream, response.outputStream) }
    }
}

interface MappingService {
    fun findRoleByIdToMapper(id: Long): Role
}
@Service
class AbstractRoleService(
    @Autowired
    val roleRepository: RoleRepository
) : MappingService {
    override fun findRoleByIdToMapper(id: Long): Role {
        return roleRepository.findById(id).orElse(null)
    }
}

// Tag that establishes that this class is a Spring Service
@Service
// Tag that establishes that is a Transactional Service. This one makes a transaction when
// this service is in operation.
@Transactional
// AppUserDetailsService Service Class
class AppUserDetailsService(
    // Variables Declaration to use in functions
    @Autowired
    val userRepository: UserRepository,
) : UserDetailsService {
    // Tag that allows to throw a Username Not Found Exception
    @Throws(UsernameNotFoundException::class)
    // Function that is used to the user details during the authentication
    override fun loadUserByUsername(email: String): UserDetails {
        // Check if an "AccessPageCategory" with the given "id" already exists, if not exists,
        // returns a Spring Security User
        val user: User = userRepository.findById(email).orElse(null)
            ?: return org.springframework.security.core.userdetails.User(
                "", "", true, true, true, true,
                emptyList()
            )
        // Returns a Spring Security "User" with the "User" information found
        return org.springframework.security.core.userdetails.User(
            user.email, user.password, user.enabled, true, true,
            true, emptyList()
        )
    }
}