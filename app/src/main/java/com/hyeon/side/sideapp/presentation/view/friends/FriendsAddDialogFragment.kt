package com.hyeon.side.sideapp.presentation.view.friends

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.play.integrity.internal.u
import com.hyeon.side.sideapp.R
import com.hyeon.side.sideapp.databinding.FragmentFriendsAddDialogBinding
import com.hyeon.side.sideapp.databinding.FragmentFriendsBinding
import com.hyeon.side.sideapp.domain.entity.Friends
import com.hyeon.side.sideapp.presentation.adapter.FriendsAddListAdapter
import com.hyeon.side.sideapp.presentation.adapter.FriendsListAdapter
import com.hyeon.side.sideapp.presentation.view.chat.ChatActivity
import com.hyeon.side.sideapp.presentation.viewmodel.FriendsListViewModel
import com.hyeon.side.sideapp.presentation.viewmodel.GetUserViewmodel


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class FriendsAddDialogFragment : DialogFragment() {
    private var _binding: FragmentFriendsAddDialogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GetUserViewmodel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
    }


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
        val userId = requireActivity().intent.getStringExtra("userId") ?: return
        viewModel.fetchUserData(userId)

    }

    private fun observeViewModel() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            (binding.friendsRecyclerView.adapter as FriendsAddListAdapter).updateFriends(user)
        }
    }
    private fun setupRecyclerView() {
        val adapter = FriendsAddListAdapter(emptyList()) { friend ->
            // 체크 박스 체크한 친구 데이터 친구리스트로 보내기
            val intent = Intent(activity, FriendsFragment::class.java).apply {
                putExtra("FRIEND_ID", friend.id)
                putExtra("FRIEND_NICKNAME", friend.nickname)
                putExtra("FRIENDS_STATUS", friend.status)
            }
            startActivity(intent)
        }
        binding.friendsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.friendsRecyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}