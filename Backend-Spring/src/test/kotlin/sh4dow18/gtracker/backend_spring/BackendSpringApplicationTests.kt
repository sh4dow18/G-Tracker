package sh4dow18.gtracker.backend_spring

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql

@SpringBootTest
@Sql("/insert-needed.sql")
class BackendSpringApplicationTests {

	@Test
	fun contextLoads() {
	}

}
