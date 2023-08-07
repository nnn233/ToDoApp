package com.example.todoapp.network.mocks

import com.example.todoapp.network.mocks.CustomMockResponse.successAuthorization
import com.example.todoapp.network.mocks.CustomMockResponse.successResponse
import com.example.todoapp.network.mocks.CustomMockResponse.successResponseWithRevision
import okhttp3.mockwebserver.MockResponse

class MockUtils {
    companion object {
        const val TOKEN =
            "oGiePXIhmwt2RCpW1-M/UfSQHhP/ADMjBir?FC7IJnjSBEjdmaZ?hho6Sbtnrek95unOyh!vJnRZZFtbEiGIcXWM24UC4wmdB=7WD7n7pUuLo2dRNnQZhodJmdBu!cei"
        const val LIST_GET_REQUEST_PATH = "/api/list"
        const val LIST_UPDATE_REQUEST_PATH = "/api/list/update"
        const val ITEM_BY_ID_1_REQUEST_PATH = "/api/list/1"
        const val ITEM_BY_ID_2_REQUEST_PATH = "/api/list/2"
        const val ITEM_BY_ID_3_REQUEST_PATH = "/api/list/3"
        const val ITEM_BY_ID_4_REQUEST_PATH = "/api/list/4"
        const val ITEM_BY_ID_5_REQUEST_PATH = "/api/list/5"
        const val AUTHORIZATION_REQUEST_PATH = "/api/authorization/$TOKEN"

        const val LIST_RESPONSE_FILE = "api/list.json"
        const val UPDATED_LIST_RESPONSE_FILE = "api/list/update.json"
        const val ITEM_BY_ID_1_RESPONSE_FILE = "api/list/1.json"
        const val ITEM_BY_ID_2_RESPONSE_FILE = "api/list/2.json"
        const val ITEM_BY_ID_3_RESPONSE_FILE = "api/list/3.json"
        const val ITEM_BY_ID_4_RESPONSE_FILE = "api/list/4.json"
        const val ITEM_BY_ID_5_RESPONSE_FILE = "api/list/5.json"
        const val AUTHORIZATION_RESPONSE_FILE = "api/list.json"
    }

    // TODO: Create json files
    val baseRequestResponseMap: HashMap<String, MockResponse?> = hashMapOf(
        LIST_GET_REQUEST_PATH to successResponse(LIST_RESPONSE_FILE),
        LIST_UPDATE_REQUEST_PATH to successResponseWithRevision(UPDATED_LIST_RESPONSE_FILE),
        ITEM_BY_ID_1_REQUEST_PATH to successResponse(ITEM_BY_ID_1_RESPONSE_FILE),
        ITEM_BY_ID_2_REQUEST_PATH to successResponse(ITEM_BY_ID_2_RESPONSE_FILE),
        ITEM_BY_ID_3_REQUEST_PATH to successResponse(ITEM_BY_ID_3_RESPONSE_FILE),
        ITEM_BY_ID_4_REQUEST_PATH to successResponse(ITEM_BY_ID_4_RESPONSE_FILE),
        ITEM_BY_ID_5_REQUEST_PATH to successResponse(ITEM_BY_ID_5_RESPONSE_FILE),
        AUTHORIZATION_REQUEST_PATH to successAuthorization(AUTHORIZATION_RESPONSE_FILE, TOKEN)
    )
}