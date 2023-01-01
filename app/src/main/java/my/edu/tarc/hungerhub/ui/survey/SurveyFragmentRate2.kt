package my.edu.tarc.hungerhub.ui.survey

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentSurveyRate2Binding

class SurveyFragmentRate2 : Fragment() {
    private var _binding: FragmentSurveyRate2Binding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSurveyRate2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonNavLack.setOnClickListener {
            val ReasonableYes = binding.radioButtonReasonableYes.isChecked
            val ReasonableNo = binding.radioButtonReasonableNo.isChecked

            if((!ReasonableYes)&&(!ReasonableNo)){
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
            findNavController().navigate(R.id.action_surveyFragmentRate2_to_surveyFragmentLack)
        }


    }
}