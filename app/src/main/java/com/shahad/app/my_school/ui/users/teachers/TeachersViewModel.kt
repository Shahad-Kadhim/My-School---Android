package com.shahad.app.my_school.ui.users.teachers

import android.util.Log
import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.data.remote.response.BaseResponse
import com.shahad.app.my_school.data.remote.response.UserDto
import com.shahad.app.my_school.domain.mappers.UserSelected
import com.shahad.app.my_school.ui.users.BaseUsersViewModel
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeachersViewModel @Inject constructor(
    repository: MySchoolRepository
): BaseUsersViewModel(repository){

    private val teachers= MediatorLiveData<LiveData<State<BaseResponse<List<UserSelected>>?>>>().apply {
        addSource(schoolName){
            it?.let { schoolName ->
                this.postValue(repository.getSchoolTeachers(schoolName, search.value?.takeIf { it.isNotBlank() }).asLiveData())
            }
        }
        addSource(search){ searchKey ->
            schoolName.value?.let { schoolName ->
                this.postValue(repository.getSchoolTeachers(schoolName ,searchKey?.takeIf { it.isNotBlank() }).asLiveData())
            }
        }
        addSource(refreshState) {
            this.refresh(it,repository::getSchoolTeachers)
        }
    }

    override val users: LiveData<State<BaseResponse<List<UserSelected>>?>> = Transformations.switchMap(teachers) { it }

    private val _clickAddTeacherEvent = MutableLiveData<Event<String>>()
    val clickAddTeacherEvent: LiveData<Event<String>> = _clickAddTeacherEvent

    override fun onClickAdd() {
        schools.value?.find { it.name == schoolName.value }?.id?.let { schoolId ->
            Log.i("TAG",schoolId)
            _clickAddTeacherEvent.postValue(Event(schoolId))
        }
    }
}