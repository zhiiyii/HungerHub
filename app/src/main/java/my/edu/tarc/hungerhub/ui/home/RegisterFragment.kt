package my.edu.tarc.hungerhub.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentHomeBinding
import my.edu.tarc.hungerhub.databinding.FragmentRegisterBinding

@Suppress("UNREACHABLE_CODE")
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        return binding.root

        binding.textView.setOnClickListener{
            findNavController().navigate(R.id.action_nav_home_to_loginFragment)
        }
        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val comfirmPass = binding.confirmPassEt.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty() && comfirmPass.isNotEmpty()) {
                if (pass == comfirmPass) {

                    firebaseAuth.createUserWithEmailAndPassword(email , pass).addOnCompleteListener{
                        if(it.isSuccessful){
                            findNavController().navigate(R.id.action_nav_home_to_loginFragment)
                        }else{
                            Toast.makeText(requireActivity(), it.exception.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(requireActivity(), "Password is not matching", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(requireActivity(), "Empty Fields Are not Allowed!!", Toast.LENGTH_LONG).show()
                }
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
