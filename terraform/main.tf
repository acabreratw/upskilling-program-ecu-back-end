module "aws-ecs-service" {
  source = "git@github.com:Lightweight-Project-Execution/iac-template.git//module-ecs-service/?ref=master"
  module_path = ".terraform/modules/aws-ecs-service/module-ecs-service/"
  log_managing = true
  aws_region  = var.aws_region
  project_name = var.project_name
  project_repo_name = var.project_repo_name
  project_repo_details = var.project_repo_details
  docker_image_name = var.docker_image_name
  subnets = var.subnets
  profile = var.profile
  db_host = var.db_host
  db_port = var.db_port
  db_name = var.db_name
  db_username = var.db_username
  db_password = var.db_password
  auth0_audience = var.auth0_audience
  auth0_issuer_uri =  var.auth0_issuer_uri
  project_repo_version = var.project_repo_version
}