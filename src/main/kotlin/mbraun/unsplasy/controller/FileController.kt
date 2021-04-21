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
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

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

    @GetMapping("/files")
    fun getListFiles(): ResponseEntity<Sequence<File>> {
        val files: Sequence<File> = fileService.findFiles()

        return ResponseEntity.status(HttpStatus.OK).body(files)
    }

    @GetMapping("/files/{id}")
    fun getFile(@PathVariable id: UUID): ResponseEntity<File> {
        val file = fileService.getFile(id)

        return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${file.name} ")
            .body(file)
    }

    @GetMapping("/files/download/{id}")
    fun downloadFile(@PathVariable id: UUID, request: HttpServletRequest): ResponseEntity<ByteArrayResource> {
        val file = fileService.getFile(id)
        val data = fileService.getData(file)

        return ResponseEntity
            .ok()
            .contentType(MediaType.parseMediaType(file.type))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${file.name} ")
            .body(ByteArrayResource(data))
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

    @GetMapping("/files/image/{id}")
    fun displayImage(@PathVariable id: UUID, response: HttpServletResponse) {
        val file = fileService.getFile(id)
        val data = fileService.getData(file)
        response.contentType = file.type
        response.outputStream.write(data)
        response.outputStream.close()
    }
}