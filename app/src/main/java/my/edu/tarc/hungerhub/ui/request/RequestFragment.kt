package my.edu.tarc.hungerhub.ui.request

import android.annotation.SuppressLint
import android.app.AlertDialog
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
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentRequestBinding
import my.edu.tarc.hungerhub.model.Request
import my.edu.tarc.hungerhub.model.RequestViewModel
import java.util.Calendar

class RequestFragment: Fragment() {

    private var _binding: FragmentRequestBinding? = null
    private val binding get() = _binding!!

    private val requestViewModel: RequestViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onStart() {
        super.onStart()

        // open and close TNC popup card view
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
            if (binding.spinnerMarital.selectedItemPosition == 0) {
                Snackbar.make(this.requireView(), getString(R.string.marital_required), Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.spinnerJob.selectedItemPosition == 0) {
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
            if (binding.editTextPax.text.toString().isEmpty()) {
                binding.editTextPax.error = getString(R.string.value_required)
                return@setOnClickListener
            }

            // validate pax amount
            if (binding.editTextPax.text.toString().toInt() > 10) {
                binding.editTextPax.error = getString(R.string.invalid_pax)
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

            // TODO: maybe 1 time a week?
            // press submit in dialog
            builder.setPositiveButton(R.string.submit) { _, _ ->
                val reference = FirebaseDatabase.getInstance().getReference(getString(R.string.firebase_req))

                // get submit date and time
                val dateFormat = "yyyy/MM/dd HH:mm:ss"
                val calendar = Calendar.getInstance()
                val format = SimpleDateFormat(dateFormat)
                val time = format.format(calendar.time)

                val request = Request(time,
                    binding.editTextIncome.text.toString().toInt(),
                    binding.spinnerJob.selectedItem.toString(),
                    binding.spinnerMarital.selectedItem.toString(),
                    binding.editTextPax.text.toString().toInt(),
                    binding.editTextReason.text.toString(),
                    getString(R.string.pending)
                )

                // save data in firebase and DAO database
                reference.child(time).setValue(request)
                requestViewModel.insert(request)

                Snackbar.make(this.requireActivity().findViewById(R.id.constraintLayout_request),
                    getString(R.string.form_submitted), Snackbar.LENGTH_SHORT).show()

                findNavController().navigateUp()
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