package com.overheat.capstoneproject.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.overheat.capstoneproject.core.data.source.remote.RemoteDataSource
import com.overheat.capstoneproject.core.domain.model.Diagnose
import com.overheat.capstoneproject.core.domain.usecase.SkinCancerUseCase
import com.overheat.capstoneproject.core.utils.DataMapper

class ProfileViewModel(
    private val useCase: SkinCancerUseCase,
    private val remote: RemoteDataSource
) : ViewModel() {

    fun isLogin() : Boolean {
        return useCase.getUserName() != null
    }

    fun getUserName() : String? {
        return useCase.getUserName()
    }

    fun getUserEmail() : String? {
        return useCase.getUserEmail()
    }

    fun addNewUser(name: String, email: String, passHash: String) {
        useCase.addNewUser(name, email, passHash)
        useCase.getActiveToken(email, passHash)
    }

    var added: MutableLiveData<Boolean?> = MutableLiveData(null)
    fun addNewUserRemote(name: String, email: String, passHash: String) {
        remote.addNewUserVoid(name, email, passHash) {
            if (it != null) {
                useCase.getActiveToken(name, email)
                added.value = true
            } else {
                added.value = false
            }
        }
    }

    fun login(email: String, passHash: String) {
        useCase.getActiveToken(email, passHash)
    }

    var successLogin: MutableLiveData<Boolean?> = MutableLiveData(null)
    fun loginRemote(email: String, passHash: String) {
        remote.getActiveTokenVoid(email, passHash) {
            if (it != null) {
                if (it.isValid) {
                    useCase.setUserLogin(it.name, it.email, passHash, it.token, it.isValid)
                    successLogin.value = true
                } else {
                    successLogin.value = false
                }
            }
        }
    }

    val listDiagnose: MutableLiveData<List<Diagnose>?> = MutableLiveData(null)
    fun getUserHistory() {
        remote.getAllHistoryDiagnoseVoid {
            if (it != null) {
                listDiagnose.value = DataMapper.mapResponseToDomainListDiagnose(it)
            }
        }
    }
}