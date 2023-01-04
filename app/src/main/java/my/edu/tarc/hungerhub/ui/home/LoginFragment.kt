package my.edu.tarc.hungerhub.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
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

        //read data from firebase and add in to users
        var users = mutableListOf<User>()
        var setUsers = mutableSetOf<User>()
        binding.buttonSignIn.setOnClickListener {
            val ic: String = binding.editTextLogInIC.text.toString()
            val pass: String = binding.editTextLogInPassword.text.toString()

            if (binding.editTextLogInIC.text.isEmpty()) {
                binding.editTextLogInIC.setError(getString(R.string.emailrequired))
                return@setOnClickListener
            }
            if (binding.editTextLogInPassword.text.isEmpty()) {
                binding.editTextLogInPassword.setError(getString(R.string.passwordrequired))
                return@setOnClickListener
            }

            database = FirebaseDatabase.getInstance().getReference("User")

            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (userSnapshot in dataSnapshot.children) {
                        var user = userSnapshot.getValue(User()::class.java)
                        if (user != null) {
                            if (user !in setUsers) {
                                users.add(user)
                                setUsers.add(user)
                            }
                        }
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle error
                }
            })

            var isUser: Boolean = false
            if (ic.isNotEmpty() && pass.isNotEmpty()) {
                for (user in users) {
                    if (ic == user.ic && pass == user.pass) {
                        isUser = true
                        break;
                    }
                }
                if (isUser) {
                    getActivity()?.let { it1 ->
                        Snackbar.make(
                            it1.findViewById(android.R.id.content),
                            "Login Success!", Snackbar.LENGTH_LONG
                        ).show()
                    }
                    findNavController().navigate(R.id.action_loginFragment_to_nav_request2)
                } else {
                    getActivity()?.let { it1 ->
                        Snackbar.make(
                            it1.findViewById(android.R.id.content),
                            "Ic or Password are incorrect!", Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
            else{
                getActivity()?.let { it1 ->
                    Snackbar.make(
                        it1.findViewById(android.R.id.content),
                        "Invalid ic and password, please try again!", Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }

    }
}