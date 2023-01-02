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
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentHomeBinding
import my.edu.tarc.hungerhub.databinding.FragmentLoginBinding

@Suppress("UNREACHABLE_CODE")
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    //private lateinit var firebaseAuth: FirebaseAuth
    //val User = User("hello","123456")
    //private lateinit var database: DatabaseReference
    val password = "123123"
    val email = "pinkkting@gmail.com"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.buttonSignIn.setOnClickListener{
            if(binding.editTextLogInEmail.text.isEmpty()){
                binding.editTextLogInEmail.setError(getString(R.string.emailrequired))
                return@setOnClickListener
            }
            if(binding.editTextLogInPassword.text.isEmpty()){
                binding.editTextLogInPassword.setError(getString(R.string.passwordrequired))
                return@setOnClickListener
            }
            if(binding.editTextLogInEmail.text.toString() == email && binding.editTextLogInPassword.text.toString() == password ){
                val snack = Snackbar.make(it,"Login Success!", Snackbar.LENGTH_LONG)
                snack.show()
                findNavController().navigate(R.id.action_nav_home_to_loginFragment)
            }else{
                val snack = Snackbar.make(it,"Invalid email/Password!", Snackbar.LENGTH_LONG)
                snack.show()

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
