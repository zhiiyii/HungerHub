package my.edu.tarc.hungerhub.ui.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentRegisterBinding
import my.edu.tarc.hungerhub.model.User
import android.widget.Toast.makeText as toastMakeText

@Suppress("UNREACHABLE_CODE")
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database:DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.buttonLogIn.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.btnRegister.setOnClickListener {

            firebaseAuth = FirebaseAuth.getInstance()
            val fullName = binding.editTextRegisterFullname.text.toString()
            val email = binding.editTextRegisterEmail.text.toString()
            val ic = binding.editTextRegisterIC.text.toString()
            val phoneNo = binding.editTextRegisterPhoneNo.text.toString()
            val address = binding.editTextRegisterAddress.text.toString()
            val state = binding.editTextRegisterState.text.toString()
            val posCode = binding.editTextRegisterPostCode.text.toString()
            val pass = binding.editTextRegisterPassword.text.toString()
            val comfirmPass = binding.editTextRegisterComfirmPassword.text.toString()
            var type: String? = null
            if(binding.radioButtonDonor.isChecked){
                type = "Donor"
            } else if(binding.radioButtonRecipient.isChecked) {
                type = "Recipient"
            }

            database = FirebaseDatabase.getInstance().getReference("User")
            val user = User(ic,email,fullName,state,pass,phoneNo,address,posCode,type)
            database.child(ic).setValue(user).addOnSuccessListener {

                binding.editTextRegisterFullname.text.clear()
                binding.editTextRegisterEmail.text.clear()
                binding.editTextRegisterIC.text.clear()
                binding.editTextRegisterPhoneNo.text.clear()
                binding.editTextRegisterAddress.text.clear()
                binding.editTextRegisterState.text.clear()
                binding.editTextRegisterPostCode.text.clear()
                binding.editTextRegisterComfirmPassword.text.clear()
                binding.editTextRegisterPassword.text.clear()

            }.addOnFailureListener {
                toastMakeText(requireActivity(), "Failed to add details to database", Toast.LENGTH_LONG)
                    .show()
            }

            if (pass.isNotEmpty() && comfirmPass.isNotEmpty()) {
                if (pass == comfirmPass) {
                    toastMakeText(requireActivity(), "Successfully to create an account!", Toast.LENGTH_LONG)
                        .show()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

                } else if((pass != comfirmPass)){
                    toastMakeText(requireActivity(), "Password is not matching", Toast.LENGTH_LONG)
                        .show()
                    return@setOnClickListener
                }

            } else {
                toastMakeText(
                    requireActivity(),
                    "Empty Fields Are not Allowed!!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        if (binding.editTextRegisterFullname.text.isEmpty()) {
            binding.editTextRegisterFullname.setError(getString(R.string.fullNameRequired))
        }
        if (binding.editTextRegisterIC.text.isEmpty()) {
            binding.editTextRegisterIC.setError(getString(R.string.emailRequired))
        }
        if (binding.editTextRegisterPhoneNo.text.isEmpty()) {
            binding.editTextRegisterPhoneNo.setError(getString(R.string.icRequired))
        }
        if (binding.editTextRegisterEmail.text.isEmpty()) {
            binding.editTextRegisterEmail.setError(getString(R.string.emailRequired))
        }
        if (binding.editTextRegisterAddress.text.isEmpty()) {
            binding.editTextRegisterAddress.setError(getString(R.string.addressRequired))
        }
        if (binding.editTextRegisterState.text.isEmpty()) {
            binding.editTextRegisterState.setError(getString(R.string.stateRequired))
        }
        if (binding.editTextRegisterPostCode.text.isEmpty()) {
            binding.editTextRegisterPostCode.setError(getString(R.string.poscodeRequired))
        }

    }

}