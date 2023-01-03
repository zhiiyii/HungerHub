package my.edu.tarc.hungerhub.ui.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentLoginBinding

@Suppress("UNREACHABLE_CODE")
class LoginFragment : Fragment() {
    private lateinit var database:FirebaseDatabase
    private lateinit var reference:DatabaseReference
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth
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
//                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
//                    if(it.isSuccessful){
//
//                        findNavController().navigate(R.id.action_loginFragment_to_nav_request2)
//
//                    }
//                }
                val snack = Snackbar.make(it,"Login Success!", Snackbar.LENGTH_LONG)
                snack.show()
                findNavController().navigate(R.id.action_loginFragment_to_nav_request2)
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
