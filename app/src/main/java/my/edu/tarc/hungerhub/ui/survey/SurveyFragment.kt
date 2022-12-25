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

class SurveyFragment : Fragment() {

    private var _binding: FragmentSurveyBinding? = null
    private val binding get() = _binding!!
    private lateinit var heightInput: EditText
    private lateinit var weightInput: EditText
    private lateinit var calculateButton: Button
    private lateinit var resetButton: Button
    private lateinit var resultText: TextView
    private lateinit var statusText: TextView
    private lateinit var nextButton: Button

    private var allInputsFilled = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_survey, container, false)


        // Initialize the views
        heightInput = view.findViewById(R.id.editTextNumberHeight)
        weightInput = view.findViewById(R.id.editTextNumberWeight)
        calculateButton = view.findViewById(R.id.buttonCalculate)
        resetButton = view.findViewById(R.id.buttonReset)
        resultText = view.findViewById(R.id.textViewResult)
        statusText = view.findViewById(R.id.textViewStatus)
      //  nextButton = view.findViewById(R.id.imageButtonNext)

        // Set up the input field watchers
//        heightInput.addTextChangedListener(InputFieldWatcher())
//        weightInput.addTextChangedListener(InputFieldWatcher())

        // Set up the Next button click listener
//        nextButton.setOnClickListener {
//            // Advance to the next page
//            navigateToNextFragment()
//        }

        // Set up the button click listener
        calculateButton.setOnClickListener {
            calculateBMI()
        }

        resetButton.setOnClickListener{
            heightInput.setText("")
            weightInput.setText("")
            resultText.text = ""
            statusText.text = ""
        }

        return view
//       _binding = FragmentSurveyBinding.inflate(inflater, container, false)
//       return binding.root
    }

//    inner class InputFieldWatcher : TextWatcher {
//        override fun afterTextChanged(s: Editable?) {
//            // Check if the input field is empty
//            if (s.isNullOrEmpty()) {
//                // If the input field is empty, set allInputsFilled to false and disable the Next button
//                allInputsFilled = false
//                nextButton.isEnabled = false
//            } else {
//                // If the input field is not empty, set allInputsFilled to true and enable the Next button if all input fields are filled out
//                allInputsFilled = heightInput.text.isNotEmpty() && weightInput.text.isNotEmpty()
//                nextButton.isEnabled = allInputsFilled
//            }
//        }
//
//        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            // This method is called before the text in the input field is changed
//        }
//
//        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            // This method is called when the text in the input field is changed
//        }
//    }

//    private fun navigateToNextFragment() {
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        val nextFragment = SurveyFragment2()
//        fragmentTransaction.replace(R.id.fragment_container, nextFragment)
//        fragmentTransaction.addToBackStack(null)
//        fragmentTransaction.commit()
//    }



    private fun calculateBMI() {
        // Get the user's input
        val heightStr = heightInput.text.toString()
        val weightStr = weightInput.text.toString()

        // Validate the input
        if (heightStr.isEmpty() || weightStr.isEmpty()) {
            resultText.text = "Please enter both height and weight."
            return
        }

        // Convert the input to numbers
        val height = heightStr.toDouble()
        val weight = weightStr.toDouble()

        // Calculate the BMI
        val bmi = (weight / height / height) * 10000

        // Display the result
        resultText.text = "Your BMI is ${"%.2f".format(bmi)}"
        statusText.text = "Status:  ${messages(bmi)}"

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