package my.edu.tarc.hungerhub.ui.survey

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentSurveyLackBinding

class SurveyLackFragment : Fragment() {
    private var _binding: FragmentSurveyLackBinding? = null
    private val binding get() = _binding!!

    var database = FirebaseDatabase.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSurveyLackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val referenceUser = FirebaseDatabase.getInstance().getReference(getString(R.string.firebase_user))

        val sharedPref = activity?.getSharedPreferences("Login", Context.MODE_PRIVATE)
        val loginIc = sharedPref?.getString("ic", null)
        val findUser = referenceUser.orderByChild("ic").equalTo(loginIc)

        findUser.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists() && loginIc != null) {
                    Log.d("checkpoint", "got snapshot")
                    val children = loginIc.let { it1 -> dataSnapshot.child(it1) }
                    val name = children.child("name").value.toString()
                    val ic = children.child("ic").value.toString()
                    val email = children.child("email").value.toString()
                    val phoneNo = children.child("phoneNo").value.toString()
                    val address = children.child("address").value.toString()
                    val postcode = children.child("posCode").value.toString()
                    val state = children.child("state").value.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

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

            // Initialize a new instance of alert dialog builder object
            val builder = AlertDialog.Builder(this.requireContext())

            // Set a title for alert dialog
            builder.setTitle("Confirm Submit")

            // Set a message for alert dialog
            builder.setMessage("Are you sure you want to submit the form?")

            // On click listener for dialog buttons
            val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
                when(which){
                    DialogInterface.BUTTON_POSITIVE -> {
                        // Submit the form
                        val snack = Snackbar.make(it,"Form submitted",Snackbar.LENGTH_LONG)
                        snack.show()

                        findNavController().navigate(R.id.action_surveyFragmentLack_to_mainMenuFragment)
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        // Cancel the dialog
                        val snack = Snackbar.make(it,"Action Cancelled",Snackbar.LENGTH_LONG)
                        snack.show()
                    }
                }
            }

            // Set the alert dialog positive/yes button
            builder.setPositiveButton("YES",dialogClickListener)

            // Set the alert dialog negative/no button
            builder.setNegativeButton("NO",dialogClickListener)

            // Initialize the AlertDialog using builder object
            val alertDialog = builder.create()

            // Finally, display the alert dialog
            alertDialog.show()


//            val snack = Snackbar.make(it,"Form submitted",Snackbar.LENGTH_LONG)
//            snack.show()
//
//            findNavController().navigate(R.id.action_surveyFragmentLack_to_mainMenuFragment)
        }

        binding.checkBoxWheat.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            // Store the selected option in the Firebase Realtime Database
            if (isChecked) {
                database.child("User").child(loginIc.toString()).child("survey").child("LackOfWheat").setValue(true)
            } else {
                database.child("User").child(loginIc.toString()).child("survey").child("LackOfWheat").removeValue()
            }
            //dataRef.child("LackOfWheat").setValue(isChecked)
        })

        binding.checkBoxVege.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            // Store the selected option in the Firebase Realtime Database
            if (isChecked) {
                database.child("User").child(loginIc.toString()).child("survey").child("LackOfVege").setValue(true)
            } else {
                database.child("User").child(loginIc.toString()).child("survey").child("LackOfVege").removeValue()
            }
            //dataRef.child("LackOfVege").setValue(isChecked)
        })
        binding.checkBoxMeat.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            // Store the selected option in the Firebase Realtime Database
            //dataRef.child("LackOfMeat").setValue(isChecked)
            if (isChecked) {
                database.child("User").child(loginIc.toString()).child("survey").child("LackOfMeat").setValue(true)
            } else {
                database.child("User").child(loginIc.toString()).child("survey").child("LackOfMeat").removeValue()
            }
        })
        binding.checkBoxFats.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            // Store the selected option in the Firebase Realtime Database
            if (isChecked) {
                database.child("User").child(loginIc.toString()).child("survey").child("LackOfFats").setValue(true)
            } else {
                database.child("User").child(loginIc.toString()).child("survey").child("LackOfFats").removeValue()
            }
            //dataRef.child("LackOfFats").setValue(isChecked)
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}