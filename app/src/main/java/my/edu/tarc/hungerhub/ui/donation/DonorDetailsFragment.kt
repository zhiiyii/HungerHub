package my.edu.tarc.hungerhub.ui.donation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.FirebaseDatabase
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentDonation2Binding
import my.edu.tarc.hungerhub.databinding.FragmentDonorDetailsBinding
import my.edu.tarc.hungerhub.ui.home.User

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
        var currentUser: User?=null
        val referenceUser = FirebaseDatabase.getInstance().getReference(getString(R.string.firebase_user))
        var database = FirebaseDatabase.getInstance().getReference("User")
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = activity?.getSharedPreferences("Login", Context.MODE_PRIVATE)
        val loginUserIc = sharedPref?.getString("ic", null)
//        val loginUserName = sharedPref?.getString("name", null)
//        val loginUserEmail = sharedPref?.getString("email", null)
//        val loginUserPhone = sharedPref?.getString("phone", null)
        val donateAmt = requireArguments().getInt("totalDonation").toString()

        database.child(loginUserIc.toString()).get().addOnSuccessListener {
            if (it.exists()) {
                val currentUser = it.getValue(User()::class.java)!!
                binding.textViewDonorICOutput.text = currentUser.ic
                binding.textViewDonorNameOutput.text = currentUser.name
                binding.textViewDonorEmailOutput.text = currentUser.email
                binding.textViewDonorPhoneOutput.text = currentUser.phoneNo
            }
        }

//        binding.textViewDonorICOutput.text = loginUserIc
//        binding.textViewDonorNameOutput.text = loginUserName
//        binding.textViewDonorEmailOutput.text = loginUserEmail
//        binding.textViewDonorPhoneOutput.text = loginUserPhone
        binding.textViewDonationAmtOutput.text = donateAmt


//        Toast.makeText(
//            context,
//            "You have successfully donated RM $donateAmt",
//            Toast.LENGTH_SHORT
//        ).show()

        binding.buttonDonorDetails.setOnClickListener {
            findNavController().navigate(R.id.action_donorDetailsFragment_to_nav_donation2, Bundle().apply {
                putInt("totalDonation", donateAmt.toInt())
            })
//            findNavController().navigate(R.id.action_donorDetailsFragment_to_nav_donation2)
        }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}