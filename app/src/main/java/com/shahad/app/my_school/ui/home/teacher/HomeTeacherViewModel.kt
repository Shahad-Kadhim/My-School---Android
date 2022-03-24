package com.shahad.app.my_school.ui.home.teacher

import androidx.lifecycle.*
import com.shahad.app.my_school.data.MySchoolRepository
import com.shahad.app.my_school.ui.base.BaseViewModel
import com.shahad.app.my_school.ui.ClassInteractionListener
import com.shahad.app.my_school.util.Event
import com.shahad.app.my_school.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeTeacherViewModel @Inject constructor(
    private val repository: MySchoolRepository
): BaseViewModel(), ClassInteractionListener {

    val search = MutableLiveData<String?>(null)

    val refreshState = MutableLiveData<Boolean>(false)

    val classes = Transformations.switchMap(search){ searchKey ->
            repository.getTeacherClasses(searchKey?.takeIf { it.isNotBlank() }).asLiveData()
        }



//        MediatorLiveData<LiveData<List<ClassList>>>().apply {
//            addSource(search){ searchKey ->
//                this.postValue(repository.getTeacherClasses(searchKey?.takeIf { it.isNotBlank() }).asLiveData())
//            }
//            addSource(refreshState){
//                if(it){
//                    viewModelScope.launch {
//                        repository.refreshTeacherClasses(search.value)
//                        this@apply.postValue(repository.getTeacherClasses(search.value?.takeIf { it.isNotBlank() }).asLiveData())
//                        refreshState.postValue(false)
//                    }
//                }
//            }
//        }


    private val _clickCreateClassEvent = MutableLiveData<Event<Boolean>>()
    val clickCreateClassEvent: LiveData<Event<Boolean>> = _clickCreateClassEvent

    private val _clickSchoolEvent = MutableLiveData<Event<Boolean>>()
    val clickSchoolEvent: LiveData<Event<Boolean>> = _clickSchoolEvent

    private val _clickClassEvent = MutableLiveData<Event<Pair<String,String>>>()
    val clickClassEvent: LiveData<Event<Pair<String,String>>> = _clickClassEvent

    private val _clickProfileEvent = MutableLiveData<Event<Boolean>>()
    val clickProfileEvent: LiveData<Event<Boolean>> = _clickProfileEvent


    init {
        refreshClasses()
    }

    fun refreshClasses(){
        viewModelScope.launch {
            repository.refreshTeacherClasses(search.value)
            refreshState.postValue(false)
        }
    }

    val unAuthentication = MediatorLiveData<State.UnAuthorization?>().apply {
        addSource(classes){
//            if(it is State.UnAuthorization) this.postValue(it)
        }
    }

    fun onClickCreateClass(){
        _clickCreateClassEvent.postValue(Event(true))
    }

    fun onClickSchools(){
        _clickSchoolEvent.postValue(Event(true))
    }

    override fun onClickClass(classId: String, className: String) {
        _clickClassEvent.postValue(Event(Pair(classId,className)))
    }

    fun onclickProfile(){
        _clickProfileEvent.postValue(Event(true))
    }

}