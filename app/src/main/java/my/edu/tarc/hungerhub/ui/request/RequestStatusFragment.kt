package my.edu.tarc.hungerhub.ui.request

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.adapter.RequestAdapter
import my.edu.tarc.hungerhub.databinding.FragmentRequestStatusBinding
import my.edu.tarc.hungerhub.model.RequestViewModel

class RequestStatusFragment : Fragment() {

    private var _binding: FragmentRequestStatusBinding? = null
    private val binding get() = _binding!!

    private val requestViewModel: RequestViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRequestStatusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // attach adapter to the RecyclerView
        val requestAdapter = RequestAdapter()

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}