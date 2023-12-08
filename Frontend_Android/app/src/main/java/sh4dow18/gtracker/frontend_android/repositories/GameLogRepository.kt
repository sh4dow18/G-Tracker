package sh4dow18.gtracker.frontend_android.repositories

import sh4dow18.gtracker.frontend_android.services.GameLogService
import sh4dow18.gtracker.frontend_android.utils.GameLogRegistrationRequest
import sh4dow18.gtracker.frontend_android.utils.GameLogUpdateRequest

class GameLogRepository(
    private val gameLogService: GameLogService
) {
    suspend fun findAllByUserEmail(email: String) = gameLogService.findAllByUserEmail(email)

    suspend fun findGameLogById(id: Long) = gameLogService.findGameLogById(id)

    suspend fun gameLogRegistration(gameLogRegistrationRequest: GameLogRegistrationRequest) =
        gameLogService.gameLogRegistration(gameLogRegistrationRequest)

    suspend fun gameLogUpdateFinished(id: Long) = gameLogService.gameLogUpdateFinished(id)

    suspend fun gameLogUpdateFinishedAtAll(id: Long) = gameLogService.gameLogUpdateFinishedAtAll(id)
}