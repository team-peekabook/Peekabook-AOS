package com.sopt.peekabookaos.presentation.bookshelf

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.data.entity.bookshelf.FriendProfileData
import com.sopt.peekabookaos.data.entity.bookshelf.PickData
import com.sopt.peekabookaos.data.entity.bookshelf.ShelfData
import com.sopt.peekabookaos.data.entity.bookshelf.UserData

class BookShelfViewModel : ViewModel() {
    private val _friendData: MutableLiveData<List<FriendProfileData>> = MutableLiveData()
    val friendData: LiveData<List<FriendProfileData>> = _friendData

    private val _pickData: MutableLiveData<List<PickData>> = MutableLiveData()
    val pickData: LiveData<List<PickData>> = _pickData

    private val _shelfData: MutableLiveData<List<ShelfData>> = MutableLiveData()
    val shelfData: LiveData<List<ShelfData>> = _shelfData

    private val _userData: MutableLiveData<UserData> = MutableLiveData()
    val userData: LiveData<UserData> = _userData
    var bookCount: Int = 0

    init {
        initUserData()
        initFriendData()
        initShelfData()
        initPickData()
        initBookCount()
    }

    private fun initBookCount() {
        bookCount = shelfData.value?.size ?: 0
    }

    private fun initUserData() {
        _userData.value = UserData(
            profile = "https://play-lh.googleusercontent.com/R8-LD7m5rwQwIdAit3PwUG8QgYoDecAZBSaEuPAjhTpsG6mkqo130b-RKm9RrXBj-kI",
            name = "문수빈",
            comment = "안녕하세요, 저는 한줄소개입니다! 제 책장을 둘러보세요."
        )
    }

    private fun initFriendData() {
        _friendData.value = listOf(
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
            ),
            FriendProfileData(
                "박현정",
                "https://biz.chosun.com/resizer/y4pry-0SHJzZOl1Ua0ywZgkchLg=/616x0/smart/cloudfront-ap-northeast-1.images.arcpublishing.com/chosunbiz/7DYW3QZRCWRA23AZ4AWUIM5SJ4.jpg"
            ),
            FriendProfileData(
                "박현정",
                "https://biz.chosun.com/resizer/y4pry-0SHJzZOl1Ua0ywZgkchLg=/616x0/smart/cloudfront-ap-northeast-1.images.arcpublishing.com/chosunbiz/7DYW3QZRCWRA23AZ4AWUIM5SJ4.jpg"
            )
        )
    }

    private fun initShelfData() {
        _shelfData.value = listOf(
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
    }

    private fun initPickData() {
        _pickData.value = listOf(
            PickData(
                "1",
                "지구에서 한아뿐",
                "https://image.yes24.com/goods/76106687/XL",
                "20만 광년의 사랑"
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
    }
}
