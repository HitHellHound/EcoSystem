package ecxeptions;

public class FileServiceException extends  Exception{
    public FileServiceException() {
    }

    public FileServiceException(String message) {
        super(message);
    }
}
