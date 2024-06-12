# CustomUtility Readme

## Introduction

CustomUtility is a powerful utility library designed to enhance your project by providing additional functionalities. This document will guide you through the process of integrating and using CustomUtility in your project.

## Installation

### 1. Download the Jar

Place the `CustomUtility-0.0.1-SNAPSHOT.jar` file in the root folder of your project where you intend to use it.

### 2. Run Maven Install

Open a terminal in the project directory and execute the following Maven command to install the CustomUtility Jar into your local Maven repository:

```bash
mvn install:install-file -Dfile=CustomUtility-0.0.1-SNAPSHOT.jar -DgroupId=com.zanbeel -DartifactId=CustomUtility -Dpackaging=jar
