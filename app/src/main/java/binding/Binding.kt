package binding

import adapter.TodoAdapter
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

//BINDING ADAPTER FOR RECYCLER VIEW OF _TODO ADAPTER
@BindingAdapter("todoAdapter")
fun setTodoAdapter(recyclerView: RecyclerView, todoAdapter: TodoAdapter) {
    val layoutManager = LinearLayoutManager(recyclerView.context)
    recyclerView.setHasFixedSize(true)
    recyclerView.layoutManager = layoutManager
    recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context,LinearLayoutManager.VERTICAL))
    recyclerView.adapter = todoAdapter
}