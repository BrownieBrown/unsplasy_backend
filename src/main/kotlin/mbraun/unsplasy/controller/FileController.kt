package mbraun.unsplasy.controller

import mbraun.unsplasy.message.ResponseFile
import mbraun.unsplasy.message.ResponseMessage
import mbraun.unsplasy.service.FileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*

@Controller
class FileController(@Autowired val fileService: FileService) {

    @PostMapping("/upload")
    fun uploadFile(@RequestParam("file") file: MultipartFile): ResponseEntity<ResponseMessage> {
        return try {
            fileService.store(file)
            ResponseEntity.status(HttpStatus.OK).body(
                ResponseMessage(
                "Uploaded the file ${file.originalFilename} successfully"
                )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                ResponseMessage(
                    "Could not upload the file: ${file.originalFilename}!"
                ))
        }
    }

    @GetMapping("/files")
    fun getListFiles(): ResponseEntity<List<ResponseFile>> {
        val files: List<ResponseFile> = fileService.getAllFiles().map { dbFile ->
            val fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/files/")
                .path(dbFile.id.toString())
                .toUriString()

            ResponseFile(
                dbFile.name,
                fileDownloadUri,
                dbFile.type,
                dbFile.data.size.toLong()
            )
        }.toList()

        return ResponseEntity.status(HttpStatus.OK).body(files)
    }

    @GetMapping("/files/{id}")
    fun getFile(@PathVariable id: UUID): ResponseEntity<ByteArray> {
        val file = fileService.getFile(id)

        return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${file.name} ")
            .body(file.data)
    }

    @DeleteMapping("/files/delete/{id}")
    fun deleteFile(@PathVariable id: UUID): ResponseEntity<ResponseMessage> {
        fileService.deleteById(id)
        return try {
             ResponseEntity.status(HttpStatus.OK).body(
                ResponseMessage(
                    "Deleted the file with id: $id successfully"
                ))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                ResponseMessage(
                    "Could not delete the file with id: $id!"
                ))
        }
    }
}