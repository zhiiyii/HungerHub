package my.edu.tarc.hungerhub.ui.request

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentRequestBinding

class RequestFragment : Fragment() {

    private var _binding: FragmentRequestBinding? = null
    private val binding get() = _binding!!

    private var darkStatusBar = false

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

        val fab : View? = requireActivity().findViewById(R.id.fab)
        fab!!.isVisible = false
    }

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
                Toast.makeText(this.requireContext(), getString(R.string.marital_required), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (binding.radioGroupJob.checkedRadioButtonId == -1) {
                Toast.makeText(this.requireContext(), getString(R.string.job_required), Toast.LENGTH_LONG).show()
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
                Toast.makeText(this.requireContext(), getString(R.string.tnc_required), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // alert dialog to prompt confirmation
            val builder = AlertDialog.Builder(this.requireContext())
            builder.setTitle(R.string.dialog_title)
            builder.setMessage(R.string.dialog_message)
            builder.setIcon(R.drawable.alert)

            // press submit in dialog
            builder.setPositiveButton(R.string.submit) { dialogInterface, which ->
                // create a reference to the Shared Preference
                val sharedPref = activity?.getSharedPreferences(getString(R.string.form_pref),
                    Context.MODE_PRIVATE)?: return@setPositiveButton

                with (sharedPref.edit()) {
                    val form = Form(
                        binding.radioGroupMarital.toString(),
                        binding.radioGroupJob.toString(),
                        binding.editTextIncome.text.toString().toInt(),
                        binding.editTextReason.text.toString()
                    )
                    putString(getString(R.string.marital_status), form.maritalStatus)
                    putString(getString(R.string.job_status), form.jobStatus)
                    putString(getString(R.string.income), form.income.toString())
                    putString(getString(R.string.reason), form.reason)
                    apply()
                }

                Snackbar.make(this.requireActivity().findViewById(R.id.constraintLayout_request),
                    getString(R.string.form_submitted), Snackbar.LENGTH_SHORT).show()
            }

            // press cancel in dialog
            builder.setNeutralButton(R.string.cancel) { dialogInterface, which ->
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