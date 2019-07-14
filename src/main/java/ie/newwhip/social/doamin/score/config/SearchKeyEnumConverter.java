package ie.newwhip.social.doamin.score.config;

import ie.newwhip.social.doamin.score.model.KeyType;
import org.springframework.core.convert.converter.Converter;

//@Component
public class SearchKeyEnumConverter implements Converter<String, KeyType> {
    @Override
    public KeyType convert(String value) {
        return KeyType.valueOf(value);
    }
}
