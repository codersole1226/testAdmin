package com.codersole.knowledgeserver.service;

public interface CodeService {
    String generate(String username);
    boolean verify(String username, String code);
}
