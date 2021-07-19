package org.jqassistant.contrib.plugin.text.api.model;

import com.buschmais.jqassistant.plugin.common.api.model.FileDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Relation;

import java.util.List;

public interface TextFileDescriptor extends TextDescriptor, FileDescriptor {

    @Relation("CONTAINS_LINE")
    List<TextLineDescriptor> getLines();

}
