# microservice
My personal, and simple microservice (IMHO) framework for Java applications

## concept

Create a simple, convention over configuration framework that provides
JAX-RS applications with a easily repeatable process.  The goal with writing
web applications should be writing them, not making decisions about which
framework is best.

## method

Utilizes [undertow](http://undertow.io), [resteasy](http://resteasy.jboss.org),
and a modern, flexible and easy to use logging framework [log4j2](http://logging.apache.org/log4j/2.x/).

This mostly provides just a skeleton set of dependencies with some autowiring
of JAX-RS Application classes onto the listener.