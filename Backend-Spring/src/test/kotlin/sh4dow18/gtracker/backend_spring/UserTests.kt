package sh4dow18.gtracker.backend_spring

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class UserTests(
    @Autowired
    val userRepository: UserRepository,
    @Autowired
    val userMapper: UserMapper,
    @Autowired
    val mappingService: MappingService
) {
    @Test
    fun findUserById() {
        val user: User? = userRepository.findById("ramses.solano.arias@gmail.com").orElse(null)
        Assertions.assertNotNull(user)
    }

    @Test
    @Transactional
    fun userRegistration() {
        val userRegistrationRequest = UserRegistrationRequest("example@example.com", "sh4dow18", "rootroot123")
        val newUser = userMapper.userRegistrationRequestToUser(userRegistrationRequest, mappingService, passwordEncoder())
        val newResponse = userMapper.userToUserResponse(newUser)
        Assertions.assertNotNull(newResponse)
    }
}