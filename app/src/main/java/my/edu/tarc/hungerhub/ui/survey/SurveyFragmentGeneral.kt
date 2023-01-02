package my.edu.tarc.hungerhub.ui.survey

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentSurveyGeneralBinding


class SurveyFragmentGeneral : Fragment() {
    private var _binding: FragmentSurveyGeneralBinding? = null
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
        _binding = FragmentSurveyGeneralBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonNavGen2.setOnClickListener {
            val member1 = binding.radioButton13.isChecked
            val member2 = binding.radioButton46.isChecked
            val member3 = binding.radioButton79.isChecked
            val member4 = binding.radioButton10Above.isChecked

            if ((!member1) && (!member2) && (!member3) && (!member4)) {
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
            findNavController().navigate(R.id.action_surveyFragmentGeneral_to_surveyFragmentGeneral2)
        }

            binding.radioGroupMembersInHouse.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val selectedOption = when (checkedId) {
                    R.id.radioButton1_3 -> "1 - 3"
                    R.id.radioButton4_6 -> "4 - 6"
                    R.id.radioButton7_9 -> "7 - 9"
                    R.id.radioButton10_above -> "10 or above"
                    else -> ""
                }

                // Store the selected option in the Firebase Realtime Database
                dataRef.child("NumberOfMemberInHouse").setValue(selectedOption)
                //dataRef.setValue(selectedOption)

//            val checkedMember = binding.radioGroupMembersInHouse.checkedRadioButtonId.text.toString()
//            writeNumMemberdata(checkedMember)


        })

    }
}