package com.sopt.peekabookaos.presentation.bookshelf

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.data.entity.FriendUser
import com.sopt.peekabookaos.data.entity.Pick
import com.sopt.peekabookaos.data.entity.Shelf
import com.sopt.peekabookaos.data.entity.User

class BookShelfViewModel : ViewModel() {
    private val _pickData: MutableLiveData<List<Pick>> = MutableLiveData()
    val pickData: LiveData<List<Pick>> = _pickData

    private val _shelfData: MutableLiveData<List<Shelf>> = MutableLiveData()
    val shelfData: LiveData<List<Shelf>> = _shelfData

    private val _friendUserData: MutableLiveData<List<FriendUser>> = MutableLiveData()
    val friendUserData: LiveData<List<FriendUser>> = _friendUserData

    private val _userData: MutableLiveData<User> = MutableLiveData()
    val userData: LiveData<User> = _userData

    private val _friendShelf: MutableLiveData<Boolean> = MutableLiveData()
    var friendShelf: LiveData<Boolean> = _friendShelf

    private val _userId: MutableLiveData<Int> = MutableLiveData(0)
    var userId: LiveData<Int> = _userId

    init {
        initFriendUserData()
        initShelfData()
        initPickData()
        initUserData()
    }

    fun updateShelfState(state: Boolean) {
        _friendShelf.value = state
    }

    fun updateUserId(position: Int) {
        _userId.value = position
    }

    private fun initUserData(){
        _userData.value = User(
            "수빈은문수빈",
            "https://play-lh.googleusercontent.com/R8-LD7m5rwQwIdAit3PwUG8QgYoDecAZBSaEuPAjhTpsG6mkqo130b-RKm9RrXBj-kI",
            "안녕하세요, 문수빈의 책장입니다. \n선배들 구경 할람할 말람말 편한대로 하셔"
        )
    }

    private fun initFriendUserData() {
        _friendUserData.value = listOf(
            FriendUser(
                "이빵주",
                "https://cdn.imweb.me/thumbnail/20190126/5c4c28d2bc320.jpg",
                "안녕하세요, 이빵주의 책장입니다."
            ),
            FriendUser(
                "김하정",
                "https://cdn.pixabay.com/photo/2021/03/27/05/16/chicken-6127516_960_720.png",
                "안녕하세요, 김하정의 책장입니다."
            ),
            FriendUser(
                "강희",
                "https://img.freepik.com/premium-vector/vector-illustration-of-killer-whale_25420-22.jpg",
                "안녕하세요, 박강희의 책장입니다."
            ),
            FriendUser(
                "조예슬",
                "https://www.urbanbrush.net/web/wp-content/uploads/edd/2018/10/urbanbrush-20181004162920232431.png",
                "안녕하세요, 조예슬의 책장입니다. 예수리 마수리 얍!"
            ),
            FriendUser(
                "뿍",
                "https://img.lovepik.com/free-png/20211216/lovepik-corgi-dog-png-image_401706065_wh1200.png",
                "안녕하세요, 문새연의 책장입니다. 뿍X탱X쥬 최강 디자이너"
            ),
            FriendUser(
                "윤수빈",
                "https://mblogthumb-phinf.pstatic.net/20120821_2/prod03_13455331040414pYgp_JPEG/%C5%AC%B7%CE%B9%F6.jpg?type=w2",
                "안녕하세요, 윤수빈의 책장입니다. \n수빈은 윤수빈"
            ),
            FriendUser(
                "이빵주",
                "https://cdn.imweb.me/thumbnail/20190126/5c4c28d2bc320.jpg",
                "안녕하세요, 이빵주의 책장입니다."
            ),
            FriendUser(
                "김하정",
                "https://cdn.pixabay.com/photo/2021/03/27/05/16/chicken-6127516_960_720.png",
                "안녕하세요, 김하정의 책장입니다."
            ),
            FriendUser(
                "강희",
                "https://img.freepik.com/premium-vector/vector-illustration-of-killer-whale_25420-22.jpg",
                "안녕하세요, 박강희의 책장입니다."
            ),
            FriendUser(
                "조예슬",
                "https://www.urbanbrush.net/web/wp-content/uploads/edd/2018/10/urbanbrush-20181004162920232431.png",
                "안녕하세요, 조예슬의 책장입니다. 예수리 마수리 얍!"
            ),
            FriendUser(
                "뿍",
                "https://img.lovepik.com/free-png/20211216/lovepik-corgi-dog-png-image_401706065_wh1200.png",
                "안녕하세요, 문새연의 책장입니다. 뿍X탱X쥬 최강 디자이너"
            ),
            FriendUser(
                "윤수빈",
                "https://mblogthumb-phinf.pstatic.net/20120821_2/prod03_13455331040414pYgp_JPEG/%C5%AC%B7%CE%B9%F6.jpg?type=w2",
                "안녕하세요, 윤수빈의 책장입니다. \n수빈은 윤수빈"
            )

        )
    }

    private fun initShelfData() {
        _shelfData.value = listOf(
            Shelf("https://image.yes24.com/goods/76106687/XL", false),
            Shelf("https://image.yes24.com/goods/114671132/XL", true),
            Shelf(
                "https://image.yes24.com/goods/97255028/XL",
                false
            ),
            Shelf("https://image.yes24.com/momo/TopCate215/MidCate002/21414510.jpg", true),
            Shelf("https://image.yes24.com/goods/72127217/XL", false),
            Shelf(
                "https://image.yes24.com/goods/91159773/XL",
                false
            ),
            Shelf(
                "https://blog.kakaocdn.net/dn/66RoG/btqSGiU51MK/4NZO9mMJnAoFHmjXtSGiuK/img.png",
                false
            ),
            Shelf("https://image.yes24.com/goods/72310907/XL", true)
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
                "바로의 여행",
                "https://image.yes24.com/goods/114671132/XL",
                "『아빠 셋 꽃다발 셋』 『엄마 셋 도시락 셋』 등으로 부모와 아이 사이의 지극한 마음을 따뜻하게 그려 온 작가 국지승의 새 그림책이 출간되었다."
            ),
            Pick(
                "3",
                "모순",
                "https://blog.kakaocdn.net/dn/66RoG/btqSGiU51MK/4NZO9mMJnAoFHmjXtSGiuK/img.png",
                "내 삶의 부피는 너무 얇다. 겨자씨 한 알 심을 만한 깊이도 없다. 이렇게 살아도 되는 것일까."
            )
        )
    }
}
