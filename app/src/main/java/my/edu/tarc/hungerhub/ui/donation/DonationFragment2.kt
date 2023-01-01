package my.edu.tarc.hungerhub.ui.donation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import my.edu.tarc.hungerhub.databinding.FragmentDonation2Binding

class DonationFragment2: Fragment() {
    private var _binding: FragmentDonation2Binding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDonation2Binding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonDonate.setOnClickListener {
            val cardNum = binding.editTextCardNumber.text.toString()

            if(cardNum.length != 13 && cardNum.length != 16){
                binding.editTextCardNumber.setError("Invalid format")
//                binding.editTextCardNumber.setError(getString(R.string.invalid_format))
                return@setOnClickListener
            }
            if (binding.editTextCardHolderName.text.isEmpty()){
                binding.editTextCardHolderName.setError("Value is required")
//                binding.editTextCardHolderName.setError(getString(R.string))
                return@setOnClickListener
            }
            if(binding.editTextCVC.length()==3){
                if(binding.editTextCVC.text.isEmpty()) {
                    binding.editTextCardHolderName.setError("Value is required")
                    return@setOnClickListener
                }
            } else{
                binding.editTextCVC.setError("3 characters only")
                return@setOnClickListener
            }



        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}