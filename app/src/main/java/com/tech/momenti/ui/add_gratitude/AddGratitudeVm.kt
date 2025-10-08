package com.tech.momenti.ui.add_gratitude

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
class AddGratitudeVm @Inject constructor(private val apiHelper: ApiHelper) : BaseViewModel() {

    val observeCommon = SingleRequestEvent<JsonObject>()

    fun addGratitude(data: HashMap<String, Any>, url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            observeCommon.postValue(Resource.loading(null))
            try {
                val response = apiHelper.apiPostForRawBody(url, data)
                if (response.isSuccessful && response.body() != null) {
                    observeCommon.postValue(Resource.success("addGratitude", response.body()))
                } else {
                    observeCommon.postValue(
                        Resource.error(
                            handleErrorResponse(
                                response.errorBody(),
                                response.code()
                            ), null
                        )
                    )
                }
            } catch (e: Exception) {
                observeCommon.postValue(Resource.error(e.message.toString(), null))
            }
        }
    }
}