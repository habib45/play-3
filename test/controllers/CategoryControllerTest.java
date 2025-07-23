package controllers;

import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.*;

public class CategoryControllerTest extends WithApplication {

    @Test
    public void testIndex() {
        Result result = route(app, controllers.routes.CategoryController.index());
        assertEquals(OK, result.status());
    }

    @Test
    public void testCreateForm() {
        Result result = route(app, controllers.routes.CategoryController.createForm());
        assertEquals(OK, result.status());
    }

    @Test
    public void testSaveCategory() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .uri(controllers.routes.CategoryController.save().url())
                .bodyForm(ImmutableMap.of(
                        "name", "Test Category",
                        "code", "TST",
                        "description", "Sample description"
                ));
        Result result = route(app, request);
        assertEquals(SEE_OTHER, result.status()); // redirect expected
    }
}
