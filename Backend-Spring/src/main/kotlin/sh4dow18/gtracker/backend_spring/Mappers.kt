package sh4dow18.gtracker.backend_spring

import org.mapstruct.Context
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.ReportingPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserMapper {
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(userRegistrationRequest.getPassword()))")
    @Mapping(target = "createdDate", expression = "java(java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern(\"dd/MM/yyyy\")))")
    @Mapping(target = "enabled", expression = "java(true)")
    @Mapping(target = "imagePath", expression = "java(null)")
    @Mapping(target = "role", expression = "java(mappingService.findRoleByIdToMapper(1))")
    @Mapping(target = "gamesLogsList", expression = "java(java.util.Collections.emptyList())")
    @Mapping(target = "logsList", expression = "java(java.util.Collections.emptyList())")
    fun userRegistrationRequestToUser(
        userRegistrationRequest: UserRegistrationRequest,
        @Context mappingService: MappingService,
        @Context passwordEncoder: BCryptPasswordEncoder
    ): User
    fun userToUserResponse(
        user: User
    ): UserResponse
}