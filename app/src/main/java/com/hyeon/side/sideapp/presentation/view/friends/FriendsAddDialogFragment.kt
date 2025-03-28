package com.hyeon.side.sideapp.presentation.view.friends

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyeon.side.sideapp.databinding.FragmentFriendsAddDialogBinding
import com.hyeon.side.sideapp.presentation.adapter.FriendsAddListAdapter
import com.hyeon.side.sideapp.presentation.viewmodel.GetUserViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FriendsAddDialogFragment : DialogFragment() {
    private var _binding: FragmentFriendsAddDialogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GetUserViewmodel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFriendsAddDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        viewModel.fetchAllUsers()
    }

    private fun observeViewModel() {
        viewModel.user.observe(viewLifecycleOwner) { users ->
            Log.d("FriendsAddDialogFragment", "Observed users: $users")
            (binding.friendsRecyclerView.adapter as FriendsAddListAdapter).updateFriends(users)
        }
    }

    private fun setupRecyclerView() {
        val adapter = FriendsAddListAdapter(emptyList()) { friend ->
            // Handle friend selection
        }
        binding.friendsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.friendsRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}