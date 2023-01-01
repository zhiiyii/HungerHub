package my.edu.tarc.hungerhub.ui.donation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentDonationBinding

class DonationFragment : Fragment() {

    private var _binding: FragmentDonationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDonationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var donationAmt = 0
        binding.buttonSubmitDonation.setOnClickListener {
            findNavController().navigate(R.id.action_nav_donation_to_nav_donation2)
        }

//        binding.buttonDonation50.setOnClickListener {
//            donationAmt = 50
//        }
//
//        binding.buttonDonation100.setOnClickListener {
//            donationAmt = 100
//        }
//        binding.buttonDonation500.setOnClickListener {
//            donationAmt = 500
//        }
//
//        binding.buttonDonation1000.setOnClickListener {
//            donationAmt = 1000
//        }


//        binding.buttonSubmitDonation.setOnClickListener {
//            if(binding.editTextDonationPrice.text.isEmpty()){
//                binding.editTextDonationPrice.setError(getString(R.string.value_required))
//            }else{
//                if(isNumericToX(binding.editTextDonationPrice.text.toString())){
//                    donationAmt = binding.editTextDonationPrice.text.toString().toInt()
//                }else{
//                    binding.editTextDonationPrice.setError(getString(R.string.invalid_format))
//                }
//                //TODO:validation for double or non-numeric
////                donationAmt = binding.editTextDonationPrice.text.toString().toInt()
//            }
//        }
    }



    fun isNumericToX(toCheck: String): Boolean {
        return toCheck.toDoubleOrNull() != null
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}