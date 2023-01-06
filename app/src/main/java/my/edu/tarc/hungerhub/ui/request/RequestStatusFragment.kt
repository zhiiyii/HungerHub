package my.edu.tarc.hungerhub.ui.request

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.adapter.RequestAdapter
import my.edu.tarc.hungerhub.databinding.FragmentRequestStatusBinding
import my.edu.tarc.hungerhub.model.RequestViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

class RequestStatusFragment: Fragment() {

    private var _binding: FragmentRequestStatusBinding? = null
    private val binding get() = _binding!!

    private val requestViewModel: RequestViewModel by viewModels()
    private val requestAdapter = RequestAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRequestStatusBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = activity?.getSharedPreferences("Login", Context.MODE_PRIVATE)
        val loginIc = sharedPref?.getString("ic", null)

        // check for changes of live data
        requestViewModel.requestList.observe(viewLifecycleOwner) {
            // query to select records only for login user
            val unfilteredList = loginIc?.let { it1 -> requestViewModel.removeFilter(it1) }
            if (unfilteredList != null) {
                requestAdapter.setForm(unfilteredList)
            }
            checkRecordNum()
        }

        with(binding.recyclerViewStatus) {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = requestAdapter
        }

        binding.floatingActionButtonAddForm.setOnClickListener {
            findNavController().navigate(R.id.action_nav_request_to_RequestFragment)
        }

        binding.floatingActionButtonFilter.setOnClickListener {
            val builder = AlertDialog.Builder(this.requireContext())
            builder.setTitle(R.string.filter_by)
            builder.setIcon(R.drawable.ic_baseline_filter_alt_24)

            val filterBy = arrayOf("Day", "Month", "Year", "Approved", "Pending", "Rejected")
            builder.setItems(filterBy, DialogInterface.OnClickListener { _, which ->
                if (which == 0 || which == 1 || which == 2) {
                    val calendar = Calendar.getInstance()
                    val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
                        calendar.set(Calendar.YEAR, year)
                        calendar.set(Calendar.MONTH, month)
                        calendar.set(Calendar.DAY_OF_MONTH, day)

                        var dateFormat = ""
                        when (which) {
                            0 -> dateFormat = "yyyy/MM/dd"
                            1 -> dateFormat = "yyyy/MM"
                            2 -> dateFormat = "yyyy"
                        }

                        val standardFormat = SimpleDateFormat(dateFormat)
                        val selectedDate = standardFormat.format(calendar.time)

                        if (loginIc != null) {
                            val queryList = requestViewModel.filterByDate(loginIc, selectedDate)
                            requestAdapter.setForm(queryList)
                        }

                        Toast.makeText(this.requireContext(), (getString(R.string.show_record) + " " + selectedDate), Toast.LENGTH_SHORT).show()
                        checkRecordNum()
                    }

                    this.context?.let {
                        DatePickerDialog(
                            it,
                            dateSetListener,
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }
                } else {
                    var status = ""
                    when (which) {
                        3 -> status = "approved"
                        4 -> status = "pending"
                        5 -> status = "rejected"
                    }

                    if (loginIc != null) {
                        val queryList = requestViewModel.filterByStatus(loginIc, status)
                        requestAdapter.setForm(queryList)
                    }

                    Toast.makeText(this.requireContext(), (getString(R.string.showing) + " " + status + " " + getString(R.string.records)), Toast.LENGTH_SHORT).show()
                    checkRecordNum()
                }
            })

            builder.setPositiveButton(R.string.remove_filter) { _, _ ->
                val unfilteredList = loginIc?.let { it1 -> requestViewModel.removeFilter(it1) }
                if (unfilteredList != null) {
                    requestAdapter.setForm(unfilteredList)
                }
                checkRecordNum()

                Toast.makeText(this.requireContext(), R.string.remove_filter_msg, Toast.LENGTH_SHORT).show()
            }

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

    // function to display message to tell user there are no records
    private fun checkRecordNum() {
        if (binding.recyclerViewStatus.adapter?.itemCount == 0) {
            binding.textViewEmpty.text = getString(R.string.no_record)
        } else {
            binding.textViewEmpty.text = ""
        }
    }
}