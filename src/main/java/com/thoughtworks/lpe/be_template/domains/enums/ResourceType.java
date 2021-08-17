package com.thoughtworks.lpe.be_template.domains.enums;

public enum ResourceType {
    VID("VIDEO"), DOW("DOWNLOADABLE"), LIN("EXTRA_LINKS"), PRO("PROOF_OF_KNOWLEDGE"), LOA("LOADABLE");

    private final String description;

    ResourceType(String description) {
        this.description = description;
    }

}
