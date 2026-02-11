package pt.ipvc.acessomais.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import pt.ipvc.acessomais.data.local.AppDatabase
import pt.ipvc.acessomais.data.local.UserEntity
import pt.ipvc.acessomais.data.model.Local
import pt.ipvc.acessomais.data.repo.LocalRepository

class LocalViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = AppDatabase.getInstance(app).localDao()
    private val repository = LocalRepository(dao)

    val locais: StateFlow<List<Local>> = repository.getLocais()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _localRecemAdicionado = MutableStateFlow<Local?>(null)
    val localRecemAdicionado = _localRecemAdicionado.asStateFlow()

    // Nova função para gerir Login e Registo
    fun autenticar(email: String, pass: String, isLogin: Boolean, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            if (isLogin) {
                val user = dao.getUserByEmail(email)
                if (user != null && user.password == pass) onSuccess()
                else onError("Email ou password incorretos")
            } else {
                val exist = dao.getUserByEmail(email)
                if (exist != null) onError("O utilizador já existe")
                else {
                    dao.insertUser(UserEntity(email, pass))
                    onSuccess()
                }
            }
        }
    }

    fun saveLocal(local: Local) {
        viewModelScope.launch {
            repository.saveLocal(local)
            _localRecemAdicionado.value = local
        }
    }

    fun deleteLocal(local: Local) {
        viewModelScope.launch { repository.deleteLocal(local) }
    }

    fun focarLocal(local: Local) {
        _localRecemAdicionado.value = local
    }

    fun limparFoco() {
        _localRecemAdicionado.value = null
    }
}