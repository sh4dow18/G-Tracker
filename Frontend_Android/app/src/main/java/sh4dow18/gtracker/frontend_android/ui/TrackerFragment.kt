package sh4dow18.gtracker.frontend_android.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import sh4dow18.gtracker.frontend_android.R
import sh4dow18.gtracker.frontend_android.databinding.FragmentTrackerBinding

class TrackerFragment : Fragment() {
    private var _binding: FragmentTrackerBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrackerBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() = TrackerFragment()
    }
}