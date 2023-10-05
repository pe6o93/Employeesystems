package com.example.infosystems.service;

import com.example.infosystems.model.entity.FileEntity;
import com.example.infosystems.model.enums.FileStatus;
import com.example.infosystems.model.enums.FileType;
import com.example.infosystems.repository.FileRepository;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.entity.ContentType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FileService {

    private final MinioClient minioClient;
    private final FileRepository fileRepository;
    private String documentBucketName;

    private String mimeType;

    public FileService(final MinioClient minioClient, final FileRepository fileRepository) {
        this.minioClient = minioClient;
        this.fileRepository = fileRepository;
    }

    @PostConstruct
    private void init() {
        // we store all documents in this bucket
        documentBucketName = "documents";
        mimeType = ContentType.MULTIPART_FORM_DATA.getMimeType();
    }


    public String uploadFiles(final MultipartFile[] contracts, final Integer recordId,
                              final String fileType) {


        final List<String> fileNames = new ArrayList<>();

        if (ArrayUtils.isNotEmpty(contracts)) {

            Arrays.stream(contracts).forEach(file -> {
                final String fileName = file.getOriginalFilename();
                try {
                    final InputStream inputStream = new BufferedInputStream(file.getInputStream());
                    final PutObjectArgs args = PutObjectArgs.builder()
                            .bucket(documentBucketName)
                            .contentType(mimeType)
                            .object(fileName)
                            .stream(inputStream, inputStream.available(), -1)
                            .build();

                    this.minioClient.putObject(args);
                    createFileEntity(fileName, recordId, fileType);
                    fileNames.add(fileName);
                    inputStream.close();
                } catch (IOException | ErrorResponseException | InsufficientDataException | InternalException |
                         InvalidKeyException | InvalidResponseException | NoSuchAlgorithmException
                         | ServerException | XmlParserException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return String.join(", ", fileNames);
    }

    private void createFileEntity(final String fileName, final Integer recordId,
                                  final String recordType) {
        FileEntity file = new FileEntity();
        file.setName(fileName);
        file.setRecordId(recordId);
        file.setFileType(FileType.getFileTypeByName(recordType));
        file.setBucket(documentBucketName);
        file.setStatus(FileStatus.UPLOADED);
        this.fileRepository.save(file);

    }

    public void deleteFile(final String recordType, final Integer recordId, final String fileName) {
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                .bucket(documentBucketName)
                .object(fileName)
                .build();
        try {
            this.minioClient.removeObject(removeObjectArgs);
        } catch (ErrorResponseException | InsufficientDataException | NoSuchAlgorithmException | ServerException |
                 XmlParserException | InternalException | InvalidKeyException | InvalidResponseException |
                 IOException e) {
            throw new RuntimeException(e);
        }
        final FileType fileType = FileType.getFileTypeByName(recordType);
        FileEntity fileEntity = this.fileRepository.findByFileTypeAndRecordIdAndName(fileType, recordId, fileName)
                .orElseThrow();
        fileEntity.setStatus(FileStatus.DELETED);
        this.fileRepository.save(fileEntity);
    }
}
