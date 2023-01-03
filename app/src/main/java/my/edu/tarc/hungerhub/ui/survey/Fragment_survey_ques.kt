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
import my.edu.tarc.hungerhub.databinding.FragmentSurveyBinding
import my.edu.tarc.hungerhub.databinding.FragmentSurveyQuesBinding

class fragment_survey_ques : Fragment() {

    private var _binding: FragmentSurveyQuesBinding? = null
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
        _binding = FragmentSurveyQuesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var index = 0
        val arrQuestion = arrayOf<String>(
            "How many members are there in your household",
            "How many members are working currently in your household",
            "How many meals do you and your family consume daily"
        )
        val arrAnswerA = arrayOf("1-3", "1-3", "1")
        val arrAnswerB = arrayOf("4-6", "4-6", "2")
        val arrAnswerC = arrayOf("7-9", "7-9", "3")
        val arrAnswerD = arrayOf("10 or above", "10 or above", "4 or above")

        binding.textViewQues.text = String.format(arrQuestion.get(index))
        binding.radioButtonQ1.text = String.format(arrAnswerA.get(index))
        binding.radioButtonQ2.text = String.format(arrAnswerB.get(index))
        binding.radioButtonQ3.text = String.format(arrAnswerC.get(index))
        binding.radioButtonQ4.text = String.format(arrAnswerD.get(index))
        if(index==0) {
            binding.radioGroupTest.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val selectedOption = when (checkedId) {
                R.id.radioButtonQ1 -> "1-3"
                R.id.radioButtonQ2 -> "4-6"
                R.id.radioButtonQ3 -> "7-9"
                R.id.radioButtonQ4 -> "10 or above"
                else -> ""
            }

            // Store the selected option in the Firebase Realtime Database
         dataRef.child("NumMemberInHouse").setValue(selectedOption)
            //dataRef.setValue(selectedOption)

        })

        index++


        }

        binding.buttonNextQues.setOnClickListener {
            //binding.radioGroupTest.clearCheck()
            if (index<=arrQuestion.size-1) {

                binding.textViewQues.text = String.format(arrQuestion.get(index))
                binding.radioButtonQ1.text = String.format(arrAnswerA.get(index))
                binding.radioButtonQ2.text = String.format(arrAnswerB.get(index))
                binding.radioButtonQ3.text = String.format(arrAnswerC.get(index))
                binding.radioButtonQ4.text = String.format(arrAnswerD.get(index))
                check(index)
                index++

            }

            else{
                findNavController().navigate(R.id.action_fragment_survey_ques_to_fragment_survey_ques1)

            }
        }
    }

    private fun check(index: Int) {

        when (index) {
            1 -> {
                binding.radioGroupTest.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val selectedOption = when (checkedId) {
                        R.id.radioButtonQ1 -> "1-3"
                        R.id.radioButtonQ2 -> "4-6"
                        R.id.radioButtonQ3 -> "7-9"
                        R.id.radioButtonQ4 -> "10 or above"
                        else -> ""
                    }

                    // Store the selected option in the Firebase Realtime Database
                    dataRef.child("MembersWorking").setValue(selectedOption)

                })

                binding.radioGroupTest.clearCheck()
            }
            2 -> {
                binding.radioGroupTest.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val selectedOption = when (checkedId) {
                        R.id.radioButtonQ1 -> "1"
                        R.id.radioButtonQ2 -> "2"
                        R.id.radioButtonQ3 -> "3"
                        R.id.radioButtonQ4 -> "4 or above"
                        else -> ""
                    }
                    dataRef.child("MealsConsumeDaily").setValue(selectedOption)
                })
                binding.radioGroupTest.clearCheck()
            }
//            3 -> {
//                findNavController().navigate(R.id.action_fragment_survey_ques_to_fragment_survey_ques1)
//            }
        }

    }

}