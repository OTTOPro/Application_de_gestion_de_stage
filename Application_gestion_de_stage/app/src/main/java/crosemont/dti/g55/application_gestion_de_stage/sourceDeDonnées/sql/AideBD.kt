// DBHelper.kt
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AideBD(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "offres_database"
        const val DATABASE_VERSION = 1
        const val TABLE_OFFRES = "offres"

        const val COLUMN_ID = "id"
        const val COLUMN_TITRE_DU_POSTE = "titre_du_poste"
        const val COLUMN_EMPLOYEUR = "employeur"
        const val COLUMN_TYPE = "type"
        const val COLUMN_LIEU = "lieu"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_OFFRE_SAUVEGARDEE = "offre_sauvegardee"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_OFFRES (
                $COLUMN_ID INTEGER PRIMARY KEY,
                $COLUMN_TITRE_DU_POSTE TEXT,
                $COLUMN_EMPLOYEUR TEXT,
                $COLUMN_TYPE TEXT,
                $COLUMN_LIEU TEXT,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_OFFRE_SAUVEGARDEE INTEGER
            )
        """.trimIndent()

        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Upgrade logic here
    }
}
