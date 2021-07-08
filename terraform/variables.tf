variable "aws_region" {
  description = "AWS region."
  default = "us-east-2"
}
variable "project_name" {
  description = "General project name."
  default = "lightweight"
}
variable "docker_image_name" {
  description = "Docker image."
  default = ""
}
variable "profile" {
  description = "Spring profile."
  default = ""
}
variable "db_host" {
  description = "MySql host."
  default = ""
}
variable "db_port" {
  description = "MySql port."
  default = 3306
}
variable "db_name" {
  description = "MySql DB."
  default = ""
}
variable "db_username" {
  description = "MySql username."
  default = ""
}
variable "db_password" {
  description = "MySql password."
  default = ""
}
variable "auth0_audience" {
  description = "Auth0 audience."
  default = ""
}
variable "auth0_issuer_uri" {
  description = "Auth0 issuer uri."
  default = ""
}
variable "project_repo_name" {
  description = "Artifact name."
  default = "be-template"
}
variable "project_repo_version" {
  description = "Project repo version"
  default = 0.1
}
variable "project_repo_details" {
  description = "Artifact information."
  default = {
    port: 3000,
    containerMemory: 2048,
    containerCpu: 10,
    taskDefMemory: 8192,
    taskDefCpu: 2048
  }
}
variable "subnets" {
  description = "Subnet address spaces."
  default = {
    "subnet1" = {
      index: 0
    },
    "subnet2" = {
      index: 1
    },
    "subnet3" = {
      index: 2
    }
  }
}

locals {
  global_resource_base_name = "${var.project_name}-${terraform.workspace}"
}