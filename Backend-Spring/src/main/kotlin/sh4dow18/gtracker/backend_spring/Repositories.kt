package sh4dow18.gtracker.backend_spring

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, String>

@Repository
interface RoleRepository: JpaRepository<Role, Long>