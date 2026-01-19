    package com.example.myapplication.data.network

    import retrofit2.Response
    import retrofit2.http.GET
    import retrofit2.http.PATCH
    import retrofit2.http.Path

    interface TableApiClient {
        @GET("tables/uid/{uid}")
        suspend fun getByUid(@Path("uid") uid: String): Response<String>

        @PATCH("tables/uid/{uid}/call-waiter")
        suspend fun callWaiter(@Path("uid") uid: String): Response<Unit>

        @PATCH("tables/uid/{uid}/request-bill")
        suspend fun requestBill(@Path("uid") uid: String): Response<Unit>
    }
