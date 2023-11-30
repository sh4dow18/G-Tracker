package sh4dow18.gtracker.frontend_android.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import sh4dow18.gtracker.frontend_android.R
import sh4dow18.gtracker.frontend_android.adapters.GameLogsAdapter
import sh4dow18.gtracker.frontend_android.databinding.FragmentTrackerBinding
import sh4dow18.gtracker.frontend_android.utils.GameLogRegistrationRequest
import sh4dow18.gtracker.frontend_android.utils.MyApplication
import sh4dow18.gtracker.frontend_android.view_models.game.GameViewModelFactory
import sh4dow18.gtracker.frontend_android.view_models.gameLog.GameLogViewModel
import sh4dow18.gtracker.frontend_android.view_models.gameLog.GameLogViewModelFactory
import sh4dow18.gtracker.frontend_android.view_models.gameLog.StateGameLog

class TrackerFragment : Fragment() {
    private var _binding: FragmentTrackerBinding? = null
    private val binding get() = _binding!!
    private lateinit var gameLogViewModel: GameLogViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val adapter = GameLogsAdapter()
        _binding = FragmentTrackerBinding.inflate(inflater, container, false)
        binding.GamesRecyclerView.adapter = adapter
        gameLogViewModel = ViewModelProvider(this, GameLogViewModelFactory())[GameLogViewModel::class.java]
        gameLogViewModel.findAllByUserEmail(MyApplication.sessionManager!!.getUserEmail())
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
                is StateGameLog.SuccessList -> {
                    state.gameLogsList?.let { adapter.setGamesList(it) }
                    binding.GamesRecyclerView.visibility = View.VISIBLE
                }
                else -> {}
            }
        }
        return binding.root
    }

    companion object {
        fun newInstance() = TrackerFragment()
    }
}