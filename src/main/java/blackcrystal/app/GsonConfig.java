package blackcrystal.app;

import blackcrystal.utility.OffsetDateTimeTypeConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.time.OffsetDateTime;


@Configuration
public class GsonConfig {

    @Bean
    public GsonHttpMessageConverter gsonHttpMessageConverter() {
        GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
        converter.setGson(gsonBuilder());
        return converter;
    }

    /**
     * Default behaviour is to convert OffsetDateTime to following gson output;
     *
     * {"startTime":{"dateTime":{"date":{"year":2015,"month":12,"day":30},"time":{"hour":9,"minute":54,"second":0,"nano":13000000}},"offset":{"totalSeconds":3600}}}
     *
     * Following converter prints as:
     * {"startTime": "2015-12-30T09:54:00.013+01:00"}
     *
     * @return Gson
     */
    public static Gson gsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeTypeConverter());
        return builder.create();
    }

}
