package co.k2web.springboot;

import co.k2web.springboot.service.RequestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringbootProjectApplicationTests {

    @Autowired
    private RequestService requestService;

    @Test
    void storeRequest_withAValueRequest_storeSuccessfully() {
        String value = "REQUEST";
		Map<String, String> requestMap = requestService.storeRequest(value);
		assertThat(requestMap.size()).isEqualTo(1);
	}

}
