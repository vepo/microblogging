package io.vepo.microblogging.infra;

public class TestSetupException extends RuntimeException {

    public TestSetupException(String message, Exception exception) {
        super(message, exception);
    }
}
