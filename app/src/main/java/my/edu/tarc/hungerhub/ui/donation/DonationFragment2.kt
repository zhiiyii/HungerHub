package my.edu.tarc.hungerhub.ui.donation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentDonation2Binding

class DonationFragment2: Fragment() {
    private var _binding: FragmentDonation2Binding? = null
    private val binding get() = _binding!!

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

        //validate card number input
        binding.editTextCardNumber.addTextChangedListener(object : TextWatcher {
            private val TOTAL_CHARS = 19 // size of pattern 0000-0000-0000-0000
            private val TOTAL_DIGITS = 16 // max numbers of digits in pattern: 0000 x 4
            private val DIVIDER_MODULO = 5 // means divider position is every 5th symbol beginning with 1
            private val DIVIDER_POSITION = DIVIDER_MODULO - 1 // means divider position is every 4th symbol beginning with 0
            private val DIVIDER = ' '
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
                if (!isInputCorrect(s, TOTAL_CHARS, DIVIDER_MODULO, DIVIDER)) {
                    var validCardNum = buildCorrectString(
                        getDigitArray(s, TOTAL_DIGITS),
                        DIVIDER_POSITION,
                        DIVIDER
                    )
                    binding.editTextCardNumber.clearFocus();
                    binding.editTextCardNumber.setText(validCardNum);
                    binding.editTextCardNumber.requestFocus();
                    binding.editTextCardNumber.setSelection(validCardNum!!.length);
                }
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

        })

        binding.buttonDonate.setOnClickListener {
            val reference = FirebaseDatabase.getInstance().getReference("Donors")


            if(binding.editTextCardNumber.text.toString().length != 19){
                binding.editTextCardNumber.setError(getString(R.string.invalid_format))
                return@setOnClickListener
            }

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

                val donateAmt = requireArguments().getInt("totalDonation").toString()
                Toast.makeText(context, "You have successfully donated RM $donateAmt", Toast.LENGTH_SHORT).show()

                val donor = Donor(
                    binding.editTextCardNumber.text.toString(),
                    binding.editTextCardHolderName.text.toString(),
                    binding.editTextCVC.text.toString(),
                    donateAmt
                )

                reference.child(binding.editTextCardNumber.text.toString()).setValue(donor)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}