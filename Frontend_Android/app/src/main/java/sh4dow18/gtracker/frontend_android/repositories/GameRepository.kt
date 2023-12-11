package sh4dow18.gtracker.frontend_android.repositories

import sh4dow18.gtracker.frontend_android.services.GameService

class GameRepository(
    private val gameService: GameService
) {
    suspend fun findFirst10ByOrderByRatingDesc() = gameService.findFirst10ByOrderByRatingDesc()
    suspend fun findByNameContainingIgnoreCase(name: String) = gameService.findByNameContainingIgnoreCase(name)
    suspend fun findById(id: Long) = gameService.findById(id)
}