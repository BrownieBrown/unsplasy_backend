package mbraun.unsplasy.repository

import mbraun.unsplasy.model.File
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*
import javax.transaction.Transactional

@Repository
interface FileRepository: JpaRepository<File, UUID> {

    @Query(value = "select * from file", nativeQuery = true)
    fun findFiles(): List<File>

    @Query(value = "select data from file where id= :id", nativeQuery = true)
    fun findData(@Param("id")id: UUID) :ByteArray

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update file set likes = likes + 1 where id = :id", nativeQuery = true)
    fun increaseLikeByOne(@Param("id")id: UUID): Int
}