package my.edu.tarc.hungerhub.ui.survey

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentSurveyGeneralBinding
import my.edu.tarc.hungerhub.databinding.FragmentSurveyLackBinding

class SurveyFragmentGeneral : Fragment() {
    private var _binding: FragmentSurveyGeneralBinding? = null
    private val binding get() = _binding!!


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

            findNavController().navigate(R.id.action_surveyFragmentGeneral_to_surveyFragmentGeneral2)
        }





    }
}