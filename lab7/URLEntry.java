package ru.izebit.seventh;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.URL;

@AllArgsConstructor
@Data
public class URLEntry {
    private final int depth;
    private final URL url;
}
