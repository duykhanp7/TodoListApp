package bottomsheet

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.todolistapp.R
import com.example.todolistapp.databinding.BottomSheetAddTodoItemLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import listener.IAddOrUpNewTodoItem
import model.TodoItem

class BottomSheetTodoItem(iAddOrUpNewTodoItemTemp: IAddOrUpNewTodoItem) : BottomSheetDialogFragment() {

    //PROPERTIES
    private var todoItem:TodoItem = TodoItem("","", state = false)
    private lateinit var bottomSheetAddTodoItemLayoutBinding:BottomSheetAddTodoItemLayoutBinding
    private var iAddNewTodoItem = iAddOrUpNewTodoItemTemp
    //0 is ADD and 1 is UPDATE
    private var typeAddOrUp = 0
    var idPos:Int = 0


    //CREATE VIEW
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("AAA","VIEW CREAETD")
        bottomSheetAddTodoItemLayoutBinding = BottomSheetAddTodoItemLayoutBinding.inflate(layoutInflater)
        changeTitleButtonAddOrUpdate()
        return bottomSheetAddTodoItemLayoutBinding.root
    }

    //VIEW CREATED
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheetAddTodoItemLayoutBinding.todo = todoItem
        bottomSheetAddTodoItemLayoutBinding.buttonAddNewTodoItem.setOnClickListener(View.OnClickListener {
            onButtonAddOrUpNewTodoItem(typeAddOrUp)
        })
    }

    //RETURN A NEW _TODO ITEM OR _TODO ITEM AFTER CHANGED
    private fun getTodoItem():TodoItem {
        if(typeAddOrUp == 0){
            idPos += 1
            Log.i("AAA","POS ID : "+ idPos)
            return TodoItem(idPos.toString(),todoItem.content, state = false)
        }
        return TodoItem(todoItem.id,todoItem.content, state = false)
    }

    //SET NEW VALUES FOR _TODO ITEM
    fun setTodoItemValues(id:String,text:String){
        this.todoItem.id = id
        this.todoItem.content = text
    }

    //SET TYPE ADD OR UPDATE
    fun setTypeAddOrUp(type:Int) {
        this.typeAddOrUp = type
    }

    //CHANG TITLE OF BUTTON ADD NEW _TODO ITEM OR UPDATE
    private fun changeTitleButtonAddOrUpdate() {
        if(typeAddOrUp == 0){
            bottomSheetAddTodoItemLayoutBinding.buttonAddNewTodoItem.text = getString(R.string.add_item_text)
        }
        else if(typeAddOrUp == 1){
            bottomSheetAddTodoItemLayoutBinding.buttonAddNewTodoItem.text = getString(R.string.update_item_text)
        }
    }

    //EVENT CLICK ON BUTTON ADD OR UPDATE _TODO ITEM
    private fun onButtonAddOrUpNewTodoItem(type:Int) {
        iAddNewTodoItem.addOrUpNewTodoItem(getTodoItem(),type)
        dismiss()
    }

    //OVERRIDE SHOW
    override fun show(manager: FragmentManager, tag: String?) {
        super.show(manager, tag)
    }

    //OVERRIDE DISMISS
    override fun onDismiss(dialog: DialogInterface) {
        typeAddOrUp = 0
        todoItem.content = ""
        super.onDismiss(dialog)
    }


}