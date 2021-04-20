package mbraun.unsplasy.model

import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Lob
import javax.persistence.Table
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table
data class File(
    @Id
    val id: UUID = UUID.randomUUID(),
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now(),
    var likes: Int = 0,
    var likedByUser: Boolean = false,
    var description: String = "",
    val selfUrl: String = "http://localhost:8080/files/$id",
    @NotBlank(message = "Name is mandatory")
    var name: String = "",
    var type: String = "",
    @NotNull
    @Lob
    var data: ByteArray = byteArrayOf()
) {
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
        if (selfUrl != other.selfUrl) return false
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
        result = 31 * result + selfUrl.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }

    override fun toString(): String {
        return "File(id=$id, createdAt=$createdAt, updatedAt=$updatedAt, likes=$likes, likedByUser=$likedByUser, description='$description', selfUrl='$selfUrl', name='$name', type='$type', data=${data.contentToString()})"
    }

}
