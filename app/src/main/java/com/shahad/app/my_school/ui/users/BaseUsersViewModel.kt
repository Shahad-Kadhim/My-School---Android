package com.shahad.app.my_school.ui.users

import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.domain.mappers.UserSelected
import com.shahad.app.my_school.ui.UserSelectedInteractionListener
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State

abstract class BaseUsersViewModel(
    repository: MySchoolRepository
): BaseViewModel(), UserSelectedInteractionListener {

    val schools = repository.getMangerSchool().asLiveData()

    val search = MutableLiveData<String?>(null)

    val schoolName = MutableLiveData<String?>()

    abstract val users: LiveData<State<BaseResponse<List<UserSelected>>?>>

    abstract fun onClickAdd()

    private val _clickBackEvent = MutableLiveData<Event<Boolean>>()
    val clickBackEvent: LiveData<Event<Boolean>> = _clickBackEvent

    fun onClickBack(){
        _clickBackEvent.postValue(Event(true))
    }

    override fun onClickSelect(id: String) {
        TODO("Not yet implemented")
    }

}