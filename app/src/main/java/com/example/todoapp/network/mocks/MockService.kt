package com.example.todoapp.network.mocks

import okhttp3.mockwebserver.MockResponse

object MockService {

    private const val TYPE_REQUEST_GET = "GET"
    private const val TYPE_REQUEST_UPDATE = "UPDATE"
    private const val TYPE_REQUEST_AUTH = "AUTH"

    fun mockRepos(
        path: String,
        typeRequest: String = TYPE_REQUEST_GET
    ): MockResponse {
        val mockUtils = MockUtils()
        val requestResponseMap = mockUtils.baseRequestResponseMap
        if (requestResponseMap.containsKey(path)) {
            when (typeRequest) {
                TYPE_REQUEST_GET -> return requestResponseMap[path]
                    ?: CustomMockResponse.failNotFoundResponseMock()
                TYPE_REQUEST_UPDATE -> return requestResponseMap["$path/update"]
                    ?: CustomMockResponse.failNotFoundResponseMock()
                TYPE_REQUEST_AUTH -> return requestResponseMap[path]
                    ?: CustomMockResponse.failAuthorization()
                else -> CustomMockResponse.failBadRequestResponseMock()
            }
        }
        return CustomMockResponse.failOnServerResponseMock()
    }
}