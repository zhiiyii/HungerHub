package my.edu.tarc.hungerhub.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import kotlinx.coroutines.delay
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private lateinit var database: DatabaseReference
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

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
        binding.buttonRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        //read data from firebase and set a user
        var currentUser: User? = null
        var isUser: Boolean = false
        database = FirebaseDatabase.getInstance().getReference("User")

        binding.buttonSignIn.setOnClickListener {
            if (binding.editTextLogInIC.text.isEmpty()) {
                binding.editTextLogInIC.setError(getString(R.string.emailrequired))
                return@setOnClickListener
            }
            if (binding.editTextLogInPassword.text.isEmpty()) {
                binding.editTextLogInPassword.setError(getString(R.string.passwordrequired))
                return@setOnClickListener
            }

            var ic: String = binding.editTextLogInIC.text.toString()
            var pass: String = binding.editTextLogInPassword.text.toString()

            database.child(ic).get().addOnSuccessListener {
                if (it.exists()) {
                    currentUser = it.getValue(User()::class.java)!!
                    Log.d("cw", currentUser.toString())
                }
            }

            if (ic == currentUser?.ic && pass == currentUser?.pass) {
                Log.d(
                    "Test",
                    "ic = $ic, pass = $pass, icFB = ${currentUser!!.ic} , icPw = ${currentUser!!.pass}"
                )
                isUser = true
            }

            if (isUser) {
                // create shared preference for login account details
                val sharedPref = activity?.getSharedPreferences("Login", Context.MODE_PRIVATE)
                if (sharedPref != null) {
                    with(sharedPref.edit()) {
                        this?.clear()
                        this?.apply()
                    }
                }
                with(sharedPref?.edit()) {
                    this?.putString("ic", binding.editTextLogInIC.text.toString())
                    this?.apply()
                }

                activity?.let { it1 ->
                    Snackbar.make(
                        it1.findViewById(android.R.id.content),
                        "Login Success!", Snackbar.LENGTH_LONG
                    ).show()
                }

                findNavController().navigate(R.id.action_loginFragment_to_mainMenuFragment)
            }
        }
    }
}


