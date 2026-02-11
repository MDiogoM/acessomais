package pt.ipvc.acessomais.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalDao {
    @Query("SELECT * FROM locais ORDER BY updatedAt DESC")
    fun getAllLocais(): Flow<List<LocalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(local: LocalEntity)

    @Delete
    suspend fun delete(local: LocalEntity)

    // Funções para o sistema de Login/Registo
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: UserEntity)
}