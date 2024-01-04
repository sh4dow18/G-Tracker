package sh4dow18.gtracker.frontend_android.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import sh4dow18.gtracker.frontend_android.R
import sh4dow18.gtracker.frontend_android.databinding.FragmentGameLogBinding
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
                    var genres = "No Information Found"
                    if (game.gendersList.isNotEmpty()) {
                        genres = ""
                        game.gendersList.forEach { genres += (it.name + " / ") }
                        genres = genres.substring(0, genres.length - 3)
                    }
                    var platforms = "No Information Found"
                    if (game.platformsList.isNotEmpty()) {
                        platforms = ""
                        game.platformsList.forEach { platforms += (it.name + " / ") }
                        platforms = platforms.substring(0, platforms.length - 3)
                    }
                    if (game.imageUrl != "") {
                        Glide.with(this)
                            .load(game.imageUrl)
                            .centerCrop()
                            .into(binding.GameImage)
                    }
                    binding.GameName.text = game.name
                    binding.MetacriticDescription.text = game.metacritic.toString()
                    binding.RatingUsersDescription.text = game.rating.toString()
                    binding.GameReleaseDescription.text = game.releaseDate
                    binding.GameGenresDescription.text = genres
                    binding.GamePlatformDescription.text = platforms
                    binding.GameLogCreateDateDescription.text = state.gameLog.createdDate
                    binding.GameLogFinishedDescription.text = if (state.gameLog.finished != "") state.gameLog.finished else "No"
                    binding.GameLogFinishedAtAllDescription.text = if (state.gameLog.finishedAtAll != "") state.gameLog.finishedAtAll else "No"
                    binding.FinishedButton.setOnClickListener {
                        gameLogViewModel.updateGameLogFinished(state.gameLog.id)
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
                    binding.FinishedAtAllButton.isEnabled = state.gameLog.finished != ""
                    binding.FinishedAtAllButton.setOnClickListener {
                        gameLogViewModel.updateGameLogFinishedAtAll(state.gameLog.id)
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
                    binding.MoreOptionsButton.setOnClickListener {
                        val bundle = bundleOf("game_log_id" to state.gameLog.id)
                        findNavController().navigate(R.id.nav_game_log_more_options, bundle)
                    }
                    binding.FragmentLoading.visibility = View.GONE
                    binding.FragmentContainer.visibility = View.VISIBLE
                }
                else -> {}
            }
        }
        return binding.root
    }
}