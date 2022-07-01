package com.trybe.dronefeeder.services;

import static com.trybe.dronefeeder.utils.Messages.EXCEPTION_ENTITY_NOT_FOUND;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.trybe.dronefeeder.models.Delivery;
import com.trybe.dronefeeder.models.Video;
import com.trybe.dronefeeder.repositories.VideoRepository;
import com.trybe.dronefeeder.services.exceptions.ResourceNotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/** Class video service. */
@Service
public class VideoService {

  @Autowired
  private VideoRepository videoRepository;
  
  @Autowired
  private AmazonS3 clientS3;
  private String bucketName = "drone-feeder-trybe";
  
  @Transactional(readOnly = true)
  public List<Video> findAll() {
    List<Video> videos = videoRepository.findAll(); 
    return videos;
  }
  
  /** find by id method. */
  @Transactional(readOnly = true)
  public Video findById(Long id) {
    Optional<Video> video = videoRepository.findById(id);
    Video entity = video
        .orElseThrow(() -> new ResourceNotFoundException(EXCEPTION_ENTITY_NOT_FOUND));
    return entity;
  }
  
  /** upload method. */
  @Transactional
  public Video upload(Delivery delivery, MultipartFile file) throws
      AmazonServiceException, SdkClientException, IOException {
    File fileObj = convertMultipartFileToFile(file);
    String fileName = DateTime.now() + "-delivery-" + delivery.getId();
    
    clientS3.putObject(bucketName, fileName, fileObj);
    fileObj.delete();
    
    Video video = new Video();
    video.setName(fileName);
    video.setDelivery(delivery);
    
    videoRepository.save(video);
    
    return video;
  }
  
  /** download method. */
  @Transactional(readOnly = true)
  public byte[] download(Long id) throws IOException {
    Video video = findById(id);
    String fileName = video.getName();
    
    S3Object s3Object = clientS3.getObject(bucketName, fileName);
    S3ObjectInputStream inputStream = s3Object.getObjectContent();

    try {
      byte[] content = IOUtils.toByteArray(inputStream);
      return content;
    } catch (IOException e) {
      throw new IOException();
    }
  }
  
  private File convertMultipartFileToFile(MultipartFile file) throws IOException {
    File convertedFile = new File(file.getOriginalFilename());

    try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
      fos.write(file.getBytes());
    } catch (IOException e) {
      throw new IOException();
    }
  
    return convertedFile;
  }
  
}
