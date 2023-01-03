package com.sopt.peekabookaos.presentation.bookshelf

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.data.entity.Pick
import com.sopt.peekabookaos.data.entity.Shelf
import com.sopt.peekabookaos.data.entity.User

class BookShelfViewModel : ViewModel() {
    private val _pickData: MutableLiveData<List<Pick>> = MutableLiveData()
    val pickData: LiveData<List<Pick>> = _pickData

    private val _shelfData: MutableLiveData<List<Shelf>> = MutableLiveData()
    val shelfData: LiveData<List<Shelf>> = _shelfData

    private val _userData: MutableLiveData<List<User>> = MutableLiveData()
    val userData: LiveData<List<User>> = _userData
    var bookCount: Int = 0

    private val _friendShelf: MutableLiveData<Boolean> = MutableLiveData()
    var friendShelf: LiveData<Boolean> = _friendShelf

    private val _userId: MutableLiveData<Int> = MutableLiveData(0)
    var userId: LiveData<Int> = _userId

    init {
        initUserData()
        initShelfData()
        initPickData()
        initBookCount()
    }

    fun updateShelfState(state: Boolean) {
        _friendShelf.value = state
    }

    private fun initBookCount() {
        bookCount = shelfData.value?.size ?: 0
    }

    fun updateUserId(position: Int) {
        _userId.value = position + 1
    }

    private fun initUserData() {
        _userData.value = listOf(
            User(
                "수빈은문수빈",
                "https://play-lh.googleusercontent.com/R8-LD7m5rwQwIdAit3PwUG8QgYoDecAZBSaEuPAjhTpsG6mkqo130b-RKm9RrXBj-kI",
                "안녕하세요, 문수빈의 책장입니다. \n선배들 구경 할람할 말람말 편한대로 하셔"
            ),
            User(
                "이빵주",
                "https://cdn.imweb.me/thumbnail/20190126/5c4c28d2bc320.jpg",
                "안녕하세요, 이빵주의 책장입니다."
            ),
            User(
                "김하정",
                "https://cdn.pixabay.com/photo/2021/03/27/05/16/chicken-6127516_960_720.png",
                "안녕하세요, 김하정의 책장입니다."
            ),
            User(
                "박강희",
                "https://img.freepik.com/premium-vector/vector-illustration-of-killer-whale_25420-22.jpg",
                "안녕하세요, 박강희의 책장입니다."
            ),
            User(
                "조예슬",
                "https://www.urbanbrush.net/web/wp-content/uploads/edd/2018/10/urbanbrush-20181004162920232431.png",
                "안녕하세요, 조예슬의 책장입니다. 예수리 마수리 얍!"
            ),
            User(
                "문새연",
                "https://img.lovepik.com/free-png/20211216/lovepik-corgi-dog-png-image_401706065_wh1200.png",
                "안녕하세요, 문새연의 책장입니다. 뿍X탱X쥬 최강 디자이너"
            ),
            User(
                "윤수빈",
                "https://mblogthumb-phinf.pstatic.net/20120821_2/prod03_13455331040414pYgp_JPEG/%C5%AC%B7%CE%B9%F6.jpg?type=w2",
                "안녕하세요, 윤수빈의 책장입니다. \n수빈은 윤수빈"
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
