package my.edu.tarc.hungerhub.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentHomeBinding
import my.edu.tarc.hungerhub.databinding.FragmentLoginBinding

@Suppress("UNREACHABLE_CODE")
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth
    val User = User("hello","123456")
    //private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root

        firebaseAuth = FirebaseAuth.getInstance()
        binding.textView.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_loginFragment)
        }

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty()) {

                    firebaseAuth.createUserWithEmailAndPassword(email , pass).addOnCompleteListener{
                        if(it.isSuccessful){
                            findNavController().navigate(R.id.action_loginFragment_to_nav_request)
                        }else{
                            Toast.makeText(requireActivity(), it.exception.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
            }else{
                Toast.makeText(requireActivity(), "Empty Fields Are not Allowed!!", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if(firebaseAuth.currentUser != null){
            findNavController().navigate(R.id.action_loginFragment_to_nav_request)
        }
    }

//    override fun onStart() {
//        super.onStart()
//        database = FirebaseDatabase.getInstance().getReference("Users")
//
//
//        //Username validation
//        val usernameStream = RxTextView.textChanges(binding.etEmail)
//            .skipInitialValue()
//            .map { username ->
//                username.isEmpty()
//            }
//        usernameStream.subscribe{
//            showTextMinimalAlert(it,"Email/Username")
//        }
//
////Password validation
//        val passwordStream = RxTextView.textChanges(binding.etPassword)
//            .skipInitialValue()
//            .map { password ->
//                password.isEmpty()
//            }
//        passwordStream.subscribe{
//            showTextMinimalAlert(it,"Password")
//        }
//
////Button Enable True or False
//        val invalidFieldStream = Observable.combineLatest(
//            usernameStream,
//            passwordStream,
//            { usernameInvalid: Boolean, passwordInvalid: Boolean ->
//                !usernameInvalid && !passwordInvalid
//            })
//        invalidFieldStream.subscribe { isValid ->
//            if(isValid){
//                binding.btnLogin.isEnabled = true
//                binding.btnLogin.backgroundTintList = ContextCompat.getColorStateList(this.requireContext(), R.color.primary_color)
//            }else
//                binding.btnLogin.isEnabled = false
//            binding.btnLogin.backgroundTintList = ContextCompat.getColorStateList(this.requireContext(),android.R.color.darker_gray)
//        }
//
////Click
//        binding.btnLogin.setOnClickListener {
//            findNavController().navigate(R.id.action_loginFragment_to_nav_request)
//        }
//        binding.tvHaventAccount.setOnClickListener {
//            findNavController().navigate(R.id.action_nav_home_to_registerFragment)
//        }
//    }
//
//    private fun showTextMinimalAlert(isNotValid: Boolean, text: String){
//        if(text == "Email/Username") {
//            val username = text
//            binding.etEmail.error = if (isNotValid) "$text cannot be empty" else null
//        }else if (text == "Password"){
//            val password = text
//            binding.etPassword.error = if (isNotValid) "$text cannot be empty" else null
//    }
//        val User = User(username,password)
//        database.setValue(User)}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
