package my.edu.tarc.hungerhub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import my.edu.tarc.hungerhub.databinding.FragmentHomeBinding
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

        binding.imageButtonRequest.setOnClickListener{
            findNavController().navigate(R.id.action_mainMenuFragment_to_nav_request)
        }

        binding.imageButtonSurvey.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_nav_survey)
        }

    }

        override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}