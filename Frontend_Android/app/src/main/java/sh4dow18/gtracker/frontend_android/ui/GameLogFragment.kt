package sh4dow18.gtracker.frontend_android.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import sh4dow18.gtracker.frontend_android.R
import sh4dow18.gtracker.frontend_android.databinding.FragmentGameLogBinding

class GameLogFragment : Fragment() {
    private var _binding: FragmentGameLogBinding? = null
    private val binding get() = _binding!!
    companion object {
        fun newInstance() = GameLogFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameLogBinding.inflate(inflater, container, false)
        return binding.root
    }
}