package com.hyeon.side.sideapp.presentation.view.friends

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyeon.side.sideapp.presentation.adapter.FriendsListAdapter
import com.hyeon.side.sideapp.databinding.FragmentFriendsBinding
import com.hyeon.side.sideapp.presentation.view.chat.ChatActivity
import com.hyeon.side.sideapp.presentation.viewmodel.FriendsListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FriendsFragment : Fragment() {
    private var _binding: FragmentFriendsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FriendsListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFriendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()

        val userId = requireActivity().intent.getStringExtra("userId") ?: return
        val userNickname = requireActivity().intent.getStringExtra("nickname") ?: return
        viewModel.fetchFriendsList(userId)
        binding.NickName.text = userNickname
    }

    private fun setupRecyclerView() {
        val adapter = FriendsListAdapter(emptyList()) { friend ->
            val intent = Intent(activity, ChatActivity::class.java).apply {
                putExtra("FRIEND_ID", friend.id)
                putExtra("FRIEND_NICKNAME", friend.nickname)
            }
            startActivity(intent)
        }
        binding.recyclerViewFriends.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewFriends.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.friendsList.observe(viewLifecycleOwner) { friends ->
            (binding.recyclerViewFriends.adapter as FriendsListAdapter).updateFriends(friends)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
