package erfolgi.com.football_match.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import erfolgi.com.football_match.models.Favorites
import org.jetbrains.anko.db.*

class DatabaseHelper (ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteMatch.db", null, 1) {
    companion object {
        private var instance: DatabaseHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseHelper {
            if (instance == null) {
                instance = DatabaseHelper(ctx.applicationContext)
            }
            return instance as DatabaseHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(Favorites.TABLE_FAVORITE, true,
                Favorites.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Favorites.EVENT_ID to TEXT + UNIQUE,
                Favorites.DATE to TEXT,
                Favorites.TIME to TEXT,
                Favorites.HOME_TEAM to TEXT,
                Favorites.HOME_SCORE to TEXT,
                Favorites.AWAY_TEAM to TEXT,
                Favorites.AWAY_SCORE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(Favorites.TABLE_FAVORITE, true)
    }
}

// Access property for Context
val Context.database: DatabaseHelper
    get() = DatabaseHelper.getInstance(applicationContext)