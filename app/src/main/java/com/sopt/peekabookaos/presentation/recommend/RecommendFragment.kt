package com.sopt.peekabookaos.presentation.recommend

import android.os.Bundle
import android.view.View
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.data.entity.Recommend
import com.sopt.peekabookaos.databinding.FragmentRecommendBinding
import com.sopt.peekabookaos.util.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecommendFragment :
    BindingFragment<FragmentRecommendBinding>(R.layout.fragment_recommend) {
    private lateinit var recommendAdapter: BookRecommendAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTextAppearance()
        initAdapter()
        initRecommendedClickListener()
        initRecommendingClickListener()
    }

    private fun initRecommendedClickListener() {
        binding.tvRecommendRecommended.setOnClickListener {
            recommendAdapter.submitList(recommendedList)
            with(binding) {
                tvRecommendRecommended.isSelected = true
                tvRecommendRecommending.isSelected = false
                if (tvRecommendRecommended.isSelected) {
                    tvRecommendRecommending.setTextAppearance(R.style.H4)
                    tvRecommendRecommended.setTextAppearance(R.style.NameBd)
                }
            }
        }
    }

    private fun initRecommendingClickListener() {
        binding.tvRecommendRecommending.setOnClickListener {
            recommendAdapter.submitList(recommendingList)
            with(binding) {
                tvRecommendRecommended.isSelected = false
                tvRecommendRecommending.isSelected = true
                if (tvRecommendRecommending.isSelected) {
                    tvRecommendRecommending.setTextAppearance(R.style.NameBd)
                    tvRecommendRecommended.setTextAppearance(R.style.H4)
                }
            }
        }
    }

    private fun initTextAppearance() {
        with(binding) {
            tvRecommendRecommended.isSelected = true
            tvRecommendRecommended.setTextAppearance(R.style.NameBd)
        }
    }

    private fun initAdapter() {
        recommendAdapter = BookRecommendAdapter()
        binding.rvRecommend.adapter = recommendAdapter
        recommendAdapter.submitList(recommendedList)
    }

    companion object {
        private val recommendedList = listOf(
            Recommend(
                0,
                "“내가 그리워한 건 여름이 아니라 여름의 나였다” 휴가, 수영, 낮술, 머슬 셔츠, 전 애인… 여름을 말할 때 우리가 이야기하는 것들 아무튼 시리즈의 서른 번째 책. 『보노보노처럼 살다니 다행이야』 『아무것도 안 해도 아무렇지 않구나』 등으로 많은 독자의 공감을 불러일으킨 김신회 작가의 신작으로, 1년 내내 여름만 기다리며 사는 그가 마치 여름에게 ",
                "박현정",
                "조예슬",
                "2022/01/27",
                0,
                "아무튼, 여름",
                "김신회",
                "http://image.yes24.com/goods/90365124/XL",
                "https://image.aladin.co.kr/product/25517/79/cover500/e422537751_1.jpg"
            ),
            Recommend(
                1,
                "공감을 불러일으키는 이야기와 서정적이며 사려 깊은 문장, 그리고 그 안에 자리한 뜨거운 문제의식으로 등단 이후 줄곧 폭넓은 독자의 지지를 받은 책",
                "문새연",
                "이태영",
                "2022/05/08",
                1,
                "밝은 밤",
                "누구지",
                "https://image.yes24.com/goods/114671132/XL",
                "https://image.aladin.co.kr/product/25517/79/cover500/e422537751_1.jpg"
            ),
            Recommend(
                2,
                "“왜 아무리 노력해도 나쁜 심리 습관에서 벗어나지 못하는 걸까?” 지나친 감정 기복, 과도한 자기 비난, 오래된 마음의 상처, 습관적 외로움 등으로 매일매일 흔들리는 사람들에게 마음의 중심을 잡고 인생을 주도적으로 이끄는 법을 알려주는 책 《홀로서기 심리학》. 저자는 지나친 의존과 예민함으로 인해 쉽게 상처받는 사람들, 마음대로 되지 않는 세상과 타인을 ",
                "문수빈",
                "이현우",
                "2022/12/11",
                2,
                "홀로서기 심리학",
                "누구야",
                "https://image.yes24.com/momo/TopCate215/MidCate002/21414510.jpg",
                "https://image.aladin.co.kr/product/25517/79/cover500/e422537751_1.jpg"
            ),
            Recommend(
                0,
                "“내가 그리워한 건 여름이 아니라 여름의 나였다” 휴가, 수영, 낮술, 머슬 셔츠, 전 애인… 여름을 말할 때 우리가 이야기하는 것들 아무튼 시리즈의 서른 번째 책. 『보노보노처럼 살다니 다행이야』 『아무것도 안 해도 아무렇지 않구나』 등으로 많은 독자의 공감을 불러일으킨 김신회 작가의 신작으로, 1년 내내 여름만 기다리며 사는 그가 마치 여름에게 ",
                "박현정",
                "조예슬",
                "2022/01/27",
                0,
                "아무튼, 여름",
                "김신회",
                "http://image.yes24.com/goods/90365124/XL",
                "https://image.aladin.co.kr/product/25517/79/cover500/e422537751_1.jpg"
            ),
            Recommend(
                1,
                "공감을 불러일으키는 이야기와 서정적이며 사려 깊은 문장, 그리고 그 안에 자리한 뜨거운 문제의식으로 등단 이후 줄곧 폭넓은 독자의 지지와 문학적 조명을 두루 받고 있는 작가 최은영의 첫 장편소설. ‘문화계 프로가 뽑은 차세대 주목할 작가’(동아일보) ‘2016, 2018 소설가들이 뽑은 올해의 소설’(교보문고 주관) ‘독자들이 뽑은 한국문학의 미래가 될 ",
                "문새연",
                "이태영",
                "2022/05/08",
                1,
                "밝은 밤",
                "누구지",
                "http://image.yes24.com/goods/90365124/XL",
                "https://image.aladin.co.kr/product/25517/79/cover500/e422537751_1.jpg"
            ),
            Recommend(
                2,
                "“왜 아무리 노력해도 나쁜 심리 습관에서 벗어나지 못하는 걸까?” 지나친 감정 기복, 과도한 자기 비난, 오래된 마음의 상처, 습관적 외로움 등으로 매일매일 흔들리는 사람들에게 마음의 중심을 잡고 인생을 주도적으로 이끄는 법을 알려주는 책 《홀로서기 심리학》. 저자는 지나친 의존과 예민함으로 인해 쉽게 상처받는 사람들, 마음대로 되지 않는 세상과 타인을 ",
                "문수빈",
                "이현우",
                "2022/12/11",
                2,
                "홀로서기 심리학",
                "누구야",
                "http://image.yes24.com/goods/90365124/XL",
                "https://image.aladin.co.kr/product/25517/79/cover500/e422537751_1.jpg"
            )
        )
        private val recommendingList = listOf(
            Recommend(
                0,
                "“내가 그리워한 건 여름이 아니라 여름의 나였다” 휴가, 수영, 낮술, 머슬 셔츠, 전 애인… 여름을 말할 때 우리가 이야기하는 것들 아무튼 시리즈의 서른 번째 책. 『보노보노처럼 살다니 다행이야』 『아무것도 안 해도 아무렇지 않구나』 등으로 많은 독자의 공감을 불러일으킨 김신회 작가의 신작으로, 1년 내내 여름만 기다리며 사는 그가 마치 여름에게 ",
                "김하정",
                "이영주",
                "2022/01/27",
                0,
                "눈보라 체이스",
                "양윤옥",
                "https://image.yes24.com/goods/72310907/XL",
                "https://image.aladin.co.kr/product/25517/79/cover500/e422537751_1.jpg"
            ),
            Recommend(
                1,
                "공감을 불러일으키는 이야기와 서정적이며 사려 깊은 문장, 그리고 그 안에 자리한 뜨거운 문제의식으로 등단 이후 줄곧 폭넓은 독자의 지지와 문학적 조명을 두루 받고 있는 작가 최은영의 첫 장편소설. ‘문화계 프로가 뽑은 차세대 주목할 작가’(동아일보) ‘2016, 2018 소설가들이 뽑은 올해의 소설’(교보문고 주관) ‘독자들이 뽑은 한국문학의 미래가 될 ",
                "이영주",
                "박강희",
                "2022/05/08",
                1,
                "오늘 밥 뭐지?",
                "누구지",
                "http://image.yes24.com/goods/90365124/XL",
                "https://image.aladin.co.kr/product/25517/79/cover500/e422537751_1.jpg"
            )
        )
    }
}
