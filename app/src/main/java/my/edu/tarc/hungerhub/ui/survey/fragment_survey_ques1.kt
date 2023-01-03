package my.edu.tarc.hungerhub.ui.survey

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
import my.edu.tarc.hungerhub.databinding.FragmentSurveyQues1Binding
import my.edu.tarc.hungerhub.databinding.FragmentSurveyQuesBinding

class fragment_survey_ques1 : Fragment() {
    private var _binding: FragmentSurveyQues1Binding? = null
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

        if (index == 0) {
            binding.radioGroupQues2.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val selectedOption = when (checkedId) {
                    R.id.radioButtonQQ1 -> "Yes"
                    R.id.radioButtonQQ2 -> "No"
                    else -> ""
                }

                // Store the selected option in the Firebase Realtime Database
                dataRef.child("FamilyIncomeAbleToCoverDailyFoodExpenses").setValue(selectedOption)
                //dataRef.setValue(selectedOption)

            })
            index++

        }
        binding.buttonNextSurvey.setOnClickListener {
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

        if (index == 1) {
            binding.radioGroupQues2.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val selectedOption = when (checkedId) {
                    R.id.radioButtonQQ1 -> "Yes"
                    R.id.radioButtonQQ2 -> "No"
                    else -> ""
                }

                // Store the selected option in the Firebase Realtime Database
                dataRef.child("FoodPriceReasonable").setValue(selectedOption)
                //dataRef.setValue(selectedOption)

            })
            binding.radioGroupQues2.clearCheck()
        }
    }
}