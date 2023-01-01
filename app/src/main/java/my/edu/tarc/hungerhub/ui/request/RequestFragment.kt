package my.edu.tarc.hungerhub.ui.request

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentRequestBinding
import java.util.*

class RequestFragment : Fragment() {

    private var _binding: FragmentRequestBinding? = null
    private val binding get() = _binding!!
    private val requestViewModel: RequestViewModel by viewModels()

    //private var darkStatusBar = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onStart() {
        super.onStart()

        binding.textViewTnc.setOnClickListener {
            binding.cardViewTnc.isVisible = true
        }

        binding.imageButtonCloseTnc.setOnClickListener {
            binding.cardViewTnc.isVisible = false
        }

        binding.buttonAgree.setOnClickListener {
            binding.checkBoxTnc.isChecked = true
            binding.cardViewTnc.isVisible = false
        }

        binding.buttonSubmit.setOnClickListener {
            // check empty fields
            if (binding.radioGroupMarital.checkedRadioButtonId == -1) {
                Snackbar.make(this.requireView(), getString(R.string.marital_required), Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.radioGroupJob.checkedRadioButtonId == -1) {
                Snackbar.make(this.requireView(), getString(R.string.job_required), Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.editTextIncome.text.toString().isEmpty()) {
                binding.editTextIncome.error = getString(R.string.value_required)
                return@setOnClickListener
            }
            if (binding.editTextReason.text.toString().isEmpty()) {
                binding.editTextReason.error = getString(R.string.value_required)
                return@setOnClickListener
            }

            // check agreement of TNC
            if (!binding.checkBoxTnc.isChecked) {
                Snackbar.make(this.requireView(), getString(R.string.tnc_required), Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // alert dialog to prompt confirmation
            val builder = AlertDialog.Builder(this.requireContext())
            builder.setTitle(R.string.dialog_title)
            builder.setMessage(R.string.dialog_message)
            builder.setIcon(R.drawable.alert)

            // press submit in dialog
            builder.setPositiveButton(R.string.submit) { _, _ ->
                /*
                // create a reference to the Shared Preference
                val sharedPref = activity?.getSharedPreferences(getString(R.string.form_pref),
                    Context.MODE_PRIVATE)?: return@setPositiveButton

                // get submit date and time
                val calendar = Calendar.getInstance()
                val format = SimpleDateFormat(" d MMM yyyy HH:mm:ss ")
                val time = format.format(calendar.time)

                binding.textView5.text = time

                with (sharedPref.edit()) {
                    val form = Request(
                        binding.radioGroupMarital.toString(),
                        binding.radioGroupJob.toString(),
                        binding.editTextIncome.text.toString().toInt(),
                        binding.editTextReason.text.toString(),
                        binding.editTextPax.text.toString().toInt(),
                        time
                    )
                    putString(getString(R.string.marital_status), form.maritalStatus)
                    putString(getString(R.string.job_status), form.jobStatus)
                    putString(getString(R.string.income), form.income.toString())
                    putString(getString(R.string.reason), form.reason)
                    putString(getString(R.string.pax), form.pax.toString())
                    putString(getString(R.string.submit_time), form.date)
                    apply()
                }
                 */

                // get submit date and time
                val calendar = Calendar.getInstance()
                val format = SimpleDateFormat(" d MMM yyyy HH:mm:ss ")
                val time = format.format(calendar.time)

                // TODO: test date and time display
                binding.textView5.text = time

                val request = Request(
                    binding.radioGroupMarital.toString(),
                    binding.radioGroupJob.toString(),
                    binding.editTextIncome.text.toString().toInt(),
                    binding.editTextReason.text.toString(),
                    binding.editTextPax.text.toString().toInt(),
                    time
                )

                // MainActivity.contactList.add(contact)
                requestViewModel.insert(request)

                Snackbar.make(this.requireActivity().findViewById(R.id.constraintLayout_request),
                    getString(R.string.form_submitted), Snackbar.LENGTH_SHORT).show()

                // TODO: navigate up to home page
                //findNavController().navigateUp()
            }

            // press cancel in dialog
            builder.setNeutralButton(R.string.cancel) { _, _ ->
                Toast.makeText(this.requireContext(), R.string.action_cancelled, Toast.LENGTH_SHORT).show()
            }

            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}