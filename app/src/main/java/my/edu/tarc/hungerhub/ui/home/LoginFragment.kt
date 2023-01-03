package my.edu.tarc.hungerhub.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentLoginBinding

@Suppress("UNREACHABLE_CODE")
class LoginFragment : Fragment() {

    private lateinit var database :DatabaseReference
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
//    private lateinit var firebaseAuth: FirebaseAuth
//    val password = "123123"
//    val email = "pinkkting@gmail.com"
    private lateinit var tvRedirectSignUp: TextView
    lateinit var etEmail: EditText
    private lateinit var etPass: EditText
    lateinit var btnLogin: Button

    // Creating firebaseAuth object
    lateinit var auth: FirebaseAuth

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



    // initialising Firebase auth object
        //auth = FirebaseAuth.getInstance()


        binding.buttonRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.buttonSignIn.setOnClickListener{
            val ic : String = binding.editTextLogInIC.text.toString()
            val pass: String = binding.editTextLogInPassword.text.toString()

            if(ic.isNotEmpty() && pass.isNotEmpty()){

                database = FirebaseDatabase.getInstance().getReference("User")
                database.child(ic).get().addOnSuccessListener {
                    database.child(pass).get().addOnSuccessListener {

                        if(binding.editTextLogInIC.text.toString() == ic && binding.editTextLogInPassword.text.toString() == pass ){
                            getActivity()?.let { it1 ->
                                Snackbar.make(
                                    it1.findViewById(android.R.id.content),
                                    "Login Success!", Snackbar.LENGTH_LONG).show()
                            }

                            findNavController().navigate(R.id.action_loginFragment_to_nav_request2)
                        }else {
//                val snack = Snackbar.make(it,"Invalid email/Password!", Snackbar.LENGTH_LONG)
//                snack.show()
                            getActivity()?.let { it1 ->
                                Snackbar.make(
                                    it1.findViewById(android.R.id.content),
                                    "Ic or Password are incorrect!", Snackbar.LENGTH_LONG
                                ).show()
                            }
                        }
                    }

                }

            }else{
                //Toast.makeText(this, "Please enter the email", Toast.LENGTH_SHORT).show()
            }
            if(binding.editTextLogInIC.text.isEmpty()){
                binding.editTextLogInIC.setError(getString(R.string.emailrequired))
                return@setOnClickListener
            }
            if(binding.editTextLogInPassword.text.isEmpty()){
                binding.editTextLogInPassword.setError(getString(R.string.passwordrequired))
                return@setOnClickListener
            }

        }

    }

    private fun readData(ic : String ,pass : String){}

//        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
//            if (it.isSuccessful) {
//                Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show()
//            } else
//                Toast.makeText(this, "Log In failed ", Toast.LENGTH_SHORT).show()
//        }
//    }

//    private fun readData(userName: String) {
//        userName.replace(".",",")
//        Log.d("cw","test data $userName")
//        database = FirebaseDatabase.getInstance().getReference("User")
//        val newEmail = email.replace(".",",")
//        database.child(newEmail).get().addOnSuccessListener {
//
//            if(it.exists()){
//                val username = it.child("Email").value
//                val pass = it.child("Password").value
//                Log.d("cw","Test data $username and $pass")
//                val state = it.child("State").value
//
//                //Toast.makeText(this, "Successfuly login!", Toast.LENGTH_SHORT).show()
//
//                binding.editTextLogInEmail.text.clear()
//                binding.editTextLogInPassword.text.clear()
//                findNavController().navigate(R.id.action_loginFragment_to_nav_request2)
//
//            }else{
//
//                //Toast.makeText(this, "Doesn't Not Exist", Toast.LENGTH_SHORT).show()
//            }
//        }.addOnFailureListener {
//
//            //Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
//        }
//
//    }
//
//    private fun readData2(userName: String,password: String){
//        val databaseReference = FirebaseDatabase.getInstance().reference
//
//        databaseReference.child("users").addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (userSnapshot in dataSnapshot.children) {
//                    val user = userSnapshot.getValue(User::class.java)
//                    if (user != null) {
//                        val age = user.age
//                        if (age == 25) {
//                            // Do something
//                        }
//                    }
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Handle error
//            }
//        })
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
