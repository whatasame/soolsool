package shop.soolsool.acceptance;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.jdbc.Sql;
import shop.soolsool.core.config.RedisTestConfig;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql({"/liquor-type.sql", "/member-type.sql", "/order-type.sql", "/receipt-type.sql"})
@Import(RedisTestConfig.class)
public abstract class AcceptanceTest {

    public static final String BEARER = "Bearer ";

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = this.port;
    }
}
