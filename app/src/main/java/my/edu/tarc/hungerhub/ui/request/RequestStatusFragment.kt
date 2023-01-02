package my.edu.tarc.hungerhub.ui.request

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import my.edu.tarc.hungerhub.R
import my.edu.tarc.hungerhub.adapter.RequestAdapter
import my.edu.tarc.hungerhub.databinding.FragmentRequestStatusBinding
import my.edu.tarc.hungerhub.model.RequestViewModel

class RequestStatusFragment : Fragment() {

    private var _binding: FragmentRequestStatusBinding? = null
    private val binding get() = _binding!!

    //private lateinit var requestViewModel: RequestViewModel
    private val requestViewModel: RequestViewModel by viewModels()

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

        with(binding.recyclerViewStatus) {
            layoutManager = LinearLayoutManager(context)
            adapter = requestAdapter
        }

        //requestViewModel = ViewModelProvider(this)[RequestViewModel::class.java]

        Log.d("bug2", "bug2")

        requestViewModel.requestList.observe(viewLifecycleOwner, Observer {
            Log.d("bug3", "bug3")
            requestAdapter.updateRequestList(it)
        })

        Log.d("bug4", "bug4")

        binding.floatingActionButtonAddForm.setOnClickListener {
            findNavController().navigate(R.id.action_nav_request_to_RequestFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}