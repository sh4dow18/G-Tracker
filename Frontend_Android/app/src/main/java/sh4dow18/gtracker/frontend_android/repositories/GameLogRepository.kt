package sh4dow18.gtracker.frontend_android.repositories

import sh4dow18.gtracker.frontend_android.services.GameLogService
import sh4dow18.gtracker.frontend_android.utils.GameLogRegistrationRequest

class GameLogRepository(
    private val gameLogService: GameLogService
) {
    suspend fun findFirst5ByUserEmail(email: String) = gameLogService.findFirst5ByUserEmail(email)

    suspend fun findGameLogById(id: Long) = gameLogService.findGameLogById(id)

    suspend fun findTop5ByUserEmailAndGameName(userEmail: String, gameName: String)
        = gameLogService.findTop5ByUserEmailAndGameName(userEmail, gameName)

    suspend fun gameLogRegistration(gameLogRegistrationRequest: GameLogRegistrationRequest) =
        gameLogService.gameLogRegistration(gameLogRegistrationRequest)

    suspend fun gameLogUpdateFinished(id: Long) = gameLogService.gameLogUpdateFinished(id)

    suspend fun gameLogUpdateFinishedAtAll(id: Long) = gameLogService.gameLogUpdateFinishedAtAll(id)
}