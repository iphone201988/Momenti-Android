package com.tech.momenti.ui.auth

import com.tech.momenti.base.BaseViewModel
import com.tech.momenti.base.utils.Resource
import com.tech.momenti.base.utils.event.SingleRequestEvent
import com.tech.momenti.data.api.ApiHelper
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthCommonVM @Inject constructor(
    private val apiHelper: ApiHelper,
) : BaseViewModel() {
    val observeCommon = SingleRequestEvent<JsonObject>()

    fun login(data: HashMap<String, Any>, url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            observeCommon.postValue(Resource.loading(null))
            try {
                val response = apiHelper.apiForRawBody(data, url)
                if (response.isSuccessful && response.body() != null) {
                    observeCommon.postValue(Resource.success("login", response.body()))
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


    fun createAccount(data: HashMap<String, Any>, url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            observeCommon.postValue(Resource.loading(null))
            try {
                val response = apiHelper.apiForRawBody(data, url)
                if (response.isSuccessful && response.body() != null) {
                    observeCommon.postValue(Resource.success("createAccount", response.body()))
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

    fun verifyOtp(data: HashMap<String, Any>, url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            observeCommon.postValue(Resource.loading(null))
            try {
                val response = apiHelper.apiForRawBody(data, url)
                if (response.isSuccessful && response.body() != null) {
                    observeCommon.postValue(Resource.success("verifyOtp", response.body()))
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


