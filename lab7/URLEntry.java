package ru.izebit.seventh;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.URL;

/**
 * @author <a href="mailto:izebit@gmail.com">Artem Konovalov</a> <br/>
 * Date: 24.10.2019
 */
@AllArgsConstructor
@Data
public class URLEntry {
    private final int depth;
    private final URL url;
}
