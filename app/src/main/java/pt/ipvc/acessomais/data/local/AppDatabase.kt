package pt.ipvc.acessomais.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [LocalEntity::class, UserEntity::class], // Ambas as entidades aqui
    version = 2, // Subir para a versão 2
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun localDao(): LocalDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "acesso_mais_db"
                )
                    .fallbackToDestructiveMigration() // Limpa dados antigos se a versão mudar
                    .build().also { INSTANCE = it }
            }
    }
}