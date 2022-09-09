package com.example.my_email.db.letters

import androidx.room.*
import com.example.my_email.db.attachments.Attachments
import com.example.my_email.db.attachments.AttachmentsContract
import com.example.my_email.db.letterStatuses.LetterStatusConverter
import com.example.my_email.db.letterStatuses.LetterStatuses
import com.example.my_email.db.letterStatuses.LetterStatusesContract
import com.example.my_email.db.letterStatuses.Status
import com.example.my_email.db.lettersChains.LettersChains
import com.example.my_email.db.lettersChains.LettersChainsContract
import com.example.my_email.db.users.Users
import com.example.my_email.db.users.UsersContract
import java.time.Instant

@Entity(
    tableName = LettersContract.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = Users::class,
            parentColumns = [UsersContract.Columns.EMAIL],
            childColumns = [LettersContract.Columns.TO_USER_EMAIL]
        ),
        ForeignKey(
          entity = Users::class,
          parentColumns = [UsersContract.Columns.EMAIL],
          childColumns = [LettersContract.Columns.FROM_USER_EMAIL]
        )
    ]
)
@TypeConverters(ConverterInstant::class, ConvertTypeLetter::class, LetterStatusConverter::class)
data class Letters(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = LettersContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = LettersContract.Columns.TO_USER_EMAIL)
    val to_user_email: String,
    @ColumnInfo(name = LettersContract.Columns.FROM_USER_EMAIL)
    val from_user_email: String,
    @ColumnInfo(name = LettersContract.Columns.ATTACHMENTS_TITLE)
    val attachments_title: String? = null,
    @ColumnInfo(name = LettersContract.Columns.TITLE)
    val title: String,
    @ColumnInfo(name = LettersContract.Columns.TIME)
    val time: Instant,
    @ColumnInfo(name = LettersContract.Columns.TYPE_LETTER)
    val type_letter: TypeLetter,
    @ColumnInfo(name = LettersContract.Columns.CHAIN_TITLE)
    val chain_title: String,
    @ColumnInfo(name = LettersContract.Columns.STATUS_TEXT)
    val status_text: String,
    @ColumnInfo(name = LettersContract.Columns.TEXT_LETTER)
    val text_letter: String
)
