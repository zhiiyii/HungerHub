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
import my.edu.tarc.hungerhub.databinding.FragmentSurveyGeneral3Binding
import my.edu.tarc.hungerhub.databinding.FragmentSurveyGeneral4Binding

class SurveyFragmentGeneral4 : Fragment() {
    private var _binding: FragmentSurveyGeneral4Binding? = null
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
        _binding = FragmentSurveyGeneral4Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonNavRate1.setOnClickListener {
            val cover_expen_yes = binding.radioButtonCoverExpenYes.isChecked
            val cover_expen_no = binding.radioButtonCoverExpenNo.isChecked

            if((!cover_expen_yes)&&(!cover_expen_no)){
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
            findNavController().navigate(R.id.action_surveyFragmentGeneral4_to_surveyFragmentRate1)
        }

        binding.radioGroupFoodExpensesCover.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            val selectedOption = when (checkedId) {
                R.id.radioButtonCoverExpenYes -> "Yes"
                R.id.radioButtonCoverExpenNo -> "No"
                else -> ""
            }

            // Store the selected option in the Firebase Realtime Database
            dataRef.child("IncomeAbleToCoverExpenses").setValue(selectedOption)
            //dataRef.setValue(selectedOption)

//            val checkedMember = binding.radioGroupMembersInHouse.checkedRadioButtonId.text.toString()
//            writeNumMemberdata(checkedMember)


        })


    }
}
