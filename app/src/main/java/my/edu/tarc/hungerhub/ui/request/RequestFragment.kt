package my.edu.tarc.hungerhub.ui.request

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.databinding.FragmentRequestBinding
import my.edu.tarc.hungerhub.model.Request
import my.edu.tarc.hungerhub.model.RequestViewModel
import java.util.Calendar

class RequestFragment: Fragment() {

    private var _binding: FragmentRequestBinding? = null
    private val binding get() = _binding!!

    private val requestViewModel: RequestViewModel by viewModels()
    private lateinit var userListFromFirebase: List<String>

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
                val referenceReq = FirebaseDatabase.getInstance().getReference(getString(R.string.firebase_req))

                // get submit date and time
                val dateFormat = "yyyy/MM/dd HH:mm:ss"
                val calendar = Calendar.getInstance()
                val format = SimpleDateFormat(dateFormat)
                val time = format.format(calendar.time)

                Log.d("hiii", "before read data")

                readData(object: UserListCallback {
                    override fun onCallback(value: List<String>) {
                        Log.d("hiii", "oncallback")
//                        val request = Request(
//                            time, value[0], value[1], value[2],
//                            value[3], value[4], value[5], value[6],
//                            binding.editTextIncome.text.toString().toInt(),
//                            binding.spinnerJob.selectedItem.toString(),
//                            binding.spinnerMarital.selectedItem.toString(),
//                            binding.editTextPax.text.toString().toInt(),
//                            binding.editTextReason.text.toString(),
//                            getString(R.string.pending)
//                        )
//
//                        requestViewModel.insert(request)
                    }
                })

                Log.d("hiii", "after read data")

//                val request = Request(
//                    time,
//                    value,
//                    binding.editTextIncome.text.toString().toInt(),
//                    binding.spinnerJob.selectedItem.toString(),
//                    binding.spinnerMarital.selectedItem.toString(),
//                    binding.editTextPax.text.toString().toInt(),
//                    binding.editTextReason.text.toString(),
//                    getString(R.string.pending)
//                )

                // save data in firebase and room database
//                            referenceReq.child(time).setValue(request)
//                requestViewModel.insert(request)

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

    private fun readData(myCallback: UserListCallback) {
        val referenceUser = FirebaseDatabase.getInstance().getReference(getString(R.string.firebase_user))

        val sharedPref = activity?.getSharedPreferences("Login", Context.MODE_PRIVATE)
        val loginIc = sharedPref?.getString("ic", null)
        val findUser = referenceUser.orderByChild("ic").equalTo(loginIc)

        findUser.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists() && loginIc != null) {
                    val children = loginIc.let { it1 -> dataSnapshot.child(it1) }
                    val name = children.child("name").value.toString()
                    val ic = children.child("ic").value.toString()
                    val email = children.child("email").value.toString()
                    val phoneNo = children.child("phoneNo").value.toString()
                    val address = children.child("address").value.toString()
                    val postcode = children.child("posCode").value.toString()
                    val state = children.child("state").value.toString()
                    userListFromFirebase = listOf(name, ic, phoneNo, email, address, postcode, state)
                    Log.d("hiii", "list done")
                    myCallback.onCallback(userListFromFirebase)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("firebase", "firebase error")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}