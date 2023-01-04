package my.edu.tarc.hungerhub.ui.donation


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentDonationBinding


class DonationFragment : Fragment(){


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


        binding.buttonSubmitDonation.setOnClickListener {

            val donationAmt = binding.editTextDonationPrice.text.toString()

            if(donationAmt.isNotEmpty()){
                findNavController().navigate(R.id.action_nav_donation_to_donorDetailsFragment, Bundle().apply {
                    putInt("totalDonation", donationAmt.toInt())
                })
            }else{
                Toast.makeText(context,"Please enter the amount of donation", Toast.LENGTH_SHORT).show()
            }
//            if(donationAmt.isNotEmpty()){
//                findNavController().navigate(R.id.action_nav_donation_to_donorDetailsFragment)
//
//            }else{
//                Toast.makeText(context,"Please enter the amount of donation", Toast.LENGTH_SHORT).show()
//
//            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}