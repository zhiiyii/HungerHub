package my.edu.tarc.hungerhub.ui.survey

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentSurveyQues1Binding
import my.edu.tarc.hungerhub.databinding.FragmentSurveyQuesBinding

class fragment_survey_ques1 : Fragment() {
    private var _binding: FragmentSurveyQues1Binding? = null
    private val binding get() = _binding!!

    var database = FirebaseDatabase.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSurveyQues1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var index = 0
        val arrQuestion = arrayOf<String>(
            "Is your family income able to cover daily food expenses",
            "Do you think the food price in your area is reasonable/affordable?"
        )
        val arrAnswerA = arrayOf("Yes", "Yes")
        val arrAnswerB = arrayOf("No", "No")

        binding.textViewQues1.text = String.format(arrQuestion.get(index))
        binding.radioButtonQQ1.text = String.format(arrAnswerA.get(index))
        binding.radioButtonQQ2.text = String.format(arrAnswerB.get(index))

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

        if (index == 0) {
            binding.radioGroupQues2.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val selectedOption = when (checkedId) {
                    R.id.radioButtonQQ1 -> "Yes"
                    R.id.radioButtonQQ2 -> "No"
                    else -> ""
                }


                // Store the selected option in the Firebase Realtime Database
                database.child("User").child(loginIc.toString()).child("survey").child("FamilyIncomeAbleToCoverDailyFoodExpenses").setValue(selectedOption)
                //dataRef.setValue(selectedOption)

            })
            index++

        }
        binding.buttonNextSurvey.setOnClickListener {
            val member1 = binding.radioButtonQQ1.isChecked
            val member2 = binding.radioButtonQQ2.isChecked

            if((!member1)&&(!member2)){
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
            if (index <= arrQuestion.size - 1) {
                binding.textViewQues1.text = String.format(arrQuestion.get(index))
                binding.radioButtonQQ1.text = String.format(arrAnswerA.get(index))
                binding.radioButtonQQ2.text = String.format(arrAnswerB.get(index))
                check(index)
                index++
            } else {
                findNavController().navigate(R.id.action_fragment_survey_ques1_to_surveyFragmentRate1)
            }
        }
    }

    private fun check(index: Int) {

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

        if (index == 1) {
            binding.radioGroupQues2.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val selectedOption = when (checkedId) {
                    R.id.radioButtonQQ1 -> "Yes"
                    R.id.radioButtonQQ2 -> "No"
                    else -> ""
                }

                // Store the selected option in the Firebase Realtime Database
                database.child("User").child(loginIc.toString()).child("survey").child("FoodPriceReasonable").setValue(selectedOption)
                //dataRef.setValue(selectedOption)

            })
            binding.radioGroupQues2.clearCheck()
        }
    }
}