package cloud.cloud.dao;

import java.io.File;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
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

        File folder = new File(path);
        File[] files = folder.listFiles();
        System.out.println(files.length);

        for (int i = 0; i < files.length; i++) {
            PutObjectRequest request = new PutObjectRequest(this.name, files[i].getName(), files[i]).withCannedAcl(CannedAccessControlList.PublicRead);
            S3.client.putObject(request);
        }

        System.out.println(String.format("Uploading completed for %s object to S3 bucket %s", path, this.name));

    }
}
