package com.sopt.peekabookaos.presentation.bookshelf

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.data.entity.FriendProfile
import com.sopt.peekabookaos.data.entity.Pick
import com.sopt.peekabookaos.data.entity.Shelf
import com.sopt.peekabookaos.data.entity.User

class BookShelfViewModel : ViewModel() {
    private val _friendData: MutableLiveData<List<FriendProfile>> = MutableLiveData()
    val friendData: LiveData<List<FriendProfile>> = _friendData

    private val _pickData: MutableLiveData<List<Pick>> = MutableLiveData()
    val pickData: LiveData<List<Pick>> = _pickData

    private val _shelfData: MutableLiveData<List<Shelf>> = MutableLiveData()
    val shelfData: LiveData<List<Shelf>> = _shelfData

    private val _userData: MutableLiveData<User> = MutableLiveData()
    val userData: LiveData<User> = _userData
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
        _userData.value = User(
            profile = "https://play-lh.googleusercontent.com/R8-LD7m5rwQwIdAit3PwUG8QgYoDecAZBSaEuPAjhTpsG6mkqo130b-RKm9RrXBj-kI",
            name = "문수빈",
            comment = "안녕하세요, 저는 한줄소개입니다! 제 책장을 둘러보세요."
        )
    }

    private fun initFriendData() {
        _friendData.value = listOf(
            FriendProfile(
                "이영주이빵주6글자",
                "https://blog.kakaocdn.net/dn/cnCXEQ/btqO7jbSdwy/TDPQ03fpqWkRLr1bhNjQm1/img.jpg"
            ),
            FriendProfile(
                "김하정",
                "https://blog.kakaocdn.net/dn/cnCXEQ/btqO7jbSdwy/TDPQ03fpqWkRLr1bhNjQm1/img.jpg"
            ),
            FriendProfile("박강희", "https://cdn.rnx.kr/news/photo/201902/79734_72030_2437.jpg"),
            FriendProfile(
                "이현우",
                "http://talkimg.imbc.com/TVianUpload/tvian/TViews/image/2017/01/13/iW4xELQwGuw1636198924766594933.jpg"
            ),
            FriendProfile(
                "박현정",
                "https://biz.chosun.com/resizer/y4pry-0SHJzZOl1Ua0ywZgkchLg=/616x0/smart/cloudfront-ap-northeast-1.images.arcpublishing.com/chosunbiz/7DYW3QZRCWRA23AZ4AWUIM5SJ4.jpg"
            ),
            FriendProfile(
                "박현정",
                "https://biz.chosun.com/resizer/y4pry-0SHJzZOl1Ua0ywZgkchLg=/616x0/smart/cloudfront-ap-northeast-1.images.arcpublishing.com/chosunbiz/7DYW3QZRCWRA23AZ4AWUIM5SJ4.jpg"
            ),
            FriendProfile(
                "박현정",
                "https://biz.chosun.com/resizer/y4pry-0SHJzZOl1Ua0ywZgkchLg=/616x0/smart/cloudfront-ap-northeast-1.images.arcpublishing.com/chosunbiz/7DYW3QZRCWRA23AZ4AWUIM5SJ4.jpg"
            )
        )
    }

    private fun initShelfData() {
        _shelfData.value = listOf(
            Shelf("http://image.auction.co.kr/itemimage/1c/ae/9f/1cae9f7271.jpg", true),
            Shelf("https://cover.bookoob.co.kr/5/2/org/589952_org.jpg", false),
            Shelf(
                "https://image.aladin.co.kr/product/8663/31/cover500/s412836496_1.jpg",
                true
            ),
            Shelf("https://image.yes24.com/goods/74261416/XL", false),
            Shelf("http://image.auction.co.kr/itemimage/1c/ae/9f/1cae9f7271.jpg", true),
            Shelf("https://cover.bookoob.co.kr/5/2/org/589952_org.jpg", false),
            Shelf(
                "https://image.aladin.co.kr/product/8663/31/cover500/s412836496_1.jpg",
                true
            ),
            Shelf("https://image.yes24.com/goods/74261416/XL", false),
            Shelf("http://image.auction.co.kr/itemimage/1c/ae/9f/1cae9f7271.jpg", true),
            Shelf("https://cover.bookoob.co.kr/5/2/org/589952_org.jpg", false),
            Shelf(
                "https://image.aladin.co.kr/product/8663/31/cover500/s412836496_1.jpg",
                true
            ),
            Shelf("https://image.yes24.com/goods/74261416/XL", false),
            Shelf("http://image.auction.co.kr/itemimage/1c/ae/9f/1cae9f7271.jpg", true),
            Shelf("https://cover.bookoob.co.kr/5/2/org/589952_org.jpg", false),
            Shelf(
                "https://image.aladin.co.kr/product/8663/31/cover500/s412836496_1.jpg",
                true
            ),
            Shelf("https://image.yes24.com/goods/74261416/XL", false)
        )
    }

    private fun initPickData() {
        _pickData.value = listOf(
            Pick(
                "1",
                "지구에서 한아뿐",
                "https://image.yes24.com/goods/76106687/XL",
                "20만 광년의 사랑"
            ),
            Pick(
                "2",
                "홀로서기 심리학",
                "https://image.aladin.co.kr/product/25517/79/cover500/e422537751_1.jpg",
                "당신을 괴롭히는 문제의 90%는 당신 힘으로 바꿀 수 없는 것들이다.\n" + "그것을 인정하고 나에게 집중하는 것이 홀로서기의 시작이다!"
            ),
            Pick(
                "3",
                "홀로서기 심리학",
                "https://image.aladin.co.kr/product/25517/79/cover500/e422537751_1.jpg",
                "당신을 괴롭히는 문제의 90%는 당신 힘으로 바꿀 수 없는 것들이다.\n" + "그것을 인정하고 나에게 집중하는 것이 홀로서기의 시작이다!"
            )
        )
    }
}
