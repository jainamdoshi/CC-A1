package cloud.cloud.dao;

import java.io.File;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;

public class S3 {
    
    public static AmazonS3 client;
    private String name;

    public S3(String name) {
        this.name = name;
        this.init();
    }

    private void init() {
        if (S3.client == null) {
            System.out.println("Init S3 Client");
            S3.client = AmazonS3ClientBuilder.standard().build();
        }
    }

    public void addObject(String path) {

        TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(S3.client).build();
        // Upload the folder to S3
        MultipleFileUpload upload = transferManager.uploadDirectory(this.name, null, new File(path), false);
        try {
            upload.waitForCompletion();
            System.out.println(String.format("Uploading completed for %s object to S3 bucket %s", path, this.name));
        } catch (Exception e) {
            System.out.println(String.format("Error uploading %s object to S3 bucket %s", path, this.name));
            e.printStackTrace();
        }

    }
}
