package com.saba.modelflow;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ModelflowApplicationTests {

    @Test
    void healthEndpointReturnsExpectedMessage() {
        PredictionController controller = new PredictionController();

        assertEquals("ModelFlow is running", controller.health());
    }
}
