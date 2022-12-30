package my.edu.tarc.hungerhub.ui.survey

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.ActivityMainBinding
import my.edu.tarc.hungerhub.databinding.FragmentSurveyBinding
import kotlin.math.pow
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController

class SurveyFragment : Fragment() {

    private var _binding: FragmentSurveyBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSurveyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set up the button click listener
        binding.buttonCalculate.setOnClickListener {
            val heightStr = binding.editTextNumberHeight.text.toString()
            val weightStr = binding.editTextNumberWeight.text.toString()

            // Validate the input
            if (heightStr.isEmpty() || weightStr.isEmpty()) {
                binding.textViewResult.text = "Please enter both height and weight."
            }

            // Convert the input to numbers
            val height = heightStr.toDouble()
            val weight = weightStr.toDouble()

            // Calculate the BMI
            val bmi = (weight / height / height) * 10000

            // Display the result
            binding.textViewResult.text = "Your BMI is " + String.format("%.2f", bmi)
            binding.textViewStatus.text = "Status: " + messages(bmi)
        }

        binding.buttonNav2.setOnClickListener {
            findNavController().navigate(R.id.action_nav_survey_to_surveyFragment2)
        }


        binding.buttonReset.setOnClickListener {
            binding.editTextNumberHeight.setText("")
            binding.editTextNumberWeight.setText("")
            binding.textViewResult.text = ""
            binding.textViewStatus.text = ""
        }
    }


    private fun messages(bmi:Double):String{
        return if (bmi<18.5)
            "Underweight"
        else if (bmi<25.0)
            "Healthy"
        else
            "Overweight"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

