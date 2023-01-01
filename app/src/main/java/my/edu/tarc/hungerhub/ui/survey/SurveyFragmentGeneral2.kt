package my.edu.tarc.hungerhub.ui.survey

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentSurveyGeneral2Binding
import my.edu.tarc.hungerhub.databinding.FragmentSurveyLackBinding

class SurveyFragmentGeneral2 : Fragment() {

    private var _binding: FragmentSurveyGeneral2Binding? = null
    private val binding get() = _binding!!


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

    }
}