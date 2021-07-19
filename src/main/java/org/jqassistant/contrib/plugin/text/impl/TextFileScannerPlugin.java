package org.jqassistant.contrib.plugin.text.impl;

import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import com.buschmais.jqassistant.core.scanner.api.ScannerPlugin.Requires;
import com.buschmais.jqassistant.core.scanner.api.Scope;
import com.buschmais.jqassistant.plugin.common.api.model.FileDescriptor;
import com.buschmais.jqassistant.plugin.common.api.scanner.AbstractScannerPlugin;
import com.buschmais.jqassistant.plugin.common.api.scanner.filesystem.FileResource;
import org.jqassistant.contrib.plugin.text.api.model.TextFileDescriptor;
import org.jqassistant.contrib.plugin.text.api.model.TextLineDescriptor;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

@Requires(FileDescriptor.class)
public class TextFileScannerPlugin extends AbstractScannerPlugin<FileResource, TextFileDescriptor> {

    @Override
    public boolean accepts(FileResource fileResource, String fileName, Scope scope) throws IOException {
        return fileName.endsWith(".txt");
    }

    @Override
    public TextFileDescriptor scan(FileResource fileResource, String s, Scope scope, Scanner scanner) throws IOException {
        ScannerContext context = scanner.getContext();
        FileDescriptor fileDescriptor = context.getCurrentDescriptor();
        TextFileDescriptor textFileDescriptor = context.getStore().addDescriptorType(fileDescriptor, TextFileDescriptor.class);

        try (LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(fileResource.createStream()))) {
            String value;
            while ((value = lineNumberReader.readLine()) != null) {
                TextLineDescriptor textLineDescriptor = context.getStore().create(TextLineDescriptor.class);
                textLineDescriptor.setValue(value);
                textLineDescriptor.setLineNumber(lineNumberReader.getLineNumber());
                textFileDescriptor.getLines().add(textLineDescriptor);
            }
        }

        return textFileDescriptor;
    }
}
