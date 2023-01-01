package com.sopt.peekabookaos.presentation.bookshelf

import android.os.Bundle
import android.view.View
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.data.entity.bookshelf.FriendProfileData
import com.sopt.peekabookaos.data.entity.bookshelf.PickData
import com.sopt.peekabookaos.data.entity.bookshelf.ShelfData
import com.sopt.peekabookaos.databinding.FragmentBookshelfBinding
import com.sopt.peekabookaos.util.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookshelfFragment : BindingFragment<FragmentBookshelfBinding>(R.layout.fragment_bookshelf) {
    private lateinit var myShelfAdapter: BookShelfShelfAdapter
    private lateinit var pickAdapter: BookShelfPickAdapter
    private lateinit var friendAdapter: BookShelfFriendAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        val itemDeco = BookshelfShelfAdapterDeco(requireContext())
        binding.rvBookshelfBottomViewShelf.addItemDecoration(itemDeco)
    }

    private fun initAdapter() {
        myShelfAdapter = BookShelfShelfAdapter()
        binding.rvBookshelfBottomViewShelf.adapter = myShelfAdapter
        myShelfAdapter.submitList(mocMyShelfList)

        pickAdapter = BookShelfPickAdapter()
        binding.rvBookshelfPick.adapter = pickAdapter
        pickAdapter.submitList(mocPickList)

        friendAdapter = BookShelfFriendAdapter()
        binding.rvBookshelfFriendList.adapter = friendAdapter
        friendAdapter.submitList(mocFriendList)
    }

    companion object {
        private val mocMyShelfList = listOf<ShelfData>(
            ShelfData("http://image.auction.co.kr/itemimage/1c/ae/9f/1cae9f7271.jpg", true),
            ShelfData("https://cover.bookoob.co.kr/5/2/org/589952_org.jpg", false),
            ShelfData(
                "https://image.aladin.co.kr/product/8663/31/cover500/s412836496_1.jpg",
                true
            ),
            ShelfData("https://image.yes24.com/goods/74261416/XL", false),
            ShelfData("http://image.auction.co.kr/itemimage/1c/ae/9f/1cae9f7271.jpg", true),
            ShelfData("https://cover.bookoob.co.kr/5/2/org/589952_org.jpg", false),
            ShelfData(
                "https://image.aladin.co.kr/product/8663/31/cover500/s412836496_1.jpg",
                true
            ),
            ShelfData("https://image.yes24.com/goods/74261416/XL", false),
            ShelfData("http://image.auction.co.kr/itemimage/1c/ae/9f/1cae9f7271.jpg", true),
            ShelfData("https://cover.bookoob.co.kr/5/2/org/589952_org.jpg", false),
            ShelfData(
                "https://image.aladin.co.kr/product/8663/31/cover500/s412836496_1.jpg",
                true
            ),
            ShelfData("https://image.yes24.com/goods/74261416/XL", false),
            ShelfData("http://image.auction.co.kr/itemimage/1c/ae/9f/1cae9f7271.jpg", true),
            ShelfData("https://cover.bookoob.co.kr/5/2/org/589952_org.jpg", false),
            ShelfData(
                "https://image.aladin.co.kr/product/8663/31/cover500/s412836496_1.jpg",
                true
            ),
            ShelfData("https://image.yes24.com/goods/74261416/XL", false)
        )
        private val mocPickList = listOf<PickData>(
            PickData(
                "1",
                "홀로서기 심리학",
                "https://image.aladin.co.kr/product/25517/79/cover500/e422537751_1.jpg",
                "당신을 괴롭히는 문제의 90%는 당신 힘으로 바꿀 수 없는 것들이다.\n" + "그것을 인정하고 나에게 집중하는 것이 홀로서기의 시작이다!"
            ),
            PickData(
                "2",
                "홀로서기 심리학",
                "https://image.aladin.co.kr/product/25517/79/cover500/e422537751_1.jpg",
                "당신을 괴롭히는 문제의 90%는 당신 힘으로 바꿀 수 없는 것들이다.\n" + "그것을 인정하고 나에게 집중하는 것이 홀로서기의 시작이다!"
            ),
            PickData(
                "3",
                "홀로서기 심리학",
                "https://image.aladin.co.kr/product/25517/79/cover500/e422537751_1.jpg",
                "당신을 괴롭히는 문제의 90%는 당신 힘으로 바꿀 수 없는 것들이다.\n" + "그것을 인정하고 나에게 집중하는 것이 홀로서기의 시작이다!"
            )
        )
        private val mocFriendList = listOf<FriendProfileData>(
            FriendProfileData(
                "이영주이빵주6글자",
                "https://blog.kakaocdn.net/dn/cnCXEQ/btqO7jbSdwy/TDPQ03fpqWkRLr1bhNjQm1/img.jpg"
            ),
            FriendProfileData(
                "김하정",
                "https://blog.kakaocdn.net/dn/cnCXEQ/btqO7jbSdwy/TDPQ03fpqWkRLr1bhNjQm1/img.jpg"
            ),
            FriendProfileData("박강희", "https://cdn.rnx.kr/news/photo/201902/79734_72030_2437.jpg"),
            FriendProfileData(
                "이현우",
                "http://talkimg.imbc.com/TVianUpload/tvian/TViews/image/2017/01/13/iW4xELQwGuw1636198924766594933.jpg"
            ),
            FriendProfileData(
                "박현정",
                "https://biz.chosun.com/resizer/y4pry-0SHJzZOl1Ua0ywZgkchLg=/616x0/smart/cloudfront-ap-northeast-1.images.arcpublishing.com/chosunbiz/7DYW3QZRCWRA23AZ4AWUIM5SJ4.jpg"
            )
        )
    }
}
