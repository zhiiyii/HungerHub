package my.edu.tarc.hungerhub.ui.survey

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentSurveyGeneral3Binding
import my.edu.tarc.hungerhub.databinding.FragmentSurveyLackBinding

class SurveyFragmentGeneral3 : Fragment() {
    private var _binding: FragmentSurveyGeneral3Binding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSurveyGeneral3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonNavGen4.setOnClickListener {
            val member1 = binding.radioButtonMealOne.isChecked
            val member2 = binding.radioButtonMealTwo.isChecked
            val member3 = binding.radioButtonMealThree.isChecked
            val member4 = binding.radioButtonMealFourAbove.isChecked

            if((!member1)&&(!member2)&&(!member3)&&(!member4)){
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
            findNavController().navigate(R.id.action_surveyFragmentGeneral3_to_surveyFragmentGeneral4)
        }


    }

}