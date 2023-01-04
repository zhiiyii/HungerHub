package my.edu.tarc.hungerhub.ui.donation

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentDonation2Binding
import my.edu.tarc.hungerhub.ui.home.User
//import my.edu.tarc.hungerhub.ui.request.MyCallback

class DonationFragment2: Fragment() {
    private var _binding: FragmentDonation2Binding? = null
    private val binding get() = _binding!!

    private val totalCharacter = 19 // size of pattern 0000-0000-0000-0000
    private val totalDigits = 16 // max numbers of digits in pattern: 0000 x 4
    private val dividerModulo = 5 // means divider position is every 5th symbol beginning with 1
    private val dividerPosition = dividerModulo - 1 // means divider position is every 4th symbol beginning with 0
    private val divider= ' '

    val referenceUser = FirebaseDatabase.getInstance().getReference(getString(R.string.firebase_user))
    var database = FirebaseDatabase.getInstance().reference

    val sharedPref = activity?.getSharedPreferences("Login", Context.MODE_PRIVATE)
    val loginIc = sharedPref?.getString("ic", null)
    val findUser = referenceUser.orderByChild("ic").equalTo(loginIc)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDonation2Binding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //card number validation
        binding.editTextCardNumber.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) { // no operation
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) { // no operation
            }

            override fun afterTextChanged(s: Editable) {
                if (!isInputCorrect(s, totalCharacter, dividerModulo, divider)) {
                    var validCardNum = buildCorrectString(
                        getDigitArray(s, totalDigits),
                        dividerPosition,
                        divider
                    )
                    binding.editTextCardNumber.clearFocus();
                    binding.editTextCardNumber.setText(validCardNum);
                    binding.editTextCardNumber.requestFocus();
                    binding.editTextCardNumber.setSelection(validCardNum!!.length);
                }
            }

        })

        binding.buttonDonate.setOnClickListener {
            if (binding.editTextCardHolderName.text.isEmpty()){
                binding.editTextCardHolderName.setError(getString(R.string.input_empty))
                return@setOnClickListener
            }

            if(binding.editTextCVC.length()!=3){
                binding.editTextCVC.setError(getString(R.string.invalid_format))

                if(binding.editTextCVC.text.isEmpty()) {
                    binding.editTextCVC.setError(getString(R.string.input_empty))
                    return@setOnClickListener
                }
            }else {
                //store data into firebase
                findUser.addListenerForSingleValueEvent(object: ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists() && loginIc != null) {
                            Log.d("checkpoint", "got snapshot")
                            val children = loginIc.let { it1 -> dataSnapshot.child(it1) }
                            children.child("name").value.toString()
                            children.child("ic").value.toString()
                            children.child("email").value.toString()
                            children.child("phoneNo").value.toString()
                            children.child("address").value.toString()
                            children.child("posCode").value.toString()
                            children.child("state").value.toString()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })

                //store data
                val donateAmt = requireArguments().getInt("totalDonation").toString()
                val donor = Donor(
                    binding.editTextCardNumber.text.toString(),
                    binding.editTextCardHolderName.text.toString(),
                    binding.editTextCVC.text.toString(),
                    donateAmt
                )
                database.child("User").child(loginIc.toString()).child("donation").child("payment").setValue(donor)

                Toast.makeText(
                    context,
                    "Thanks for donating RM $donateAmt!",
                    Toast.LENGTH_SHORT
                ).show()


            }
            }
        }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //check the length
    private fun isInputCorrect(
        s: Editable,
        totalCharacters: Int,
        dividerModulo: Int,
        divider: Char
    ): Boolean {
        var isCorrect =
            s.length <= totalCharacters // check if it is 19 characters in entered string including separator
        for (i in 0 until s.length) { // check if every element is correct
            isCorrect = if (i > 0 && (i + 1) % dividerModulo == 0) {
                isCorrect and (divider == s[i])
            } else {
                isCorrect and Character.isDigit(s[i])
            }
        }
        return isCorrect
    }

    //add spaces after every 4 digits
    private fun buildCorrectString(
        digits: CharArray,
        dividerPosition: Int,
        divider: Char
    ): String? {
        val formatted = StringBuilder()
        for (i in digits.indices) { //loop i in the range of valid indices for the array
            if (digits[i] != '\u0000') {  //not equal to null
                formatted.append(digits[i])
                if (i > 0 && i < digits.size - 1 && (i + 1) % dividerPosition == 0) {
                    formatted.append(divider)
                }
            }
        }
        return formatted.toString()
    }

    //get the digit array input
    private fun getDigitArray(s: Editable, size: Int): CharArray {
        val digits = CharArray(size)
        var index = 0
        var i = 0
        while (i < s.length && index < size) {
            val current = s[i]
            if (Character.isDigit(current)) {
                digits[index] = current
                index++
            }
            i++
        }
        return digits
    }


}