package cn.edu.njue.blackStone.backend.utils.exception;

/**
 * Created by Edward on 2016/10/17.
 */
public class RestClientException extends  Exception{
    public RestClientException(String message) {
        super(message);
    }

    public RestClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestClientException(Throwable cause) {
        super(cause);
    }
}
