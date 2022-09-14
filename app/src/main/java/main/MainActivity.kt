package main

import adapter.TodoAdapter
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import bottomsheet.BottomSheetTodoItem
import com.example.todolistapp.R
import com.example.todolistapp.databinding.ActivityMainBinding
import database.StoreData
import listener.IAddOrUpNewTodoItem
import model.TodoItem

class MainActivity : AppCompatActivity(), IAddOrUpNewTodoItem {

    //PROPERTIES
    private var todoListItem:ArrayList<TodoItem> = ArrayList()
    lateinit var todoAdapter:TodoAdapter
    var nothingTodo = true
    private lateinit var binding:ActivityMainBinding
    private lateinit var bottomSheetTodoItem: BottomSheetTodoItem
    private lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var itemTouchCallBack:ItemTouchHelper.SimpleCallback

    //GLOBAL VARIABLES
    companion object{
        lateinit var storeData: StoreData
    }

    //ON CREATE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //CHANGE COLOR OF STATUS BAR
        window.statusBarColor = ContextCompat.getColor(applicationContext,R.color.top_tool_bar_color)

        //INITIALIZED BINDING
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.main = this

        //STORE DATABASE
        storeData = StoreData(applicationContext)

        //BOTTOM SHEET
        bottomSheetTodoItem = BottomSheetTodoItem(this)
        bottomSheetTodoItem.setStyle(DialogFragment.STYLE_NORMAL,R.style.customBottomSheetDialogBackground)

        //_TODO ADAPTER
        todoAdapter = TodoAdapter(bottomSheetTodoItem)
        todoAdapter.setFragmentManager(supportFragmentManager)
        todoListItem = storeData.retrieveDataFromDatabase()
        todoAdapter.setTodoList(todoListItem)

        if(todoListItem.size > 0){
            nothingTodo = false
            binding.recycleViewTodo.visibility = View.VISIBLE
            binding.nothingAddedView.visibility = View.GONE
        }
        else{
            binding.recycleViewTodo.visibility = View.GONE
            binding.nothingAddedView.visibility = View.VISIBLE
        }

        //INITIALIZED TOUCH CALL BACK
        itemTouchCallBack = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                storeData.removeTodoItem(todoAdapter.getItemTodo(pos))
                todoAdapter.removeTodoItem(pos)
                Toast.makeText(applicationContext,"Remove item successfully !",Toast.LENGTH_LONG).show()
            }

        }

        //ITEM TOUCH HELPER
        itemTouchHelper = ItemTouchHelper(itemTouchCallBack)
        //ATTACH ITEM TOUCH HELPER TO RECYCLER VIEW
        itemTouchHelper.attachToRecyclerView(binding.recycleViewTodo)

        bottomSheetTodoItem.idPos = todoListItem.size

    }


    //ON FLOATING BUTTON ADD _TODO ITEM CLICKED
    @SuppressLint("NotifyDataSetChanged")
    fun onButtonAddNewTodoItem() {
        showBottomSheetAddNewTodoItem()
    }

    private fun showBottomSheetAddNewTodoItem() {
        bottomSheetTodoItem.show(supportFragmentManager,"AAA")
        //bottomSheetTodoItem.changeTitleButtonAddOrUpdate()
    }

    //ADD OR UPDATE _TODO ITEM BY TYPE typeAddOrUp 0 -> add, 1->up
    override fun addOrUpNewTodoItem(todoItem: TodoItem, typeAddOrUp:Int) {
        if(typeAddOrUp == 0){
            todoAdapter.addNewTodoItem(todoItem)
            binding.nothingAddedView.visibility = View.GONE
            binding.recycleViewTodo.visibility = View.VISIBLE
            storeData.addTodoItem(todoItem)
            Toast.makeText(applicationContext,"Add new item successfully.",Toast.LENGTH_LONG).show()
        }
        else if(typeAddOrUp == 1){
            todoAdapter.updateNewTodoItem(todoItem)
            binding.nothingAddedView.visibility = View.GONE
            binding.recycleViewTodo.visibility = View.VISIBLE
            storeData.updateTodoItem(todoItem)
            Toast.makeText(applicationContext,"Update item successfully.",Toast.LENGTH_LONG).show()
            bottomSheetTodoItem.setTypeAddOrUp(0)
        }
    }


}