package mbraun.unsplasy.repository

import mbraun.unsplasy.model.File
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface FileRepository: JpaRepository<File, UUID> {
}