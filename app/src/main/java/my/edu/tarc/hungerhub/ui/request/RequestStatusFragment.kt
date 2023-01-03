package my.edu.tarc.hungerhub.ui.request

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.adapter.RequestAdapter
import my.edu.tarc.hungerhub.databinding.FragmentRequestStatusBinding
import my.edu.tarc.hungerhub.model.RequestViewModel
import java.text.SimpleDateFormat
import java.util.*

class RequestStatusFragment : Fragment(), MenuProvider {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // check for changes of live data
        requestViewModel.requestList.observe(viewLifecycleOwner) {
            requestAdapter.setForm(it)

            if (binding.recyclerViewStatus.adapter?.itemCount == 0) {
                binding.textViewEmpty.text = getString(R.string.no_record)
            } else {
                binding.textViewEmpty.text = ""
            }
        }

        with(binding.recyclerViewStatus) {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = requestAdapter
        }

        // add request button
        binding.floatingActionButtonAddForm.setOnClickListener {
            findNavController().navigate(R.id.action_nav_request_to_RequestFragment)
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//        menu.findItem(R.id.action_filterByDay).isVisible = true
//        menu.findItem(R.id.action_filterByMonth).isVisible = true
//        menu.findItem(R.id.action_filterByYear).isVisible = true
    }

    @SuppressLint("SimpleDateFormat")
    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        val calendar = Calendar.getInstance()

        if (menuItem.itemId == R.id.action_filterByDay) {
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, day ->
                // set calendar value
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)

                val dateFormat = "yyyy/MM/dd"
                val standardFormat = SimpleDateFormat(dateFormat)
                val selectedDate = standardFormat.format(calendar.time)
                val queryList = requestViewModel.filterByDate(selectedDate)
                requestAdapter.setForm(queryList)

                if (binding.recyclerViewStatus.adapter?.itemCount == 0) {
                    binding.textViewEmpty.text = getString(R.string.no_record)
                } else {
                    binding.textViewEmpty.text = ""
                }
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
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}