package xyz.mongop.chatapp.models

/**
 * Created by Owner on 2017/10/25.
 */
class FriendlyMessage() {
    var id:String? = null
    var text:String? = null
    var name:String? = null

    constructor(id: String, text: String,name: String):this(){
        this.id = id
        this.text = text
        this.name = name
    }
}