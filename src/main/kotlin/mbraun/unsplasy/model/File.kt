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
    val id: UUID = UUID.randomUUID(),
    @NotBlank(message = "Name is mandatory")
    var name: String = "",
    @NotBlank(message = "Type is mandatory")
    var type: String = "",
    @NotNull
    @Lob
    var data: ByteArray = byteArrayOf()
) {
    constructor(name: String, type: String, data: ByteArray) : this() {
        this.name = name
        this.type = type
        this.data = data
    }

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
