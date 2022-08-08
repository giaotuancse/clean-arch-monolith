package com.tuangiao.monolith.network

import com.tuangiao.monolith.data.model.LegoThemeModel
import com.tuangiao.monolith.network.model.ResultsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LegoServices {

    companion object {
        const val ENDPOINT = "https://rebrickable.com/api/v3/"
    }

    @GET("lego/themes/")
    suspend fun getThemes(@Query("page") page: Int? = null,
                          @Query("page_size") pageSize: Int? = null,
                          @Query("ordering") order: String? = null): ResultsResponse<List<LegoThemeModel>>


}