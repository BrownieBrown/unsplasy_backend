package mbraun.unsplasy.model

import java.net.URI
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "file")
data class File(
    @Id
    val id: UUID = UUID.randomUUID(),
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now(),
    var likes: Int = 0,
    var likedByUser: Boolean = false,
    var description: String = "",
    val downloadURI: URI = URI("https://unsplasy-backend.herokuapp.com/files/download/$id"),
    var name: String = "",
    var type: String = "",
    private val data: ByteArray = byteArrayOf()
) {

    fun getData(): ByteArray {
        return this.data
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as File

        if (id != other.id) return false
        if (createdAt != other.createdAt) return false
        if (updatedAt != other.updatedAt) return false
        if (likes != other.likes) return false
        if (likedByUser != other.likedByUser) return false
        if (description != other.description) return false
        if (downloadURI != other.downloadURI) return false
        if (name != other.name) return false
        if (type != other.type) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + updatedAt.hashCode()
        result = 31 * result + likes
        result = 31 * result + likedByUser.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + downloadURI.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }

    override fun toString(): String {
        return "File(id=$id, createdAt=$createdAt, updatedAt=$updatedAt, likes=$likes, likedByUser=$likedByUser, description='$description', downloadURI=$downloadURI, name='$name', type='$type', data=${data.contentToString()})"
    }
}
