package io.vepo.microblogging.validators;

import org.assertj.core.api.Condition;

import static org.assertj.core.condition.VerboseCondition.verboseCondition;
import io.restassured.response.Response;
import io.vepo.microblogging.post.Page;
import io.vepo.microblogging.post.Post;

public class Validators {

    public static Condition<? super Response> statusCode(int statusCode) {
        return verboseCondition(response -> response.statusCode() == statusCode, 
                                String.format("Status code should be %d", statusCode), 
                                response -> String.format(", but was %d", response.statusCode()));
    }

    public static Condition<? super Page<Post>> offset(int offset) {
        return verboseCondition(page -> page.offset() == offset, 
                                String.format("Page should have offset %d", offset),
                                page -> String.format(", but has offset=%s", page.offset()));
    }

    public static Condition<? super Page<Post>> lenght(int lenght) {
        return verboseCondition(page -> page.items().size() == lenght, 
                                String.format("Items should have lenght %d", lenght),
                                page -> String.format(", but has %d", page.items().size()));
    }

}
