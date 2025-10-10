package com.tech.momenti.ui.edit_profile

import com.google.gson.JsonObject
import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.base.utils.Resource
import com.tech.momenti.base.utils.event.SingleRequestEvent
import com.tech.momenti.data.api.ApiHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileVm @Inject constructor(private val apiHelper: ApiHelper) : BaseViewModel() {

    val obrCommon  = SingleRequestEvent<JsonObject>()


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

    fun updateProfile(data : HashMap<String, Any>, url : String){
        CoroutineScope(Dispatchers.IO).launch {
            obrCommon.postValue(Resource.loading(null))
            try {
                val response = apiHelper.apiPostForRawBody(url,data)
                if (response.isSuccessful && response.body() != null) {
                    obrCommon.postValue(Resource.success("updateProfile", response.body()))
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