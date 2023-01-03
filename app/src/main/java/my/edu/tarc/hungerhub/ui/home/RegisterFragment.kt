package my.edu.tarc.hungerhub.ui.home


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
            val email = binding.editTextRegisterEmail.text.toString()
            val state = binding.editTextRegisterState.text.toString()
            val pass = binding.editTextRegisterPassword.text.toString()
            val comfirmPass = binding.editTextRegisterComfirmPassword.text.toString()

            database = FirebaseDatabase.getInstance().getReference("User")
            val User = User(email,state,pass,comfirmPass)
            database.child(pass).setValue(User).addOnSuccessListener {

                binding.editTextRegisterEmail.text.clear()
                binding.editTextRegisterState.text.clear()
                binding.editTextRegisterComfirmPassword.text.clear()
                binding.editTextRegisterPassword.text.clear()

                //Toast.makeText(this,"Successfully Saved",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                //Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
            }

            if (email.isNotEmpty() && state.isNotEmpty() && pass.isNotEmpty() && comfirmPass.isNotEmpty()) {
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
        if (binding.Recipient.text.isEmpty() || binding.Donor.text.isEmpty()) {
            binding.Recipient.setError(getString(R.string.emailrequired))
        }
    }
}






//    override fun onStart() {
//        super.onStart()
//
//        //Fullname validation
//        val nameStream = RxTextView.textChanges(binding.etFullname)
//            .skipInitialValue()
//            .map { name ->
//                name.isEmpty()
//            }
//        nameStream.subscribe {
//            showNameExistAlert(it)
//        }
//
////Email validation
//    val emailStream = RxTextView.textChanges(binding.etEmail)
//        .skipInitialValue()
//        .map { email ->
//            !Patterns.EMAIL_ADDRESS.matcher(email).matches()
//        }
//    emailStream.subscribe {
//        showEmailValidAlert(it)
//    }
//
////Username validation
//    val usernameStream = RxTextView.textChanges(binding.etUsername)
//        .skipInitialValue()
//        .map { username ->
//            username.length < 6
//        }
//    usernameStream.subscribe{
//        showTextMinimalAlert(it,"Username")
//    }
//
////Password validation
//    val passwordStream = RxTextView.textChanges(binding.etPassword)
//        .skipInitialValue()
//        .map { password ->
//            password.length < 6
//        }
//    passwordStream.subscribe{
//        showTextMinimalAlert(it,"Password")
//    }
//
////Comfirm Password validation
//    val passwordComfirmStream = Observable.merge(
//        RxTextView.textChanges(binding.etPassword)
//            .skipInitialValue()
//            .map{ password ->
//                password.toString() != binding.etComfirmPassword.text.toString()
//            },
//        RxTextView.textChanges(binding.etComfirmPassword)
//            .skipInitialValue()
//            .map {comfirmPassword ->
//                comfirmPassword.toString() != binding.etPassword.text.toString()
//            })
//    passwordComfirmStream.subscribe{
//        showPasswordComfirmAlert(it)
//    }
//
////Button Enable True or False
//    val invalidFieldStream = Observable.combineLatest(
//        nameStream,
//        emailStream,
//        usernameStream,
//        passwordStream,
//        passwordComfirmStream,
//        {nameInvalid: Boolean, emailInvalid: Boolean, usernameInvalid: Boolean, passwordInvalid: Boolean, passwordComfirmInvalid: Boolean ->
//            !nameInvalid && !emailInvalid && !usernameInvalid && !passwordInvalid && !passwordComfirmInvalid
//        })
//    invalidFieldStream.subscribe { isValid ->
//        if(isValid){
//            binding.btnRegister.isEnabled = true
//            binding.btnRegister.backgroundTintList = ContextCompat.getColorStateList(this.requireContext(), R.color.primary_color)
//        }else
//            binding.btnRegister.isEnabled = false
//        binding.btnRegister.backgroundTintList = ContextCompat.getColorStateList(this.requireContext(),android.R.color.darker_gray)
//    }

////click
//    binding.btnRegister.setOnClickListener {
//        findNavController().navigate(R.id.action_nav_home_to_loginFragment)
//    }
//    binding.tvHaveAccount.setOnClickListener {
//        findNavController().navigate(R.id.action_nav_home_to_loginFragment)
//    }
//}
//
//    private fun showNameExistAlert(isNotValid:Boolean){
//        binding.etFullname.error = if (isNotValid) "Name cannot be empty" else null
//    }
//
//    private fun showTextMinimalAlert(isNotValid: Boolean, text: String){
//        if (text == "Username")
//            binding.etUsername.error = if (isNotValid) "$text must be more than 6!" else null
//        else if (text == "Password")
//            binding.etPassword.error = if (isNotValid) "$text must be more than 8!" else null
//    }
//
//    private fun showEmailValidAlert(isNotValid: Boolean){
//        binding.etEmail.error = if(isNotValid) "Email invalid!" else null
//    }
//
//    private fun showPasswordComfirmAlert(isNotValid: Boolean){
//        binding.etComfirmPassword.error = if(isNotValid)"Password is not same" else null
//    }
//
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
