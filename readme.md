# 🚀 Azure DevOps Production-Grade CI/CD Pipeline for Spring Boot

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://dev.azure.com)
[![Quality Gate](https://img.shields.io/badge/quality%20gate-passed-brightgreen)](https://sonarqube.com)
[![Azure DevOps](https://img.shields.io/badge/Azure%20DevOps-Pipeline-blue)](https://azure.microsoft.com/en-us/services/devops/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7+-green)](https://spring.io/projects/spring-boot)

This repository contains a **production-ready Spring Boot application** with a comprehensive **Azure DevOps multi-stage YAML pipeline** designed for enterprise-grade Continuous Integration (CI) and Continuous Delivery (CD). The pipeline automates the complete software delivery lifecycle across multiple Azure environments: **Development (Dev)**, **Quality Assurance (QA)**, and **Production (Prod)**.

---

## 📋 Table of Contents

- [🎯 Core Principles & Approach](#-core-principles--approach)
- [🏗️ Pipeline Stages Overview](#️-pipeline-stages-overview)
- [🛠️ Prerequisites](#️-prerequisites)
- [🏁 Getting Started](#-getting-started)
- [▶️ Running the Pipeline](#️-running-the-pipeline)
- [📊 Pipeline Flow](#-pipeline-flow)
- [🔧 Configuration Details](#-configuration-details)

---

## 🎯 Core Principles & Approach

This pipeline is built upon key DevOps principles to ensure **reliability**, **consistency**, and **traceability**:

### 🔄 Build Once, Deploy Many (BODM)
- **Single Artifact Creation**: The application artifact (`.jar` file) is built only once in the initial Build stage
- **Consistent Deployment**: This exact same artifact is promoted and deployed sequentially to Dev, QA, and Production environments
- **Eliminates Environment Drift**: Guarantees that what is tested in lower environments is precisely what runs in production
- **Reduces Risk**: Eliminates "works on my machine/QA but not in Prod" issues

### ✅ Automated Quality Gates
- **Comprehensive Testing**: Each deployment stage includes automated checks and validations
- **Multi-layered Validation**: Unit tests, integration tests, and static code analysis must pass
- **Fail-Fast Approach**: Pipeline automatically stops if quality standards aren't met
- **Production Protection**: Prevents low-quality code from reaching production

### 🛡️ Environments and Approvals
- **Governance Controls**: Azure DevOps Environments define deployment targets
- **Manual Approvals**: Strict manual approvals for critical stages (QA and Production)
- **Automated Checks**: Additional automated validations and compliance checks
- **Role-Based Access**: Different permission levels for different environments

### 🏗️ Infrastructure as Code (IaC) Ready
- **Seamless Integration**: Designed to work with IaC practices (Terraform, ARM templates)
- **Consistent Provisioning**: Underlying Azure App Services provisioned consistently across environments
- **Version Control**: Infrastructure changes tracked and versioned
- **Scalable Architecture**: Easy to extend and modify for different environments

---

## 🏗️ Pipeline Stages Overview

The `azure-pipelines.yml` defines the following stages, executed in sequence:

### 1️⃣ **Build Stage** (`Build`)

> **Purpose**: Continuous Integration (CI) phase - compiles, tests, and packages the application

**Key Activities:**
- ✅ Compiles the Spring Boot application
- ✅ Runs unit tests with comprehensive coverage
- ✅ Performs code coverage analysis (JaCoCo)
- ✅ Packages application into executable JAR file

**Outcome:** 
- 📦 Versioned `demo-0.0.1-SNAPSHOT.jar` artifact created
- 📤 Published as `WebApp` within Azure Pipelines

**Key Tasks:**
- `Maven@4`: Executes `clean install` goals, runs unit tests, collects JaCoCo coverage
- `PublishBuildArtifacts@1`: Publishes the generated `.jar` file as an artifact

---

### 2️⃣ **Deploy to Development Stage** (`DeployToDev`)

> **Purpose**: Initial deployment for rapid iteration and integration testing

**Characteristics:**
- 🚀 **Trigger**: Runs automatically upon successful Build completion
- ⚙️ **Control**: Can be optionally skipped using pipeline runtime parameter (`deployDev`)
- 🔄 **Environment**: Less restrictive, allows for rapid iteration

**Key Tasks:**
- `DownloadPipelineArtifact@2`: Downloads the WebApp artifact from Build stage
- `AzureRmWebAppDeployment@5`: Deploys `.jar` to specified Azure App Service (Windows)
- **Environment**: Linked to Azure DevOps `Dev` environment

---

### 3️⃣ **Deploy to QA Stage** (`DeployToQA`)

> **Purpose**: Comprehensive testing and quality assurance with automated validations

**Quality Gates:**
- 🧪 **Automated Testing**: Integration and end-to-end test execution
- 📊 **Static Code Analysis**: SonarQube analysis with quality gate enforcement
- ❌ **Failure Handling**: Any test failures prevent Production deployment

**Key Tasks:**
- `DownloadPipelineArtifact@2`: Downloads the WebApp artifact
- `AzureRmWebAppDeployment@5`: Deploys to QA Azure App Service
- **Automated Testing**: Custom scripts for integration/E2E tests
- `PublishTestResults@2`: Publishes JUnit-formatted test results
- `SonarQubePrepare@5`: Prepares environment for SonarQube analysis
- `Maven@4`: Executes SonarQube code analysis (`sonar:sonar` goal)
- `SonarQubePublish@5`: Publishes results and enforces Quality Gate

**Environment**: Linked to Azure DevOps `QA` environment with optional manual approvals

---

### 4️⃣ **Deploy to Production Stage** (`DeployToProd`)

> **Purpose**: Final deployment of validated artifact to live Production environment

**Critical Controls:**
- 🔒 **Strict Approvals**: Manual approvals from Release Managers/Product Owners
- 🛡️ **Automated Checks**: Health checks, blackout windows, compliance validations
- 📈 **Monitoring**: Production deployment monitoring and rollback capabilities

**Key Tasks:**
- `DownloadPipelineArtifact@2`: Downloads the same validated WebApp artifact
- `AzureRmWebAppDeployment@5`: Deploys to Production Azure App Service

**Environment**: Linked to Azure DevOps `Production` environment with stringent controls

---

## 🛠️ Prerequisites

Before running this pipeline, ensure the following components are configured:

### ☁️ **Azure Subscriptions**
- Separate Azure subscriptions for **Dev**, **QA**, and **Production** environments
- Proper subscription-level permissions and governance policies

### 🌐 **Azure App Serv
