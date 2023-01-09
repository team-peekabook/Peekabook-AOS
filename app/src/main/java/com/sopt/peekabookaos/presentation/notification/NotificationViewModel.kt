package com.sopt.peekabookaos.presentation.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopt.peekabookaos.data.entity.Notification

class NotificationViewModel : ViewModel() {
    private val _notificationData: MutableLiveData<List<Notification>> = MutableLiveData()
    val notificationData: LiveData<List<Notification>> = _notificationData

    init {
        initNotificationData()
    }

    private fun initNotificationData() {
        _notificationData.value = listOf(
            Notification(
                0,
                "https://cdn.imweb.me/thumbnail/20190126/5c4c28d2bc320.jpg",
                "한지우지우",
                1,
                1,
                "데미안",
                2,
                "2022-12-28"
            ),
            Notification(
                1,
                "https://cdn.pixabay.com/photo/2021/03/27/05/16/chicken-6127516_960_720.png",
                "이빵주",
                1,
                1,
                null,
                1,
                "2022-12-26"
            ),
            Notification(
                2,
                "https://img.lovepik.com/free-png/20211216/lovepik-corgi-dog-png-image_401706065_wh1200.png",
                "조예슬",
                1,
                1,
                "목소리를 들려줄게요",
                3,
                "2022-12-25"
            ),
            Notification(
                2,
                "https://www.urbanbrush.net/web/wp-content/uploads/edd/2018/10/urbanbrush-20181004162920232431.png",
                "박현정",
                1,
                1,
                "어린왕자",
                2,
                "2022-12-24"
            ),
            Notification(
                2,
                "https://img.freepik.com/premium-vector/vector-illustration-of-killer-whale_25420-22.jpg",
                "김인영",
                1,
                1,
                "불편한 편의점",
                2,
                "2022-12-22"
            ),
            Notification(
                0,
                "https://cdn.imweb.me/thumbnail/20190126/5c4c28d2bc320.jpg",
                "한지우지우",
                1,
                1,
                "데미안",
                2,
                "2022-12-28"
            ),
            Notification(
                1,
                "https://cdn.pixabay.com/photo/2021/03/27/05/16/chicken-6127516_960_720.png",
                "이빵주",
                1,
                1,
                null,
                1,
                "2022-12-26"
            ),
            Notification(
                2,
                "https://img.lovepik.com/free-png/20211216/lovepik-corgi-dog-png-image_401706065_wh1200.png",
                "조예슬",
                1,
                1,
                "목소리를 들려줄게요",
                3,
                "2022-12-25"
            ),
            Notification(
                2,
                "https://www.urbanbrush.net/web/wp-content/uploads/edd/2018/10/urbanbrush-20181004162920232431.png",
                "박현정",
                1,
                1,
                "어린왕자",
                2,
                "2022-12-24"
            ),
            Notification(
                2,
                "https://img.freepik.com/premium-vector/vector-illustration-of-killer-whale_25420-22.jpg",
                "김인영",
                1,
                1,
                "불편한 편의점",
                2,
                "2022-12-22"
            )
        )
    }
}
