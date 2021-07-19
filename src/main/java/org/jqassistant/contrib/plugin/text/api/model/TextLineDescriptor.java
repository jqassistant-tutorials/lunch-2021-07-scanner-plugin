package org.jqassistant.contrib.plugin.text.api.model;

import com.buschmais.xo.neo4j.api.annotation.Label;

@Label("Line")
public interface TextLineDescriptor extends TextDescriptor {

    String getValue();
    void setValue(String value);

    int getLineNumber();
    void setLineNumber(int lineNumber);

}
