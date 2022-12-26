package my.edu.tarc.hungerhub.ui.request

import android.app.Activity
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
            // TODO: check empty fields
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

            // TODO: check format
            if (binding.editTextIncome.text.toString().toInt() < 0) {
                binding.editTextIncome.error = getString(R.string.invalid_input)
                return@setOnClickListener
            }

            // TODO: check agreement of TNC
            if (!binding.checkBoxTnc.isChecked) {
                Toast.makeText(this.requireContext(), getString(R.string.tnc_required), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // TODO: prompt confirmation

            // TODO: submit form & store data

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}