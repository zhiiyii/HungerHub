package my.edu.tarc.hungerhub.ui.survey

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentSurveyGeneral4Binding
import my.edu.tarc.hungerhub.databinding.FragmentSurveyRate1Binding

class SurveyFragmentRate1 : Fragment() {
    private var _binding: FragmentSurveyRate1Binding? = null
    private val binding get() = _binding!!

    var mAuth: FirebaseAuth? = FirebaseAuth.getInstance()
    var currentUser: FirebaseUser? = mAuth?.getCurrentUser()

    var database = FirebaseDatabase.getInstance().reference
    var dataRef = database.child("survey").child(currentUser.toString()).child("data")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSurveyRate1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonNavRate2.setOnClickListener {
            val rating = binding.ratingBar.rating
            if(rating==0f){
                // Show error message using alert dialog
                val errorMessage = getString(R.string.value_required)
                val builder = AlertDialog.Builder(requireActivity())
                builder.setTitle("Error")
                    .setMessage(errorMessage)
                    .setPositiveButton("OK", null)
                val alertDialog = builder.create()
                alertDialog.show()

                return@setOnClickListener
            }
            findNavController().navigate(R.id.action_surveyFragmentRate1_to_surveyFragmentRate2)
        }

        binding.ratingBar.setOnRatingBarChangeListener(RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser ->
            // Store the rating value in the Firebase Realtime Database
            dataRef.child("RatingHygieneLivingEnvironment").setValue(rating)
            //dataRef.setValue(rating)
        })


    }
}