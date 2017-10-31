package com.yyoo.upload.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by yoonyoulyoo on 11/17/16.
 */
@ConfigurationProperties("storage")
public class StorageProperties {

    private String location = "upload-dir";
    public String getLocation(){
        return location;
    }
    public void setLocation(String location){
        this.location = location;
    }
}
