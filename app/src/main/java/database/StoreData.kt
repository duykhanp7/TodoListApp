package database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import model.TodoItem
import util.Utils

class StoreData(context: Context) : SQLiteOpenHelper(context,Utils.DATABASE_NAME,null,Utils.DATABASE_VERSION) {

    //CREATE
    override fun onCreate(sqlite: SQLiteDatabase?) {
        sqlite?.execSQL(Utils.CREATE_TABLE)
    }

    //ADD NEW _TODO ITEM
    fun addTodoItem(todoItem: TodoItem) {
        val sqlite = writableDatabase
        val contentValues = ContentValues()
        val stateInt = when(todoItem.state){
                    true -> 1
                    false -> 0
        }
        contentValues.put(Utils.COLUMN_ID,todoItem.id)
        contentValues.put(Utils.COLUMN_CONTENTS,todoItem.content)
        contentValues.put(Utils.COLUMN_COMPLETED,stateInt.toString())

        sqlite.insert(Utils.TABLE_NAME,null,contentValues)
    }

    //UPDATE _TODO ITEM
    fun updateTodoItem(todoItem: TodoItem) {
        val sqlite = writableDatabase
        val contentValues = ContentValues()
        val stateInt = when(todoItem.state){
            true -> 1
            false -> 0
        }
        contentValues.put(Utils.COLUMN_CONTENTS,todoItem.content)
        contentValues.put(Utils.COLUMN_COMPLETED,stateInt.toString())

        sqlite.update(Utils.TABLE_NAME,contentValues,"${Utils.COLUMN_ID}=?", arrayOf(todoItem.id))
    }

    //REMOVE _TODO ITEM
    fun removeTodoItem(todoItem: TodoItem) {
        val sqlite = writableDatabase
        sqlite.delete(Utils.TABLE_NAME,"${Utils.COLUMN_ID}=?", arrayOf(todoItem.id))
    }

    //RETURN A LIST _TODO ITEMS
    @SuppressLint("Recycle")
    fun retrieveDataFromDatabase():ArrayList<TodoItem> {
        val sqlite = writableDatabase
        var list = ArrayList<TodoItem>()
        val cursor = sqlite.rawQuery("SELECT * FROM ${Utils.TABLE_NAME}",null)

        if(cursor != null){

            while (cursor.moveToNext()){
                var todoItem = TodoItem("","", state = false)
                todoItem.id = cursor.getString(0)
                todoItem.content = cursor.getString(1)
                todoItem.state = when(cursor.getString(2)){
                                "1" -> true
                                "0" -> false
                                else -> {false}
                }
                list.add(todoItem)
            }
        }

        return list
    }

    //UPGRADE
    override fun onUpgrade(sqlite: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}