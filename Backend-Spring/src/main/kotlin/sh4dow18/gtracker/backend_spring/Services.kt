package sh4dow18.gtracker.backend_spring

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Bean
fun passwordEncoder(): BCryptPasswordEncoder {
    return BCryptPasswordEncoder()
}

interface UserService {
    fun findUserById(email: String): UserResponse
    fun userRegistration(userRegistrationRequest: UserRegistrationRequest): UserResponse
}
@Service
class AbstractUserService(
    @Autowired
    val userRepository: UserRepository,
    @Autowired
    val userMapper: UserMapper,
    @Autowired
    val mappingService: MappingService
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