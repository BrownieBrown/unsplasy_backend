package mbraun.unsplasy.controller

import mbraun.unsplasy.message.ResponseMessage
import mbraun.unsplasy.model.File
import mbraun.unsplasy.service.FileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.net.URL
import java.util.*
import javax.imageio.ImageIO
import javax.servlet.http.HttpServletRequest

@Controller
@CrossOrigin("http://localhost:3000")
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

//    @GetMapping("/files")
//    fun getListFiles(): ResponseEntity<List<ResponseFile>> {
//        val files: List<ResponseFile> = fileService.getAllFiles().map { dbFile ->
//            val fileDownloadUri = ServletUriComponentsBuilder
//                .fromCurrentContextPath()
//                .path("/files/")
//                .path(dbFile.id.toString())
//                .toUriString()
//
//            ResponseFile(
//                dbFile.name,
//                fileDownloadUri,
//                dbFile.type,
//                dbFile.data.size.toLong()
//            )
//        }.toList()
//
//        return ResponseEntity.status(HttpStatus.OK).body(files)
//    }

    @GetMapping("/files")
    fun getListFiles(): ResponseEntity<List<File>> {
        val files: List<File> = fileService.findFiles()

        return ResponseEntity.status(HttpStatus.OK).body(files)
    }

    @GetMapping("/files/download/{id}")
    fun downloadFile(@PathVariable id: UUID, request: HttpServletRequest): ResponseEntity<ByteArrayResource> {
        val file = fileService.getFile(id)

        return ResponseEntity
            .ok()
            .contentType(MediaType.parseMediaType(file.type))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${file.name} ")
            .body(ByteArrayResource(file.getData()))
    }

    @GetMapping("/files/{id}")
    fun displayFile(@PathVariable id: UUID): ResponseEntity<File> {
        val file = fileService.getFile(id)
        return ResponseEntity.status(HttpStatus.OK).body(file)
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