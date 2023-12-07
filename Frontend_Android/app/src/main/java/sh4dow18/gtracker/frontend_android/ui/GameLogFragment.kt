package sh4dow18.gtracker.frontend_android.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import sh4dow18.gtracker.frontend_android.R
import sh4dow18.gtracker.frontend_android.databinding.FragmentGameLogBinding
import sh4dow18.gtracker.frontend_android.utils.GameLogRegistrationRequest
import sh4dow18.gtracker.frontend_android.utils.MyApplication
import sh4dow18.gtracker.frontend_android.view_models.game.GameViewModelFactory
import sh4dow18.gtracker.frontend_android.view_models.gameLog.GameLogViewModel
import sh4dow18.gtracker.frontend_android.view_models.gameLog.GameLogViewModelFactory
import sh4dow18.gtracker.frontend_android.view_models.gameLog.StateGameLog

class GameLogFragment : Fragment() {
    private var _binding: FragmentGameLogBinding? = null
    private val binding get() = _binding!!
    private lateinit var gameLogViewModel: GameLogViewModel
    companion object {
        fun newInstance() = GameLogFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameLogBinding.inflate(inflater, container, false)
        gameLogViewModel = ViewModelProvider(this, GameLogViewModelFactory())[GameLogViewModel::class.java]
        gameLogViewModel.findGameLogById(arguments?.getLong("game_log_id")!!)
        gameLogViewModel.state.observe(viewLifecycleOwner){ state ->
            when (state) {
                StateGameLog.Loading -> {
                    binding.FragmentLoading.visibility = View.VISIBLE
                    binding.FragmentContainer.visibility = View.GONE
                }
                is StateGameLog.Error -> {
                    Toast.makeText(
                        requireContext(),
                        state.message,
                        Toast.LENGTH_LONG
                    ).show()
                    binding.FragmentLoading.visibility = View.GONE
                    binding.FragmentContainer.visibility = View.VISIBLE
                }
                is StateGameLog.Success -> {
                    val game = state.gameLog!!.game
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
                    binding.GameLogCreateDateDescription.text = state.gameLog.createdDate
                    binding.GameLogFinishedDescription.text = booleanToString(state.gameLog.finished)
                    binding.GameLogFinishedAtAllDescription.text = booleanToString(state.gameLog.finishedAtAll)
                    binding.FinishedButton.setOnClickListener {
                        gameLogViewModel.gameLogUpdateFinished(state.gameLog.id)
                        gameLogViewModel.state.observe(viewLifecycleOwner){ state ->
                            when (state) {
                                StateGameLog.Loading -> {
                                    binding.FragmentLoading.visibility = View.VISIBLE
                                }
                                is StateGameLog.Error -> {
                                    Toast.makeText(
                                        requireContext(),
                                        state.message,
                                        Toast.LENGTH_LONG
                                    ).show()
                                    binding.FragmentLoading.visibility = View.GONE
                                }
                                is StateGameLog.Success -> {
                                    Toast.makeText(
                                        requireContext(),
                                        "Successful Update",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    binding.FragmentLoading.visibility = View.GONE
                                }
                                else -> {}
                            }
                        }
                    }
                    binding.FinishedAtAllButton.setOnClickListener {
                        gameLogViewModel.gameLogUpdateFinishedAtAll(state.gameLog.id)
                        gameLogViewModel.state.observe(viewLifecycleOwner){ state ->
                            when (state) {
                                StateGameLog.Loading -> {
                                    binding.FragmentLoading.visibility = View.VISIBLE
                                }
                                is StateGameLog.Error -> {
                                    Toast.makeText(
                                        requireContext(),
                                        state.message,
                                        Toast.LENGTH_LONG
                                    ).show()
                                    binding.FragmentLoading.visibility = View.GONE
                                }
                                is StateGameLog.Success -> {
                                    Toast.makeText(
                                        requireContext(),
                                        "Successful Update",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    binding.FragmentLoading.visibility = View.GONE
                                }
                                else -> {}
                            }
                        }
                    }
                    binding.FragmentLoading.visibility = View.GONE
                    binding.FragmentContainer.visibility = View.VISIBLE
                }
                else -> {}
            }
        }
        return binding.root
    }

    private fun booleanToString(boolean: Boolean): String {
        if (boolean) {
            return "Yes"
        }
        return "No"
    }
}