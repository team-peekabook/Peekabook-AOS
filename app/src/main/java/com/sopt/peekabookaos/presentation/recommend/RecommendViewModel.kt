import com.sopt.peekabookaos.data.repository.RecommendRepository
import dagger.hilt.android.lifecycle.HiltViewModel
@HiltViewModel
class RecommendViewModel @Inject constructor(
    private val recommendRepository: RecommendRepository
) : ViewModel() {
