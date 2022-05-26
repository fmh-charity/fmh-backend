package cucumber;

import io.cucumber.java.en.Then;
import lombok.Getter;
import lombok.Setter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Getter
@Setter
public class ResultSteps {

    private String status = "SUCCESS";

    @Then("Результат {string}")
    public void result(String result) {
        assertEquals(status, result);
    }

}
