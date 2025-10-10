package com.tech.momenti.ui.home_screen

import com.google.gson.JsonObject
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.base.utils.Resource
import com.tech.momenti.base.utils.event.SingleRequestEvent
import com.tech.momenti.data.TaskData
import com.tech.momenti.data.api.ApiHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val apiHelper: ApiHelper) : BaseViewModel() {


    val obrCommon = SingleRequestEvent<JsonObject>()


    fun getStreak(url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            obrCommon.postValue(Resource.loading(null))
            try {
                val response = apiHelper.apiGetOnlyAuthToken(url)
                if (response.isSuccessful && response.body() != null) {
                    obrCommon.postValue(Resource.success("getStreak", response.body()))
                } else {
                    obrCommon.postValue(
                        Resource.error(
                            handleErrorResponse(
                                response.errorBody(),
                                response.code()
                            ), null
                        )
                    )
                }
            } catch (e: Exception) {
                obrCommon.postValue(Resource.error(e.message.toString(), null))
            }
        }
    }

    fun getTaskGratitude(data : HashMap<String, Any>, url: String){
        CoroutineScope(Dispatchers.IO).launch {
            obrCommon.postValue(Resource.loading(null))
            try {
                val response = apiHelper.apiGetWithQueryAuth(url,data)
                if (response.isSuccessful && response.body() != null) {
                    obrCommon.postValue(Resource.success("getTaskGratitude", response.body()))
                } else {
                    obrCommon.postValue(
                        Resource.error(
                            handleErrorResponse(
                                response.errorBody(),
                                response.code()
                            ), null
                        )
                    )
                }
            } catch (e: Exception) {
                obrCommon.postValue(Resource.error(e.message.toString(), null))
            }
        }
    }


    fun getProfile(url : String){
        CoroutineScope(Dispatchers.IO).launch {
            obrCommon.postValue(Resource.loading(null))
            try {
                val response = apiHelper.apiGetOnlyAuthToken(url)
                if (response.isSuccessful && response.body() != null) {
                    obrCommon.postValue(Resource.success("getProfile", response.body()))
                } else {
                    obrCommon.postValue(
                        Resource.error(
                            handleErrorResponse(
                                response.errorBody(),
                                response.code()
                            ), null
                        )
                    )
                }
            } catch (e: Exception) {
                obrCommon.postValue(Resource.error(e.message.toString(), null))
            }
        }
    }
}