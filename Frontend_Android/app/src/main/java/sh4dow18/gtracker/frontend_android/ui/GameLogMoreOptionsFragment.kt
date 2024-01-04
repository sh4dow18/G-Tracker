package sh4dow18.gtracker.frontend_android.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import sh4dow18.gtracker.frontend_android.R
import sh4dow18.gtracker.frontend_android.databinding.FragmentGameLogMoreOptionsBinding
import sh4dow18.gtracker.frontend_android.utils.UpdateGameLogsDatesRequest
import sh4dow18.gtracker.frontend_android.view_models.gameLog.GameLogViewModel
import sh4dow18.gtracker.frontend_android.view_models.gameLog.GameLogViewModelFactory
import sh4dow18.gtracker.frontend_android.view_models.gameLog.StateGameLog


class GameLogMoreOptionsFragment : Fragment() {
    private var _binding: FragmentGameLogMoreOptionsBinding? = null
    private val binding get() = _binding!!
    private lateinit var gameLogViewModel: GameLogViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameLogMoreOptionsBinding.inflate(inflater, container, false)
        gameLogViewModel = ViewModelProvider(this, GameLogViewModelFactory())[GameLogViewModel::class.java]
        binding.Submit.setOnClickListener {
            val selectedRadioButton: RadioButton? = binding.root.findViewById(binding.radioButtonsGroup.checkedRadioButtonId)
            if (selectedRadioButton != null) {
                val formattedDate = String.format("%02d-%02d-%02d", binding.CreatedDateDate.year, binding.CreatedDateDate.month + 1, binding.CreatedDateDate.dayOfMonth)
                val formattedTime = String.format("%02d:%02d", binding.CreatedDateTime.hour, binding.CreatedDateTime.minute)
                gameLogViewModel.updateGameLogDates(
                    UpdateGameLogsDatesRequest(
                        id = arguments?.getLong("game_log_id")!!,
                        dateToUpdate = selectedRadioButton.tag.toString(),
                        date = formattedDate,
                        time = formattedTime,
                    )
                )
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
            else {
                Toast.makeText(
                    requireContext(),
                    "Please select a date to update",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        binding.DeleteGameLogSubmit.setOnClickListener {
            gameLogViewModel.deleteGameLog(arguments?.getLong("game_log_id")!!)
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
                            "Successful Delete",
                            Toast.LENGTH_LONG
                        ).show()
                        binding.FragmentLoading.visibility = View.GONE
                        findNavController().navigate(R.id.nav_profile)
                    }
                    else -> {}
                }
            }
        }
        return binding.root
    }

    companion object {
        fun newInstance() = GameLogMoreOptionsFragment()
    }
}