package vn.hcmute.app.model;

import java.io.Serializable;
import java.util.List;

public class MessageVideoModel implements Serializable {
    private boolean success;
    private String message;
    private List<VideoModel> result;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<VideoModel> getResult() {
        return result;
    }
}
