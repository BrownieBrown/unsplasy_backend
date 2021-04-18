package mbraun.unsplasy.service

import mbraun.unsplasy.model.File
import mbraun.unsplasy.repository.FileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.FileNotFoundException
import java.util.*

@Service
class FileService(@Autowired val fileRepository: FileRepository) {

    fun store(file: MultipartFile): File {
        val fileName = file.originalFilename.toString()
        val fileToSave = File(fileName, file.contentType.toString(), file.bytes)
        return fileRepository.save(fileToSave)
    }

    fun getFile(id: UUID): File {
        return fileRepository.findById(id).get()
    }

    fun getAllFiles(): Sequence<File> {
        return fileRepository.findAll().asSequence()
    }

    fun deleteById(id: UUID) {
        if(!fileRepository.existsById(id)) {
            throw FileNotFoundException("The File with id $id does not exist")
        }
        fileRepository.deleteById(id)
    }
}