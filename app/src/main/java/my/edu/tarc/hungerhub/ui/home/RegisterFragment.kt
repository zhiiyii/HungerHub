package my.edu.tarc.hungerhub.ui.home
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentRegisterBinding
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
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

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

            //This variable is use to solve the problems that firebase cannot store the value with "."
//            val newEmail = email.replace(".",",")

            database = FirebaseDatabase.getInstance().getReference("User")

            val user = User(ic,email,fullName,state,pass,phoneNo,address,posCode)


//            Toast.makeText(this,"Successfully Saved",Toast.LENGTH_SHORT).show()

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

                //Toast.makeText(this,"Successfully Saved",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                //Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
            }

            if (ic.isNotEmpty() && email.isNotEmpty() && state.isNotEmpty() && pass.isNotEmpty() && comfirmPass.isNotEmpty()) {
                if (pass == comfirmPass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            findNavController().navigate(R.id.action_nav_home_to_loginFragment)

                        } else {
                            toastMakeText(
                                requireActivity(),
                                it.exception.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                } else {
                    toastMakeText(requireActivity(), "Password is not matching", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                toastMakeText(
                    requireActivity(),
                    "Empty Fields Are not Allowed!!",
                    Toast.LENGTH_LONG
                ).show()

            }
        }


//        binding.radioGroupRandD.setOnCheckedChangeListener {
//
//        }
        if (binding.editTextRegisterFullname.text.isEmpty()) {
            binding.editTextRegisterFullname.setError(getString(R.string.emailrequired))
        }
        if (binding.editTextRegisterIC.text.isEmpty()) {
            binding.editTextRegisterIC.setError(getString(R.string.emailrequired))
        }
        if (binding.editTextRegisterPhoneNo.text.isEmpty()) {
            binding.editTextRegisterPhoneNo.setError(getString(R.string.emailrequired))
        }
        if (binding.editTextRegisterEmail.text.isEmpty()) {
            binding.editTextRegisterEmail.setError(getString(R.string.emailrequired))
        }
        if (binding.editTextRegisterAddress.text.isEmpty()) {
            binding.editTextRegisterAddress.setError(getString(R.string.emailrequired))
        }
        if (binding.editTextRegisterState.text.isEmpty()) {
            binding.editTextRegisterState.setError(getString(R.string.emailrequired))
        }
        if (binding.editTextRegisterPostCode.text.isEmpty()) {
            binding.editTextRegisterPostCode.setError(getString(R.string.emailrequired))
        }

    }

}