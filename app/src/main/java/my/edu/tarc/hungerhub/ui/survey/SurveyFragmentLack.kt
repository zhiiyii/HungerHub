package my.edu.tarc.hungerhub.ui.survey

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentSurveyLackBinding

class SurveyFragmentLack : Fragment() {
    private var _binding: FragmentSurveyLackBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSurveyLackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageButtonFoodPyramidInfo.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("http://www.myhealth.gov.my/en/malaysian-food-pyramid-2/")
            startActivity(intent)
        }

        binding.buttonSubmitSurvey.setOnClickListener {
            val wheat = binding.checkBoxWheat.isChecked
            val vege = binding.checkBoxVege.isChecked
            val meat = binding.checkBoxMeat.isChecked
            val fats = binding.checkBoxFats.isChecked

            if((!wheat)&&(!vege)&&(!meat)&&(!fats)){
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

            //findNavController().navigate(R.id.action_surveyFragmentRate2_to_surveyFragmentLack2)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}