package teamproject.gunha.config;

import java.nio.charset.StandardCharsets;

import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;

// @AutoconfigureMockMvc를 사용하여 MockMvc를 주입하는 경우에 한해서만 적용
// @Component
@TestComponent
class CharSetConfig implements MockMvcBuilderCustomizer {
    @Override
    public void customize(ConfigurableMockMvcBuilder<?> builder) {
        builder.alwaysDo(result -> result.getResponse().setCharacterEncoding(StandardCharsets.UTF_8.name()));
    }
}