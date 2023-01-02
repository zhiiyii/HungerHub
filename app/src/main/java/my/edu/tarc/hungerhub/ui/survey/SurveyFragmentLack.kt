package my.edu.tarc.hungerhub.ui.survey

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentSurveyLackBinding

class SurveyFragmentLack : Fragment() {
    private var _binding: FragmentSurveyLackBinding? = null
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
        _binding = FragmentSurveyLackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageButtonFoodPyramidInfo.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("http://www.myhealth.gov.my/en/malaysian-food-pyramid-2/")
            startActivity(intent)
        }

        binding.buttonSubmitSurvey.setOnClickListener {
            val wheat = binding.checkBoxWheat.isChecked
            val vege = binding.checkBoxVege.isChecked
            val meat = binding.checkBoxMeat.isChecked
            val fats = binding.checkBoxFats.isChecked

            if((!wheat)&&(!vege)&&(!meat)&&(!fats)){
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
            val snack = Snackbar.make(it,"Form submitted",Snackbar.LENGTH_LONG)
            snack.show()

            //findNavController().navigate(R.id.action_surveyFragmentRate2_to_surveyFragmentLack2)
        }

        binding.checkBoxWheat.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            // Store the selected option in the Firebase Realtime Database
            if (isChecked) {
                dataRef.child("LackOfWheat").setValue(true)
            } else {
                dataRef.child("LackOfWheat").removeValue()
            }
            //dataRef.child("LackOfWheat").setValue(isChecked)
        })

        binding.checkBoxVege.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            // Store the selected option in the Firebase Realtime Database
            if (isChecked) {
                dataRef.child("LackOfVege").setValue(true)
            } else {
                dataRef.child("LackOfVege").removeValue()
            }
            //dataRef.child("LackOfVege").setValue(isChecked)
        })
        binding.checkBoxMeat.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            // Store the selected option in the Firebase Realtime Database
            //dataRef.child("LackOfMeat").setValue(isChecked)
            if (isChecked) {
                dataRef.child("LackOfMeat").setValue(true)
            } else {
                dataRef.child("LackOfMeat").removeValue()
            }
        })
        binding.checkBoxFats.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            // Store the selected option in the Firebase Realtime Database
            if (isChecked) {
                dataRef.child("LackOfFats").setValue(true)
            } else {
                dataRef.child("LackOfFats").removeValue()
            }
            //dataRef.child("LackOfFats").setValue(isChecked)
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}