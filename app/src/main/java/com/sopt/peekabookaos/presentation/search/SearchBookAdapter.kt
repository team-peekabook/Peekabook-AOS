import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.data.entity.Book
class SearchBookAdapter :
    ListAdapter<Book, SearchBookAdapter.SearchBookViewHolder>(searchBookDiffCallBack) {
}
