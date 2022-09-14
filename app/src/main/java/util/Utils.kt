package util

class Utils {

    companion object{
        const val DATABASE_NAME = "TODO_DATABASE"
        const val TABLE_NAME = "TODO_TABLE"
        const val COLUMN_ID = "ID"
        const val COLUMN_CONTENTS = "CONTENTS"
        const val COLUMN_COMPLETED = "COMPLETE"
        const val DATABASE_VERSION = 1

        const val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ( $COLUMN_ID TEXT NOT NULL PRIMARY KEY, " +
                                                            "$COLUMN_CONTENTS TEXT, " +
                                                            "$COLUMN_COMPLETED TEXT )"

    }

}