/*
 * author: Vitalik Scherbatyuk
 * version: 1
 * development of an online store for a portfolio
 * 20.11.2023
 */
package scherbatyuk.shoping.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import scherbatyuk.shoping.component.StringToCategoriaListConverter;

import java.util.Locale;

/**
 * a configuration class for setting up the web layer in the Spring Framework
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("start");
        registry.addViewController("/registration").setViewName("registration");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/403").setViewName("403");
    }

    /**
     * method sets the type converter by adding StringToCategoriaListConverter to the
     * formatter registry (FormatterRegistry). This approach allows Spring to automatically use this converter
     * when it is necessary to convert strings into lists of Categoria objects and vice versa.
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToCategoriaListConverter());
    }

    /**
     * method configures and returns a ReloadableResourceBundleMessageSource object that
     * is used to localize messages.
     * @return
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages"); // змінено на "messages"
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /**
     * method configures and returns a SessionLocaleResolver object that is used to
     * determine the current locale based on the session. The default locale is set to Locale.US.
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    /**
     * method configures and returns a LocaleChangeInterceptor object that specifies the locale
     * change request parameter. In this case, the query parameter is set to "lang".
     * @return
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    /**
     * method adds the localization interceptor, which was configured using the
     * localeChangeInterceptor() method, to the interceptor registry (InterceptorRegistry).
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
