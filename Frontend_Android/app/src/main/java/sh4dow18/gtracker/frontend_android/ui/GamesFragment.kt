package sh4dow18.gtracker.frontend_android.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import sh4dow18.gtracker.frontend_android.adapters.GamesAdapter
import sh4dow18.gtracker.frontend_android.databinding.FragmentGamesBinding
import sh4dow18.gtracker.frontend_android.utils.GameResponse
import sh4dow18.gtracker.frontend_android.view_models.game.GameViewModel
import sh4dow18.gtracker.frontend_android.view_models.game.GameViewModelFactory
import sh4dow18.gtracker.frontend_android.view_models.game.StateGame
import sh4dow18.gtracker.frontend_android.view_models.user.StateUser

class GamesFragment : Fragment() {
    private var _binding: FragmentGamesBinding? = null
    private val binding get() = _binding!!
    private lateinit var gameViewModel: GameViewModel
    companion object {
        fun newInstance() = GamesFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val adapter = GamesAdapter()
        _binding = FragmentGamesBinding.inflate(inflater, container, false)
        binding.GamesRecyclerView.adapter = adapter
        binding.GamesRecyclerView.visibility = View.GONE
        gameViewModel = ViewModelProvider(this, GameViewModelFactory())[GameViewModel::class.java]
        gameViewModel.findFirst20ByOrderByRatingDesc()
        gameViewModel.state.observe(viewLifecycleOwner){ state ->
            when (state) {
                StateGame.Loading -> {
                    binding.GamesRecyclerViewLoading.visibility = View.VISIBLE
                }
                is StateGame.Error -> {
                    Toast.makeText(
                        requireContext(),
                        state.message,
                        Toast.LENGTH_LONG
                    ).show()
                    binding.GamesRecyclerViewLoading.visibility = View.GONE
                    binding.GamesRecyclerView.visibility = View.VISIBLE
                }
                is StateGame.SuccessList -> {
                    state.gamesList?.let { adapter.setGamesList(it) }
                    binding.GamesRecyclerViewLoading.visibility = View.GONE
                    binding.GamesRecyclerView.visibility = View.VISIBLE
                }
                else -> {}
            }
        }
        binding.Search.setOnClickListener {
            val gameName = binding.Searcher.text.toString()
            if (gameName != "") {
                gameViewModel.findByNameContainingIgnoreCase(gameName)
                gameViewModel.state.observe(viewLifecycleOwner){ state ->
                    when (state) {
                        StateGame.Loading -> {
                            binding.GamesRecyclerView.visibility = View.GONE
                            binding.GamesRecyclerViewLoading.visibility = View.VISIBLE
                        }
                        is StateGame.Error -> {
                            Toast.makeText(
                                requireContext(),
                                state.message,
                                Toast.LENGTH_LONG
                            ).show()
                            binding.GamesRecyclerViewLoading.visibility = View.GONE
                            binding.GamesRecyclerView.visibility = View.VISIBLE
                        }
                        is StateGame.SuccessList -> {
                            state.gamesList?.let { adapter.setGamesList(it) }
                            binding.GamesRecyclerViewLoading.visibility = View.GONE
                            binding.GamesRecyclerView.visibility = View.VISIBLE
                        }
                        else -> {}
                    }
                }
            } else {
                gameViewModel.findFirst20ByOrderByRatingDesc()
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
                        is StateGame.SuccessList -> {
                            state.gamesList?.let { adapter.setGamesList(it) }
                            binding.GamesRecyclerView.visibility = View.VISIBLE
                        }
                        else -> {}
                    }
                }
            }
        }
        return binding.root
    }
}