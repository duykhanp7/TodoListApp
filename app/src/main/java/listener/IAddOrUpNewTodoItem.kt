package listener

import model.TodoItem

interface IAddOrUpNewTodoItem {
    fun addOrUpNewTodoItem(todoItem: TodoItem, typeAddOrUp:Int)
}