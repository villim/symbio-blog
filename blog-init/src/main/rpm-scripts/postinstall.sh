#!/bin/bash

# blog-persistence.properties can linked to blog-persistence-sqlite.[env].properties in deployment script

if [ ! -f "/opt/symbio/config/blog/blog-persistence.properties" ]
then 
    ln -sf /opt/symbio/config/blog/blog-persistence-sqlite.localhost.properties /opt/symbio/config/blog/blog-persistence-sqlite.properties;
    chown deploy:deploy /opt/symbio/config/blog/blog-persistence-sqlite.properties;
    chmod 644 /opt/symbio/config/blog/blog-persistence-sqlite.properties;
fi

