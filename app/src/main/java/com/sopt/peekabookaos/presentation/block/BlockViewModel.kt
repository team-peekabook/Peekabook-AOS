package com.sopt.peekabookaos.presentation.block

import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.domain.entity.FriendList

class BlockViewModel : ViewModel() {
    val blockList =
        listOf(
            FriendList(0, "문수빈", "https://nhim.splf.in/image/acnh/animal/Poppy.png"),
            FriendList(1, "이현우", "https://nhim.splf.in/image/acnh/animal/Bluebear.png"),
            FriendList(2, "박강희", "https://nhim.splf.in/image/acnh/animal/Ketchup.png"),
            FriendList(3, "김하정", "https://nhim.splf.in/image/acnlanimal/Marshal.png"),
            FriendList(4, "이영주", "https://nhim.splf.in/image/acnh/animal/Agent%20S.png")
        )

    fun getBlockData() {
        // 서버연결시 사용
    }
}
