package sh4dow18.gtracker.frontend_android.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import sh4dow18.gtracker.frontend_android.MainActivity
import sh4dow18.gtracker.frontend_android.R
import sh4dow18.gtracker.frontend_android.databinding.FragmentGameBinding
import sh4dow18.gtracker.frontend_android.utils.GameLogRegistrationRequest
import sh4dow18.gtracker.frontend_android.utils.MyApplication
import sh4dow18.gtracker.frontend_android.view_models.game.GameViewModel
import sh4dow18.gtracker.frontend_android.view_models.game.GameViewModelFactory
import sh4dow18.gtracker.frontend_android.view_models.game.StateGame
import sh4dow18.gtracker.frontend_android.view_models.gameLog.GameLogViewModel
import sh4dow18.gtracker.frontend_android.view_models.gameLog.GameLogViewModelFactory
import sh4dow18.gtracker.frontend_android.view_models.gameLog.StateGameLog

class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var gameViewModel: GameViewModel
    private lateinit var gameLogViewModel: GameLogViewModel
    companion object {
        fun newInstance() = GameFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        gameViewModel = ViewModelProvider(this, GameViewModelFactory())[GameViewModel::class.java]
        gameLogViewModel = ViewModelProvider(this, GameLogViewModelFactory())[GameLogViewModel::class.java]
        gameViewModel.findById(arguments?.getLong("game_id")!!)
        gameViewModel.state.observe(viewLifecycleOwner){ state ->
            when (state) {
                StateGame.Loading -> {}
                is StateGame.Error -> {
                    Toast.makeText(
                        requireContext(),
                        state.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
                is StateGame.Success -> {
                    val game = state.game!!
                    val gameMetacritic = "Rating(Metacritic): " + game.metacritic + " / 100"
                    val gameRating = "Rating(Users): " + game.rating + " / 5"
                    var genres = ""
                    game.gendersList.forEach { genres += (it.name + " / ") }
                    genres = genres.substring(0, genres.length - 3)
                    var platforms = ""
                    game.platformsList.forEach { platforms += (it.name + " / ") }
                    platforms = platforms.substring(0, platforms.length - 3)
                    Glide.with(this)
                        .load("http://192.168.0.13:8080/api/public/image/game/${game.id}.png")
                        .into(binding.GameImage)
                    binding.GameName.text = game.name
                    binding.Metacritic.text = gameMetacritic
                    binding.Genders.text = gameRating
                    binding.GameReleaseDescription.text = game.releaseDate
                    binding.GameGenresDescription.text = genres
                    binding.GamePlatformDescription.text = platforms
                    binding.AddGameToGameTrackerButton.setOnClickListener {
                        gameLogViewModel.gameLogRegistration(GameLogRegistrationRequest(game.id,
                            MyApplication.sessionManager!!.getUserEmail()))
                        gameLogViewModel.state.observe(viewLifecycleOwner){ state ->
                            when (state) {
                                StateGameLog.Loading -> {}
                                is StateGameLog.Error -> {
                                    Toast.makeText(
                                        requireContext(),
                                        state.message,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                                is StateGameLog.Success -> {
                                    findNavController().navigate(R.id.nav_tracker)
                                    Toast.makeText(
                                        requireContext(),
                                        "The Game \"${game.name}\" was added to Tracker",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                                else -> {}
                            }
                        }
                    }
                }
                else -> {}
            }
        }
        return binding.root
    }
}