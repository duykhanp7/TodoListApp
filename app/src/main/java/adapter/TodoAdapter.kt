package adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.view.get
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import bottomsheet.BottomSheetTodoItem
import com.example.todolistapp.R
import com.example.todolistapp.databinding.LayoutItemTodoBinding
import main.MainActivity.Companion.storeData
import model.TodoItem

class TodoAdapter(bottomSheetTodoItemTemp: BottomSheetTodoItem) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    //PROPERTIES
    private var todoListItems:ArrayList<TodoItem> = ArrayList()
    private var bottomSheetTodoItem: BottomSheetTodoItem = bottomSheetTodoItemTemp

    //GLOBAL VARIABLES
    companion object{
        lateinit var fragmentManager:FragmentManager
    }

    //ON CREATE VIEW HOLDER
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutItemTodoBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding,bottomSheetTodoItem);
    }

    //BIND VIEW HOLDER
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todoItem = todoListItems[position]
        holder.binding.todo = todoItem
        holder.setTodoItem(todoItem)
    }

    //RETURN QUANTITY OF LIST _TODO ITEMS
    override fun getItemCount(): Int {
        return todoListItems.size
    }

    //SET _TODO LIST ITEMS
    @SuppressLint("NotifyDataSetChanged")
    fun setTodoList(list: ArrayList<TodoItem>) {
        this.todoListItems = list
        notifyDataSetChanged()
    }

    //ADD NEW _TODO ITEM
    fun addNewTodoItem(todoItem: TodoItem) {
        this.todoListItems.add(todoItem)
        val size = todoListItems.size
        notifyItemInserted(size)
    }

    //UPDATE _TODO ITEM
    fun updateNewTodoItem(todoItem: TodoItem) {
        for(pos in todoListItems.indices){
            if(todoListItems[pos].id == todoItem.id){
                todoListItems[pos].content = todoItem.content
                notifyItemChanged(pos)
                break;
            }
        }
    }

    //REMOVE _TODO ITEM OUT OF LIST
    fun removeTodoItem(pos:Int) {
        this.todoListItems.removeAt(pos)
        this.notifyItemRemoved(pos)
    }

    //RETURN A _TODO ITEM
    fun getItemTodo(pos:Int):TodoItem {
        return todoListItems[pos]
    }

    //SET FRAGMENT MANAGER TO SHOW BOTTOM SHEET DIALOG ADD _TODO ITEM
    fun setFragmentManager(fragmentManagerTemp: FragmentManager){
        fragmentManager = fragmentManagerTemp
    }

    //VIEW HOLDER
    @SuppressLint("ResourceAsColor")
    inner class ViewHolder(bindingTemp: LayoutItemTodoBinding,bottomSheetTodoItemTemp: BottomSheetTodoItem) : RecyclerView.ViewHolder(bindingTemp.root), View.OnClickListener{
        var binding:LayoutItemTodoBinding = bindingTemp
        private lateinit var todoItem: TodoItem
        private var bottomSheetTodoItem: BottomSheetTodoItem = bottomSheetTodoItemTemp

        fun setTodoItem(todoItem: TodoItem) {
            this.todoItem = todoItem
        }

        //INITIALIZED
        init {
            binding.checkBoxTodoItem.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener {
                    compoundButton, b ->
                Log.i("AAA","STATE CHANGED : "+b)
                todoItem.state = b
                storeData.updateTodoItem(todoItem)
                if(b){
                    //binding.textViewContents.setTextColor(R.color.brown)
                    binding.textViewContents.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                }
                else{
                    //binding.textViewContents.setTextColor(R.color.black)
                    binding.textViewContents.paintFlags = 0
                }
            })


            binding.root.setOnClickListener(this)

            binding.layoutItemTodo.setOnClickListener(View.OnClickListener {
                Log.i("AAA","LAYOUT ITEM")
                showBottomSheetChangeContents()
            })

            binding.textViewContents.setOnClickListener(View.OnClickListener {
                Log.i("AAA","TEXT ITEM")
                showBottomSheetChangeContents()
            })

        }

        //EVENT CLICK
        override fun onClick(view: View?) {
            Log.i("AAA","ROOT")
            showBottomSheetChangeContents()
        }

        //FUNCTION SHOW BOTTOM SHEET TO ADD OR UPDATE _TODO ITEM
        private fun showBottomSheetChangeContents() {
            bottomSheetTodoItem.setTypeAddOrUp(1)
            bottomSheetTodoItem.setTodoItemValues(todoItem.id,todoItem.content)
            bottomSheetTodoItem.show(fragmentManager,"AAA")
        }

    }

}