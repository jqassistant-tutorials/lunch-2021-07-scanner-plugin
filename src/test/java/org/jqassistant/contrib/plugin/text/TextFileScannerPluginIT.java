package org.jqassistant.contrib.plugin.text;

import com.buschmais.jqassistant.core.scanner.api.DefaultScope;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.jqassistant.plugin.common.test.AbstractPluginIT;
import org.jqassistant.contrib.plugin.text.api.model.TextFileDescriptor;
import org.jqassistant.contrib.plugin.text.api.model.TextLineDescriptor;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static org.assertj.core.api.Assertions.assertThat;


public class TextFileScannerPluginIT extends AbstractPluginIT {

    public static final String TEST_FILE = "/test.txt";

    @Test
    // @TestStore(type = TestStore.Type.REMOTE)
    public void scan() {
        File classesDirectory = getClassesDirectory(TextFileScannerPluginIT.class);
        File file = new File(classesDirectory, TEST_FILE);
        Descriptor descriptor = getScanner().scan(file, TEST_FILE, DefaultScope.NONE);
        assertThat(descriptor).isInstanceOf(TextFileDescriptor.class);

        store.beginTransaction();

        TextFileDescriptor textFileDescriptor = (TextFileDescriptor) descriptor;
        Map<Integer, TextLineDescriptor> lines = textFileDescriptor.getLines().stream().collect(toMap(line -> line.getLineNumber(), line -> line));
        assertThat(lines).isNotEmpty();
        assertThat(lines.get(2604).getValue()).contains("To be or not to be--that is the question");

        store.commitTransaction();
    }
}
