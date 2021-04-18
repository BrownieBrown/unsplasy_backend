package mbraun.unsplasy.model

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
    private val id: UUID = UUID.randomUUID(),
    @NotBlank(message = "Name is mandatory")
    private var name: String = "",
    @NotBlank(message = "Type is mandatory")
    private var type: String = "",
    @NotNull
    @Lob
    private var data: ByteArray = byteArrayOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as File

        if (id != other.id) return false
        if (name != other.name) return false
        if (type != other.type) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }

    override fun toString(): String {
        return "File(id=$id, name='$name', type='$type', data=${data.contentToString()})"
    }
}
