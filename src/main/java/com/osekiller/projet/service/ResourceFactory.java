package com.osekiller.projet.service;

import org.springframework.core.io.Resource;

import java.net.MalformedURLException;
import java.net.URI;

public interface ResourceFactory {
    Resource createResource(URI uri) throws MalformedURLException;
}
