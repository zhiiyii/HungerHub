package my.edu.tarc.hungerhub.ui.donation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentDonorDetailsBinding

class DonorDetailsFragment: Fragment() {
    private var _binding: FragmentDonorDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDonorDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = activity?.getSharedPreferences("Login", Context.MODE_PRIVATE)
        val loginUserIc = sharedPref?.getString("ic", null)
        val loginUserName = sharedPref?.getString("name", null)
        val loginUserEmail = sharedPref?.getString("email",null)
        val loginUserPhone = sharedPref?.getString("phoneNo",null)
        val donateAmt = requireArguments().getInt("totalDonation").toString()

        binding.textViewDonorICOutput.text = loginUserIc
        binding.textViewDonorNameOutput.text = loginUserName
        binding.textViewDonorEmailOutput.text = loginUserEmail
        binding.textViewDonorPhoneOutput.text = loginUserPhone
        binding.textViewDonationAmtOutput.text = donateAmt

        binding.buttonDonorDetails.setOnClickListener {
            findNavController().navigate(R.id.action_donorDetailsFragment_to_nav_donation2, Bundle().apply {
                putInt("totalDonation", donateAmt.toInt())
            })
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}