package com.example.todoapp.network.mocks

import okhttp3.mockwebserver.MockResponse

object CustomMockResponse {
    private var revision = 0

    fun failNotFoundResponseMock(): MockResponse =
        MockResponse()
            .setResponseCode(404)

    fun successResponse(fileName: String): MockResponse =
        MockResponse()
            .setResponseCode(200)
            .setHeader("X-Last-Known-Revision", 1)
            .setBody(FileToStringConverter.getFileAsString(fileName))

    fun successResponseWithRevision(fileName: String): MockResponse =
        MockResponse()
            .setResponseCode(200)
            .setHeader("X-Last-Known-Revision", ++revision)
            .setBody(FileToStringConverter.getFileAsString(fileName))

    fun failBadRequestResponseMock(): MockResponse =
        MockResponse()
            .setResponseCode(400)

    fun failOnServerResponseMock(): MockResponse =
        MockResponse()
            .setResponseCode(500)

    fun successAuthorization(fileName: String, token:String): MockResponse =
        MockResponse()
            .setResponseCode(200)
            .setHeader("Authorization: Bearer {token}", token)
            .setBody(FileToStringConverter.getFileAsString(fileName))

    fun failAuthorization(): MockResponse =
        MockResponse().setResponseCode(401)
}