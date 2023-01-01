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
import my.edu.tarc.hungerhub.databinding.FragmentRequestStatusBinding

class RequestStatusFragment : Fragment() {

    private var _binding: FragmentRequestStatusBinding? = null
    private val binding get() = _binding!!

    private val requestViewModel: RequestViewModel by viewModels()

    //private var darkStatusBar = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRequestStatusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Attach adapter to the RecyclerView
        val requestAdapter = RequestAdapter()

        // check for changes, requestList is live data
        requestViewModel.requestList.observe(viewLifecycleOwner) {
            requestAdapter.setForm(it)
        }

        with(binding.recyclerViewStatus) {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = requestAdapter
        }
    }

    override fun onStart() {
        super.onStart()

        binding.floatingActionButtonAddForm.setOnClickListener {
            findNavController().navigate(R.id.action_nav_request_to_RequestFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}