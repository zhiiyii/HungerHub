package my.edu.tarc.hungerhub

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import my.edu.tarc.hungerhub.databinding.FragmentMainMenuBinding

class MainMenuFragment: Fragment()  {
    private var _binding: FragmentMainMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        readLoginData()

        binding.imageButtonDonate.setOnClickListener{
            findNavController().navigate(R.id.action_mainMenuFragment_to_nav_donation)
        }
        binding.textViewDonateMainMenu.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_nav_donation)
        }

        binding.imageButtonRequest.setOnClickListener{
            findNavController().navigate(R.id.action_mainMenuFragment_to_nav_request)
        }
        binding.textViewRequestMainMenu.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_nav_request)
        }

        binding.imageButtonSurvey.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_nav_survey)
        }
        binding.textViewSurveyMainMenu.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_nav_survey)
        }

        binding.imageButtonInfo.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://nutritionfacts.org/")
            startActivity(intent)
        }
        binding.textViewMoreInfo.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://nutritionfacts.org/")
            startActivity(intent)
        }
    }

    private fun readLoginData() {
        val referenceUser = FirebaseDatabase.getInstance().getReference(getString(R.string.firebase_user))

        val sharedPref = activity?.getSharedPreferences("Login", Context.MODE_PRIVATE)
        val loginIc = sharedPref?.getString("ic", null)
        val findUser = referenceUser.orderByChild("ic").equalTo(loginIc)

        findUser.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists() && loginIc != null) {
                    val children = loginIc.let { it1 -> dataSnapshot.child(it1) }
                    val name = children.child("name").value.toString()
                    val email = children.child("email").value.toString()
                    val phoneNo = children.child("phoneNo").value.toString()

                    with(sharedPref.edit()) {
                        this?.putString("name", name)
                        this?.putString("email", email)
                        this?.putString("phone", phoneNo)
                        this?.apply()
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("firebase", "firebase error")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}