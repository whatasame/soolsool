package shop.soolsool.receipt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shop.soolsool.receipt.domain.model.converter.ReceiptStatusTypeConverter;

@Configuration
public class ReceiptConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addConverter(new ReceiptStatusTypeConverter());
    }
}
