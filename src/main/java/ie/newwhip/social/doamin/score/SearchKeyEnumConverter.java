package ie.newwhip.social.doamin.score;

import org.springframework.core.convert.converter.Converter;

//@Component
public class SearchKeyEnumConverter implements Converter<String, KeyType> {
    @Override
    public KeyType convert(String value) {
        return KeyType.valueOf(value);
    }
}
