package my.edu.tarc.hungerhub.ui.survey

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentSurveyBinding
import my.edu.tarc.hungerhub.model.bmiData

//import my.edu.tarc.hungerhub.ui.request.UserListCallback


class SurveyFragment : Fragment() {

    private var _binding: FragmentSurveyBinding? = null
    private val binding get() = _binding!!

    var database = FirebaseDatabase.getInstance().reference


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
            val heightStr = binding.editTextNumberHeight
            val weightStr = binding.editTextNumberWeight
            val result = binding.textViewResult.text.toString()
            val status = binding.textViewStatus.text.toString()
            //database = FirebaseDatabase.getInstance().getReference("BMI_data")
            if(heightStr.text.isEmpty()){
                heightStr.setError(getString(R.string.value_required))
                return@setOnClickListener
            }
            if(weightStr.text.isEmpty()) {
                weightStr.setError(getString(R.string.value_required))
                return@setOnClickListener
            }
            val height = binding.editTextNumberHeight.text.toString()
            val weight = binding.editTextNumberWeight.text.toString()

            writeBMIdata(height,weight,result,status)

            findNavController().navigate(R.id.action_nav_survey_to_fragment_survey_ques)
//            val intent = Intent(this, activity_survey::class.java)
//            startActivity(intent)
        }

        binding.buttonReset.setOnClickListener {
            binding.editTextNumberHeight.setText("")
            binding.editTextNumberWeight.setText("")
            binding.textViewResult.text = ""
            binding.textViewStatus.text = ""
        }

    }

    private fun writeBMIdata(height:String, weight:String, bmi:String, status:String){

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
        val bmiDatas = bmiData(height,weight, bmi, status)
        database.child("User").child(loginIc.toString()).child("survey").child("bmi").setValue(bmiDatas)
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


