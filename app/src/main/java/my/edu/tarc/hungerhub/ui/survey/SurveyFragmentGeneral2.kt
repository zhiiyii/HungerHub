package my.edu.tarc.hungerhub.ui.survey

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentSurveyGeneral2Binding
import my.edu.tarc.hungerhub.databinding.FragmentSurveyLackBinding

class SurveyFragmentGeneral2 : Fragment() {

    private var _binding: FragmentSurveyGeneral2Binding? = null
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
        _binding = FragmentSurveyGeneral2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonNavGen3.setOnClickListener {
            val Work1_3 = binding.radioButtonWork13.isChecked
            val Work4_6 = binding.radioButtonWorking46.isChecked
            val Work7_9 = binding.radioButtonWorking79.isChecked
            val Work10_above = binding.radioButtonWorking10Above.isChecked

            if((!Work1_3)&&(!Work4_6)&&(!Work7_9)&&(!Work10_above)){
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
            findNavController().navigate(R.id.action_surveyFragmentGeneral2_to_surveyFragmentGeneral3)
        }

        binding.radioGroupWorking.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            val selectedOption = when (checkedId) {
                R.id.radioButtonWork1_3 -> "1 - 3"
                R.id.radioButtonWorking4_6 -> "4 - 6"
                R.id.radioButtonWorking7_9 -> "7 - 9"
                R.id.radioButtonWorking10_above -> "10 or above"
                else -> ""
            }
            // Store the selected option in the Firebase Realtime Database
            dataRef.child("NumberOfMemberWorking").setValue(selectedOption)
            //dataRef.setValue(selectedOption)

//            val checkedMember = binding.radioGroupMembersInHouse.checkedRadioButtonId.text.toString()
//            writeNumMemberdata(checkedMember)
        })

    }
}