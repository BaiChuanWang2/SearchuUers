package com.example.searchusers.data.api.`in`

import com.example.searchusers.data.model.UserModel

class SearchResponse(val items: ArrayList<UserModel>) : BaseResponse() {
}