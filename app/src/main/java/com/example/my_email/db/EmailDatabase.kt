package com.example.my_email.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.my_email.db.attachments.Attachments
import com.example.my_email.db.attachments.AttachmentsDao
import com.example.my_email.db.folders.Folders
import com.example.my_email.db.folders.FoldersDao
import com.example.my_email.db.letterStatuses.LetterStatusConverter
import com.example.my_email.db.letterStatuses.LetterStatuses
import com.example.my_email.db.letterStatuses.LettersStatusesDao
import com.example.my_email.db.letters.ConvertTypeLetter
import com.example.my_email.db.letters.ConverterInstant
import com.example.my_email.db.letters.Letters
import com.example.my_email.db.letters.LettersDao
import com.example.my_email.db.lettersChains.LettersChains
import com.example.my_email.db.lettersChains.LettersChainsDao
import com.example.my_email.db.signatures.Signatures
import com.example.my_email.db.signatures.SignaturesDao
import com.example.my_email.db.users.Users
import com.example.my_email.db.users.UsersDao

@Database(
    entities = [
        Users::class, Signatures::class, Attachments::class, Folders::class,
        Letters::class, LettersChains::class, LetterStatuses::class
    ],
    version = EmailDatabase.DB_VERSION
)
@TypeConverters(LetterStatusConverter::class, ConvertTypeLetter::class, ConverterInstant::class)
abstract class EmailDatabase : RoomDatabase() {

    abstract fun usersDao(): UsersDao
    abstract fun attachmentsDao(): AttachmentsDao
    abstract fun foldersDao(): FoldersDao
    abstract fun lettersDao(): LettersDao
    abstract fun lettersChainsDao(): LettersChainsDao
    abstract fun lettersStatusesDao(): LettersStatusesDao
    abstract fun signaturesDao(): SignaturesDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "my_email"
    }
}