package com.innso.request.model;

import com.innso.request.interfaces.BaseRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class MultipartRequest implements BaseRequest{

    @Override
    public Object getBody() {
        return null;
    }

    @Override
    public Map<String, String> getUrlParams() {
        return null;
    }

    @Override
    public String getMediaType() {
        return null;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public HttpMethod getMethod() {
        return null;
    }

    @Override
    public Map<String, String> getHeaders() {
        return null;
    }

    @Override
    public Map<String,String> getFormParams() {
        return null;
    }

    public ArrayList<MultipartParam> getMultipartParams() {
        return null;
    }

    public class MultipartParam{

        private String key;
        private String value;
        private String mediaType;
        private File file;

        public MultipartParam(String key, String value, String mediaType, File file) {
            this.key = key;
            this.value = value;
            this.mediaType = mediaType;
            this.file = file;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public String getMediaType() {
            return mediaType;
        }

        public File getFile() {
            return file;
        }
    }

}
