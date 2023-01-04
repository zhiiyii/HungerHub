package my.edu.tarc.hungerhub

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import my.edu.tarc.hungerhub.databinding.FragmentMainMenuBinding

class MainMenuFragment: Fragment()  {
    private var _binding: FragmentMainMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.imageButtonDonate.setOnClickListener{
            findNavController().navigate(R.id.action_mainMenuFragment_to_nav_donation)
        }
        binding.textViewDonateMainMenu.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_nav_donation)
        }

        binding.imageButtonRequest.setOnClickListener{
            findNavController().navigate(R.id.action_mainMenuFragment_to_nav_request)
        }
        binding.textViewRequestMainMenu.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_nav_request)
        }

        binding.imageButtonSurvey.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_nav_survey)
        }
        binding.textViewSurveyMainMenu.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_nav_survey)
        }

        binding.imageButtonInfo.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://nutritionfacts.org/")
            startActivity(intent)
        }
        binding.textViewMoreInfo.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://nutritionfacts.org/")
            startActivity(intent)
        }
    }

        override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}