package com.example.my_email.db.letters

object LettersContract {

    const val TABLE_NAME = "letters"

    object Columns {
        const val ID = "id"
        const val TO_USER_EMAIL = "to_user_email"
        const val FROM_USER_EMAIL = "from_user_email"
        const val ATTACHMENTS_TITLE = "attachments_title"
        const val TITLE = "title"
        const val TIME = "time"
        const val TYPE_LETTER = "type_letter"
        const val CHAIN_TITLE = "chain_title"
        const val STATUS_TEXT = "status_text"
        const val TEXT_LETTER = "text_letter"
    }
}