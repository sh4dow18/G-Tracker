package sh4dow18.gtracker.frontend_android.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import sh4dow18.gtracker.frontend_android.databinding.FragmentAppBinding

class AppFragment : Fragment() {
    private var _binding : FragmentAppBinding ?= null
    private val binding get() = _binding!!
    companion object {
        fun newInstance() = AppFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppBinding.inflate(inflater, container, false)
        binding.CreatorWebsiteDescription.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://sh4dow18.github.io")))
        }
        binding.AppRepoDescription.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/sh4dow18/G-Tracker")))
        }
        binding.CreatorCompanyWebsiteDescription.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://sh4dowtech.github.io")))
        }
        binding.CreatorCompanyFacebookDescription.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://facebook.com/Sh4dowtech")))
        }
        return binding.root
    }
}