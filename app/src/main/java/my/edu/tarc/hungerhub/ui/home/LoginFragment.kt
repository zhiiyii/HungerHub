package my.edu.tarc.hungerhub.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentLoginBinding

@Suppress("UNREACHABLE_CODE")
class LoginFragment : Fragment() {

    private lateinit var database :DatabaseReference
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

        binding.buttonSignIn.setOnClickListener{
            val ic : String = binding.editTextLogInIC.text.toString()
            val pass: String = binding.editTextLogInPassword.text.toString()

            if(ic.isNotEmpty() && pass.isNotEmpty()){
                readData(ic,pass)
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

    private fun readData(ic : String ,pass : String){

        database = FirebaseDatabase.getInstance().getReference("User")
        database.child(ic).get().addOnSuccessListener {
            database.child(pass).get().addOnSuccessListener {
                if(binding.editTextLogInIC.text.toString() == ic && binding.editTextLogInPassword.text.toString() == pass ){
                    // create shared preference for login account details
                    val sharedPref = activity?.getSharedPreferences("Login", Context.MODE_PRIVATE)
                    if (sharedPref != null) {
                        with(sharedPref.edit()) {
                            this?.clear()
                            this?.apply()
                        }
                    }
                    with (sharedPref?.edit()) {
                        this?.putString("ic", binding.editTextLogInIC.text.toString())
                        this?.apply()
                    }

                    getActivity()?.let { it1 ->
                        Snackbar.make(
                            it1.findViewById(android.R.id.content),
                            "Login Success!", Snackbar.LENGTH_LONG).show()
                    }

                    findNavController().navigate(R.id.action_loginFragment_to_nav_request2)
                }else{
//                val snack = Snackbar.make(it,"Invalid email/Password!", Snackbar.LENGTH_LONG)
//                snack.show()
                    getActivity()?.let { it1 ->
                        Snackbar.make(
                            it1.findViewById(android.R.id.content),
                            "Ic or Password are incorrect!", Snackbar.LENGTH_LONG).show()
                    }
                }
            }.addOnFailureListener {
                //Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
            }
        }

    }
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