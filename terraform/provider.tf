# Configure the AWS Provider
provider "aws" {
  version = "~> 2.0"
  profile = "default"
  region = var.aws_region
}